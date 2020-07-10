package com.proxy.smart.core.utils;

import java.io.Closeable;
import java.io.InputStream;
import java.io.OutputStream;


public class StreamUtils {

    /**
     * 写出流
     *
     * @param outputStream 输出流
     * @param sendBytes    发送数据
     * @throws Exception
     */
    public static void writeStreamOnce(OutputStream outputStream, byte[] sendBytes) throws Exception {
        writeStreamOnceIfClose(outputStream, sendBytes, true);
    }

    /**
     * 写出流
     *
     * @param outputStream 输出流
     * @param sendBytes    发送数据
     * @param closeStream  是否关闭流
     * @throws Exception
     */
    public static void writeStreamOnceIfClose(OutputStream outputStream,
                                              byte[] sendBytes, boolean closeStream) throws Exception {
        try {
            outputStream.write(sendBytes);
            outputStream.flush();
        } catch (Exception e) {
            throw e;
        } finally {
            if (closeStream) {
                StreamUtils.closeStream(outputStream);
            }
        }
    }

    /**
     * 流进度接口
     */
    public static interface IStreamPro {

        public void hook(int len);

    }

    /**
     * 读取流然后写出流
     *
     * @param is
     * @param os
     * @throws Exception
     */
    public static void readAndWrite(InputStream is, OutputStream os) throws Exception {
        readAndWrite(is, os, null);
    }

    /**
     * 读取流然后写出流
     *
     * @param is
     * @param os
     * @throws Exception
     */
    public static void readAndWrite(InputStream is, OutputStream os, IStreamPro streamPro) throws Exception {
        try {
            byte[] fileByteBuff = new byte[1024];

            while (true) {
                int len = is.read(fileByteBuff);
                if (-1 == len) {
                    break;
                }
                os.write(fileByteBuff, 0, len);
                os.flush();

                if (null != streamPro) {
                    streamPro.hook(len);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        } finally {
            closeStream(is);
            closeStream(os);
        }
    }


    /**
     * 关闭流
     *
     * @param cs 流对象
     */
    public static void closeStream(Closeable cs) {
        if (null != cs) {
            try {
                cs.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
