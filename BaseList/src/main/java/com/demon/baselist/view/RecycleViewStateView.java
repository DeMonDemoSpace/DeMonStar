package com.demon.baselist.view;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.demon.baselist.R;

/**
 * @author DeMon
 * @date 2019/7/18
 * @email 757454343@qq.com
 * @description
 */
public class RecycleViewStateView {

    private String loadingText = "";
    private String emptyText = "";
    private String errorText = "";
    private View loadingView;
    private View emptyView;
    private View errorView;
    private TextView loadingTextView;
    private TextView emptyTextView;
    private TextView errorTextView;
    private Context context;

    public RecycleViewStateView(Context context) {
        this.context = context;
        loadingView = inflateWithParent(R.layout.widget_page_loading);
        loadingTextView = loadingView.findViewById(R.id.recycle_loading_text);
        emptyView = inflateWithParent(R.layout.widget_page_error);
        emptyTextView = emptyView.findViewById(R.id.tv_msg_error);
        errorView = inflateWithParent(R.layout.widget_page_error);
        errorTextView = errorView.findViewById(R.id.tv_msg_error);

    }

    public View setEmptyView(int id) {
        emptyView = inflateWithParent(id);
        return emptyView;
    }

    public RecycleViewStateView setLoadingText(String loadingText) {
        this.loadingText = loadingText;
        return this;
    }

    public RecycleViewStateView setEmptyText(String emptyText) {
        this.emptyText = emptyText;
        return this;
    }

    public RecycleViewStateView setErrorText(String errorText) {
        this.errorText = errorText;
        return this;
    }


    public View getLoadingView() {
        if (!TextUtils.isEmpty(loadingText)) {
            loadingTextView.setText(loadingText);
        }
        return loadingView;
    }

    public View getEmptyView() {
        if (!TextUtils.isEmpty(emptyText)) {
            emptyTextView.setText(emptyText);
        } else {
            emptyText = "暂无数据哦~~";
            emptyTextView.setText(emptyText);
        }

        return emptyView;
    }

    public View getErrorView() {
        if (!TextUtils.isEmpty(errorText)) {
            errorTextView.setText(emptyText);
        } else {
            emptyText = "加载失败，请稍后重试···";
            errorTextView.setText(emptyText);
        }
        return errorView;
    }


    private View inflateWithParent(int id) {
        return LayoutInflater.from(context).inflate(id, null, false);
    }

}
