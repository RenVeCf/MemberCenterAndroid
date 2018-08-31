package com.liantong.membercenter.membercenter.fragment;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.liantong.membercenter.membercenter.R;
import com.liantong.membercenter.membercenter.adapter.NotUseAdapter;
import com.liantong.membercenter.membercenter.base.BaseFragment;
import com.liantong.membercenter.membercenter.bean.CouponListBean;
import com.liantong.membercenter.membercenter.contract.CouponListContract;
import com.liantong.membercenter.membercenter.presenter.CouponListPresenter;
import com.liantong.membercenter.membercenter.utils.ApplicationUtil;
import com.liantong.membercenter.membercenter.utils.ToastUtil;
import com.trello.rxlifecycle2.android.FragmentEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import butterknife.BindView;
import io.reactivex.ObservableTransformer;

/**
 * Description ：未使用
 * Author ： MengYang
 * Email ： 942685687@qq.com
 * Time ： 2018/8/21.
 */
public class CouponNotUsedFragment extends BaseFragment<CouponListContract.View, CouponListContract.Presenter> implements CouponListContract.View {

    @BindView(R.id.srl_not_use)
    SwipeRefreshLayout srlNotUse;
    @BindView(R.id.rv_not_use)
    RecyclerView rvNotUse;
    @BindView(R.id.tv_not_use_total_num)
    TextView tvNotUseTotalNum;

    private NotUseAdapter notUseAdapter;
    private List<CouponListBean.TicketListBean> notUseBean;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_coupon_not_use;
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
        rvNotUse.setLayoutManager(NotUseList);
        rvNotUse.setHasFixedSize(true);
        rvNotUse.setItemAnimator(new DefaultItemAnimator());

        notUseBean = new ArrayList<>();
        notUseAdapter = new NotUseAdapter(notUseBean);
        rvNotUse.setAdapter(notUseAdapter);
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
        TreeMap<String, String> map = new TreeMap<>();
        getPresenter().getCouponList(map, true, true);
    }

    @Override
    public void getCouponList(CouponListBean data) {
        notUseBean.clear();
        for (int i = 0; i < data.getTicket_list().size(); i++) {
            if (data.getTicket_list().get(i).getCoupon_status().equals("0"))
                notUseBean.add(data.getTicket_list().get(i));
        }
        tvNotUseTotalNum.setText("共" + notUseBean.size() + "张券");
        notUseAdapter = new NotUseAdapter(notUseBean);
        rvNotUse.setAdapter(notUseAdapter);
        notUseAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                ToastUtil.showShortToast(position);
            }
        });
    }

    @Override
    public void setMsg(String msg) {

    }

    @Override
    public <T> ObservableTransformer<T, T> bindLifecycle() {
        return this.bindUntilEvent(FragmentEvent.PAUSE);
    }
}
