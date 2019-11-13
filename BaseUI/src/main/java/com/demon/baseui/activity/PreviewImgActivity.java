package com.demon.baseui.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;

import com.demon.baseui.R;
import com.demon.baseui.gilde.GlideDownLoad;
import com.demon.baseui.gilde.GlideUtil;
import com.demon.baseui.view.TouchImageView;

import java.io.File;
import java.io.Serializable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class PreviewImgActivity extends AppCompatActivity {
    private TouchImageView zoomImageView;
    public static final int URL = 0; //链接
    public static final int FILE = 1;//文件
    public static final int RES = 2;
    private static final String KEY = "key";
    private static final String VALUE = "value";
    private ProgressBar progressBar;
    private File imgFile;
    private ExecutorService singleExecutor = null;//单线程列队执行

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

    public static Intent newIntent(Context context, int type, Object value) {
        Intent intent = new Intent(context, PreviewImgActivity.class);
        intent.putExtra(KEY, type);
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
        int type = getIntent().getIntExtra(KEY, -1);
        switch (type) {
            case URL:
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
                runOnQueue(glideDownLoad);
                break;
            case FILE:
                String value2 = getIntent().getStringExtra(VALUE);
                GlideUtil.setImage(this, Uri.parse(value2), zoomImageView);
                break;
            case RES:
                int v = getIntent().getIntExtra(VALUE, 0);
                if (v != 0) {
                    zoomImageView.setImageBitmap(BitmapFactory.decodeResource(getResources(), v));
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


    /**
     * 执行单线程列队执行
     */
    public void runOnQueue(Runnable runnable) {
        if (singleExecutor == null) {
            singleExecutor = Executors.newSingleThreadExecutor();
        }
        singleExecutor.submit(runnable);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN); //显示状态栏
        if (singleExecutor != null) {
            singleExecutor.shutdownNow();
        }
        handler.removeCallbacksAndMessages(null);
    }
}
