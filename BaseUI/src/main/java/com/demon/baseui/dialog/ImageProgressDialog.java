package com.demon.baseui.dialog;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.demon.baseui.R;
import com.demon.baseui.view.CircleImageView;

public class ImageProgressDialog extends ProgressDialog {
    private int layoutId = -1;
    private Context mContext;
    private TextView tv_text;
    private CircleImageView riv_image;
    private ProgressBar progressBar;
    private String text;
    private int img = R.drawable.base_google;

    public ImageProgressDialog(Context context) {
        super(context, R.style.TransparentDialog);
        mContext = context;
    }

    public ImageProgressDialog(Context context, int layoutId) {
        super(context, R.style.TransparentDialog);
        this.layoutId = layoutId;
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (layoutId == -1) {
            setContentView(R.layout.dialog_progress_image);
            riv_image = findViewById(R.id.riv_image);
            progressBar = findViewById(R.id.progress);
            tv_text = findViewById(R.id.tv_text);
            setText(text);
            setImage(img);
            Animation animation = AnimationUtils.loadAnimation(mContext, R.anim.anim_rote);
            riv_image.startAnimation(animation);
        } else {
            setContentView(layoutId);
        }
        setCanceledOnTouchOutside(false);
        Window window = getWindow();
        WindowManager.LayoutParams params = window.getAttributes();
        params.gravity = Gravity.CENTER;
        window.setAttributes(params);
    }


    public ImageProgressDialog setText(String text) {
        this.text = text;
        if (!TextUtils.isEmpty(text) && tv_text != null) {
            tv_text.setText(text);
        }
        return this;
    }

    public ImageProgressDialog setImage(int img) {
        this.img = img;
        if (riv_image != null) {
            riv_image.setImageDrawable(mContext.getResources().getDrawable(img));
        }
        return this;
    }

}
