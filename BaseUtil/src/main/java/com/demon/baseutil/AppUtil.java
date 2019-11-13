package com.demon.baseutil;

import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import androidx.core.app.ActivityCompat;

import java.util.ArrayList;
import java.util.List;

/**
 * @author DeMon
 * @date 2018/1/10
 * @description APP相关的辅助类
 */

public class AppUtil {

    /**
     * 获取应用程序名称
     */
    public static String getAppName(Context context) {
        return context.getResources().getString(getPackageInfo(context).applicationInfo.labelRes);
    }

    /**
     * 获取版本名
     */
    public static String getVersionName(Context context) {
        return getPackageInfo(context).versionName;
    }

    /**
     * 获取版本号
     */
    public static int getVersionCode(Context context) {
        return getPackageInfo(context).versionCode;
    }

    private static PackageInfo getPackageInfo(Context context) {
        PackageInfo pi = null;

        try {
            PackageManager pm = context.getPackageManager();
            pi = pm.getPackageInfo(context.getPackageName(),
                    PackageManager.GET_CONFIGURATIONS);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return pi;
    }

    /**
     * 判断应用是否在运行
     *
     * @param context
     * @return
     */
    public static boolean isAppRunning(Context context) {
        String packageName = context.getPackageName();
        String topActivityClassName = getTopActivityName(context);
        return packageName != null && topActivityClassName != null && topActivityClassName.startsWith(packageName);
    }

    private static String getTopActivityName(Context context) {
        String topActivityClassName = null;
        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> runningTaskInfos = activityManager != null ? activityManager.getRunningTasks(1) : null;
        if (runningTaskInfos != null) {
            ComponentName f = runningTaskInfos.get(0).topActivity;
            topActivityClassName = f.getClassName();
        }
        return topActivityClassName;
    }


    /**
     * 判断某个服务是否正在运行的方法
     *
     * @param mContext
     * @param serviceName 是包名+服务的类名（例如：net.loonggg.testbackstage.TestService）
     * @return true代表正在运行，false代表服务没有正在运行
     */
    public static boolean isServiceRunning(Context mContext, String serviceName) {
        boolean isWork = false;
        ActivityManager myAM = (ActivityManager) mContext.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningServiceInfo> myList = myAM.getRunningServices(50);
        if (myList.size() <= 0) {
            return false;
        }
        for (int i = 0; i < myList.size(); i++) {
            String mName = myList.get(i).service.getClassName().toString();
            if (mName.equals(serviceName)) {
                isWork = true;
                break;
            }
        }
        return isWork;
    }


    /**
     * 检查所需权限中缺少的权限
     * 防止权限重复获取
     *
     * @param context
     * @param permissions
     * @return
     */
    public static String[] checkPermission(Context context, String[] permissions) {
        List<String> noPermissionList = new ArrayList<>(); //程序缺少的权限
        for (String permission : permissions) {
            if (ActivityCompat.checkSelfPermission(context, permission) == PackageManager.PERMISSION_DENIED) {
                noPermissionList.add(permission);
            }
        }
        String[] needPermissions = new String[noPermissionList.size()];
        for (int i = 0; i < noPermissionList.size(); i++) {
            needPermissions[i] = noPermissionList.get(i);
        }

        return needPermissions;
    }
}
