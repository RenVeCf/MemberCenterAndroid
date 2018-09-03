package com.liantong.membercenter.membercenter.activity;

import com.gyf.barlibrary.ImmersionBar;
import com.liantong.membercenter.membercenter.R;
import com.liantong.membercenter.membercenter.base.BaseActivity;
import com.liantong.membercenter.membercenter.base.BasePresenter;
import com.liantong.membercenter.membercenter.base.BaseView;
import com.liantong.membercenter.membercenter.common.view.TopView;

import butterknife.BindView;

public class UndeterminedActivity extends BaseActivity {

    @BindView(R.id.tv_undetermined_top)
    TopView tvUndeterminedTop;

    @Override
    public int getLayoutId() {
        return R.layout.activity_undetermined;
    }

    @Override
    public BasePresenter createPresenter() {
        return null;
    }

    @Override
    public BaseView createView() {
        return null;
    }

    @Override
    public void init() {
        //防止状态栏和标题重叠
        ImmersionBar.setTitleBar(this, tvUndeterminedTop);
    }

    @Override
    public void initListener() {

    }

    @Override
    public void initData() {

    }
}
