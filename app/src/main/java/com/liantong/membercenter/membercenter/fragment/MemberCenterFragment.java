package com.liantong.membercenter.membercenter.fragment;

import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import com.gyf.barlibrary.ImmersionBar;
import com.liantong.membercenter.membercenter.R;
import com.liantong.membercenter.membercenter.activity.GrowthValueActivity;
import com.liantong.membercenter.membercenter.activity.RegisterActivity;
import com.liantong.membercenter.membercenter.activity.VipCardDeclarationsActivity;
import com.liantong.membercenter.membercenter.activity.VipManualActivity;
import com.liantong.membercenter.membercenter.base.BaseFragment;
import com.liantong.membercenter.membercenter.base.BaseResponse;
import com.liantong.membercenter.membercenter.bean.LoginBean;
import com.liantong.membercenter.membercenter.contract.LoginContract;
import com.liantong.membercenter.membercenter.presenter.LoginPresenter;
import com.liantong.membercenter.membercenter.utils.ToastUtil;
import com.liantong.membercenter.membercenter.utils.TopView;
import com.trello.rxlifecycle2.android.FragmentEvent;

import java.util.TreeMap;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.ObservableTransformer;

/**
 * 作者：rmy on 2017/12/27 16:36
 * 邮箱：942685687@qq.com
 */

public class MemberCenterFragment extends BaseFragment<LoginContract.View, LoginContract.Presenter> implements LoginContract.View {

    @BindView(R.id.tv_member_center_top)
    TopView tvMemberCenterTop;
    @BindView(R.id.tv_growth_value)
    TextView tvGrowthValue;


    @Override
    public int getLayoutId() {
        return R.layout.fragment_member_center;
    }

    @Override
    public LoginContract.Presenter createPresenter() {
        return new LoginPresenter(mContext);
    }

    @Override
    public LoginContract.View createView() {
        return this;
    }

    @Override
    public void init() {
        //防止状态栏和标题重叠
        ImmersionBar.setTitleBar(getActivity(), tvMemberCenterTop);
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
        ToastUtil.showShortToast(msg);
    }

    @Override
    public <T> ObservableTransformer<T, T> bindLifecycle() {
        return this.bindUntilEvent(FragmentEvent.PAUSE);
    }

    @OnClick({R.id.tv_growth_value})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_growth_value:
                startActivity(new Intent(getActivity(), GrowthValueActivity.class));
                break;
        }
    }
}
