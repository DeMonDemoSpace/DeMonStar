package com.demon.baseui.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.demon.baseui.R;
import com.demon.baseui.view.ArrowDownloadView;

/**
 * @author DeMon
 * @date 2019/7/24
 * @email 757454343@qq.com
 * @description
 */
public class DownLoadDialog extends Dialog {

    public DownLoadDialog(Context context) {
        super(context, R.style.TransparentDialog);
    }

    private ArrowDownloadView downloadView;
    private String tips;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.widget_download_dialog);
        setCancelable(false);
        setCanceledOnTouchOutside(false);
        TextView tvTips = findViewById(R.id.tvTips);
        if (!TextUtils.isEmpty(tips)){
            tvTips.setText(tips);
        }
        downloadView = findViewById(R.id.downView);
        downloadView.startAnimating();
        Window window = getWindow();
        WindowManager.LayoutParams params = window.getAttributes();
        params.gravity = Gravity.CENTER;
        window.setAttributes(params);
    }


    public void setProgress(int progress) {
        //this.progress = progress;
        downloadView.setProgress(progress);
    }

    public DownLoadDialog setTips(String tips) {
        this.tips = tips;
        return this;
    }
}
