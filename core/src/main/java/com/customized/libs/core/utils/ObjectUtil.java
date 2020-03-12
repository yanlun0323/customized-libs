package com.customized.libs.core.utils;

/**
 * Created by zhaoyang on 23/05/2017.
 */
public class ObjectUtil {

    public static boolean isBasicType(Object obj) {
        if (obj == null) return false;
        if (obj instanceof String) {
            return true;
        } else if (obj instanceof Character) {
            return true;
        } else if (obj instanceof Byte) {
            return true;
        } else if (obj instanceof Short) {
            return true;
        } else if (obj instanceof Boolean) {
            return true;
        } else if (obj instanceof Integer) {
            return true;
        } else if (obj instanceof Long) {
            return true;
        } else if (obj instanceof Float) {
            return true;
        } else if (obj instanceof Double) {
            return true;
        }
        return false;
    }

    public static boolean isBasicType(Class clazz) {
        if (clazz == null) return false;
        if (clazz.equals(String.class)) {
            return true;
        } else if (clazz.equals(Character.class)) {
            return true;
        } else if (clazz.equals(Number.class)) {
            return true;
        } else if (clazz.equals(Byte.class)) {
            return true;
        } else if (clazz.equals(Short.class)) {
            return true;
        } else if (clazz.equals(Boolean.class)) {
            return true;
        } else if (clazz.equals(Integer.class)) {
            return true;
        } else if (clazz.equals(Long.class)) {
            return true;
        } else if (clazz.equals(Float.class)) {
            return true;
        } else if (clazz.equals(Double.class)) {
            return true;
        }
        return false;
    }

    public static <T> T objToBasicType(Object obj, Class<T> clazz) {
        if (obj instanceof String) {
            if (clazz.equals(String.class)) {
                return (T) obj;
            }
        } else if (obj instanceof Character) {
            if (clazz.equals(Character.class)) {
                return (T) obj;
            } else if (clazz.equals(String.class)) {
                return (T) obj.toString();
            }
        } else if (obj instanceof Boolean) {
            if (clazz.equals(Boolean.class)) {
                return (T) obj;
            }
        } else if (obj instanceof Number) {
            if (Number.class.isAssignableFrom(clazz)) {
                Object numberObj = null;
                if (clazz.equals(Number.class)) {
                    numberObj = obj;
                } else if (clazz.equals(Byte.class)) {
                    numberObj = ((Number) obj).byteValue();
                } else if (clazz.equals(Short.class)) {
                    numberObj = ((Number) obj).shortValue();
                } else if (clazz.equals(Integer.class)) {
                    numberObj = ((Number) obj).intValue();
                } else if (clazz.equals(Long.class)) {
                    numberObj = ((Number) obj).longValue();
                } else if (clazz.equals(Float.class)) {
                    numberObj = ((Number) obj).floatValue();
                } else if (clazz.equals(Double.class)) {
                    numberObj = ((Number) obj).doubleValue();
                }
                if (numberObj != null) return (T) numberObj;
            }
        } else {
            throw new IllegalArgumentException("Not Supported Type ==> " + clazz.getTypeName());
        }
        return null;
    }
}
