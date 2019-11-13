package com.demon.baseframe.helper;

import android.util.Log;

import com.demon.baseframe.util.FileUtil;

/**
 * @author DeMon
 * @date 2019/3/15
 * @email 757454343@qq.com
 * @description
 */
public class SysLogHelper {
    public static final String SYS_LOG = "SysLog";

    public static void wtf(String tag, String msg) {
        Log.i(tag, msg);
        FileUtil.writeToFile(SYS_LOG, tag, msg);
    }

    public static void wtf(String tag, String msg, Throwable e) {
        Log.i(tag, msg, e);
        StringBuilder sb = new StringBuilder();
        sb.append(msg + "\r\n");
        sb.append(e.getMessage() + "\r\n");

        StackTraceElement[] steArr = e.getStackTrace();
        if (steArr != null && steArr.length > 0) {
            for (StackTraceElement ste : steArr) {
                sb.append(ste.toString() + "\r\n");
            }
        }

        FileUtil.writeToFile(SYS_LOG, tag, sb.toString());
    }
}
