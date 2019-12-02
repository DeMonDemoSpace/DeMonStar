package com.demon.baseframe.app;

import android.content.pm.ActivityInfo;

import com.demon.baseframe.R;

/**
 * @author liuhui
 * @date 2019/12/2
 * @email 757454343@qq.com
 * @description 框架内容单例配置
 */
public class AppConfig {
    private static AppConfig instance ;
    private AppConfig(){

    }
    public static AppConfig getInstance(){
        if (instance == null){
            instance=  new AppConfig();
        }
        return instance;
    }


    private int screenMode  = ActivityInfo.SCREEN_ORIENTATION_USER; //屏幕模式，直接使用ActivityInfo进行设置即可

    private int toolbarColor = R.color.colorPrimary; //标题栏颜色
    private int statusBarColor =  R.color.colorPrimary; //状态栏颜色
    private boolean isStatusBarDark = false; //


    public int getScreenMode() {
        return screenMode;
    }

    public void setScreenMode(int screenMode) {
        this.screenMode = screenMode;
    }

    public int getToolbarColor() {
        return toolbarColor;
    }

    public void setToolbarColor(int toolbarColor) {
        this.toolbarColor = toolbarColor;
    }

    public int getStatusBarColor() {
        return statusBarColor;
    }

    public void setStatusBarColor(int statusBar) {
        this.statusBarColor = statusBar;
    }

    public boolean isStatusBarDark() {
        return isStatusBarDark;
    }

    public void setStatusBarDark(boolean statusBarDark) {
        isStatusBarDark = statusBarDark;
    }
}
