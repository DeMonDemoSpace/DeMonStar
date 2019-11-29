package com.demon.baseframe.app;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.os.Bundle;

import com.demon.baseframe.helper.ActivityHelper;

public class BaseApp extends Application {
    public static Context mContext;
    public static int SCREEN_MODE;
    private static BaseApp application;

    @Override
    public void onCreate() {
        super.onCreate();
        application = this;
        mContext = getApplicationContext();
        SCREEN_MODE = ActivityInfo.SCREEN_ORIENTATION_USER;
        registerActivityLifecycleCallbacks(new ActivityLifecycleCallbacks() {
            @Override
            public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
                ActivityHelper.pushActivity(activity);
            }

            @Override
            public void onActivityStarted(Activity activity) {

            }

            @Override
            public void onActivityResumed(Activity activity) {
            }

            @Override
            public void onActivityPaused(Activity activity) {

            }

            @Override
            public void onActivityStopped(Activity activity) {
            }

            @Override
            public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

            }

            @Override
            public void onActivityDestroyed(Activity activity) {
                ActivityHelper.popActivity(activity);
            }
        });
    }

    public static Context getContext() {
        return mContext;
    }

    public static BaseApp getApplication() {
        return application;
    }

}
