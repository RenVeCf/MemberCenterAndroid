package com.liantong.membercenter.membercenter.activity;

import android.graphics.drawable.Drawable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RadioButton;

import com.liantong.membercenter.membercenter.R;
import com.liantong.membercenter.membercenter.base.BaseActivity;
import com.liantong.membercenter.membercenter.base.BaseResponse;
import com.liantong.membercenter.membercenter.bean.LoginBean;
import com.liantong.membercenter.membercenter.contract.MemberCenterContract;
import com.liantong.membercenter.membercenter.fragment.ActivitiesFragment;
import com.liantong.membercenter.membercenter.fragment.CouponFragment;
import com.liantong.membercenter.membercenter.fragment.MemberCenterFragment;
import com.liantong.membercenter.membercenter.utils.ApplicationUtil;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.ObservableTransformer;

/**
 * Description ：会员中心
 * Author ： MengYang
 * Email ： 942685687@qq.com
 * Time ： 2018/8/27.
 */

public class MemberCenterActivity extends BaseActivity<MemberCenterContract.View, MemberCenterContract.Presenter> implements MemberCenterContract.View {

    @BindView(R.id.ll_member_center)
    LinearLayout llMemberCenter;
    @BindView(R.id.vp_member_center)
    ViewPager vpMemberCenter;
    @BindView(R.id.rb_navigation_member_center)
    RadioButton rbNavigationMemberCenter;
    @BindView(R.id.rb_navigation_coupon)
    RadioButton rbNavigationCoupon;
    @BindView(R.id.rb_navigation_activities)
    RadioButton rbNavigationActivities;

    private Fragment currentFragment = new Fragment();
    private MemberCenterFragment memberCenterFragment = new MemberCenterFragment();
    private CouponFragment couponFragment = new CouponFragment();
    private ActivitiesFragment activitiesFragment = new ActivitiesFragment();

    @Override
    public int getLayoutId() {
        return R.layout.activity_member_center;
    }

    @Override
    public MemberCenterContract.Presenter createPresenter() {
        return null;
    }

    @Override
    public MemberCenterContract.View createView() {
        return null;
    }

    @Override
    public void init() {
        //将每个Activity加入到栈中
        ApplicationUtil.getManager().addActivity(this);
        //上一层界面跳过来时，要求显示对的碎片
        switch (getIntent().getIntExtra("howPage", 0)) {
            case 0:
                rbNavigationMemberCenter.setChecked(true);
                switchFragment(memberCenterFragment).commit();
                break;
            case 1:
                rbNavigationCoupon.setChecked(true);
                switchFragment(couponFragment).commit();
                break;
        }

        //定义底部标签图片大小
        Drawable drawableFirst = getResources().getDrawable(R.drawable.select_bg_member_center);
        drawableFirst.setBounds(0, 0, 80, 80);//第一0是距左右边距离，第二0是距上下边距离，第三69长度,第四宽度
        rbNavigationMemberCenter.setCompoundDrawables(null, drawableFirst, null, null);//只放上面

        Drawable drawableSearch = getResources().getDrawable(R.drawable.select_bg_coupon);
        drawableSearch.setBounds(0, 0, 80, 80);//第一0是距左右边距离，第二0是距上下边距离，第三69长度,第四宽度
        rbNavigationCoupon.setCompoundDrawables(null, drawableSearch, null, null);//只放上面

        Drawable drawableMe = getResources().getDrawable(R.drawable.select_bg_activities);
        drawableMe.setBounds(0, 0, 80, 80);//第一0是距左右边距离，第二0是距上下边距离，第三69长度,第四宽度
        rbNavigationActivities.setCompoundDrawables(null, drawableMe, null, null);//只放上面
    }

    @Override
    public void initListener() {

    }

    @Override
    public void initData() {

    }

    @Override
    public void resultLogin(BaseResponse<LoginBean> data) {

    }

    @Override
    public void resultCaptcha(BaseResponse data) {

    }

    @Override
    public void setMsg(String msg) {

    }

    @Override
    public <T> ObservableTransformer<T, T> bindLifecycle() {
        return null;
    }

    //Fragment优化
    public FragmentTransaction switchFragment(Fragment targetFragment) {
        FragmentTransaction transaction = getSupportFragmentManager()
                .beginTransaction();
        if (!targetFragment.isAdded()) {
            //第一次使用switchFragment()时currentFragment为null，所以要判断一下
            if (currentFragment != null) {
                transaction.hide(currentFragment);
            }
            transaction.add(R.id.ll_member_center, targetFragment, targetFragment.getClass().getName());

        } else {
            transaction
                    .hide(currentFragment)
                    .show(targetFragment);
        }
        currentFragment = targetFragment;
        return transaction;
    }

    @OnClick({R.id.rb_navigation_member_center, R.id.rb_navigation_coupon, R.id.rb_navigation_activities})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rb_navigation_member_center:
                switchFragment(memberCenterFragment).commit();
                break;
            case R.id.rb_navigation_coupon:
                switchFragment(couponFragment).commit();
                break;
            case R.id.rb_navigation_activities:
                switchFragment(activitiesFragment).commit();
                break;
        }
    }
}
