package com.liantong.membercenter.membercenter.fragment;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RadioButton;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.gyf.barlibrary.ImmersionBar;
import com.liantong.membercenter.membercenter.R;
import com.liantong.membercenter.membercenter.activity.UndeterminedActivity;
import com.liantong.membercenter.membercenter.activity.WebViewActivity;
import com.liantong.membercenter.membercenter.base.BaseFragment;
import com.liantong.membercenter.membercenter.bean.AcitvitiesBean;
import com.liantong.membercenter.membercenter.common.config.IConstants;
import com.liantong.membercenter.membercenter.common.view.TopView;
import com.liantong.membercenter.membercenter.contract.ActivitiesContract;
import com.liantong.membercenter.membercenter.presenter.ActivitiesPresenter;
import com.liantong.membercenter.membercenter.utils.ApplicationUtil;
import com.liantong.membercenter.membercenter.utils.DisplayUtils;
import com.liantong.membercenter.membercenter.utils.SPUtil;
import com.trello.rxlifecycle2.android.FragmentEvent;
import com.zhouwei.mzbanner.MZBannerView;
import com.zhouwei.mzbanner.holder.MZHolderCreator;
import com.zhouwei.mzbanner.holder.MZViewHolder;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.ObservableTransformer;

/**
 * Description ：活动
 * Author ： MengYang
 * Email ： 942685687@qq.com
 * Time ： 2018/8/20.
 */
public class ActivitiesFragment extends BaseFragment<ActivitiesContract.View, ActivitiesContract.Presenter> implements ActivitiesContract.View {

    @BindView(R.id.tv_activities_top)
    TopView tvActivitiesTop;
    @BindView(R.id.mzb_activities_loop)
    MZBannerView mzbActivitiesLoop;
    @BindView(R.id.iv_activities_center)
    ImageView ivActivitiesCenter;
    @BindView(R.id.iv_activities_bottom)
    ImageView ivActivitiesBottom;
    @BindView(R.id.rb_weekly_receivez)
    RadioButton rbWeeklyReceivez;
    @BindView(R.id.rb_everyday_sign)
    RadioButton rbEverydaySign;
    @BindView(R.id.rb_electronic_coupons)
    RadioButton rbElectronicCoupons;

    //轮播图集合
    private List<String> loop = new ArrayList<>();
    private AcitvitiesBean acitvitiesBean;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_activities;
    }

    @Override
    public ActivitiesContract.Presenter createPresenter() {
        return new ActivitiesPresenter(mContext);
    }

    @Override
    public ActivitiesContract.View createView() {
        return this;
    }

    @Override
    public void init() {
        //防止状态栏和标题重叠
        ImmersionBar.setTitleBar(getActivity(), tvActivitiesTop);
        //设置BannerView 的切换时间间隔
        mzbActivitiesLoop.setDelayedTime(3000);

        ViewGroup.LayoutParams params = mzbActivitiesLoop.getLayoutParams();
        params.width = DisplayUtils.getScreenWidth(getActivity()) + 80;
        mzbActivitiesLoop.setLayoutParams(params);
    }

    @Override
    public void initListener() {

    }

    @Override
    public void initData() {
        getPresenter().getActivities(true, false);
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            if ((Boolean) SPUtil.get(getActivity(), String.valueOf(IConstants.IS_REFRESH), false) == true)
                initData();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        mzbActivitiesLoop.pause();//暂停轮播
    }

    @Override
    public void onResume() {
        super.onResume();
        mzbActivitiesLoop.start();//开始轮播
    }

    @Override
    public void getActivities(AcitvitiesBean data) {
        acitvitiesBean = data;
        for (int i = 0; i < acitvitiesBean.getBanners().size(); i++) {
            loop.add(acitvitiesBean.getBanners().get(i).getImage());
        }
        loop.add(acitvitiesBean.getBanners().get(0).getImage());
        SPUtil.put(getActivity(), String.valueOf(IConstants.IS_REFRESH), false);
        // 设置数据
        mzbActivitiesLoop.setPages(loop, new MZHolderCreator<BannerViewHolder>() {
            @Override
            public BannerViewHolder createViewHolder() {
                return new BannerViewHolder();
            }
        });


        if (acitvitiesBean.getImages().size() > 0)
            Glide.with(getActivity()).load(acitvitiesBean.getImages().get(0).getImage()).apply(new RequestOptions().placeholder(R.mipmap.ic_launcher_round)).into(ivActivitiesCenter);
        if (acitvitiesBean.getImages().size() > 1)
            Glide.with(getActivity()).load(acitvitiesBean.getImages().get(1).getImage()).apply(new RequestOptions().placeholder(R.mipmap.ic_launcher_round)).into(ivActivitiesBottom);
    }

    public static class BannerViewHolder implements MZViewHolder<String> {
        private ImageView mImageView;

        @Override
        public View createView(Context context) {
            // 返回页面布局
            View view = LayoutInflater.from(context).inflate(R.layout.banner_item, null);
            mImageView = (ImageView) view.findViewById(R.id.banner_img);
            return view;
        }

        @Override
        public void onBind(Context context, int position, String data) {
            // 数据绑定
            Glide.with(ApplicationUtil.getContext()).load(data).into(mImageView);
        }
    }

    @Override
    public void setMsg(String msg) {

    }

    @Override
    public <T> ObservableTransformer<T, T> bindLifecycle() {
        return this.bindUntilEvent(FragmentEvent.PAUSE);
    }

    @OnClick({R.id.iv_activities_center, R.id.iv_activities_bottom, R.id.rb_weekly_receivez, R.id.rb_everyday_sign, R.id.rb_electronic_coupons})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_activities_center:
                if (!acitvitiesBean.getImages().get(0).getLand_page().equals(""))
                    startActivity(new Intent(getActivity(), WebViewActivity.class).putExtra("land_page", acitvitiesBean.getImages().get(0).getLand_page()));
                else
                    startActivity(new Intent(getActivity(), UndeterminedActivity.class));
                break;
            case R.id.iv_activities_bottom:
                if (!acitvitiesBean.getImages().get(0).getLand_page().equals(""))
                    startActivity(new Intent(getActivity(), WebViewActivity.class).putExtra("land_page", acitvitiesBean.getImages().get(1).getLand_page()));
                else
                    startActivity(new Intent(getActivity(), UndeterminedActivity.class));
                break;
            case R.id.rb_weekly_receivez:
                startActivity(new Intent(getActivity(), WebViewActivity.class).putExtra("land_page", acitvitiesBean.getNavs().get(1).getLand_page()));
                break;
            case R.id.rb_everyday_sign:
                startActivity(new Intent(getActivity(), WebViewActivity.class).putExtra("land_page", acitvitiesBean.getNavs().get(0).getLand_page()));
                break;
            case R.id.rb_electronic_coupons:
                startActivity(new Intent(getActivity(), WebViewActivity.class).putExtra("land_page", acitvitiesBean.getNavs().get(2).getLand_page()));
                break;
        }
    }
}
