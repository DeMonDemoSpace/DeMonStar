package com.demon.baseutil.des;

public class DESUtil {

    private static String DEFAULT_ENCODING = "GB2312";
    private static String DEFAULT_KEY = "des__key";

    public static void initKey(String key) {
        DEFAULT_KEY = key;
    }

    public static void initEncoding(String code) {
        DEFAULT_ENCODING = code;
    }

    public static String decoding(String msg) {
        return decoding(DEFAULT_KEY, msg, DEFAULT_ENCODING);
    }

    public static String decoding(String msg, String key) {
        return decoding(key, msg, DEFAULT_ENCODING);
    }

    public static String encoding(String msg) {
        return encoding(DEFAULT_KEY, msg, DEFAULT_ENCODING);
    }

    public static String encoding(String msg, String key) {
        return encoding(key, msg, DEFAULT_ENCODING);
    }

    public static String decoding(String key, String encodingMsg, String decodingCharset) {
        String _key = key == null ? DEFAULT_KEY : key;
        String charset = decodingCharset == null ? DEFAULT_ENCODING : decodingCharset;
        byte[] result = null;
        try {
            DESHelper des = new DESHelper(_key, true);
            result = des.decrypt(Base64Util.decode(encodingMsg.toCharArray()));
            return new String(result, charset);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String encoding(String key, String encodingMsg, String decodingCharset) {
        String _key = key == null ? DEFAULT_KEY : key;
        String charset = decodingCharset == null ? DEFAULT_ENCODING : decodingCharset;
        char[] result = null;
        try {
            DESHelper des = new DESHelper(_key, false);
            result = Base64Util.encode(des.decrypt(encodingMsg.getBytes(charset)));
            return new String(result);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

} 
