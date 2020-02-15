package com.customized.libs.utils.logger;

import io.netty.util.concurrent.FastThreadLocal;
import org.apache.logging.log4j.core.Layout;
import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.LoggerContext;
import org.apache.logging.log4j.core.appender.ConfigurationFactoryData;
import org.apache.logging.log4j.core.appender.ManagerFactory;
import org.apache.logging.log4j.core.appender.rolling.*;
import org.apache.logging.log4j.core.appender.rolling.action.AbstractAction;
import org.apache.logging.log4j.core.appender.rolling.action.Action;
import org.apache.logging.log4j.core.config.Configuration;
import org.apache.logging.log4j.core.util.Constants;
import org.apache.logging.log4j.core.util.FileUtils;
import org.apache.logging.log4j.core.util.Log4jThreadFactory;
import org.springframework.util.StringUtils;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.file.Path;
import java.util.Collection;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @author yan
 */
public class CustomizedRollingFileManager extends RollingFileManager {

    private static RollingFileManagerFactory factory = new RollingFileManagerFactory();

    private final FastThreadLocal<OutputStream> STREAM_THREAD_LOCAL = new FastThreadLocal<>();
    private final FastThreadLocal<String> LOG_FILE_NAMES = new FastThreadLocal<>();

    private final Semaphore semaphore = new Semaphore(1);

    private volatile TriggeringPolicy triggeringPolicy;
    private volatile RolloverStrategy rolloverStrategy;

    private final Log4jThreadFactory threadFactory = Log4jThreadFactory.createThreadFactory("CustomziedRollingFileManager");

    /* This executor pool will create a new Thread for every work async action to be performed. Using it allows
       us to make sure all the Threads are completed when the Manager is stopped. */
    private final ExecutorService asyncExecutor = new ThreadPoolExecutor(0, Integer.MAX_VALUE, 0, TimeUnit.MILLISECONDS,
            new EmptyQueue(), threadFactory);

    private long initialTime;

    private static AtomicLong SEQUENCE = new AtomicLong(System.currentTimeMillis() + 2000000);

    protected CustomizedRollingFileManager(LoggerContext loggerContext, String fileName, String pattern, OutputStream os
            , boolean append, boolean createOnDemand, long size, long time, TriggeringPolicy triggeringPolicy
            , RolloverStrategy rolloverStrategy, String advertiseURI, Layout<? extends Serializable> layout
            , String filePermissions, String fileOwner, String fileGroup, boolean writeHeader, ByteBuffer buffer) {
        super(loggerContext, fileName, pattern, os, append
                , createOnDemand, size, time, triggeringPolicy
                , rolloverStrategy, advertiseURI, layout
                , filePermissions, fileOwner, fileGroup, writeHeader, buffer);
        this.initialTime = time;
        this.triggeringPolicy = triggeringPolicy;
        this.rolloverStrategy = rolloverStrategy;
    }

    @Override
    protected OutputStream createOutputStream() throws IOException {
        return super.createOutputStream();
    }

    @Override
    public synchronized void checkRollover(final LogEvent event) {
        rollover();
    }

    @Override
    public synchronized void rollover() {
        if (!hasOutputStream()) {
            return;
        }
        if (rollover(rolloverStrategy)) {
            try {
                size = 0;
                initialTime = System.currentTimeMillis();
                createFileAfterRollover();
            } catch (final IOException e) {
                logError("Failed to create file after rollover", e);
            }
        }
    }

    private boolean rollover(final RolloverStrategy strategy) {

        boolean releaseRequired = false;
        try {
            // Block until the asynchronous operation is completed.
            semaphore.acquire();
            releaseRequired = true;
        } catch (final InterruptedException e) {
            logError("Thread interrupted while attempting to check rollover", e);
            return false;
        }

        boolean success = true;

        try {
            final RolloverDescription descriptor = strategy.rollover(this);
            if (descriptor != null) {
                writeFooter();
                closeOutputStream();
                if (descriptor.getSynchronous() != null) {
                    LOGGER.debug("CustomizedRollingFileManager executing synchronous {}", descriptor.getSynchronous());
                    try {
                        success = descriptor.getSynchronous().execute();
                    } catch (final Exception ex) {
                        success = false;
                        logError("Caught error in synchronous task", ex);
                    }
                }

                if (success && descriptor.getAsynchronous() != null) {
                    LOGGER.debug("CustomizedRollingFileManager executing async {}", descriptor.getAsynchronous());
                    asyncExecutor.execute(new AsyncAction(descriptor.getAsynchronous(), this));
                    releaseRequired = false;
                }
                return true;
            }
            return false;
        } finally {
            if (releaseRequired) {
                semaphore.release();
            }
        }

    }

