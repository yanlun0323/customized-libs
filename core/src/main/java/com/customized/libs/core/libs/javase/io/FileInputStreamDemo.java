package com.customized.libs.core.libs.javase.io;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class FileInputStreamDemo {

    public static void main(String[] args) throws IOException {
        File file = new File("./abc.txt");
        FileOutputStream fos = new FileOutputStream(file);
        fos.write("t".getBytes());
        fos.flush();
        fos.close();
    }
}
