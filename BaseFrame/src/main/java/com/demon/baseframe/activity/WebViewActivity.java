package com.demon.baseframe.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.demon.baseframe.R;

/**
 * @author ZCC
 * @date 2018/1/19
 * @description 网页Activity，只包含底部工具栏功能，可继承该类进行拓展
 */

public class WebViewActivity extends BaseBarActivity implements View.OnClickListener {
    private static final String TAG = "WebViewActivity";
    private static final String WEBVIEW_URL = "WebViewUrl";
    private static final String WEBVIEW_NAV = "WebViewNav";
    private WebView mWbWebView;
    private ProgressBar mProgress;
    private ImageView mIvBack;
    private ImageView mIvForward;
    private ImageView mIvClose;
    private ImageView mIvFresh;

    private String mWebViewUrl;

    /**
     * 获取WebViewActivity的Intent
     *
     * @param context 上下文
     * @param url     网址
     * @return WebViewActivity的Intent
     */
    public static Intent newIntent(Context context, String url) {
        return newIntent(context, url, false);
    }

    public static Intent newIntent(Context context, String url, boolean isShowNav) {
        Intent intent = new Intent(context, WebViewActivity.class);
        intent.putExtra(WEBVIEW_URL, url);
        intent.putExtra(WEBVIEW_NAV, isShowNav);
        return intent;
    }


    @Override
    public int bindLayout() {
        return R.layout.activity_webview;
    }

    @Override
    public String initTitle() {
        return "";
    }

    @Override
    public void initCreate() {
        mWebViewUrl = getIntent().getStringExtra(WEBVIEW_URL);
        boolean isShowBar = getIntent().getBooleanExtra(WEBVIEW_NAV, false);
        LinearLayout webviewNavigation = findViewById(R.id.ll_webview_navigation);
        webviewNavigation.setVisibility(isShowBar ? View.VISIBLE : View.GONE);
        mWbWebView = findViewById(R.id.wb_webview);
        mProgress = findViewById(R.id.progress_webview);
        mIvForward = findViewById(R.id.iv_webview_forward);
        mIvForward.setOnClickListener(this);
        mIvBack = findViewById(R.id.iv_webview_back);
        mIvBack.setOnClickListener(this);
        mIvFresh = findViewById(R.id.iv_webview_fresh);
        mIvFresh.setOnClickListener(this);
        mIvClose = findViewById(R.id.iv_webview_close);
        mIvClose.setOnClickListener(this);
        initView();
    }


    @SuppressLint("SetJavaScriptEnabled")
    protected void initView() {
        Log.i(TAG, "访问的网址: " + mWebViewUrl);
        mWbWebView.loadUrl(mWebViewUrl);
        WebSettings webSettings = mWbWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setAllowFileAccess(true);
        webSettings.setAllowFileAccessFromFileURLs(true);
        webSettings.setAllowUniversalAccessFromFileURLs(true);
        webSettings.setSupportZoom(true);//缩放开关，设置此属性，仅支持双击缩放，不支持触摸缩放
        webSettings.setBuiltInZoomControls(true);  //设置是否可缩放，会出现缩放工具（若为true则上面的设值也默认为true）
        webSettings.setDisplayZoomControls(true);  //隐藏缩放工具
        mWbWebView.getSettings().setJavaScriptEnabled(true);
        // WebView出现net::ERR_UNKNOWN_URL_SCHEME错误：http://www.jianshu.com/p/119823e5cfb5
        mWbWebView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                if (url == null) return false;
                try {
                    if (url.startsWith("weixin://") //微信
                            || url.startsWith("alipays://") //支付宝
                            || url.startsWith("mailto://") //邮件
                            || url.startsWith("tel://")//电话
                            || url.startsWith("dianping://")//大众点评
                            || url.startsWith("baiduboxapp://")//大众点评
                        //其他自定义的scheme
                    ) {
                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                        startActivity(intent);
                        return true;
                    }
                } catch (Exception e) { //防止crash (如果手机上没有安装处理某个scheme开头的url的APP, 会导致crash)
                    return true;//没有安装该app时，返回true，表示拦截自定义链接，但不跳转，避免弹出上面的错误页面
                }
                //处理http和https开头的url
                view.loadUrl(url);
                return true;
            }

        });
        mWbWebView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                if (newProgress == 100) {
                    mProgress.setVisibility(View.GONE);
                } else {
                    if (mProgress.getVisibility() == View.GONE) {
                        mProgress.setVisibility(View.VISIBLE);
                    }
                    mProgress.setProgress(newProgress);
                }
                super.onProgressChanged(view, newProgress);
            }

            @Override
            public void onReceivedTitle(WebView view, String title) {
                super.onReceivedTitle(view, title);
                mToolbar.setTitle(title);

            }
        });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (mWbWebView.canGoBack()) {
                mWbWebView.goBack();
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }


    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.iv_webview_back) {
            if (mWbWebView.canGoBack()) {
                mWbWebView.goBack();
            } else {
                finish();
            }

        } else if (i == R.id.iv_webview_forward) {
            if (mWbWebView.canGoForward()) {
                mWbWebView.goForward();
            } else {
                Toast.makeText(mContext, "无法继续前进", Toast.LENGTH_SHORT).show();
            }
        } else if (i == R.id.iv_webview_close) {
            WebViewActivity.this.finish();
        } else if (i == R.id.iv_webview_fresh) {
            mWbWebView.reload();
        }
    }
}
