package com.liantong.membercenter.membercenter.fragment;

import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;

import com.gyf.barlibrary.ImmersionBar;
import com.liantong.membercenter.membercenter.R;
import com.liantong.membercenter.membercenter.adapter.ViewPagerAdapter;
import com.liantong.membercenter.membercenter.base.BaseFragment;
import com.liantong.membercenter.membercenter.bean.CouponListBean;
import com.liantong.membercenter.membercenter.common.view.NavitationLayout;
import com.liantong.membercenter.membercenter.common.view.TopView;
import com.liantong.membercenter.membercenter.contract.CouponListContract;
import com.liantong.membercenter.membercenter.presenter.CouponListPresenter;
import com.liantong.membercenter.membercenter.utils.ApplicationUtil;
import com.liantong.membercenter.membercenter.utils.ToastUtil;
import com.trello.rxlifecycle2.android.FragmentEvent;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import io.reactivex.ObservableTransformer;

/**
 * Description ：优惠券
 * Author ： MengYang
 * Email ： 942685687@qq.com
 * Time ： 2018/8/19.
 */
public class CouponFragment extends BaseFragment<CouponListContract.View, CouponListContract.Presenter> implements CouponListContract.View {

    private String[] titles1 = new String[]{ApplicationUtil.getContext().getResources().getString(R.string.not_use), ApplicationUtil.getContext().getResources().getString(R.string.failed), ApplicationUtil.getContext().getResources().getString(R.string.convertibility)};
    private ViewPagerAdapter mViewPagerAdapter;
    private List<Fragment> fragments1;

    @BindView(R.id.nl_coupon)
    NavitationLayout nlCoupon;
    @BindView(R.id.vp_coupon)
    ViewPager vpCoupon;
    @BindView(R.id.tv_coupon_top)
    TopView tvCouponTop;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_coupon;
    }

    @Override
    public CouponListContract.Presenter createPresenter() {
        return new CouponListPresenter(mContext);
    }

    @Override
    public CouponListContract.View createView() {
        return this;
    }

    @Override
    public void init() {
        //防止状态栏和标题重叠
        ImmersionBar.setTitleBar(getActivity(), tvCouponTop);

        fragments1 = new ArrayList<>();
        fragments1.add(new CouponNotUsedFragment());
        fragments1.add(new CouponFailedFragment());
        fragments1.add(new CouponConvertibilityFragment());
        mViewPagerAdapter = new ViewPagerAdapter(getActivity().getSupportFragmentManager(), fragments1);
        vpCoupon.setAdapter(mViewPagerAdapter);
        vpCoupon.setOffscreenPageLimit(2);

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

    @Override
    public void getCouponList(CouponListBean data) {
    }

    @Override
    public void setMsg(String msg) {
        ToastUtil.showShortToast(msg);
    }

    @Override
    public <T> ObservableTransformer<T, T> bindLifecycle() {
        return this.bindUntilEvent(FragmentEvent.PAUSE);
    }
}
