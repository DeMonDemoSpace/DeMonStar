package com.demon.baseframe.helper;

import android.app.Activity;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * @date 2018/1/19/
 * @decription Activity管理类
 * 在App类中注册生命周期回调，对方法pushActivity和popActivity进行调用
 */

public class ActivityHelper {

    private static final String TAG = "ActivityManager";

    public static List<Activity> sActivities = Collections.synchronizedList(new LinkedList<Activity>());

    /**
     * 将Activity添加到栈中
     * 应避免直接调用此方法，在Activity生命周期的onCreate()方法中会调用此方法
     *
     * @param activity 要添加的Activity
     */
    public static void pushActivity(Activity activity) {
        sActivities.add(activity);
    }

    /**
     * 移除一个Activity
     * 应避免直接调用此方法，关闭Activity应调用Activity的finish()
     * 在Activity生命周期的onDestroy()方法中会调用此方法
     *
     * @param activity 要移除的Activity
     */
    public static void popActivity(Activity activity) {
        if (!sActivities.isEmpty() && activity != null) {
            sActivities.remove(activity);
        }
    }


    /**
     * 获取当前的Activity
     *
     * @return 当前的Activity
     */
    public static Activity getCurrentActivity() {
        if (sActivities.isEmpty()) {
            return null;
        } else {
            return sActivities.get(sActivities.size() - 1);
        }
    }

    /**
     * 关闭当前的Activity
     */
    public static void finishCurrentActivity() {
        Activity activity = getCurrentActivity();
        if (activity != null) {
            activity.finish();
        }
    }

    /**
     * 关闭某个Activity及除当前Activity的其他Activity
     *
     * @param target          目标Activity.class
     * @param currentActivity 当前Activity的Activity
     */
    public static void finishActivitys(Class<? extends Activity> target, Activity currentActivity) {
        if (sActivities.isEmpty()) {
            return;
        }
        for (int i = 0; i < sActivities.size(); i++) {
            Activity activity = sActivities.get(i);
            // 将除了当前Activity和MainActivity的其他Activity关闭
            if (!activity.getClass().getName().equals(target.getName()) && activity != currentActivity) {
                activity.finish(); // finish方法会调用popActivity()方法移出栈
            }
        }
    }

    /**
     * 关闭某个非单例多次打开的activity
     */
    public static void finishAnActivity(Class<? extends Activity> target) {
        if (sActivities.isEmpty()) {
            return;
        }
        for (int i = 0; i < sActivities.size(); i++) {
            Activity activity = sActivities.get(i);
            if (activity.getClass().getName().equals(target.getName())) {
                activity.finish(); //finish方法会调用popActivity()方法移出栈
            }
        }
    }

    //退出所有activity
    public static void finishAllActivity() {
        for (Activity activity : sActivities) {
            activity.finish();
        }
    }

    /**
     * 退出应用程序
     */
    public static void appExit() {
        finishAllActivity();
    }

    /**
     * 当前activity是否在前台显示
     *
     * @param currentActivity
     * @return
     */
    public static boolean isTopActivity(Activity currentActivity) {
        if (currentActivity == sActivities.get(sActivities.size() - 1)) {
            return true;
        }
        return false;
    }
}
