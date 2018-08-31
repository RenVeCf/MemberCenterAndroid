package com.liantong.membercenter.membercenter.adapter;

import android.graphics.Color;
import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.liantong.membercenter.membercenter.R;
import com.liantong.membercenter.membercenter.bean.GrowthValueBean;

import java.util.List;

/**
 * Description ：成长值适配器
 * Author ： MengYang
 * Email ： 942685687@qq.com
 * Time ： 2018/8/27.
 */
public class GrowthValueAdapter extends BaseQuickAdapter<GrowthValueBean.GrowthValueItemBean, BaseViewHolder> {
    public GrowthValueAdapter(@Nullable List<GrowthValueBean.GrowthValueItemBean> data) {
        super(R.layout.adapter_growth_value, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, GrowthValueBean.GrowthValueItemBean item) {

        helper.setText(R.id.tv_growth_value_date, item.getCreate_time())
                .setText(R.id.tv_growth_value_describe, item.getDesc())
                .setText(R.id.tv_growth_value_exp, item.getExp());
        if (item.getFlow_type().equals("0"))
            helper.setText(R.id.tv_growth_value_exp_type, "-")
                    .setTextColor(R.id.tv_growth_value_exp_type, Color.RED)
                    .setTextColor(R.id.tv_growth_value_exp, Color.RED);
        else
            helper.setText(R.id.tv_growth_value_exp_type, "+")
                    .setTextColor(R.id.tv_growth_value_exp_type, Color.GREEN)
                    .setTextColor(R.id.tv_growth_value_exp, Color.GREEN);
    }
}
