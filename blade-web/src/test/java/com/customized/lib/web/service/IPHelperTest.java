package com.customized.lib.web.service;

import com.customized.lib.web.service.ip.region.IPHelper;
import org.junit.Test;
import org.lionsoul.ip2region.xdb.Header;
import org.lionsoul.ip2region.xdb.Searcher;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Arrays;

public class IPHelperTest {

    @Test
    public void Ip2region() throws IOException {
        System.out.println("************* getCityInfoWithVectorIndex *************");
        System.out.println(IPHelper.getCityInfoWithVectorIndex("182.50.124.211"));
        System.out.println(IPHelper.getCityInfoWithVectorIndex("222.247.64.76"));
        System.out.println(IPHelper.getCityInfoWithVectorIndex("14.215.177.38"));
        System.out.println(IPHelper.getCityInfoWithVectorIndex("114.80.221.198"));

        System.out.println("************* getCityInfoWithBuffer *************");
        System.out.println(IPHelper.getCityInfoWithBuffer("182.50.124.211"));
        System.out.println(IPHelper.getCityInfoWithBuffer("222.247.64.76"));
        System.out.println(IPHelper.getCityInfoWithBuffer("14.215.177.38"));
        System.out.println(IPHelper.getCityInfoWithBuffer("114.80.221.198"));


        System.out.println("************* getCityInfo *************");
        System.out.println(IPHelper.getCityInfo("182.50.124.211"));
        System.out.println(IPHelper.getCityInfo("222.247.64.76"));
        System.out.println(IPHelper.getCityInfo("14.215.177.38"));
        System.out.println(IPHelper.getCityInfo("114.80.221.198"));
    }

    @Test
    public void loadFile() throws IOException {
        String dbPath = "src/main/resources/ip2region.db";
        byte[] bytes = loadContentFromFile(dbPath);
        byte[] headerBytes = Arrays.copyOfRange(bytes, 0, 256);
        Header header = new Header(headerBytes);
        System.out.println(header);
        System.out.println(Searcher.loadHeaderFromFile(dbPath));

        for (int i = 0; i < 256; i++) {
            for (int j = 0; j < 256; j++) {
                int beginIndex = 256 * (i + 1);
                byte[] vector0 = Arrays.copyOfRange(bytes, beginIndex, 8 + beginIndex);
                System.out.println(Searcher.getInt(vector0, 0) + " - " + Searcher.getInt(vector0, 4));
                System.out.println();
            }
        }
    }

    public static byte[] loadContent(RandomAccessFile handle) throws IOException {
        handle.seek(0);
        final byte[] buff = new byte[(int) handle.length()];
        int rLen = handle.read(buff);
        if (rLen != buff.length) {
            throw new IOException("incomplete read: read bytes should be " + buff.length);
        }

        return buff;
    }

    public static byte[] loadContentFromFile(String dbPath) throws IOException {
        final RandomAccessFile handle = new RandomAccessFile(dbPath, "r");
        final byte[] content = loadContent(handle);
        handle.close();
        return content;
    }

}
