package com.liantong.membercenter.membercenter.fragment;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.gyf.barlibrary.ImmersionBar;
import com.liantong.membercenter.membercenter.R;
import com.liantong.membercenter.membercenter.activity.UndeterminedActivity;
import com.liantong.membercenter.membercenter.base.BaseFragment;
import com.liantong.membercenter.membercenter.bean.AcitvitiesBean;
import com.liantong.membercenter.membercenter.common.view.TopView;
import com.liantong.membercenter.membercenter.contract.ActivitiesContract;
import com.liantong.membercenter.membercenter.presenter.ActivitiesPresenter;
import com.ryane.banner.AdPageInfo;
import com.ryane.banner.AdPlayBanner;
import com.trello.rxlifecycle2.android.FragmentEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.ObservableTransformer;

import static com.ryane.banner.AdPlayBanner.ImageLoaderType.GLIDE;
import static com.ryane.banner.AdPlayBanner.IndicatorType.POINT_INDICATOR;

/**
 * Description ：活动
 * Author ： MengYang
 * Email ： 942685687@qq.com
 * Time ： 2018/8/20.
 */
public class ActivitiesFragment extends BaseFragment<ActivitiesContract.View, ActivitiesContract.Presenter> implements ActivitiesContract.View {

    @BindView(R.id.tv_activities_top)
    TopView tvActivitiesTop;
    @BindView(R.id.apb_activities_loop)
    AdPlayBanner apbActivitiesLoop;
    @BindView(R.id.iv_activities_center)
    ImageView ivActivitiesCenter;
    @BindView(R.id.iv_activities_bottom)
    ImageView ivActivitiesBottom;
    //轮播图集合
    private List<AdPageInfo> loop = new ArrayList<>();
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
        apbActivitiesLoop.measure(0, 0);
        apbActivitiesLoop.setImageViewScaleType(AdPlayBanner.ScaleType.CENTER_CROP);
    }

    @Override
    public void initListener() {

    }

    @Override
    public void initData() {
        TreeMap<String, String> activitiesMap = new TreeMap<>();
        getPresenter().getActivities(activitiesMap, true, true);
    }

    @Override
    public void getActivities(AcitvitiesBean data) {
        acitvitiesBean = data;
        for (int i = 0; i < acitvitiesBean.getBanners().size(); i++) {
            loop.add(new AdPageInfo("", acitvitiesBean.getBanners().get(i).getImage(), acitvitiesBean.getBanners().get(i).getFrom(), 1));
        }

        apbActivitiesLoop.setImageLoadType(GLIDE);
        //自动滚动
        apbActivitiesLoop.setAutoPlay(true);
        //页码指示器
        apbActivitiesLoop.setIndicatorType(POINT_INDICATOR);
        //间隔时间
        apbActivitiesLoop.setInterval(3000);
        //背景
        apbActivitiesLoop.setBannerBackground(0xffffffff);
        //数据源
        apbActivitiesLoop.setInfoList(loop);
        apbActivitiesLoop.setUp();

        if (acitvitiesBean.getImages().size() > 0)
            Glide.with(getActivity()).load(acitvitiesBean.getImages().get(0).getImage()).apply(new RequestOptions().placeholder(R.mipmap.ic_launcher_round)).into(ivActivitiesCenter);
        if (acitvitiesBean.getImages().size() > 1)
            Glide.with(getActivity()).load(acitvitiesBean.getImages().get(1).getImage()).apply(new RequestOptions().placeholder(R.mipmap.ic_launcher_round)).into(ivActivitiesBottom);
    }

    @Override
    public void setMsg(String msg) {

    }

    @Override
    public <T> ObservableTransformer<T, T> bindLifecycle() {
        return this.bindUntilEvent(FragmentEvent.PAUSE);
    }

    @OnClick({R.id.iv_activities_center, R.id.iv_activities_bottom})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_activities_center:
                if (acitvitiesBean.getImages().get(0).getLand_page().equals(""))
                    startActivity(new Intent(getActivity(), UndeterminedActivity.class));
                break;
            case R.id.iv_activities_bottom:
                if (acitvitiesBean.getImages().get(0).getLand_page().equals(""))
                    startActivity(new Intent(getActivity(), UndeterminedActivity.class));
                break;
        }
    }
}
