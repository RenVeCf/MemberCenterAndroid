package com.liantong.membercenter.membercenter.fragment;

import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.liantong.membercenter.membercenter.R;
import com.liantong.membercenter.membercenter.activity.CouponDetailsActivity;
import com.liantong.membercenter.membercenter.adapter.NotUseAdapter;
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
        //设置RecyclerView方向和是否反转
        LinearLayoutManager NotUseList = new LinearLayoutManager(ApplicationUtil.getContext(), LinearLayoutManager.VERTICAL, false);
        rvNotUse.setLayoutManager(NotUseList);
        rvNotUse.setHasFixedSize(true); //item如果一样的大小，可以设置为true让RecyclerView避免重新计算大小
        rvNotUse.setItemAnimator(new DefaultItemAnimator()); //默认动画

        //初始化数据
        notUseBean = new ArrayList<>();
        notUseAdapter = new NotUseAdapter(notUseBean);
        rvNotUse.setAdapter(notUseAdapter);
    }

    @Override
    public void initListener() {
        //下拉刷新
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
        getPresenter().getCouponList(true, false);
    }

    @Override
    public void getCouponList(CouponListBean data) {
        //清除数据
        notUseBean.clear();
        //由于后台没做分页，所以这里遍历所有数据，将该品种的券检出
        for (int i = 0; i < data.getTicket_list().size(); i++) {
            if (data.getTicket_list().get(i).getCoupon_status().equals("0"))
                notUseBean.add(data.getTicket_list().get(i));
        }
        tvNotUseTotalNum.setText("共" + notUseBean.size() + "张券");
        notUseAdapter = new NotUseAdapter(notUseBean);
        rvNotUse.setAdapter(notUseAdapter);
        //item整体的点击事件
        notUseAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                CouponNotUsedFragment.this.startActivityForResult(new Intent(getActivity(), CouponDetailsActivity.class).putExtra("ticket_type", notUseBean.get(position).getTicket_type()).putExtra("ticket_name", notUseBean.get(position).getTicket_name()).putExtra("coupon_no", notUseBean.get(position).getCoupon_no()).putExtra("use_date", notUseBean.get(position).getUse_date()).putExtra("ticket_desc", notUseBean.get(position).getTicket_desc()).putExtra("right_template_code", notUseBean.get(position).getRight_template_code()).putExtra("create_time", notUseBean.get(position).getCreate_time()).putExtra("coupon_code", notUseBean.get(position).getCoupon_code()), IConstants.REQUEST_CODE);
            }
        });
        //空数据时的页面
        notUseAdapter.setEmptyView(R.layout.null_data, rvNotUse);
    }

    @Override
    public void setMsg(String msg) {

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == IConstants.REQUEST_CODE && resultCode == 0) {
            SPUtil.put(getActivity(), String.valueOf(IConstants.IS_REFRESH), true);
            initData();
        }
    }

    @Override
    public <T> ObservableTransformer<T, T> bindLifecycle() {
        return this.bindUntilEvent(FragmentEvent.PAUSE);
    }
}
