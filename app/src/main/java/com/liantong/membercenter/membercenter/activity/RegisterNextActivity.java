package com.liantong.membercenter.membercenter.activity;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gyf.barlibrary.ImmersionBar;
import com.liantong.membercenter.membercenter.R;
import com.liantong.membercenter.membercenter.base.BaseActivity;
import com.liantong.membercenter.membercenter.base.BasePresenter;
import com.liantong.membercenter.membercenter.base.BaseView;
import com.liantong.membercenter.membercenter.common.view.TopView;
import com.liantong.membercenter.membercenter.utils.ApplicationUtil;
import com.liantong.membercenter.membercenter.utils.isClickUtil;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Description ：注册成功
 * Author ： MengYang
 * Email ： 942685687@qq.com
 * Time ： 2018/8/27.
 */

public class RegisterNextActivity extends BaseActivity {

    @BindView(R.id.tv_register_success_top)
    TopView tvRegisterSuccessTop;
    @BindView(R.id.bt_member_center_go)
    Button btMemberCenterGo;
    @BindView(R.id.ll_register_to_coupon)
    LinearLayout llRegisterToCoupon;
    @BindView(R.id.tv_register_coupon_name)
    TextView tvRegisterCouponName;

    @Override
    public int getLayoutId() {
        return R.layout.activity_register_next;
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
        ImmersionBar.setTitleBar(this, tvRegisterSuccessTop);
        //注册接口会返回券的名称
        tvRegisterCouponName.setText(getIntent().getStringExtra("getTicket_name"));
    }

    @Override
    public void initListener() {

    }

    @Override
    public void initData() {

    }

    @OnClick({R.id.bt_member_center_go, R.id.ll_register_to_coupon})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.bt_member_center_go:
                if (isClickUtil.isFastClick()) {
                    //跳会员中心的Fragment
                    startActivity(new Intent(this, MemberCenterActivity.class).putExtra("howPage", 0));
                    finish();
                }
                break;
            case R.id.ll_register_to_coupon:
                if (isClickUtil.isFastClick()) {
                    //跳优惠券的Fragment
                    startActivity(new Intent(this, MemberCenterActivity.class).putExtra("howPage", 1));
                    finish();
                }
                break;
        }
    }
}
