package com.customized.libs.core.libs.generator;

import com.alibaba.dubbo.common.utils.StringUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.asm.ClassReader;
import org.springframework.beans.factory.BeanDefinitionStoreException;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.NestedIOException;
import org.springframework.core.env.Environment;
import org.springframework.core.env.StandardEnvironment;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.core.type.classreading.AnnotationMetadataReadingVisitor;
import org.springframework.util.ClassUtils;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.*;

/**
 * CREATE TABLE `sms`.`T_ACCOUNT_RECORD`  (
 * `id` int(20) NOT NULL AUTO_INCREMENT,
 * PRIMARY KEY (`id`)
 * );
 *
 * @author yan
 */
public class JavaClass2SQL {

    private static Logger logger = LoggerFactory.getLogger(JavaClass2SQL.class);

    private static final List<String> IGNORE_FIELDS = Arrays.asList(" ", "serialVersionUID");

    private static final String DEFAULT_RESOURCE_PATTERN = "**/*.class";

    private static final Map<String, String> JAVA_TYPE_SQL_MAPPER_TEMPLATES = new HashMap<String, String>() {

        private static final long serialVersionUID = 4018806930242104951L;

        {
            this.put("java.lang.Long", "`%s` INT(20)");
            this.put("java.lang.String", "`%s` VARCHAR(256)");
            this.put("java.lang.Integer", "`%s` INT(0)");
            this.put("java.util.Date", "`%s` DATETIME");
            this.put("java.lang.Short", "`%s` SMALLINT");
            this.put("java.lang.Byte", "`%s` TINYINT");
        }
    };


    private static final String SCAN_PACKAGES = "com.customized.libs.libs.generator.pojo";

    public static void main(String[] args) throws IOException {
        List<String> sql = scanAndGeneratorSQL(SCAN_PACKAGES, "`sms`.", "Example");
        generatorSQLScripts(sql, "./sql-scripts.sql");
    }

    public static List<String> scanAndGeneratorSQL(String basePackage, String db, String... suffixIgnore) {
        // 根据配置的包名扫描JAVA类
        String[] basePackages = org.springframework.util.StringUtils.tokenizeToStringArray(basePackage,
                ConfigurableApplicationContext.CONFIG_LOCATION_DELIMITERS);

        Class<?>[] classes = scanPackages(basePackages, suffixIgnore);
        List<String> sql = new ArrayList<>();
        for (int i = 0; i < classes.length; i++) {
            logger.warn("############################### BEGIN ###############################\r\n");
            String script = generatorSQL(db, classes[i]);
            logger.warn("SQL Script ==> \r\n\r\n" + script + "\r\n");
            logger.warn("############################### END ###############################\r\n\r\n");
            sql.add(script);
        }
        return sql;
    }

    public static void generatorSQLScripts(List<String> sql, String targetPath) throws IOException {
        IOUtils.writeLines(sql, "\r\n\r\n",
                FileUtils.openOutputStream(new File(targetPath)));
    }

    public static String generatorSQL(String prefix, Class<?> clazz) {
        String table = StringUtils.camelToSplitName(clazz.getSimpleName(), "_").toUpperCase();

        StringBuilder builder = new StringBuilder();
        builder.append("CREATE TABLE ").append(prefix).append("`").append(table).append("`  ( ");
        builder.append("\r\n");

        Field[] fields = clazz.getDeclaredFields();
        // 拼接SQL字段

        Boolean containsId = Boolean.FALSE;
        for (int i = 0; i < fields.length; i++) {
            String name = fields[i].getName();
            String type = fields[i].getType().getTypeName();
            if (!IGNORE_FIELDS.contains(name)) {
                // type convert
                String template = JAVA_TYPE_SQL_MAPPER_TEMPLATES.get(type);

                logger.debug(name + " ==> " + type);

                if ("ID".equals(name.toUpperCase())) containsId = Boolean.TRUE;
                if ("ID".equals(name.toUpperCase())) {
                    builder.append(String.format(template, "ID")).append(" NOT NULL AUTO_INCREMENT, ");
                } else {
                    builder.append(String.format(template,
                            StringUtils.camelToSplitName(name, "_").toUpperCase())).append(", ");
                }
                builder.append("\r\n");
            }
        }

        // SQL TAIL
        if (containsId) {
            builder.append("PRIMARY KEY (`ID`) \r\n);");
            return builder.toString();
        } else {
            String sql = org.apache.commons.lang3.StringUtils.removeEnd(builder.toString(), ", \r\n");
            return sql + "\r\n);";
        }
    }