    @Override
    public long getFileTime() {
        return this.initialTime;
    }

    /**
     * Returns the name of the File being managed.
     *
     * @return The name of the File being managed.
     */
    @Override
    public String getFileName() {
        String theFileName = LOG_FILE_NAMES.get();
        if (StringUtils.isEmpty(theFileName)) {
            theFileName = String.format("%s-%s.log", org.apache.commons.lang3.StringUtils.
                    removeEnd(super.getName(), ".log"), SEQUENCE.incrementAndGet());
            LOG_FILE_NAMES.set(theFileName);
        }
        return theFileName;
    }

    @Override
    public boolean hasOutputStream() {
        return STREAM_THREAD_LOCAL.get() != null;
    }

    @Override
    protected OutputStream getOutputStream() throws IOException {
        if (STREAM_THREAD_LOCAL.get() == null) {
            STREAM_THREAD_LOCAL.set(createOutputStream());
        }
        return STREAM_THREAD_LOCAL.get();
    }

    @Override
    protected void setOutputStream(final OutputStream os) {
        final byte[] header = layout.getHeader();
        if (header != null) {
            try {
                os.write(header, 0, header.length);
                this.STREAM_THREAD_LOCAL.set(os); // only update field if os.write() succeeded
            } catch (final IOException ioe) {
                logError("Unable to write header", ioe);
            }
        } else {
            this.STREAM_THREAD_LOCAL.set(os);
        }
    }

    @Override
    public void updateData(final Object data) {
        final CustomizedRollingFileManager.FactoryData factoryData =
                (CustomizedRollingFileManager.FactoryData) data;
        setRolloverStrategy(factoryData.getRolloverStrategy());
        setTriggeringPolicy(factoryData.getTriggeringPolicy());
        setPatternProcessor(new PatternProcessor(factoryData.getPattern(), getPatternProcessor()));
    }

    /**
     * Returns a RollingFileManager.
     *
     * @param fileName        The file name.
     * @param pattern         The pattern for rolling file.
     * @param append          true if the file should be appended to.
     * @param bufferedIO      true if data should be buffered.
     * @param policy          The TriggeringPolicy.
     * @param strategy        The RolloverStrategy.
     * @param advertiseURI    the URI to use when advertising the file
     * @param layout          The Layout.
     * @param bufferSize      buffer size to use if bufferedIO is true
     * @param immediateFlush  flush on every write or not
     * @param createOnDemand  true if you want to lazy-create the file (a.k.a. on-demand.)
     * @param filePermissions File permissions
     * @param fileOwner       File owner
     * @param fileGroup       File group
     * @param configuration   The configuration.
     * @return A RollingFileManager.
     */
    public static CustomizedRollingFileManager getFileManager(final String fileName, final String pattern, final boolean append,
                                                              final boolean bufferedIO, final TriggeringPolicy policy, final RolloverStrategy strategy,
                                                              final String advertiseURI, final Layout<? extends Serializable> layout, final int bufferSize,
                                                              final boolean immediateFlush, final boolean createOnDemand,
                                                              final String filePermissions, final String fileOwner, final String fileGroup,
                                                              final Configuration configuration) {

        if (strategy instanceof DirectWriteRolloverStrategy && fileName != null) {
            LOGGER.error("The fileName attribute must not be specified with the DirectWriteRolloverStrategy");
            return null;
        }
        final String name = fileName == null ? pattern : fileName;
        return narrow(CustomizedRollingFileManager.class, getManager(name, new FactoryData(fileName, pattern, append,
                bufferedIO, policy, strategy, advertiseURI, layout, bufferSize, immediateFlush, createOnDemand,
                filePermissions, fileOwner, fileGroup, configuration), factory));
    }

