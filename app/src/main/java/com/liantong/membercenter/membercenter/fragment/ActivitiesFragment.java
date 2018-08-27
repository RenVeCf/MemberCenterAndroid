package com.liantong.membercenter.membercenter.fragment;

import com.gyf.barlibrary.ImmersionBar;
import com.liantong.membercenter.membercenter.R;
import com.liantong.membercenter.membercenter.base.BaseFragment;
import com.liantong.membercenter.membercenter.base.BaseResponse;
import com.liantong.membercenter.membercenter.bean.LoginBean;
import com.liantong.membercenter.membercenter.contract.LoginContract;
import com.liantong.membercenter.membercenter.common.view.TopView;
import com.ryane.banner.AdPageInfo;
import com.ryane.banner.AdPlayBanner;
import com.trello.rxlifecycle2.android.FragmentEvent;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import io.reactivex.ObservableTransformer;

import static com.ryane.banner.AdPlayBanner.ImageLoaderType.GLIDE;
import static com.ryane.banner.AdPlayBanner.IndicatorType.POINT_INDICATOR;

/**
 * Description ：活动
 * Author ： MengYang
 * Email ： 942685687@qq.com
 * Time ： 2018/8/20.
 */
public class ActivitiesFragment extends BaseFragment<LoginContract.View, LoginContract.Presenter> implements LoginContract.View {

    @BindView(R.id.tv_activities_top)
    TopView tvActivitiesTop;
    @BindView(R.id.apb_activities_loop)
    AdPlayBanner apbActivitiesLoop;
    //轮播图集合
    private List<AdPageInfo> mDatas = new ArrayList<>();

    @Override
    public int getLayoutId() {
        return R.layout.fragment_activities;
    }

    @Override
    public LoginContract.Presenter createPresenter() {
        return null;
    }

    @Override
    public LoginContract.View createView() {
        return null;
    }

    @Override
    public void init() {
        //防止状态栏和标题重叠
        ImmersionBar.setTitleBar(getActivity(), tvActivitiesTop);
        apbActivitiesLoop.measure(0, 0);
        apbActivitiesLoop.setImageViewScaleType(AdPlayBanner.ScaleType.CENTER_CROP);

        for (int i = 0; i < 3; i++) {
            mDatas.add(new AdPageInfo("", "http://dmimg.5054399.com/allimg/optuji/zoro2nh/12.jpg", null, 1));
        }

        apbActivitiesLoop.setImageLoadType(GLIDE);
        apbActivitiesLoop.setOnPageClickListener(new AdPlayBanner.OnPageClickListener() {
            @Override
            public void onPageClick(AdPageInfo info, int postion) {

            }
        });
        //自动滚动
        apbActivitiesLoop.setAutoPlay(true);
        //页码指示器
        apbActivitiesLoop.setIndicatorType(POINT_INDICATOR);
        //间隔时间
        apbActivitiesLoop.setInterval(3000);
        //背景
        apbActivitiesLoop.setBannerBackground(0xffffffff);
        //数据源
        apbActivitiesLoop.setInfoList(mDatas);
        apbActivitiesLoop.setUp();
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
        return this.bindUntilEvent(FragmentEvent.PAUSE);
    }
}
