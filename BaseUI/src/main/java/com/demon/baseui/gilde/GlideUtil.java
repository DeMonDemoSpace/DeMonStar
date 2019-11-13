package com.demon.baseui.gilde;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.os.Build;
import android.util.Log;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.request.RequestOptions;
import com.demon.baseui.R;

import jp.wasabeef.glide.transformations.BlurTransformation;

/**
 * @author DeMon
 * @date 2017/11/4
 * @description Glide 工具类
 */

public class GlideUtil {

    //根据图片url将图片显示在img控件上
    public static void setImage(Context context, String url, ImageView img) {
        if (context != null) {
            RequestOptions options = new RequestOptions().error(R.drawable.base_no_image);
            Glide.with(context).load(url).apply(options).into(img);
        }
    }

    //根据图片GlideUrl将图片显示在img控件上
    public static void setGlideUrlImage(Context context, GlideUrl url, ImageView img) {
        if (context != null) {
            RequestOptions options = new RequestOptions().error(R.drawable.base_no_image)
                    .skipMemoryCache(true)// 不使用内存缓存
                    .diskCacheStrategy(DiskCacheStrategy.NONE);
            Glide.with(context).load(url).apply(options)
                    .into(img);
        }
    }

    public static void setImage(Context context, String url, ImageView img, int defaultImg) {
        if (context != null) {
            RequestOptions options = new RequestOptions().placeholder(defaultImg).error(defaultImg);
            Glide.with(context).load(url).apply(options).into(img);
        }
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    public static void setImage(Activity activity, String url, ImageView img, int defaultImg) {
        if (activity != null && !activity.isDestroyed()) {
            RequestOptions options = new RequestOptions().placeholder(defaultImg).error(defaultImg);
            Glide.with(activity).load(url).apply(options).into(img);
        } else {
            Log.i("GlideUtil", "Picture loading failed,activity is Destroyed!");
        }
    }

    /**
     * 根据图片uri将图片显示在img控件上
     */
    public static void setImage(Context context, Uri uri, ImageView img) {
        RequestOptions options = new RequestOptions().placeholder(R.drawable.base_no_image).error(R.drawable.base_no_image);
        Glide.with(context).load(uri).apply(options).into(img);
    }

    public static void setImage(Context context, Uri uri, ImageView img, int defaultImg) {
        RequestOptions options = new RequestOptions().placeholder(defaultImg).error(defaultImg);
        Glide.with(context).load(uri).apply(options).into(img);
    }


    /**
     * 高斯模糊
     */
    public static void setBlurryImage(Context context, int id, ImageView img) {
        if (context != null) {
            RequestOptions options = new RequestOptions().transform(new BlurTransformation(14, 3));
            Glide.with(context).load(id).apply(options).into(img);
        }
    }

    public static void setBlurryImage(Context context, String url, ImageView img) {
        if (context != null) {
            RequestOptions options = new RequestOptions().transform(new BlurTransformation(14, 3));
            Glide.with(context).load(url).apply(options).into(img);
        }
    }

    public static void setBlurryImage(Context context, String url, ImageView img, int defaultImg) {
        if (context != null) {
            RequestOptions options = new RequestOptions().transform(new BlurTransformation(14, 3)).placeholder(defaultImg).error(defaultImg);
            Glide.with(context).load(url).apply(options).into(img);
        }
    }

    public static void setCircleImage(Context context, int id, ImageView img) {
        if (context != null) {
            RequestOptions options = new RequestOptions().circleCrop();
            Glide.with(context).load(id).apply(options).into(img);
        }
    }

    public static void setCircleImage(Context context, String url, ImageView img) {
        if (context != null) {
            RequestOptions options = new RequestOptions().circleCrop().error(R.drawable.base_no_image);
            Glide.with(context).load(url).apply(options).into(img);
        }
    }

    public static void setCircleImage(Context context, String url, ImageView img, int defaultImg) {
        if (context != null) {
            RequestOptions options = new RequestOptions().circleCrop().error(defaultImg).placeholder(defaultImg);
            Glide.with(context).load(url).apply(options).into(img);
        }
    }
}
