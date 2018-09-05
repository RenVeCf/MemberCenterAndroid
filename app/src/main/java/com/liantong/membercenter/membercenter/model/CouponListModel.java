package com.liantong.membercenter.membercenter.model;

import android.content.Context;

import com.liantong.membercenter.membercenter.api.Api;
import com.liantong.membercenter.membercenter.base.BaseModel;
import com.liantong.membercenter.membercenter.progress.ObserverResponseListener;

/**
 * Description ：
 * Author ： MengYang
 * Email ： 942685687@qq.com
 * Time ： 2018/8/26.
 */
public class CouponListModel<T> extends BaseModel {

    public void getCouponList(Context context, boolean isDialog, boolean cancelable, ObserverResponseListener observerListener) {

        //当不需要指定是否由dialog时，可以调用这个方法
        //        subscribe(context, Api.getApiService().login(map), observerListener);
        nullParamSubscribe(context, Api.getApiService().getCouponList(), observerListener, isDialog, cancelable);
    }
    //// TODO: 2017/12/27 其他需要请求、数据库等等的操作
}
