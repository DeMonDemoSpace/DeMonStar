package com.demon.baseframe.widget;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.demon.baseframe.R;


public class LoadingDialog extends ProgressDialog {

    private Context context;
    private View dialogView;
    private TextView text;


    public LoadingDialog(Context context) {
        super(context, R.style.TransparentDialog);
        this.context = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dialogView = View.inflate(context, R.layout.widget_dialog_loading, null);
        setContentView(dialogView);
        setCanceledOnTouchOutside(false);
        text = dialogView.findViewById(R.id.dialog_loading_text);
        Window window = getWindow();
        WindowManager.LayoutParams params = window.getAttributes();
        params.gravity = Gravity.CENTER;
        window.setAttributes(params);
    }


    public LoadingDialog setText(String str) {

        text.setText(str.trim());

        return this;
    }


    public LoadingDialog setBackpressNotDissmiss() {

        this.setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {

                return keyCode == KeyEvent.KEYCODE_BACK;

            }
        });

        return this;
    }
}