    /**
     * Factory data.
     */
    private static class FactoryData extends ConfigurationFactoryData {
        private final String fileName;
        private final String pattern;
        private final boolean append;
        private final boolean bufferedIO;
        private final int bufferSize;
        private final boolean immediateFlush;
        private final boolean createOnDemand;
        private final TriggeringPolicy policy;
        private final RolloverStrategy strategy;
        private final String advertiseURI;
        private final Layout<? extends Serializable> layout;
        private final String filePermissions;
        private final String fileOwner;
        private final String fileGroup;

        /**
         * Creates the data for the factory.
         *
         * @param pattern         The pattern.
         * @param append          The append flag.
         * @param bufferedIO      The bufferedIO flag.
         * @param advertiseURI
         * @param layout          The Layout.
         * @param bufferSize      the buffer size
         * @param immediateFlush  flush on every write or not
         * @param createOnDemand  true if you want to lazy-create the file (a.k.a. on-demand.)
         * @param filePermissions File permissions
         * @param fileOwner       File owner
         * @param fileGroup       File group
         * @param configuration   The configuration
         */
        public FactoryData(final String fileName, final String pattern, final boolean append, final boolean bufferedIO,
                           final TriggeringPolicy policy, final RolloverStrategy strategy, final String advertiseURI,
                           final Layout<? extends Serializable> layout, final int bufferSize, final boolean immediateFlush,
                           final boolean createOnDemand, final String filePermissions, final String fileOwner, final String fileGroup,
                           final Configuration configuration) {
            super(configuration);
            this.fileName = fileName;
            this.pattern = pattern;
            this.append = append;
            this.bufferedIO = bufferedIO;
            this.bufferSize = bufferSize;
            this.policy = policy;
            this.strategy = strategy;
            this.advertiseURI = advertiseURI;
            this.layout = layout;
            this.immediateFlush = immediateFlush;
            this.createOnDemand = createOnDemand;
            this.filePermissions = filePermissions;
            this.fileOwner = fileOwner;
            this.fileGroup = fileGroup;
        }

        public TriggeringPolicy getTriggeringPolicy() {
            return this.policy;
        }

        public RolloverStrategy getRolloverStrategy() {
            return this.strategy;
        }

        public String getPattern() {
            return pattern;
        }

        @Override
        public String toString() {
            final StringBuilder builder = new StringBuilder();
            builder.append(super.toString());
            builder.append("[pattern=");
            builder.append(pattern);
            builder.append(", append=");
            builder.append(append);
            builder.append(", bufferedIO=");
            builder.append(bufferedIO);
            builder.append(", bufferSize=");
            builder.append(bufferSize);
            builder.append(", policy=");
            builder.append(policy);
            builder.append(", strategy=");
            builder.append(strategy);
            builder.append(", advertiseURI=");
            builder.append(advertiseURI);
            builder.append(", layout=");
            builder.append(layout);
            builder.append(", filePermissions=");
            builder.append(filePermissions);
            builder.append(", fileOwner=");
            builder.append(fileOwner);
            builder.append("]");
            return builder.toString();
        }
    }

    /**
     * Factory to create a RollingFileManager.
     */
    private static class RollingFileManagerFactory implements ManagerFactory<CustomizedRollingFileManager, CustomizedRollingFileManager.FactoryData> {

