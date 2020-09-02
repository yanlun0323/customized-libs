package com.customized.multiple.versions.adapter.util;

import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;

/**
 * @author yan
 */
public class VersionsUtil {

    public static Double convertVersion(String version) {
        Double sum = 0D;
        if (StringUtils.isNotBlank(version)) {
            String[] components = version.split("\\.");

            for (int i = 0; i < components.length; i++) {
                sum += Integer.valueOf(components[i]) * getDisplacementMultiple(i);
            }
        }
        return sum;
    }

    public static Double convertVersionV2(String version) {
        return Double.valueOf(keepFirstPointChar(version));
    }

    public static Integer convertVersion(Integer version) {
        return version;
    }

    public static String convertVersionWithExpression(String expression) {
        return keepFirstPointChar(expression);
    }

    private static String keepFirstPointChar(String data) {
        StringBuilder target = new StringBuilder();

        if (StringUtils.isNotBlank(data)) {
            String[] components = data.split("\\.");

            target.append(components[0]).append(".");
            for (int i = 1; i < components.length; i++) {
                target.append(components[i]);
            }
        }

        return StringUtils.removeEnd(target.toString(), ".");
    }

    private static Double getDisplacementMultiple(Integer size) {
        BigDecimal target = BigDecimal.ONE;
        for (int i = 0; i < size; i++) {
            target = target.multiply(BigDecimal.valueOf(0.1));
        }
        return target.doubleValue();
    }

    public static void main(String[] args) {
        System.out.println(convertVersion("1.5.3"));
        System.out.println(convertVersionV2("1.5.3"));
        System.out.println(convertVersionWithExpression("version>=1.5.3"));
    }
}
