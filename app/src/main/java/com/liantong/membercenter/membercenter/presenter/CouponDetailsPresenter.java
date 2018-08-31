package com.liantong.membercenter.membercenter.presenter;

import android.content.Context;

import com.liantong.membercenter.membercenter.bean.CouponDetailsBean;
import com.liantong.membercenter.membercenter.contract.CouponDetailsContract;
import com.liantong.membercenter.membercenter.model.CouponDetailsModel;
import com.liantong.membercenter.membercenter.progress.ObserverResponseListener;
import com.liantong.membercenter.membercenter.utils.ExceptionHandle;
import com.liantong.membercenter.membercenter.utils.LogUtils;
import com.liantong.membercenter.membercenter.utils.ToastUtil;

import java.util.TreeMap;

/**
 * 作者：rmy on 2017/12/27 10:34
 * 邮箱：942685687@qq.com
 */
public class CouponDetailsPresenter extends CouponDetailsContract.Presenter {

    private CouponDetailsModel model;
    private Context context;

    public CouponDetailsPresenter(Context context) {
        this.model = new CouponDetailsModel();
        this.context = context;
    }

    @Override
    public void getCouponDetails(TreeMap<String, String> map, boolean isDialog, boolean cancelable) {
        model.getCouponDetails(context, map, isDialog, cancelable, getView().bindLifecycle(), new ObserverResponseListener() {
            @Override
            public void onNext(Object o) {
                LogUtils.i("rmy", "o = " + o);
                //这一步是必须的，判断view是否已经被销毁
                if (getView() != null) {
                    getView().getCouponDetails((CouponDetailsBean) o);
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