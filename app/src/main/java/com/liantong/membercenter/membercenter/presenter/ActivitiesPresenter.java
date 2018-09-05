package com.liantong.membercenter.membercenter.presenter;

import android.content.Context;

import com.liantong.membercenter.membercenter.bean.AcitvitiesBean;
import com.liantong.membercenter.membercenter.contract.ActivitiesContract;
import com.liantong.membercenter.membercenter.model.AcitvitiesModel;
import com.liantong.membercenter.membercenter.progress.ObserverResponseListener;
import com.liantong.membercenter.membercenter.utils.ExceptionHandle;
import com.liantong.membercenter.membercenter.utils.ToastUtil;

/**
 * Description ：
 * Author ： MengYang
 * Email ： 942685687@qq.com
 * Time ： 2018/8/26.
 */
public class ActivitiesPresenter extends ActivitiesContract.Presenter {

    private AcitvitiesModel model;
    private Context context;

    public ActivitiesPresenter(Context context) {
        this.model = new AcitvitiesModel();
        this.context = context;
    }

    @Override
    public void getActivities(boolean isDialog, boolean cancelable) {
        model.getActivities(context, isDialog, cancelable, new ObserverResponseListener() {
            @Override
            public void onNext(Object o) {
                //这一步是必须的，判断view是否已经被销毁
                if (getView() != null) {
                    getView().getActivities((AcitvitiesBean) o);
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