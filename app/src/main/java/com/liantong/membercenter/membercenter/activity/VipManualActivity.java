package com.liantong.membercenter.membercenter.activity;

import com.gyf.barlibrary.ImmersionBar;
import com.liantong.membercenter.membercenter.R;
import com.liantong.membercenter.membercenter.base.BaseActivity;
import com.liantong.membercenter.membercenter.base.BasePresenter;
import com.liantong.membercenter.membercenter.base.BaseView;
import com.liantong.membercenter.membercenter.common.view.TopView;
import com.liantong.membercenter.membercenter.utils.ApplicationUtil;

import butterknife.BindView;

/**
 * Description ：会员手册
 * Author ： MengYang
 * Email ： 942685687@qq.com
 * Time ： 2018/8/27.
 */

public class VipManualActivity extends BaseActivity {

    @BindView(R.id.tv_vip_manual_top)
    TopView tvVipManualTop;

    @Override
    public int getLayoutId() {
        return R.layout.activity_vip_manual;
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
        //将每个Activity加入到栈中
        ApplicationUtil.getManager().addActivity(this);
        //防止状态栏和标题重叠
        ImmersionBar.setTitleBar(this, tvVipManualTop);
    }

    @Override
    public void initListener() {

    }

    @Override
    public void initData() {

    }
}