        /**
         * Creates a RollingFileManager.
         *
         * @param name The name of the entity to manage.
         * @param data The data required to create the entity.
         * @return a RollingFileManager.
         */
        @Override
        public CustomizedRollingFileManager createManager(final String name, final CustomizedRollingFileManager.FactoryData data) {
            long size = 0;
            boolean writeHeader = !data.append;
            File file = null;
            if (data.fileName != null) {
                file = new File(data.fileName);
                // LOG4J2-1140: check writeHeader before creating the file
                writeHeader = !data.append || !file.exists();

                try {
                    FileUtils.makeParentDirs(file);
                    final boolean created = !data.createOnDemand && file.createNewFile();
                    LOGGER.trace("New file '{}' created = {}", name, created);
                } catch (final IOException ioe) {
                    LOGGER.error("Unable to create file " + name, ioe);
                    return null;
                }
                size = data.append ? file.length() : 0;
            }

            try {
                final int actualSize = data.bufferedIO ? data.bufferSize : Constants.ENCODER_BYTE_BUFFER_SIZE;
                final ByteBuffer buffer = ByteBuffer.wrap(new byte[actualSize]);
                final OutputStream os = data.createOnDemand || data.fileName == null ? null :
                        new FileOutputStream(data.fileName, data.append);
                final long time = data.createOnDemand || file == null ?
                        System.currentTimeMillis() : file.lastModified(); // LOG4J2-531 create file first so time has valid value

                final CustomizedRollingFileManager rm = new CustomizedRollingFileManager(data.getLoggerContext(), data.fileName, data.pattern, os,
                        data.append, data.createOnDemand, size, time, data.policy, data.strategy, data.advertiseURI,
                        data.layout, data.filePermissions, data.fileOwner, data.fileGroup, writeHeader, buffer);
                if (os != null && rm.isAttributeViewEnabled()) {
                    rm.defineAttributeView(file.toPath());
                }

                return rm;
            } catch (final IOException ex) {
                LOGGER.error("CustomizedRollingFileManager (" + name + ") " + ex, ex);
            }
            return null;
        }
    }

    @Override
    protected void defineAttributeView(final Path path) {
        super.defineAttributeView(path);
    }

    private static class EmptyQueue extends ArrayBlockingQueue<Runnable> {

        /**
         *
         */
        private static final long serialVersionUID = 1L;

        EmptyQueue() {
            super(1);
        }

        @Override
        public int remainingCapacity() {
            return 0;
        }

        @Override
        public boolean add(final Runnable runnable) {
            throw new IllegalStateException("Queue is full");
        }

        @Override
        public void put(final Runnable runnable) throws InterruptedException {
            /* No point in going into a permanent wait */
            throw new InterruptedException("Unable to insert into queue");
        }

        @Override
        public boolean offer(final Runnable runnable, final long timeout, final TimeUnit timeUnit) throws InterruptedException {
            Thread.sleep(timeUnit.toMillis(timeout));
            return false;
        }

        @Override
        public boolean addAll(final Collection<? extends Runnable> collection) {
            if (collection.size() > 0) {
                throw new IllegalArgumentException("Too many items in collection");
            }
            return false;
        }
    }

    /**
     * Performs actions asynchronously.
     */
    private static class AsyncAction extends AbstractAction {

        private final Action action;
        private final CustomizedRollingFileManager manager;

        /**
         * Constructor.
         *
         * @param act     The action to perform.
         * @param manager The manager.
         */
        public AsyncAction(final Action act, final CustomizedRollingFileManager manager) {
            this.action = act;
            this.manager = manager;
        }

        /**
         * Executes an action.
         *
         * @return true if action was successful.  A return value of false will cause
         * the rollover to be aborted if possible.
         * @throws java.io.IOException if IO error, a thrown exception will cause the rollover
         *                             to be aborted if possible.
         */
        @Override
        public boolean execute() throws IOException {
            try {
                return action.execute();
            } finally {
                manager.semaphore.release();
            }
        }

        /**
         * Cancels the action if not already initialized or waits till completion.
         */
        @Override
        public void close() {
            action.close();
        }

        /**
         * Determines if action has been completed.
         *
         * @return true if action is complete.
         */
        @Override
        public boolean isComplete() {
            return action.isComplete();
        }

        @Override
        public String toString() {
            final StringBuilder builder = new StringBuilder();
            builder.append(super.toString());
            builder.append("[action=");
            builder.append(action);
            builder.append(", manager=");
            builder.append(manager);
            builder.append(", isComplete()=");
            builder.append(isComplete());
            builder.append(", isInterrupted()=");
            builder.append(isInterrupted());
            builder.append("]");
            return builder.toString();
        }
    }
}
