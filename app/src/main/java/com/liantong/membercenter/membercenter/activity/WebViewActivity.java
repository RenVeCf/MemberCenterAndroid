package com.liantong.membercenter.membercenter.activity;

import android.webkit.WebSettings;
import android.webkit.WebView;

import com.liantong.membercenter.membercenter.R;
import com.liantong.membercenter.membercenter.base.BaseActivity;
import com.liantong.membercenter.membercenter.base.BasePresenter;
import com.liantong.membercenter.membercenter.base.BaseView;

import butterknife.BindView;

public class WebViewActivity extends BaseActivity {

    @BindView(R.id.common_webview)
    WebView commonWebview;

    @Override
    public int getLayoutId() {
        return R.layout.activity_web_view;
    }

    @Override
    public BasePresenter createPresenter() {
        return null;
    }

    @Override
    public BaseView createView() {
        return null;
    }

    @Override
    public void init() {
        commonWebview.loadUrl(getIntent().getStringExtra("land_page"));
        WebSettings webSettings = commonWebview.getSettings();
        webSettings.setJavaScriptEnabled(true);//允许使用js
        webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);//不使用缓存，只从网络获取数据.
        //支持屏幕缩放
        webSettings.setSupportZoom(true);
        webSettings.setBuiltInZoomControls(true);
        //不显示webview缩放按钮
        webSettings.setDisplayZoomControls(false);
    }

    @Override
    public void initListener() {

    }

    @Override
    public void initData() {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //释放资源
        commonWebview.destroy();
        commonWebview = null;
    }
}
