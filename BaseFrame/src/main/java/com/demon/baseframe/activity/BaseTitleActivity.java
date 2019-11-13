package com.demon.baseframe.activity;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

import com.demon.baseframe.R;
import com.demon.baseframe.model.BasePresenterInfc;
import com.demon.baseframe.widget.NormalTitleBar;

import butterknife.ButterKnife;

/**
 * @author DeMon
 * @date 2019/7/11
 * @email 757454343@qq.com
 * @description
 */
public abstract class BaseTitleActivity<T extends BasePresenterInfc> extends BaseActivity<T> {
    protected NormalTitleBar mTitleBar;

    //初始标题
    public abstract String initTitle();

    @Override
    protected void initLayout() {
        setContentView(R.layout.activity_base_title);
        FrameLayout frameLayout = findViewById(R.id.frameLayout);
        mTitleBar = findViewById(R.id.titleBar);
        LayoutInflater.from(this).inflate(bindLayout(), frameLayout);
        ButterKnife.bind(this);
        mTitleBar.setTitleText(initTitle());
        mTitleBar.setLeftTextListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

}
