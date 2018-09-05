package com.liantong.membercenter.membercenter.contract;

import com.liantong.membercenter.membercenter.base.BasePresenter;
import com.liantong.membercenter.membercenter.base.BaseView;
import com.liantong.membercenter.membercenter.bean.CouponListBean;

import java.util.TreeMap;

import io.reactivex.ObservableTransformer;

/**
 * Description ：CouponListContract  V 、P契约类
 * Author ： MengYang
 * Email ： 942685687@qq.com
 * Time ： 2018/8/26.
 */
public interface CouponListContract {

    interface View extends BaseView {
        //不同的Bean单独处理
        void getCouponList(CouponListBean data);

        void setMsg(String msg);

        <T> ObservableTransformer<T, T> bindLifecycle();
    }

    abstract class Presenter extends BasePresenter<View> {
        public abstract void getCouponList(boolean isDialog, boolean cancelable);
    }
}