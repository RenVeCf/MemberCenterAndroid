package com.liantong.membercenter.membercenter.base;

/**
 * 作者：rmy on 2017/12/27 10:10
 * 邮箱：942685687@qq.com
 */

public abstract class BasePresenter<V extends BaseView> {

    private V mView;

    public V getView() {
        return mView;
    }

    public void attachView(V v) {
        mView = v;
    }

    public void detachView() {
        mView = null;
    }
}