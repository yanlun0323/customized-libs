package com.customized.libs.core.libs.utils;

import org.apache.rocketmq.common.MixAll;

import java.io.File;
import java.io.IOException;

public class RocketMQUtil {

    public static void main(String[] args) throws IOException {
        String file2String = MixAll.file2String(new File("/Users/yan/Workspace/workspace-card/customized-libs/core/target/offsets.json"));
        System.out.println(file2String);
    }
}