    /**
     * @param suffixIgnore 后缀忽略
     * @param basePackages 基础包名
     * @return
     */
    private static Class<?>[] scanPackages(String[] basePackages, String... suffixIgnore) {
        List<Class<?>> classes = new ArrayList<>();
        // 递归读取basePackages包下的所有类
        for (String basePackage : basePackages) {
            Set<Class> candidates = scanCandidateComponents(basePackage);
            candidates.iterator().forEachRemaining(c -> {
                Boolean skip = Boolean.FALSE;
                for (int i = 0; i < suffixIgnore.length; i++) {
                    if (c.getTypeName().contains(suffixIgnore[i])) {
                        skip = Boolean.TRUE;
                    }
                }
                if (!skip) classes.add(c);
            });
        }
        return classes.toArray(new Class[classes.size()]);
    }

    private static Set<Class> scanCandidateComponents(String basePackage) {
        Set<Class> candidates = new LinkedHashSet<>();
        try {
            String packageSearchPath = ResourcePatternResolver.CLASSPATH_ALL_URL_PREFIX +
                    resolveBasePackage(basePackage) + '/' + DEFAULT_RESOURCE_PATTERN;
            Resource[] resources = getResourcePatternResolver().getResources(packageSearchPath);
            boolean traceEnabled = logger.isTraceEnabled();
            boolean debugEnabled = logger.isDebugEnabled();
            for (Resource resource : resources) {
                if (traceEnabled) {
                    logger.trace("Scanning " + resource);
                }
                if (resource.isReadable()) {
                    try {
                        if (debugEnabled) {
                            logger.debug("Identified candidate component class: " + resource);
                        }
                        ClassReader classReader;
                        try (InputStream is = new BufferedInputStream(resource.getInputStream())) {
                            classReader = new ClassReader(is);
                        } catch (IllegalArgumentException ex) {
                            throw new NestedIOException("ASM ClassReader failed to parse class file - " +
                                    "probably due to a new Java class file version that isn't supported yet: " + resource, ex);
                        }
                        AnnotationMetadataReadingVisitor visitor =
                                new AnnotationMetadataReadingVisitor(new DefaultResourceLoader().getClassLoader());
                        classReader.accept(visitor, ClassReader.SKIP_DEBUG);
                        candidates.add(Class.forName(ClassUtils.convertResourcePathToClassName(classReader.getClassName())));
                    } catch (Throwable ex) {
                        throw new BeanDefinitionStoreException(
                                "Failed to read candidate component class: " + resource, ex);
                    }
                } else {
                    if (traceEnabled) {
                        logger.trace("Ignored because not readable: " + resource);
                    }
                }
            }
        } catch (IOException ex) {
            throw new BeanDefinitionStoreException("I/O failure during classpath scanning", ex);
        }
        return candidates;
    }

    /**
     * Resolve the specified base package into a pattern specification for
     * the package search path.
     * <p>The default implementation resolves placeholders against system properties,
     * and converts a "."-based package path to a "/"-based resource path.
     *
     * @param basePackage the base package as specified by the user
     * @return the pattern specification to be used for package searching
     */
    private static String resolveBasePackage(String basePackage) {
        return ClassUtils.convertClassNameToResourcePath(getEnvironment().resolveRequiredPlaceholders(basePackage));
    }

    private static Environment getEnvironment() {
        return new StandardEnvironment();
    }

    private static ResourcePatternResolver getResourcePatternResolver() {
        return new PathMatchingResourcePatternResolver();
    }
}
