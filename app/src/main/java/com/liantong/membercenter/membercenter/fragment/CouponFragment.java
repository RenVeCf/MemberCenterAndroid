package com.liantong.membercenter.membercenter.fragment;

import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;

import com.gyf.barlibrary.ImmersionBar;
import com.liantong.membercenter.membercenter.R;
import com.liantong.membercenter.membercenter.adapter.ViewPagerAdapter;
import com.liantong.membercenter.membercenter.base.BaseFragment;
import com.liantong.membercenter.membercenter.common.view.NavitationLayout;
import com.liantong.membercenter.membercenter.common.view.TopView;
import com.liantong.membercenter.membercenter.contract.CouponListContract;
import com.liantong.membercenter.membercenter.utils.ApplicationUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Description ：优惠券
 * Author ： MengYang
 * Email ： 942685687@qq.com
 * Time ： 2018/8/19.
 */
public class CouponFragment extends BaseFragment {

    @BindView(R.id.nl_coupon)
    NavitationLayout nlCoupon;
    @BindView(R.id.vp_coupon)
    ViewPager vpCoupon;
    @BindView(R.id.tv_coupon_top)
    TopView tvCouponTop;

    private String[] titles1 = new String[]{ApplicationUtil.getContext().getResources().getString(R.string.not_use), ApplicationUtil.getContext().getResources().getString(R.string.failed), ApplicationUtil.getContext().getResources().getString(R.string.convertibility)};
    private ViewPagerAdapter viewPagerAdapter;
    private List<Fragment> fragments1;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_coupon;
    }

    @Override
    public CouponListContract.Presenter createPresenter() {
        return null;
    }

    @Override
    public CouponListContract.View createView() {
        return null;
    }

    @Override
    public void init() {
        //防止状态栏和标题重叠
        ImmersionBar.setTitleBar(getActivity(), tvCouponTop);

        //向集合添加Fragment
        fragments1 = new ArrayList<>();
        fragments1.add(new CouponNotUsedFragment());
        fragments1.add(new CouponFailedFragment());
        fragments1.add(new CouponConvertibilityFragment());
        viewPagerAdapter = new ViewPagerAdapter(getActivity().getSupportFragmentManager(), fragments1);
        vpCoupon.setAdapter(viewPagerAdapter);

        //设置导航条
        nlCoupon.setViewPager(getActivity(), titles1, vpCoupon, R.color.black, R.color.bg_captcha, 16, 16, 0, 45, true);
        nlCoupon.setBgLine(getActivity(), 1, R.color.bg_line);
        nlCoupon.setNavLine(getActivity(), 3, R.color.bg_captcha, 0);
    }

    @Override
    public void initListener() {

    }

    @Override
    public void initData() {

    }
}
