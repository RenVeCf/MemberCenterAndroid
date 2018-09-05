package com.liantong.membercenter.membercenter.base;

import android.content.Context;

import com.liantong.membercenter.membercenter.progress.ObserverResponseListener;
import com.liantong.membercenter.membercenter.progress.ProgressObserver;

import io.reactivex.Observable;
import io.reactivex.ObservableTransformer;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Description ：M层父类
 * Author ： MengYang
 * Email ： 942685687@qq.com
 * Time ： 2018/8/27.
 */

public class BaseModel<T> {
    /**
     * 封装线程管理和订阅的过程
     * flag  是否添加progressdialog
     * 有参
     */
    public void paramSubscribe(Context context, final Observable observable, ObserverResponseListener<T> listener, ObservableTransformer<T, T> transformer, boolean isDialog, boolean cancelable) {
        final Observer<T> observer = new ProgressObserver(context, listener, isDialog, cancelable);
        observable.compose(transformer) //参数
                .subscribeOn(Schedulers.io()) //异步加载
                .unsubscribeOn(Schedulers.io()) //释放线程
                .observeOn(AndroidSchedulers.mainThread()) //转UI线程
                .subscribe(observer); //订阅
    }

    /**
     * 封装线程管理和订阅的过程
     * flag  是否添加progressdialog
     * 无参
     */
    public void nullParamSubscribe(Context context, final Observable observable, ObserverResponseListener<T> listener, boolean isDialog, boolean cancelable) {
        final Observer<T> observer = new ProgressObserver(context, listener, isDialog, cancelable);
        observable
                .subscribeOn(Schedulers.io()) //异步加载
                .unsubscribeOn(Schedulers.io()) //释放线程
                .observeOn(AndroidSchedulers.mainThread()) //转UI线程
                .subscribe(observer); //订阅
    }
}
