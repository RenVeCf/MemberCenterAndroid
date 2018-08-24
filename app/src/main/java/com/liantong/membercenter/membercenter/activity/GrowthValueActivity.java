package com.liantong.membercenter.membercenter.activity;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.liantong.membercenter.membercenter.R;
import com.liantong.membercenter.membercenter.adapter.GrowthValueAdapter;
import com.liantong.membercenter.membercenter.base.BaseActivity;
import com.liantong.membercenter.membercenter.base.BaseResponse;
import com.liantong.membercenter.membercenter.bean.LoginBean;
import com.liantong.membercenter.membercenter.contract.LoginContract;
import com.liantong.membercenter.membercenter.presenter.LoginPresenter;
import com.liantong.membercenter.membercenter.utils.ApplicationUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import io.reactivex.ObservableTransformer;

public class GrowthValueActivity extends BaseActivity<LoginContract.View, LoginContract.Presenter> implements LoginContract.View, BaseQuickAdapter.RequestLoadMoreListener {

    @BindView(R.id.srl_growth_value)
    SwipeRefreshLayout srlGrowthValue;
    @BindView(R.id.rv_growth_value)
    RecyclerView rvGrowthValue;
    private GrowthValueAdapter mGrowthValueAdapter;
    private List<String> mGrowthValueBean;

    @Override
    public int getLayoutId() {
        return R.layout.activity_growth_value;
    }

    @Override
    public LoginContract.Presenter createPresenter() {
        return new LoginPresenter(this);
    }

    @Override
    public LoginContract.View createView() {
        return this;
    }

    @Override
    public void init() {
        LinearLayoutManager NotUseList = new LinearLayoutManager(ApplicationUtil.getContext(), LinearLayoutManager.VERTICAL, false);
        rvGrowthValue.setLayoutManager(NotUseList);
        rvGrowthValue.setHasFixedSize(true);
        rvGrowthValue.setItemAnimator(new DefaultItemAnimator());

        mGrowthValueBean = new ArrayList<>();
        mGrowthValueAdapter = new GrowthValueAdapter(mGrowthValueBean);
        rvGrowthValue.setAdapter(mGrowthValueAdapter);
    }

    @Override
    public void initListener() {
        srlGrowthValue.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                initData();
                srlGrowthValue.setRefreshing(false);
            }
        });
    }

    @Override
    public void initData() {
        mGrowthValueBean.add("1");
        mGrowthValueBean.add("2");
        mGrowthValueBean.add("3");

        mGrowthValueAdapter = new GrowthValueAdapter(mGrowthValueBean);
        rvGrowthValue.setAdapter(mGrowthValueAdapter);
        mGrowthValueAdapter.setOnLoadMoreListener(this, rvGrowthValue);
        mGrowthValueAdapter.loadMoreEnd(); //加载更多
    }

    @Override
    public void resultLogin(BaseResponse<LoginBean> data) {

    }

    @Override
    public void resultCaptcha(BaseResponse data) {

    }

    @Override
    public void setMsg(String msg) {

    }

    @Override
    public <T> ObservableTransformer<T, T> bindLifecycle() {
        return this.bindToLifecycle();
    }

    @Override
    public void onLoadMoreRequested() {
        initData();
    }
}
