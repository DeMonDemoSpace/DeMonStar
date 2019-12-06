package com.demon.baseui.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.WindowManager;
import android.widget.ProgressBar;

import com.demon.baseui.R;
import com.demon.baseui.gilde.GlideDownLoad;
import com.demon.baseui.image.StrongImageView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SinglePicActivity extends Activity implements View.OnClickListener {
    private FloatingActionButton fad_add, fad_reduce, fad_restore;
    private StrongImageView strongImageView;
    public static final int URL = 0; //链接
    public static final int FILE = 1;//文件
    public static final int ASSETS = 2;//ASSETS资源文件
    public static final int RES = 3;
    private static final String KEY = "key";
    private static final String VALUE = "value";
    private float scale = -1.0f;
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
                    strongImageView.setImage(imgFile);
                    break;
                case 0x002:
                    strongImageView.setImage(getResources().openRawResource(R.drawable.base_img_error));
                    break;
            }
        }
    };

    public static Intent newIntent(Context context, int type, Object value) {
        Intent intent = new Intent(context, SinglePicActivity.class);
        intent.putExtra(KEY, type);
        intent.putExtra(VALUE, (Serializable) value);
        return intent;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_picture);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN); //隐藏状态栏
        strongImageView = findViewById(R.id.strongImageView);
        progressBar = findViewById(R.id.progress);
        fad_add = findViewById(R.id.fad_add);
        fad_add.setOnClickListener(this);
        fad_reduce = findViewById(R.id.fad_reduce);
        fad_reduce.setOnClickListener(this);
        fad_restore = findViewById(R.id.fad_restore);
        fad_restore.setOnClickListener(this);
        int type = getIntent().getIntExtra(KEY, -1);
        switch (type) {
            case URL:
                String value1 = getIntent().getStringExtra(VALUE);
                progressBar.setVisibility(View.VISIBLE);
                GlideDownLoad glideDownLoad = new GlideDownLoad(SinglePicActivity.this, value1, new GlideDownLoad.Listener() {
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
                strongImageView.setImage(value2);
                break;
            case ASSETS:
                String s =  getIntent().getStringExtra(VALUE);
                try {
                    strongImageView.setImage(getAssets().open(s));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            case RES:
                int v = getIntent().getIntExtra(VALUE, 0);
                if (v != 0) {
                    strongImageView.setImage(BitmapFactory.decodeResource(getResources(), v));
                }
                break;
        }
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (scale == -1.0f) {
            scale = strongImageView.getBaseScale();
        }
        if (id == R.id.fad_add) {
            scale = scale * 1.2f;
        } else if (id == R.id.fad_reduce) {
            scale = scale / 1.2f;
        } else if (id == R.id.fad_restore) {
            scale = strongImageView.getBaseScale();
        }
        strongImageView.setScale(scale);
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
