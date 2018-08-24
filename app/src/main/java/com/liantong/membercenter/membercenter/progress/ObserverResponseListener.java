package com.liantong.membercenter.membercenter.progress;

import com.liantong.membercenter.membercenter.utils.ExceptionHandle.ResponeThrowable;

/**
 * 请求监听
 */

public interface ObserverResponseListener<T> {
    /**
     * 响应成功
     * @param t
     */
    void onNext(T t);

    /**
     * 响应失败
     * @param e
     */
    void onError(ResponeThrowable e);
}
