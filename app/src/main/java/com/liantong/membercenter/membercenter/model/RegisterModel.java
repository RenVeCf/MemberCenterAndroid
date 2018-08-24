package com.liantong.membercenter.membercenter.model;

import android.content.Context;

import com.liantong.membercenter.membercenter.api.Api;
import com.liantong.membercenter.membercenter.base.BaseModel;
import com.liantong.membercenter.membercenter.progress.ObserverResponseListener;

import java.util.TreeMap;

import io.reactivex.ObservableTransformer;

/**
 * Description ：
 * Author ： MengYang
 * Email ： 942685687@qq.com
 * Time ： 2018/8/18.
 */
public class RegisterModel<T> extends BaseModel {
    public void register(Context context, TreeMap<String, String> map, boolean isDialog, boolean cancelable,
                      ObservableTransformer<T, T> transformer, ObserverResponseListener observerListener) {

        //当不需要指定是否由dialog时，可以调用这个方法
//                subscribe(context, Api.getApiService().login(map), observerListener, transformer);
        subscribe(context, Api.getApiService().login(map), observerListener, transformer, isDialog, cancelable);
    }

    public void captcha(Context context, TreeMap<String, String> map, boolean isDialog, boolean cancelable,
                        ObservableTransformer<T, T> transformer, ObserverResponseListener observerListener) {

        //当不需要指定是否由dialog时，可以调用这个方法
        //        subscribe(context, Api.getApiService().login(map), observerListener);
        subscribe(context, Api.getApiService().captcha(map), observerListener, transformer, isDialog, cancelable);
    }
}
