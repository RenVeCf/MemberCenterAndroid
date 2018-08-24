package com.liantong.membercenter.membercenter.fragment;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.LinearSnapHelper;
import android.support.v7.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.liantong.membercenter.membercenter.R;
import com.liantong.membercenter.membercenter.adapter.NotUseAdapter;
import com.liantong.membercenter.membercenter.base.BaseFragment;
import com.liantong.membercenter.membercenter.base.BaseResponse;
import com.liantong.membercenter.membercenter.bean.LoginBean;
import com.liantong.membercenter.membercenter.bean.NotUseBean;
import com.liantong.membercenter.membercenter.contract.LoginContract;
import com.liantong.membercenter.membercenter.presenter.LoginPresenter;
import com.liantong.membercenter.membercenter.utils.ApplicationUtil;
import com.trello.rxlifecycle2.android.FragmentEvent;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import io.reactivex.ObservableTransformer;

/**
 * Description ：
 * Author ： MengYang
 * Email ： 942685687@qq.com
 * Time ： 2018/8/21.
 */
public class CouponNotUsedFragment extends BaseFragment<LoginContract.View, LoginContract.Presenter> implements LoginContract.View, BaseQuickAdapter.RequestLoadMoreListener {

    @BindView(R.id.srl_not_use)
    SwipeRefreshLayout srlNotUse;
    @BindView(R.id.rv_not_use)
    RecyclerView rvNotUse;
    private NotUseAdapter mNotUseAdapter;
    private List<String> mNotUseBean;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_coupon_not_use;
    }

    @Override
    public LoginContract.Presenter createPresenter() {
        return new LoginPresenter(mContext);
    }

    @Override
    public LoginContract.View createView() {
        return this;
    }

    @Override
    public void init() {
        LinearLayoutManager NotUseList = new LinearLayoutManager(ApplicationUtil.getContext(), LinearLayoutManager.VERTICAL, false);
        rvNotUse.setLayoutManager(NotUseList);
        rvNotUse.setHasFixedSize(true);
        rvNotUse.setItemAnimator(new DefaultItemAnimator());

        mNotUseBean = new ArrayList<>();
        mNotUseAdapter = new NotUseAdapter(mNotUseBean);
        rvNotUse.setAdapter(mNotUseAdapter);
    }

    @Override
    public void initListener() {
        srlNotUse.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                initData();
                srlNotUse.setRefreshing(false);
            }
        });
    }

    @Override
    public void initData() {
        mNotUseBean.add("1");
        mNotUseBean.add("2");
        mNotUseBean.add("3");

        mNotUseAdapter = new NotUseAdapter(mNotUseBean);
        rvNotUse.setAdapter(mNotUseAdapter);
        mNotUseAdapter.setOnLoadMoreListener(CouponNotUsedFragment.this, rvNotUse);
        mNotUseAdapter.loadMoreEnd(); //加载更多
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
        return this.bindUntilEvent(FragmentEvent.PAUSE);
    }

    @Override
    public void onLoadMoreRequested() {
        initData();
    }
}
