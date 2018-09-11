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
import com.liantong.membercenter.membercenter.common.config.IConstants;
import com.liantong.membercenter.membercenter.contract.CouponListContract;
import com.liantong.membercenter.membercenter.presenter.CouponListPresenter;
import com.liantong.membercenter.membercenter.utils.ApplicationUtil;
import com.liantong.membercenter.membercenter.utils.SPUtil;
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

    private FailedAdapter failedAdapter;
    private List<CouponListBean.TicketListBean> failedBean;

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
        //设置RecyclerView方向和是否反转
        LinearLayoutManager NotUseList = new LinearLayoutManager(ApplicationUtil.getContext(), LinearLayoutManager.VERTICAL, false);
        rvFailed.setLayoutManager(NotUseList);
        rvFailed.setHasFixedSize(true); //item如果一样的大小，可以设置为true让RecyclerView避免重新计算大小
        rvFailed.setItemAnimator(new DefaultItemAnimator()); //默认动画

        //初始化数据
        failedBean = new ArrayList<>();
        failedAdapter = new FailedAdapter(failedBean);
        rvFailed.setAdapter(failedAdapter);
    }

    @Override
    public void initListener() {
        //下拉刷新
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
        getPresenter().getCouponList(true, false);
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            if ((Boolean) SPUtil.get(getActivity(), String.valueOf(IConstants.IS_REFRESH), false) == true)
                initData();
        }
    }

    @Override
    public void getCouponList(CouponListBean data) {
        //清除数据
        failedBean.clear();
        //由于后台没做分页，所以这里遍历所有数据，将该品种的券检出
        for (int i = 0; i < data.getTicket_list().size(); i++) {
            if (data.getTicket_list().get(i).getCoupon_status().equals("3"))
                failedBean.add(data.getTicket_list().get(i));
        }
        tvFailedTotalNum.setText("共" + failedBean.size() + "张券");
        failedAdapter = new FailedAdapter(failedBean);
        rvFailed.setAdapter(failedAdapter);
        //空数据时的页面
        failedAdapter.setEmptyView(R.layout.null_data, rvFailed);
    }

    @Override
    public void setMsg(String msg) {

    }

    @Override
    public <T> ObservableTransformer<T, T> bindLifecycle() {
        return this.bindUntilEvent(FragmentEvent.PAUSE);
    }
}
