package com.demon.baseframe.util;

import android.content.Context;
import android.os.Environment;

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
 * @date 2019/3/19
 * @email 757454343@qq.com
 * @description
 */
public class FileUtil {
    /**
     * 写入TXT，追加写入
     *
     * @param content
     */
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
