package com.demon.baseutil.file;

import android.content.Context;
import android.content.res.AssetManager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author DeMon
 * @date 2018/8/6
 * @description
 */
public class AssetsUtil {
    /**
     * 读取Assets文件内容
     *
     * @param context
     * @param fileName
     * @return
     */
    public static String getAssetsString(Context context, String fileName) {
        //将json数据变成字符串
        StringBuilder stringBuilder = new StringBuilder();
        try {
            //获取assets资源管理器
            AssetManager assetManager = context.getAssets();
            //通过管理器打开文件并读取
            BufferedReader bf = new BufferedReader(new InputStreamReader(assetManager.open(fileName)));
            String line;
            while ((line = bf.readLine()) != null) {
                stringBuilder.append(line);
            }
            bf.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stringBuilder.toString();
    }

    /**
     * 获取Assets文件的文件/文件夹
     *
     * @param path
     * @return
     */
    public static List<String> getFilesFromAssets(Context context, String path) {
        List<String> list = new ArrayList<>();
        AssetManager assetManager = context.getAssets();
        try {
            String[] files = assetManager.list(path);
            list.addAll(Arrays.asList(files));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return list;
    }
}
