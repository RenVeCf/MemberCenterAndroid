package com.liantong.membercenter.membercenter.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.liantong.membercenter.membercenter.R;

import java.util.List;

/**
 * Created by Mengyang on 2018/8/23
 */
public class FailedAdapter extends BaseQuickAdapter<String, BaseViewHolder> {
    public FailedAdapter(@Nullable List<String> data) {
        super(R.layout.adapter_failed, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
//        helper.setText(控件ID, 数据)
//                .setText(控件ID, 数据);
//        Glide.with(ApplicationUtil.getContext()).load(图片URL).apply(new RequestOptions().placeholder(省略图)).into((ImageView) helper.getView(imgviewID));
//        helper.addOnClickListener(点击的ID);
    }
}
