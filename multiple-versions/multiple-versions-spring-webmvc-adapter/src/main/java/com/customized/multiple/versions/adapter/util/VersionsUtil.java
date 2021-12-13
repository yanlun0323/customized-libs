package com.customized.multiple.versions.adapter.util;

import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.util.regex.Pattern;

/**
 * @author yan
 */
public class VersionsUtil {

    private static final Pattern CHARS = Pattern.compile("[a-zA-Z]");

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

    public static Double fastConvertVersion(String version) {
        return Double.valueOf(keepFirstPointChar4Numeric(version));
    }

    public static Integer convertVersion(Integer version) {
        return version;
    }

    public static String convertVersionWithExpression(String expression) {
        return keepFirstPointChar4Alphanumeric(expression);
    }

    private static String keepFirstPointChar4Numeric(String data) {
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

    private static String keepFirstPointChar4Alphanumeric(String data) {
        StringBuilder target = new StringBuilder();

        if (StringUtils.isNotBlank(data)) {
            String[] components = data.split("\\.");

            for (String component : components) {
                if (CHARS.matcher(component).find()) {
                    target.append(component).append(".");
                } else {
                    target.append(component);
                }
            }
        }

        String _target = StringUtils.remove(target.toString(), " ");
        return StringUtils.removeEnd(_target, ".");
    }

    private static Double getDisplacementMultiple(Integer size) {
        BigDecimal target = BigDecimal.ONE;
        for (int i = 0; i < size; i++) {
            target = target.multiply(BigDecimal.valueOf(0.1));
        }
        return target.doubleValue();
    }

    public static void main(String[] args) {
        System.out.println(convertVersion("1.0.3"));
        System.out.println(convertVersion("1.0.11"));
        System.out.println(fastConvertVersion("1.0.3"));
        System.out.println(fastConvertVersion("1.0.11"));
        System.out.println(convertVersionWithExpression("version>=1.5.3 && version<=1.8.2"));
        System.out.println(convertVersionWithExpression("version>=1.0.3 && version<=1.0.11"));
    }
}
