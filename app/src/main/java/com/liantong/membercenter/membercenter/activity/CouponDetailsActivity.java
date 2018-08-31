package com.liantong.membercenter.membercenter.activity;

import com.liantong.membercenter.membercenter.R;
import com.liantong.membercenter.membercenter.base.BaseActivity;
import com.liantong.membercenter.membercenter.bean.CouponDetailsBean;
import com.liantong.membercenter.membercenter.contract.CouponDetailsContract;
import com.liantong.membercenter.membercenter.presenter.CouponDetailsPresenter;

import java.util.TreeMap;

import io.reactivex.ObservableTransformer;

public class CouponDetailsActivity extends BaseActivity<CouponDetailsContract.View, CouponDetailsContract.Presenter> implements CouponDetailsContract.View {
    @Override
    public int getLayoutId() {
        return R.layout.activity_coupon_details;
    }

    @Override
    public CouponDetailsContract.Presenter createPresenter() {
        return new CouponDetailsPresenter(this);
    }

    @Override
    public CouponDetailsContract.View createView() {
        return this;
    }

    @Override
    public void init() {

    }

    @Override
    public void initListener() {

    }

    @Override
    public void initData() {
        TreeMap<String, String> map = new TreeMap<>();
        getPresenter().getCouponDetails(map, true, true);
    }

    @Override
    public void getCouponDetails(CouponDetailsBean data) {

    }

    @Override
    public void setMsg(String msg) {

    }

    @Override
    public <T> ObservableTransformer<T, T> bindLifecycle() {
        return null;
    }
}
