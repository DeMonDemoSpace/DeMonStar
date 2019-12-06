package com.demon.baseframe.activity;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.Menu;
import android.widget.FrameLayout;

import com.demon.baseframe.R;
import com.demon.baseframe.app.AppConfig;
import com.demon.baseframe.model.BasePresenterInfc;

import java.lang.reflect.Method;

import androidx.appcompat.widget.Toolbar;
import butterknife.ButterKnife;

/**
 * @author DeMon
 * @date 2017/12/18
 * @description 带有toolbar标题栏的BaseActivity
 * 1. 标题栏背景&标题栏菜单背景使用:color/colorPrimary
 * 2. 标题栏图标，字体，菜单颜色使用：color/titleTextColor
 * 3. 统一修改标题栏的颜色，直接在自己项目的colors.xml重写colorPrimary，titleTextColor这两个的颜色值即可（同名的资源系统会自动覆盖，自己项目中的优先级最高）。
 * 4. 单个Activity特殊定制直接在子Activity，根据mToolbar设置即可。
 */
public abstract class BaseBarActivity<T extends BasePresenterInfc> extends BaseActivity<T> {
    protected Toolbar mToolbar;
    private boolean isMenuIcon = false;

    //初始标题
    public abstract String initTitle();

    @Override
    protected void initLayout() {
        setContentView(R.layout.activity_base_bar);
        //将继承 TopBarBaseActivity 的布局解析到 FrameLayout里面
        FrameLayout viewContent = findViewById(R.id.viewContent);
        mToolbar = findViewById(R.id.toolbar);  //#34393A
        LayoutInflater.from(this).inflate(bindLayout(), viewContent);
        setToolbar(initTitle() + "");
        ButterKnife.bind(this);

        mToolbar.setBackgroundColor(mContext.getResources().getColor(AppConfig.getInstance().getToolbarColor()));
    }


    /**
     * 控制菜单中的文字和图标是否同时显示
     */
    protected void showMenuIcon() {
        this.isMenuIcon = true;
    }

    /**
     * 通过反射，设置menu显示icon
     *
     * @param menu
     * @return
     */
    @Override
    public boolean onMenuOpened(int featureId, Menu menu) {
        if (menu != null && isMenuIcon) {
            if (menu.getClass().getSimpleName().equalsIgnoreCase("MenuBuilder")) {
                try {
                    @SuppressLint("PrivateApi") Method method = menu.getClass().getDeclaredMethod("setOptionalIconsVisible", Boolean.TYPE);
                    method.setAccessible(true);
                    method.invoke(menu, true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return super.onMenuOpened(featureId, menu);
    }

    /**
     * 置标题及按钮功能
     *
     * @param title
     */
    public void setToolbar(String title) {
        mToolbar.setTitle(title);
        setSupportActionBar(mToolbar);
        isShowBack(true);
        mToolbar.setNavigationOnClickListener(v -> finish());
    }


    public void isShowBack(boolean isShow) {
        getSupportActionBar().setDisplayHomeAsUpEnabled(isShow);//左侧添加一个默认的返回图标
        getSupportActionBar().setHomeButtonEnabled(isShow); //设置返回键可用
    }

}
