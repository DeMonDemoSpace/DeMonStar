package com.demon.baseui.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.WindowManager;
import android.widget.ProgressBar;

import androidx.viewpager.widget.ViewPager;

import com.demon.baseui.R;
import com.demon.baseui.gilde.GlideDownLoad;
import com.demon.baseui.image.StrongImageView;
import com.demon.baseui.list.adapter.PagerDataAdapter;
import com.demon.baseui.list.holder.DataViewHolder;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MultiPicActivity extends Activity {
    private ViewPager vpPictures;
    private PagerDataAdapter<String> adapter;
    private ProgressBar progressBar;
    public static final int URL = 0; //链接
    public static final int FILE = 1;//文件
    public static final int ASSETS = 2;//资源文件流
    public static final String KEY = "key";
    public static final String VALUE = "value";
    public static final String POS = "pos";
    private float scale = -1.0f;
    private List<String> list = new ArrayList<>();
    private List<File> fileList = new ArrayList<>();
    private int type, pos;
    private ExecutorService singleExecutor = null;//单线程列队执行
    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 0x001) {
                progressBar.setVisibility(View.GONE);
                initAdapter();
            }
        }
    };

    /**
     * @param context
     * @param type      图片的数据类型
     * @param valueList 图片的指向位置url，文件，资源流
     * @param pos       显示的位置
     * @return
     */
    public static Intent newIntent(Context context, int type, List<String> valueList, int pos) {
        Intent intent = new Intent(context, MultiPicActivity.class);
        intent.putExtra(KEY, type);
        intent.putExtra(VALUE, (Serializable) valueList);
        intent.putExtra(POS, pos);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multi_picture);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN); //隐藏状态栏
        vpPictures = findViewById(R.id.vp_pictures);
        progressBar = findViewById(R.id.progress);
        type = getIntent().getIntExtra(KEY, -1);
        list = (List<String>) getIntent().getSerializableExtra(VALUE);
        pos = getIntent().getIntExtra(POS, 0);
        if (type == URL) {
            downImgs(list);
        } else {
            initAdapter();
        }
    }

    private void initAdapter() {
        adapter = new PagerDataAdapter<String>(this, R.layout.activity_single_picture, list) {
            @SuppressLint("ResourceType")
            @Override
            public void convert(DataViewHolder holder, int position, String s) {
                scale = -1.0f;
                final StrongImageView strongImageView = holder.getView(R.id.strongImageView);
                switch (type) {
                    case URL:
                        if (fileList.get(position) == null) {
                            strongImageView.setImage(getResources().openRawResource(R.drawable.base_img_error));
                        } else {
                            strongImageView.setImage(fileList.get(position));
                        }
                        break;
                    case FILE:
                        strongImageView.setImage(s);
                        break;
                    case ASSETS:
                        try {
                            strongImageView.setImage(getAssets().open(s));
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        break;
                }
                holder.setOnClickListener(R.id.fad_add, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (scale == -1.0f) {
                            scale = strongImageView.getBaseScale();
                        }
                        scale = scale * 1.2f;
                        strongImageView.setScale(scale);
                    }
                });
                holder.setOnClickListener(R.id.fad_reduce, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (scale == -1.0f) {
                            scale = strongImageView.getBaseScale();
                        }
                        scale = scale / 1.2f;
                        strongImageView.setScale(scale);

                    }
                });
                holder.setOnClickListener(R.id.fad_restore, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        scale = strongImageView.getBaseScale();
                        strongImageView.setScale(scale);
                    }
                });
            }
        };
        vpPictures.setAdapter(adapter);
        vpPictures.setCurrentItem(pos);//设置起始位置
    }


    private void downImgs(final List<String> list) {
        progressBar.setVisibility(View.VISIBLE);
        for (int i = 0; i < list.size(); i++) {
            GlideDownLoad glideDownLoad = new GlideDownLoad(MultiPicActivity.this, list.get(i), new GlideDownLoad.Listener() {
                @Override
                public void onSuccess(File file) {
                    fileList.add(file);
                    if (fileList.size() == list.size()) {
                        handler.sendEmptyMessage(0x001);
                    }
                }

                @Override
                public void onFailed() {
                    fileList.add(null);
                    if (fileList.size() == list.size()) {
                        handler.sendEmptyMessage(0x001);
                    }
                }
            });
            //启动图片下载线程
            runOnQueue(glideDownLoad);
        }
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
            singleExecutor.shutdown();
        }
        handler.removeCallbacksAndMessages(null);
    }
}
