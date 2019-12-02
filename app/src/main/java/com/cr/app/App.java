package com.cr.app;

import android.content.pm.ActivityInfo;

import com.demon.baseframe.app.AppConfig;
import com.demon.baseframe.app.BaseApp;

/**
 * @author liuhui
 * @date 2019/11/29
 * @email 757454343@qq.com
 * @description
 */
public class App extends BaseApp {

    @Override
    public void onCreate() {
        super.onCreate();

        AppConfig.getInstance().setScreenMode(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        AppConfig.getInstance().setToolbarColor(R.color.colorAccent);
        AppConfig.getInstance().setStatusBarDark(false);
    }
}
