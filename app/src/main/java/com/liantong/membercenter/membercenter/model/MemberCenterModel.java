package com.liantong.membercenter.membercenter.model;

import android.content.Context;

import com.liantong.membercenter.membercenter.api.Api;
import com.liantong.membercenter.membercenter.base.BaseModel;
import com.liantong.membercenter.membercenter.common.config.UrlConfig;
import com.liantong.membercenter.membercenter.progress.ObserverResponseListener;

import java.util.TreeMap;

import io.reactivex.ObservableTransformer;

/**
 * 作者：rmy on 2017/12/27 10:33
 * 邮箱：942685687@qq.com
 */
public class MemberCenterModel<T> extends BaseModel {

    public void getUserInfo(Context context, TreeMap<String, String> map, boolean isDialog, boolean cancelable,
                            ObservableTransformer<T, T> transformer, ObserverResponseListener observerListener) {

        //当不需要指定是否由dialog时，可以调用这个方法
        //        subscribe(context, Api.getApiService().login(map), observerListener);
        nullParamSubscribe(context, Api.getApiService().getUserInfo(), observerListener, transformer, isDialog, cancelable);
    }
    //// TODO: 2017/12/27 其他需要请求、数据库等等的操作
}
