package com.liantong.membercenter.membercenter.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.liantong.membercenter.membercenter.R;
import com.liantong.membercenter.membercenter.bean.CouponListBean;
import com.liantong.membercenter.membercenter.utils.ApplicationUtil;
import com.liantong.membercenter.membercenter.utils.LogUtils;

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
        LogUtils.i("rmy", "item.getCoupon_status() = " + item.getCoupon_status());
        switch (item.getCoupon_status()) {
            case "0":
                helper.setText(R.id.tv_not_use_conpon_name, item.getTicket_name())
                        .setText(R.id.tv_not_use_conpon_time, ApplicationUtil.getContext().getResources().getString(R.string.use_date) + item.getCreate_time());

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
                helper.addOnClickListener(R.id.ll_not_use_item);
                break;
            case "1":
                helper.setText(R.id.tv_not_use_conpon_name, item.getTicket_name())
                        .setText(R.id.tv_not_use_conpon_time, ApplicationUtil.getContext().getResources().getString(R.string.use_date) + item.getCreate_time());

                switch (item.getTicket_type()) {
                    case "1":
                        helper.setText(R.id.tv_not_use_coupon, ApplicationUtil.getContext().getResources().getString(R.string.offset_coupon));
                        helper.setImageDrawable(R.id.iv_not_use_coupon, ApplicationUtil.getContext().getResources().getDrawable(R.drawable.ic_failed_offset_coupon));
                        break;
                    case "2":
                        helper.setText(R.id.tv_not_use_coupon, ApplicationUtil.getContext().getResources().getString(R.string.group_bug_coupon));
                        helper.setImageDrawable(R.id.iv_not_use_coupon, ApplicationUtil.getContext().getResources().getDrawable(R.drawable.ic_failed_group_bug_coupon));
                        break;
                    case "3":
                        helper.setText(R.id.tv_not_use_coupon, ApplicationUtil.getContext().getResources().getString(R.string.cash_coupon));
                        helper.setImageDrawable(R.id.iv_not_use_coupon, ApplicationUtil.getContext().getResources().getDrawable(R.drawable.ic_failed_cash_coupon));
                        break;
                    case "4":
                        helper.setText(R.id.tv_not_use_coupon, ApplicationUtil.getContext().getResources().getString(R.string.exchange_coupon));
                        helper.setImageDrawable(R.id.iv_not_use_coupon, ApplicationUtil.getContext().getResources().getDrawable(R.drawable.ic_failed_exchange_coupon));
                        break;
                    case "5":
                        helper.setText(R.id.tv_not_use_coupon, ApplicationUtil.getContext().getResources().getString(R.string.discount_coupon));
                        helper.setImageDrawable(R.id.iv_not_use_coupon, ApplicationUtil.getContext().getResources().getDrawable(R.drawable.ic_failed_discount_coupon));
                        break;
                }
                break;
            case "3":
                helper.setText(R.id.tv_not_use_conpon_name, item.getTicket_name())
                        .setText(R.id.tv_not_use_conpon_time, ApplicationUtil.getContext().getResources().getString(R.string.use_date) + item.getCreate_time());

                switch (item.getTicket_type()) {
                    case "1":
                        helper.setText(R.id.tv_not_use_coupon, ApplicationUtil.getContext().getResources().getString(R.string.offset_coupon));
                        helper.setImageDrawable(R.id.iv_not_use_coupon, ApplicationUtil.getContext().getResources().getDrawable(R.drawable.ic_failed_offset_coupon));
                        break;
                    case "2":
                        helper.setText(R.id.tv_not_use_coupon, ApplicationUtil.getContext().getResources().getString(R.string.group_bug_coupon));
                        helper.setImageDrawable(R.id.iv_not_use_coupon, ApplicationUtil.getContext().getResources().getDrawable(R.drawable.ic_failed_group_bug_coupon));
                        break;
                    case "3":
                        helper.setText(R.id.tv_not_use_coupon, ApplicationUtil.getContext().getResources().getString(R.string.cash_coupon));
                        helper.setImageDrawable(R.id.iv_not_use_coupon, ApplicationUtil.getContext().getResources().getDrawable(R.drawable.ic_failed_cash_coupon));
                        break;
                    case "4":
                        helper.setText(R.id.tv_not_use_coupon, ApplicationUtil.getContext().getResources().getString(R.string.exchange_coupon));
                        helper.setImageDrawable(R.id.iv_not_use_coupon, ApplicationUtil.getContext().getResources().getDrawable(R.drawable.ic_failed_exchange_coupon));
                        break;
                    case "5":
                        helper.setText(R.id.tv_not_use_coupon, ApplicationUtil.getContext().getResources().getString(R.string.discount_coupon));
                        helper.setImageDrawable(R.id.iv_not_use_coupon, ApplicationUtil.getContext().getResources().getDrawable(R.drawable.ic_failed_discount_coupon));
                        break;
                }
                break;
        }
    }
}
