package com.demon.baseutil.des;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;

/**
 * MD5加密，方法默认为32位加密
 * 16位加密 = 32位加密.substring(8, 24)
 */
public class MD5Util {
    public static char hexDigits[] = {
            '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'
    };

    /**
     * MD5小写
     */
    public static String MD5Lower(String str) {
        MessageDigest md5 = null;
        try {
            md5 = MessageDigest.getInstance("MD5");
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }

        byte[] md5Bytes = md5.digest(str.getBytes(StandardCharsets.UTF_8));
        StringBuffer hexValue = new StringBuffer();
        for (int i = 0; i < md5Bytes.length; i++) {
            int val = (md5Bytes[i]) & 0xff;
            if (val < 16) {
                hexValue.append("0");
            }
            hexValue.append(Integer.toHexString(val));
        }
        return hexValue.toString();
    }

    /**
     * MD5大写
     */
    public static String MD5Upper(String key) {
        try {
            // 获得MD5摘要算法的 MessageDigest 对象
            MessageDigest mdInst = MessageDigest.getInstance("MD5");
            // 获得密文
            byte[] md = mdInst.digest(key.getBytes(StandardCharsets.UTF_8));
            // 把密文转换成十六进制的字符串形式
            int j = md.length;
            char str[] = new char[j * 2];
            int k = 0;
            for (int i = 0; i < j; i++) {
                byte byte0 = md[i];
                str[k++] = hexDigits[byte0 >>> 4 & 0xf];
                str[k++] = hexDigits[byte0 & 0xf];
            }
            return new String(str);
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    /**
     * MD5加密为bytes
     */
    public static byte[] MD5Bytes(String str) {
        byte[] md5Bytes;
        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            byte[] byteArray = str.getBytes(StandardCharsets.UTF_8);
            md5Bytes = md5.digest(byteArray);
        } catch (Exception e) {
            e.printStackTrace();
            return new byte[0];
        }
        return md5Bytes;
    }

    /**
     * MD5加密对象
     * 对象必须为可序列化对象implements Serializable
     */
    public static String MD5EncodeObjectLower(Object object) {
        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            byte[] md5Bytes = md5.digest(ByteUtil.objectToBytes(object));
            StringBuffer hexValue = new StringBuffer();
            for (int i = 0; i < md5Bytes.length; i++) {
                int val = (md5Bytes[i]) & 0xff;
                if (val < 16) {
                    hexValue.append("0");
                }
                hexValue.append(Integer.toHexString(val));
            }
            return hexValue.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    public static String MD5EncodeObjectUpper(Object object) {
        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            byte[] md = md5.digest(ByteUtil.objectToBytes(object));
            // 把密文转换成十六进制的字符串形式
            int j = md.length;
            char str[] = new char[j * 2];
            int k = 0;
            for (int i = 0; i < j; i++) {
                byte byte0 = md[i];
                str[k++] = hexDigits[byte0 >>> 4 & 0xf];
                str[k++] = hexDigits[byte0 & 0xf];
            }
            return new String(str);
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

}
