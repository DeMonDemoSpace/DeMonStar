package com.demon.baseframe.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;

import com.demon.baseframe.app.BaseApp;
import com.demon.baseframe.model.BaseModel;
import com.demon.baseframe.model.BasePresenterInfc;
import com.demon.baseframe.model.BaseView;
import com.demon.baseframe.util.TUtil;
import com.demon.baseframe.widget.LoadingLayout;
import com.demon.baseutil.ToastUtil;
import com.demon.baseutil.status.StatusBarUtil;
import com.demon.baseutil.status.StatusFontUtil;

import androidx.appcompat.app.AppCompatActivity;
import butterknife.ButterKnife;


/**
 * @author DeMon
 * @date 2017/12/18
 * @description Activity 基类
 */
public abstract class BaseActivity<T extends BasePresenterInfc> extends AppCompatActivity implements BaseView {
    protected final String TAG = this.getClass().getSimpleName();
    public Context mContext;
    protected LoadingLayout mStatusLayout;
    private int loadingLayoutStatus;
    protected T mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(BaseApp.SCREEN_MODE);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        BaseModel.mContext = this;
        mContext = this;
        initParam(getIntent());
        initLayout();
        setStatusBar();
        initPresenter();
        initCreate();
    }


    /**
     * 初始化布局
     * 封装成方法的目的：
     * 例如需要实现含有标题栏的BaseActivity可重写此方法
     */
    protected void initLayout() {
        setContentView(bindLayout());
        ButterKnife.bind(this);
    }

    protected void setStatusBar() {
        StatusBarUtil.setTransparent(this);
        StatusFontUtil.setStatusFont(this, true);
    }

    /**
     * 获取绑定的布局
     */
    protected abstract int bindLayout();

    /**
     * 初始化Presenter
     */
    private void initPresenter() {
        mPresenter = TUtil.getT(this);
        if (mPresenter != null) {
            mPresenter.setContext(mContext);
            mPresenter.onStart(this);
        }
    }

    protected boolean isFullScreen() {
        return false;
    }

    protected void initParam(Intent intent) {

    }

    /**
     * 初始化数据
     */
    protected abstract void initCreate();

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mPresenter != null) {
            mPresenter.onDestroy();
        }
    }


    protected void bindLoadingLayout(LoadingLayout loadingLayout) {
        this.mStatusLayout = loadingLayout;
    }


    @Override
    public void setLoadingLayout(@LoadingLayout.Flavour int status, String msg) {
        if (mStatusLayout == null) {
            Log.e(TAG, "未绑定LoadingLayout！");
            return;
        }
        this.loadingLayoutStatus = status;
        mStatusLayout.setStatus(status);
        switch (loadingLayoutStatus) {
            case LoadingLayout.Empty:
                mStatusLayout.setEmptyText(msg);
                break;
            case LoadingLayout.Error:
                mStatusLayout.setErrorText(msg);
                break;
        }
    }

    @Override
    public void setLoadingLayout(String msg) {
        if (mStatusLayout == null) {
            Log.e(TAG, "未绑定LoadingLayout！");
            return;
        }
        switch (loadingLayoutStatus) {
            case LoadingLayout.Empty:
                mStatusLayout.setEmptyText(msg);
                break;
            case LoadingLayout.Error:
                mStatusLayout.setErrorText(msg);
                break;
        }
    }

    @Override
    public void setLoadingLayout(@LoadingLayout.Flavour int status) {
        if (mStatusLayout == null) {
            Log.e(TAG, "未绑定LoadingLayout！");
            return;
        }
        this.loadingLayoutStatus = status;
        mStatusLayout.setStatus(status);
    }


    @Override
    public void showMessage(String msg) {
        showMessage(msg, ToastUtil.Normal);
    }

    @Override
    public void showMessage(String msg, int status) {
        switch (status) {
            case ToastUtil.Success:
                ToastUtil.showSuccessToast(mContext, msg);
                break;
            case ToastUtil.Info:
                ToastUtil.showInfoToast(mContext, msg);
                break;
            case ToastUtil.Warning:
                ToastUtil.showWarningToast(mContext, msg);
                break;
            case ToastUtil.Error:
                ToastUtil.showErrorToast(mContext, msg);
                break;
            case ToastUtil.Long:
                ToastUtil.showLongToast(mContext, msg);
                break;
            default:
                ToastUtil.showToast(mContext, msg);
                break;
        }
    }
}
