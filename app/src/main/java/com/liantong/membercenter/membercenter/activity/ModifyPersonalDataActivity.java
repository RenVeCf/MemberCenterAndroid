package com.liantong.membercenter.membercenter.activity;

import com.gyf.barlibrary.ImmersionBar;
import com.liantong.membercenter.membercenter.R;
import com.liantong.membercenter.membercenter.base.BaseActivity;
import com.liantong.membercenter.membercenter.base.BaseResponse;
import com.liantong.membercenter.membercenter.bean.LoginBean;
import com.liantong.membercenter.membercenter.contract.LoginContract;
import com.liantong.membercenter.membercenter.presenter.LoginPresenter;
import com.liantong.membercenter.membercenter.presenter.RegisterPresenter;
import com.liantong.membercenter.membercenter.utils.TopView;

import butterknife.BindView;
import io.reactivex.ObservableTransformer;

public class ModifyPersonalDataActivity extends BaseActivity<LoginContract.View, LoginContract.Presenter> implements LoginContract.View {

    @BindView(R.id.tv_modify_personal_data_top)
    TopView tvModifyPersonalDataTop;

    @Override
    public int getLayoutId() {
        return R.layout.activity_modify_personal_data;
    }

    @Override
    public LoginContract.Presenter createPresenter() {
        return new LoginPresenter(this);
    }

    @Override
    public LoginContract.View createView() {
        return this;
    }

    @Override
    public void init() {
        //防止状态栏和标题重叠
        ImmersionBar.setTitleBar(this, tvModifyPersonalDataTop);
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
        return this.bindToLifecycle();
    }
}
