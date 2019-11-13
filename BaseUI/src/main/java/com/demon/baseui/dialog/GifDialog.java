package com.demon.baseui.dialog;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.demon.baseui.R;

/**
 * @author DeMon
 * @date 2019/8/5
 * @email 757454343@qq.com
 * @description
 */
public class GifDialog extends ProgressDialog {

    private Context mContext;
    private int gifRes;

    public GifDialog(Context context) {
        super(context, R.style.TransparentDialog);
        mContext = context;
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.widget_gif_loading);
        setCanceledOnTouchOutside(false);
        ImageView imgLoading = findViewById(R.id.imgLoading);
        Drawable drawable = mContext.getResources().getDrawable(gifRes);
        ViewGroup.LayoutParams lp = imgLoading.getLayoutParams();
        lp.width = drawable.getMinimumWidth();
        lp.height = drawable.getMinimumHeight();
        imgLoading.setLayoutParams(lp);
        Glide.with(mContext).load(gifRes).into(imgLoading);
        Window window = getWindow();
        WindowManager.LayoutParams params = window.getAttributes();
        params.gravity = Gravity.CENTER;
        window.setAttributes(params);
    }

    public GifDialog setGifRes(int gifRes) {
        this.gifRes = gifRes;
        return this;
    }
}
