package com.liantong.membercenter.membercenter.adapter;

import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.liantong.membercenter.membercenter.R;
import com.liantong.membercenter.membercenter.bean.NotUseBean;
import com.liantong.membercenter.membercenter.utils.ApplicationUtil;

import java.util.List;

/**
 * Created by Mengyang on 2018/8/23
 */
public class NotUseAdapter extends BaseQuickAdapter<String, BaseViewHolder> {
    public NotUseAdapter(@Nullable List<String> data) {
        super(R.layout.adapter_not_use, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
//        helper.setText(控件ID, 数据)
//                .setText(控件ID, 数据);
//        Glide.with(ApplicationUtil.getContext()).load(图片URL).apply(new RequestOptions().placeholder(省略图)).into((ImageView) helper.getView(imgviewID));
//        helper.addOnClickListener(点击的ID);
    }
}
