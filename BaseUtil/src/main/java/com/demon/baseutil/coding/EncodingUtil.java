package com.demon.baseutil.coding;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * Author: DeMon.
 * Date: 2018/1/30.
 * Work:
 */
public class EncodingUtil {
    /**
     * utf-8中文转码，主要解决链接含中文无法下载的问题
     *
     * @param url
     * @return
     */
    public static String ChineseEncoded(String url) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < url.length(); i++) {
            char a = url.charAt(i);
            if (a > 127) {//将中文UTF-8编码
                try {
                    sb.append(URLEncoder.encode(String.valueOf(a), "utf-8"));
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            } else {
                sb.append(String.valueOf(a));
            }
        }
        return sb.toString();
    }
}
