package com.liantong.membercenter.membercenter.contract;

import com.liantong.membercenter.membercenter.base.BasePresenter;
import com.liantong.membercenter.membercenter.base.BaseResponse;
import com.liantong.membercenter.membercenter.base.BaseView;
import com.liantong.membercenter.membercenter.bean.LoginBean;

import java.util.TreeMap;

import io.reactivex.ObservableTransformer;

/**
 * Description ：
 * Author ： MengYang
 * Email ： 942685687@qq.com
 * Time ： 2018/8/18.
 */
public interface RegisterContract {
    interface View extends BaseView {
        //不同的Bean单独处理
        void resultRegister(BaseResponse<LoginBean> data);

        void resultCaptcha(BaseResponse data);

        void setMsg(String msg);

        <T> ObservableTransformer<T, T> bindLifecycle();
    }

    abstract class Presenter extends BasePresenter<RegisterContract.View> {
        public abstract void register(TreeMap<String, String> map, boolean isDialog, boolean cancelable);

        public abstract void captcha(TreeMap<String, String> map, boolean isDialog, boolean cancelable);
    }
}
