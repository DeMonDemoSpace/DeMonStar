package com.demon.baseframe.model;

import com.demon.baseframe.widget.LoadingLayout;
import com.demon.baseutil.ToastUtil;

/**
 * @author DeMon
 * @date 2017/12/18
 * @description 让所有View接口必须实现，这个接口可以什么都不做，只是用于约束类型
 */
public interface BaseView {

    void setLoadingLayout(@LoadingLayout.Flavour int status, String msg);

    void setLoadingLayout(String msg);

    void setLoadingLayout(@LoadingLayout.Flavour int status);

    void showMessage(String msg);

    void showMessage(String msg, @ToastUtil.Flavour int status);
}
