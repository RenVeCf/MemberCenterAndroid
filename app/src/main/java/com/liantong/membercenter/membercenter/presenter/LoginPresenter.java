package com.liantong.membercenter.membercenter.presenter;

import android.content.Context;

import com.liantong.membercenter.membercenter.api.ErrorHandler;
import com.liantong.membercenter.membercenter.base.BaseResponse;
import com.liantong.membercenter.membercenter.bean.LoginBean;
import com.liantong.membercenter.membercenter.contract.LoginContract;
import com.liantong.membercenter.membercenter.model.LoginModel;
import com.liantong.membercenter.membercenter.progress.ObserverResponseListener;
import com.liantong.membercenter.membercenter.utils.ExceptionHandle;
import com.liantong.membercenter.membercenter.utils.LogUtils;
import com.liantong.membercenter.membercenter.utils.ToastUtil;

import java.util.TreeMap;

/**
 * 作者：rmy on 2017/12/27 10:34
 * 邮箱：942685687@qq.com
 */
public class LoginPresenter extends LoginContract.Presenter {

    private LoginModel model;
    private Context context;

    public LoginPresenter(Context context) {
        this.model = new LoginModel();
        this.context = context;
    }

    @Override
    public void login(TreeMap<String, String> map, boolean isDialog, boolean cancelable) {
        model.login(context, map, isDialog, cancelable, getView().bindLifecycle(), new ObserverResponseListener() {
            @Override
            public void onNext(Object o) {
                //这一步是必须的，判断view是否已经被销毁
                if (getView() != null) {
                    getView().resultLogin((LoginBean) o);
                }
            }

            @Override
            public void onError(ExceptionHandle.ResponeThrowable e) {
                if (getView() != null) {
                    //// TODO: 2017/12/28 自定义处理异常
                    LogUtils.i("rmy", "e.message = " + e.message);
                    LogUtils.i("rmy", "e.getLocalizedMessage() = " + e.getLocalizedMessage());
                    LogUtils.i("rmy", "e.getMessage() = " + e.getMessage());
                    LogUtils.i("rmy", "e.code = " + e.code);
                    LogUtils.i("rmy", "e.getCause() = " + e.getCause());
                    LogUtils.i("rmy", "e.getStackTrace() = " + e.getStackTrace());
                    LogUtils.i("rmy", "e.getSuppressed() = " + e.getSuppressed());
                    LogUtils.i("rmy", "e.fillInStackTrace() = " + e.fillInStackTrace());
                    LogUtils.i("rmy", "e.toString() = " + e.toString());


                    LogUtils.i("rmy", "error getLocalizedMessage = " + ExceptionHandle.handleException(e).getLocalizedMessage());
                    LogUtils.i("rmy", "error getMessage = " + ExceptionHandle.handleException(e).getMessage());
                    LogUtils.i("rmy", "error code = " + ExceptionHandle.handleException(e).code);
                    ToastUtil.showShortToast(ExceptionHandle.handleException(e).message);
                }
            }
        });
    }

    @Override
    public void captcha(TreeMap<String, String> map, boolean isDialog, boolean cancelable) {
        model.captcha(context, map, isDialog, cancelable, getView().bindLifecycle(), new ObserverResponseListener() {
            @Override
            public void onNext(Object o) {
                //这一步是必须的，判断view是否已经被销毁
                if (getView() != null) {
                    getView().resultCaptcha((BaseResponse) o);
                }
            }

            @Override
            public void onError(ExceptionHandle.ResponeThrowable e) {
                if (getView() != null) {
                    //// TODO: 2017/12/28 自定义处理异常
                    ToastUtil.showShortToast(ExceptionHandle.handleException(e).message);
                }
            }
        });
    }
}