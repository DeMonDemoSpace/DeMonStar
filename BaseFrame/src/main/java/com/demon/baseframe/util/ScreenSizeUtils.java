package com.demon.baseframe.util;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.WindowManager;

import com.demon.baseframe.app.BaseApp;

import java.lang.reflect.Field;


/**
 * create by Weavey
 * on date 2016-01-06
 * TODO 单例模式 获取屏幕宽高的帮助类
 */

public class ScreenSizeUtils {

    private WindowManager manager;
    private DisplayMetrics dm;
    private static ScreenSizeUtils instance = null;
    private int screenWidth, screenHeigth;

    public static ScreenSizeUtils getInstance() {

        if (instance == null) {
            synchronized (ScreenSizeUtils.class) {

                if (instance == null)
                    instance = new ScreenSizeUtils();

            }
        }
        return instance;
    }

    private ScreenSizeUtils() {

        manager = (WindowManager) BaseApp.getApplication()
                .getSystemService(Context.WINDOW_SERVICE);
        dm = new DisplayMetrics();
        manager.getDefaultDisplay().getMetrics(dm);

        screenWidth = dm.widthPixels;// 获取屏幕分辨率宽度
        screenHeigth = dm.heightPixels;
    }

    //获取屏幕宽度
    public int getScreenWidth() {

        return screenWidth;
    }

    //获取屏幕高度
    public int getScreenHeight() {

        return screenHeigth;
    }

    /**
     * 获取状态栏高度
     */
    public int getStatusBarHeight(Context context) {
        try {
            Class<?> c = Class.forName("com.android.internal.R$dimen");
            Object obj = c.newInstance();
            Field field = c.getField("status_bar_height");
            int x = Integer.parseInt(field.get(obj).toString());
            return context.getResources().getDimensionPixelSize(x);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return 0;
    }


}
