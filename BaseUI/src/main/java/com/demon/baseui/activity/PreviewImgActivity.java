package com.demon.baseui.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.WindowManager;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;

import com.demon.baseui.R;
import com.demon.baseui.gilde.GlideDownLoad;
import com.demon.baseui.view.TouchImageView;

import java.io.File;
import java.io.Serializable;

public class PreviewImgActivity extends AppCompatActivity {
    private TouchImageView zoomImageView;
    private static final String VALUE = "value";
    private ProgressBar progressBar;
    private File imgFile;

    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        @SuppressLint("ResourceType")
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0x001:
                    progressBar.setVisibility(View.GONE);
                    zoomImageView.setImageURI(Uri.fromFile(imgFile));
                    break;
                case 0x002:
                    zoomImageView.setImageResource(R.drawable.base_no_image);
                    break;
            }
        }
    };

    public static Intent newIntent(Context context, Object value) {
        Intent intent = new Intent(context, PreviewImgActivity.class);
        intent.putExtra(VALUE, (Serializable) value);
        return intent;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preview_img);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN); //隐藏状态栏
        zoomImageView = findViewById(R.id.zoomImageView);
        progressBar = findViewById(R.id.progress);
        int type = 0;
        if (getIntent().getExtras().get(VALUE) instanceof String) {
            String value = getIntent().getStringExtra(VALUE);
            if (!value.startsWith("http")) {
                type = 1;
            }
        } else {
            type = 2;
        }
        switch (type) {
            case 0:
                String value1 = getIntent().getStringExtra(VALUE);
                progressBar.setVisibility(View.VISIBLE);
                GlideDownLoad glideDownLoad = new GlideDownLoad(PreviewImgActivity.this, value1, new GlideDownLoad.Listener() {
                    @Override
                    public void onSuccess(File file) {
                        imgFile = file;
                        handler.sendEmptyMessage(0x001);
                    }

                    @Override
                    public void onFailed() {
                        handler.sendEmptyMessage(0x002);
                    }
                });
                //启动图片下载线程
                new Thread(glideDownLoad).start();
                break;
            case 1:
                String value2 = getIntent().getStringExtra(VALUE);
                try {
                    zoomImageView.setImageURI(Uri.parse(value2));
                } catch (Exception e) {
                    zoomImageView.setImageResource(R.drawable.base_no_image);
                }
                break;
            case 2:
                int v = getIntent().getIntExtra(VALUE, 0);
                if (v != 0) {
                    zoomImageView.setImageResource(v);
                }
                break;
        }

        findViewById(R.id.fabClose).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN); //显示状态栏
        handler.removeCallbacksAndMessages(null);
    }
}
