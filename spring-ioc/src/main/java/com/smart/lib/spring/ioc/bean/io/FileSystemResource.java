package com.smart.lib.spring.ioc.bean.io;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * 通过指定文件路径的方式读取文件信息，这部分大家肯定还是非常熟悉的，经常会读取一些txt、excel文件输出到控制台
 *
 * @author yan
 * @version 1.0
 * @description
 * @date 2022/8/16 09:52
 */
public class FileSystemResource implements Resource {

    private String path;

    private File file;

    public FileSystemResource(String path) {
        this.file = new File(path);
        this.path = path;
    }

    public FileSystemResource(File file) {
        this.file = file;
        this.path = file.getPath();
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    @Override
    public InputStream getInputStream() throws IOException {
        return new FileInputStream(this.file);
    }
}
