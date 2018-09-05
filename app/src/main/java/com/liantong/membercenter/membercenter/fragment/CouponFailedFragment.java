package com.liantong.membercenter.membercenter.fragment;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.liantong.membercenter.membercenter.R;
import com.liantong.membercenter.membercenter.adapter.FailedAdapter;
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
 * Description ：已失效
 * Author ： MengYang
 * Email ： 942685687@qq.com
 * Time ： 2018/8/21.
 */
public class CouponFailedFragment extends BaseFragment<CouponListContract.View, CouponListContract.Presenter> implements CouponListContract.View {

    @BindView(R.id.srl_failed)
    SwipeRefreshLayout srlFailed;
    @BindView(R.id.rv_failed)
    RecyclerView rvFailed;
    @BindView(R.id.tv_failed_total_num)
    TextView tvFailedTotalNum;

    private FailedAdapter mFailedAdapter;
    private List<CouponListBean.TicketListBean> mFailedBean;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_coupon_failed;
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
        rvFailed.setLayoutManager(NotUseList);
        rvFailed.setHasFixedSize(true);
        rvFailed.setItemAnimator(new DefaultItemAnimator());

        mFailedBean = new ArrayList<>();
        mFailedAdapter = new FailedAdapter(mFailedBean);
        rvFailed.setAdapter(mFailedAdapter);
    }

    @Override
    public void initListener() {
        srlFailed.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                initData();
                srlFailed.setRefreshing(false);
            }
        });
    }

    @Override
    public void initData() {
        getPresenter().getCouponList(true, true);
    }

    @Override
    public void getCouponList(CouponListBean data) {
        mFailedBean.clear();
        for (int i = 0; i < data.getTicket_list().size(); i++) {
            if (data.getTicket_list().get(i).getCoupon_status().equals("3"))
                mFailedBean.add(data.getTicket_list().get(i));
        }
        tvFailedTotalNum.setText("共" + mFailedBean.size() + "张券");
        mFailedAdapter = new FailedAdapter(mFailedBean);
        rvFailed.setAdapter(mFailedAdapter);
        mFailedAdapter.setEmptyView(R.layout.null_data, rvFailed);
    }

    @Override
    public void setMsg(String msg) {

    }

    @Override
    public <T> ObservableTransformer<T, T> bindLifecycle() {
        return this.bindUntilEvent(FragmentEvent.PAUSE);
    }
}
