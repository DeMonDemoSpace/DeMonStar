package com.demon.baseutil;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.widget.Toast;

import androidx.annotation.IntDef;

import es.dmoral.toasty.Toasty;


/**
 * @author D&LL
 * @date 2017/5/25
 * @description Toast工具类
 */

public class ToastUtil {
    public final static int Normal = 0;
    public final static int Success = 1;
    public final static int Info = 2;
    public final static int Error = 3;
    public final static int Warning = 4;
    public final static int Long = 5;

    @IntDef({Normal, Success, Info, Error, Warning, Long})
    public @interface Flavour {

    }

    /**
     * 显示一个Toast
     *
     * @param context
     * @param msg
     */
    public static void showToast(Context context, String msg) {
        Toasty.normal(context, msg).show();
    }

    /**
     * 显示一个Toast
     *
     * @param context
     * @param msg
     */
    public static void showLongToast(Context context, String msg) {
        Toasty.normal(context, msg, Toast.LENGTH_LONG).show();
    }

    /**
     * 异步线程中显示Toast，解决异步线程无法显示Toast的问题
     *
     * @param context
     * @param msg
     */
    public static void showThreadToast(final Context context, final String msg) {
        if (context != null && msg != null) {
            new Handler(Looper.getMainLooper()).post(new Runnable() {
                @Override
                public void run() {
                    showToast(context, msg);
                }
            });
        }
    }


    /**
     * 显示错误的toast
     *
     * @param tvStr
     * @return
     */
    public static void showErrorToast(Context context, String tvStr) {
        Toasty.error(context, tvStr, Toast.LENGTH_SHORT, true).show();
    }

    /**
     * 显示警告的toast
     *
     * @param tvStr
     * @return
     */
    public static void showWarningToast(Context context, String tvStr) {
        Toasty.warning(context, tvStr, Toast.LENGTH_SHORT, true).show();
    }

    /**
     * 显示成功的toast
     *
     * @param tvStr
     * @return
     */
    public static void showSuccessToast(Context context, String tvStr) {
        Toasty.success(context, tvStr, Toast.LENGTH_SHORT, true).show();
    }

    /**
     * 显示一般信息的toast
     *
     * @param tvStr
     * @return
     */
    public static void showInfoToast(Context context, String tvStr) {
        Toasty.info(context, tvStr, Toast.LENGTH_SHORT, true).show();
    }

}

