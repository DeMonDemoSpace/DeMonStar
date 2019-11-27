package com.demon.baseframe.util;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

import com.demon.baseframe.BuildConfig;
import com.demon.baseframe.R;
import com.demon.baseframe.app.BaseApp;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * @author DeMon
 * @date 2018/8/14
 * @description
 */
public class LogUtil {
    /*m默认不打印Log，如果要打印，置为true*/
    private static boolean enableLog = BuildConfig.DEBUG;

    public static void e(String tag, String msg) {
        if (enableLog)
            Log.e(tag, msg);

    }

    public static void d(String tag, String msg) {
        if (enableLog)
            Log.d(tag, msg);

    }

    public static void i(String tag, String msg) {
        if (enableLog)
            Log.i(tag, msg);

    }

    public static void v(String tag, String msg) {
        if (enableLog)
            Log.v(tag, msg);

    }

    public static void w(String tag, String msg) {
        if (enableLog)
            Log.w(tag, msg);

    }

    public static void wtf(String tag, String msg) {
        if (enableLog) {
            Log.i(tag, msg);
        }
         writeToFile("SysLog", tag, msg);
    }


    public static void writeToFile(String fileName, String tag, String content) {
        try {
            String now = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA).format(new Date());
            StringBuilder sb = new StringBuilder();
            sb.append(now);
            sb.append("\n");
            sb.append(tag);
            sb.append("\n");
            sb.append(content);
            sb.append("\n");
            sb.append("\n");
            String path = getPath(BaseApp.getContext(), fileName);
            File file = new File(path + now.substring(0, 10) + ".txt");
            //构造函数中的第二个参数true表示以追加形式写文件
            FileWriter fw = new FileWriter(file.getAbsolutePath(), true);
            fw.write(sb.toString());
            fw.flush();
            fw.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String getPath(Context context, String pathName) {
        String path = null;
        try {
            path = Environment.getExternalStorageDirectory().getCanonicalPath() + "/" + context.getResources().getString(R.string.app_name) + "/" + pathName + "/";
            File file = new File(path);
            if (!file.exists()) {
                file.mkdirs();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return path;
    }

}
