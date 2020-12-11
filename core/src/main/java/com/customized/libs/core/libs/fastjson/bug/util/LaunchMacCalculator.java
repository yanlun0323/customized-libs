package com.customized.libs.core.libs.fastjson.bug.util;

import java.io.IOException;

/**
 * @author yan
 */
public class LaunchMacCalculator {

    public void launch() throws IOException, InterruptedException {
        ProcessBuilder pb = new ProcessBuilder("open", "/Applications/Calculator.app");
        Process p = pb.start();
        int exitCode = p.waitFor();
        System.out.println(exitCode);
        // Runtime.getRuntime().exec("/Applications/Calculator.app");
    }
}
