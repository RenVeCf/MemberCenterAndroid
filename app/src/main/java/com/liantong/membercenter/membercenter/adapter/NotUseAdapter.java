package com.liantong.membercenter.membercenter.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.liantong.membercenter.membercenter.R;
import com.liantong.membercenter.membercenter.bean.CouponListBean;
import com.liantong.membercenter.membercenter.utils.ApplicationUtil;

import java.util.List;

/**
 * Description ：未使用适配器
 * Author ： MengYang
 * Email ： 942685687@qq.com
 * Time ： 2018/8/27.
 */
public class NotUseAdapter extends BaseQuickAdapter<CouponListBean.TicketListBean, BaseViewHolder> {

    public NotUseAdapter(@Nullable List<CouponListBean.TicketListBean> data) {
        super(R.layout.adapter_not_use, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, CouponListBean.TicketListBean item) {

        helper.setText(R.id.tv_not_use_coupon_name, item.getTicket_name())
                .setText(R.id.tv_not_use_coupon_time, ApplicationUtil.getContext().getResources().getString(R.string.use_date) + item.getUse_date());

        switch (item.getTicket_type()) {
            case "1":
                helper.setText(R.id.tv_not_use_coupon, ApplicationUtil.getContext().getResources().getString(R.string.offset_coupon));
                helper.setImageDrawable(R.id.iv_not_use_coupon, ApplicationUtil.getContext().getResources().getDrawable(R.drawable.ic_offset_coupon));
                break;
            case "2":
                helper.setText(R.id.tv_not_use_coupon, ApplicationUtil.getContext().getResources().getString(R.string.group_bug_coupon));
                helper.setImageDrawable(R.id.iv_not_use_coupon, ApplicationUtil.getContext().getResources().getDrawable(R.drawable.ic_group_bug_coupon));
                break;
            case "3":
                helper.setText(R.id.tv_not_use_coupon, ApplicationUtil.getContext().getResources().getString(R.string.cash_coupon));
                helper.setImageDrawable(R.id.iv_not_use_coupon, ApplicationUtil.getContext().getResources().getDrawable(R.drawable.ic_cash_coupon));
                break;
            case "4":
                helper.setText(R.id.tv_not_use_coupon, ApplicationUtil.getContext().getResources().getString(R.string.exchange_coupon));
                helper.setImageDrawable(R.id.iv_not_use_coupon, ApplicationUtil.getContext().getResources().getDrawable(R.drawable.ic_exchange_coupon));
                break;
            case "5":
                helper.setText(R.id.tv_not_use_coupon, ApplicationUtil.getContext().getResources().getString(R.string.discount_coupon));
                helper.setImageDrawable(R.id.iv_not_use_coupon, ApplicationUtil.getContext().getResources().getDrawable(R.drawable.ic_discount_coupon));
                break;
        }
    }
}
