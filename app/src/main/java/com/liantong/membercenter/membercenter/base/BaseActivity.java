package com.liantong.membercenter.membercenter.base;

import android.os.Bundle;
import android.view.WindowManager;

import com.gyf.barlibrary.ImmersionBar;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;

import butterknife.ButterKnife;

/**
 * 作者：rmy on 2017/12/27 10:09
 * 邮箱：942685687@qq.com
 * 父类->基类->动态指定类型->泛型设计（通过泛型指定动态类型->由子类指定，父类只需要规定范围即可）
 */
public abstract class BaseActivity<V extends BaseView, P extends BasePresenter<V>> extends RxAppCompatActivity {

    //引用V层和P层
    private P presenter;
    private V view;

    public P getPresenter() {
        return presenter;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        setContentView(getLayoutId());
        ButterKnife.bind(this);
        //沉浸式状态栏初始化
        ImmersionBar.with(this).fitsSystemWindows(false).statusBarDarkFont(true).init();
        if (presenter == null) {
            presenter = createPresenter();
        }
        if (view == null) {
            view = createView();
        }
        if (presenter != null && view != null) {
            presenter.attachView(view);
        }
        init();
        initListener();
        initData();
    }

    //由子类指定具体类型
    public abstract int getLayoutId();

    public abstract P createPresenter();

    public abstract V createView();

    public abstract void init();

    public abstract void initListener();

    public abstract void initData();

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (presenter != null) {
            presenter.detachView();
        }
    }
}