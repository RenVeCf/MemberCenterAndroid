package com.liantong.membercenter.membercenter.fragment;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.liantong.membercenter.membercenter.R;
import com.liantong.membercenter.membercenter.adapter.CouponConvertibilityAdapter;
import com.liantong.membercenter.membercenter.base.BaseFragment;
import com.liantong.membercenter.membercenter.bean.CouponListBean;
import com.liantong.membercenter.membercenter.contract.CouponListContract;
import com.liantong.membercenter.membercenter.presenter.CouponListPresenter;
import com.liantong.membercenter.membercenter.utils.ApplicationUtil;
import com.trello.rxlifecycle2.android.FragmentEvent;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import io.reactivex.ObservableTransformer;

/**
 * Description ：已兑换
 * Author ： MengYang
 * Email ： 942685687@qq.com
 * Time ： 2018/8/21.
 */
public class CouponConvertibilityFragment extends BaseFragment<CouponListContract.View, CouponListContract.Presenter> implements CouponListContract.View {

    @BindView(R.id.srl_coupon_convertibility)
    SwipeRefreshLayout srlCouponConvertibility;
    @BindView(R.id.rv_coupon_convertibility)
    RecyclerView rvCouponConvertibility;
    @BindView(R.id.tv_coupon_convertibility_total_num)
    TextView tvCouponConvertibilityTotalNum;

    private CouponConvertibilityAdapter mCouponConvertibilityAdapter;
    private List<CouponListBean.TicketListBean> mCouponConvertibilityBean;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_coupon_convertibility;
    }

    @Override
    public CouponListContract.Presenter createPresenter() {
        return new CouponListPresenter(mContext);
    }

    @Override
    public CouponListContract.View createView() {
        return this;
    }

    @Override
    public void init() {
        LinearLayoutManager NotUseList = new LinearLayoutManager(ApplicationUtil.getContext(), LinearLayoutManager.VERTICAL, false);
        rvCouponConvertibility.setLayoutManager(NotUseList);
        rvCouponConvertibility.setHasFixedSize(true);
        rvCouponConvertibility.setItemAnimator(new DefaultItemAnimator());

        mCouponConvertibilityBean = new ArrayList<>();
        mCouponConvertibilityAdapter = new CouponConvertibilityAdapter(mCouponConvertibilityBean);
        rvCouponConvertibility.setAdapter(mCouponConvertibilityAdapter);
    }

    @Override
    public void initListener() {
        srlCouponConvertibility.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                initData();
                srlCouponConvertibility.setRefreshing(false);
            }
        });
    }

    @Override
    public void initData() {
        getPresenter().getCouponList(true, true);
    }

    @Override
    public void getCouponList(CouponListBean data) {
        mCouponConvertibilityBean.clear();
        for (int i = 0; i < data.getTicket_list().size(); i++) {
            if (data.getTicket_list().get(i).getCoupon_status().equals("1"))
                mCouponConvertibilityBean.add(data.getTicket_list().get(i));
        }
        tvCouponConvertibilityTotalNum.setText("共" + mCouponConvertibilityBean.size() + "张券");
        mCouponConvertibilityAdapter = new CouponConvertibilityAdapter(mCouponConvertibilityBean);
        rvCouponConvertibility.setAdapter(mCouponConvertibilityAdapter);
        mCouponConvertibilityAdapter.setEmptyView(R.layout.null_data, rvCouponConvertibility);
    }

    @Override
    public void setMsg(String msg) {

    }

    @Override
    public <T> ObservableTransformer<T, T> bindLifecycle() {
        return this.bindUntilEvent(FragmentEvent.PAUSE);
    }
}
