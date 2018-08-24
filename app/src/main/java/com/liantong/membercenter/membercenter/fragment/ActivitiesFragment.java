package com.liantong.membercenter.membercenter.fragment;

import com.gyf.barlibrary.ImmersionBar;
import com.liantong.membercenter.membercenter.R;
import com.liantong.membercenter.membercenter.base.BaseFragment;
import com.liantong.membercenter.membercenter.base.BaseResponse;
import com.liantong.membercenter.membercenter.bean.LoginBean;
import com.liantong.membercenter.membercenter.contract.LoginContract;
import com.liantong.membercenter.membercenter.utils.TopView;
import com.trello.rxlifecycle2.android.FragmentEvent;

import butterknife.BindView;
import io.reactivex.ObservableTransformer;

/**
 * Description ：
 * Author ： MengYang
 * Email ： 942685687@qq.com
 * Time ： 2018/8/20.
 */
public class ActivitiesFragment extends BaseFragment<LoginContract.View, LoginContract.Presenter> implements LoginContract.View {

    @BindView(R.id.tv_activities_top)
    TopView tvActivitiesTop;

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
