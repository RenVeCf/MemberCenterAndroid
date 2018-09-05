package com.liantong.membercenter.membercenter.presenter;

import android.content.Context;

import com.liantong.membercenter.membercenter.bean.CouponDetailsBtUrlBean;
import com.liantong.membercenter.membercenter.contract.CouponDetailsBtUrlContract;
import com.liantong.membercenter.membercenter.model.CouponDetailsBtUrlModel;
import com.liantong.membercenter.membercenter.progress.ObserverResponseListener;
import com.liantong.membercenter.membercenter.utils.ExceptionHandle;
import com.liantong.membercenter.membercenter.utils.ToastUtil;

import java.util.TreeMap;

/**
 * Description ：
 * Author ： MengYang
 * Email ： 942685687@qq.com
 * Time ： 2018/8/26.
 */
public class CouponDetailsBtUrlPresenter extends CouponDetailsBtUrlContract.Presenter {

    private CouponDetailsBtUrlModel model;
    private Context context;

    public CouponDetailsBtUrlPresenter(Context context) {
        this.model = new CouponDetailsBtUrlModel();
        this.context = context;
    }

    @Override
    public void getCouponDetailsBtUrl(TreeMap<String, String> map, boolean isDialog, boolean cancelable) {
        model.getCouponDetailsBtUrl(context, map, isDialog, cancelable, getView().bindLifecycle(), new ObserverResponseListener() {
            @Override
            public void onNext(Object o) {
                //这一步是必须的，判断view是否已经被销毁
                if (getView() != null) {
                    getView().getCouponDetailsBtUrl((CouponDetailsBtUrlBean) o);
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