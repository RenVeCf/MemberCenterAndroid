package com.liantong.membercenter.membercenter.activity;

import android.graphics.Bitmap;
import android.net.http.SslError;
import android.os.Build;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import com.gyf.barlibrary.ImmersionBar;
import com.liantong.membercenter.membercenter.R;
import com.liantong.membercenter.membercenter.base.BaseActivity;
import com.liantong.membercenter.membercenter.base.BasePresenter;
import com.liantong.membercenter.membercenter.base.BaseView;
import com.liantong.membercenter.membercenter.common.view.TopView;
import com.liantong.membercenter.membercenter.utils.ApplicationUtil;

import butterknife.BindView;

/**
 * Description ：公共web显示页
 * Author ： MengYang
 * Email ： 942685687@qq.com
 * Time ： 2018/8/27.
 */

public class WebViewActivity extends BaseActivity {

    @BindView(R.id.tv_webview_title)
    TopView tvWebviewTitle;
    @BindView(R.id.common_webview)
    WebView commonWebview;
    @BindView(R.id.tv_top_title)
    TextView ivTopTitle;

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
        //将每个Activity加入到栈中
        ApplicationUtil.getManager().addActivity(this);
        //防止状态栏和标题重叠
        ImmersionBar.setTitleBar(this, tvWebviewTitle);

        WebSettings webSettings = commonWebview.getSettings();
        webSettings.setJavaScriptEnabled(true); //允许与JS交互
        webSettings.setDomStorageEnabled(true); //开启DOM storage API 功能
        webSettings.setUseWideViewPort(true); //自适应屏幕
        webSettings.setLoadsImagesAutomatically(true); //支持自动加载图片
        webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NORMAL); // 排版适应屏幕
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)  //图片的引用是http，但链接是https需要申请SSL安全证书
            webSettings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        commonWebview.loadUrl(getIntent().getStringExtra("land_page")); //网址链接
    }

    @Override
    public void initListener() {

    }

    @Override
    public void initData() {
        //设置客户端，让点击跳转操作在当前应用内存进行操作
        commonWebview.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                if (!TextUtils.isEmpty(view.getTitle())) {
                    ivTopTitle.setText(view.getTitle());
                }
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
            }

            //当发生证书认证错误时，采用默认的处理方法handler.cancel()，停止加载问题页面
            @Override
            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                super.onReceivedSslError(view, handler, error);
                handler.cancel();
            }
        });

        commonWebview.setWebChromeClient(new WebChromeClient() {
            //返回当前加载网页的进度
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
            }

            //获取当前网页的标题
            @Override
            public void onReceivedTitle(WebView view, String title) {
                super.onReceivedTitle(view, title);
                ivTopTitle.setText(title);
            }
        });
    }

    //使用Webview的时候，返回键没有重写的时候会直接关闭程序，这时候其实我们要其执行的知识回退到上一步的操作
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        //这是一个监听用的按键的方法，keyCode 监听用户的动作，如果是按了返回键，同时Webview要返回的话，WebView执行回退操作，因为mWebView.canGoBack()返回的是一个Boolean类型，所以我们把它返回为true
        if (keyCode == KeyEvent.KEYCODE_BACK && commonWebview.canGoBack()) {
            commonWebview.goBack();
            return true;
        } else {
            onBackPressed();
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //释放资源
        commonWebview.destroy();
        commonWebview = null;
    }
}
