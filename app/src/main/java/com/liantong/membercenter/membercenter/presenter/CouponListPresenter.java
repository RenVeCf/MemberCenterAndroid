package com.liantong.membercenter.membercenter.presenter;

import android.content.Context;

import com.liantong.membercenter.membercenter.bean.CouponListBean;
import com.liantong.membercenter.membercenter.contract.CouponListContract;
import com.liantong.membercenter.membercenter.model.CouponListModel;
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
public class CouponListPresenter extends CouponListContract.Presenter {

    private CouponListModel model;
    private Context context;

    public CouponListPresenter(Context context) {
        this.model = new CouponListModel();
        this.context = context;
    }

    @Override
    public void getCouponList(boolean isDialog, boolean cancelable) {
        model.getCouponList(context, isDialog, cancelable, new ObserverResponseListener() {
            @Override
            public void onNext(Object o) {
                //这一步是必须的，判断view是否已经被销毁
                if (getView() != null) {
                    getView().getCouponList((CouponListBean) o);
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