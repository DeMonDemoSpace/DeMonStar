package com.demon.baseframe.fragment;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.demon.baseframe.model.BasePresenterInfc;
import com.demon.baseframe.model.BaseView;
import com.demon.baseframe.util.TUtil;
import com.demon.baseframe.widget.LoadingLayout;
import com.demon.baseutil.ToastUtil;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * @author DeMon
 * @date 2017/12/18
 * @description Fragment 基类
 */
public abstract class BaseFragment<T extends BasePresenterInfc> extends Fragment implements BaseView {

    protected final String TAG = this.getClass().getSimpleName();
    protected T mPresenter;
    public Context mContext;
    protected LoadingLayout mStatusLayout;
    private int loadingLayoutStatus;
    private boolean isFristLoad;
    private boolean isVisible;
    private boolean isResume;
    protected View containerView;
    Unbinder unbinder;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mContext = getActivity();
        isFristLoad = true;
        containerView = inflater.inflate(bindLayout(), container, false);
        unbinder = ButterKnife.bind(this, containerView);
        initPresenter();
        return containerView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (!isLazyLoad()) {
            initCreate();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (isVisible) {
            isResume = true;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (isVisible && isResume) {
            isResume = false;
            initResumeData();
        }
    }

    //返回Fragment需要做一些业务，如刷新列表数据，重写此方法即可
    //此方法只会在当前返回的时候调用
    protected void initResumeData() {

    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        isVisible = isVisibleToUser;
        if (isVisibleToUser && isLazyLoad() && isFristLoad) {
            isFristLoad = false;
            initCreate();
        }

    }


    public abstract boolean isLazyLoad();//是否时懒加载，即可见时才加载数据

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

    /**
     * 初始化数据
     */
    protected abstract void initCreate();

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
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
