package com.liantong.membercenter.membercenter.presenter;

import android.content.Context;

import com.liantong.membercenter.membercenter.bean.GrowthValueBean;
import com.liantong.membercenter.membercenter.bean.UserInfoBean;
import com.liantong.membercenter.membercenter.contract.GrowthValueContract;
import com.liantong.membercenter.membercenter.model.GrowthValueModel;
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
public class GrowthValuePresenter extends GrowthValueContract.Presenter {

    private GrowthValueModel model;
    private Context context;

    public GrowthValuePresenter(Context context) {
        this.model = new GrowthValueModel();
        this.context = context;
    }

    @Override
    public void getGrowthValueList(TreeMap<String, String> map, boolean isDialog, boolean cancelable) {
        model.getGrowthValueList(context, map, isDialog, cancelable, getView().bindLifecycle(), new ObserverResponseListener() {
            @Override
            public void onNext(Object o) {
                //这一步是必须的，判断view是否已经被销毁
                if (getView() != null) {
                    getView().getGrowthValueList((GrowthValueBean) o);
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