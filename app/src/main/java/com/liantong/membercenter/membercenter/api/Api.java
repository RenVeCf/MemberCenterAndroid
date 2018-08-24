package com.liantong.membercenter.membercenter.api;

import com.liantong.membercenter.membercenter.base.BaseApi;
import com.liantong.membercenter.membercenter.utils.ApplicationUtil;
import com.liantong.membercenter.membercenter.utils.DeviceUtils;
import com.liantong.membercenter.membercenter.utils.MD5Utils;

import java.util.HashMap;
import java.util.TreeMap;

/**
 *
 */

public class Api {
    //域名
    private String baseUrl = "https://fx.10010sh.cn/mc/v1/"; //测试
    //private String baseUrl = "https://api.10010sh.cn/mc/v1/"; //正式

    private volatile static ApiService apiService;

    public static ApiService getApiService() {
        if (apiService == null) {
            synchronized (Api.class) {
                if (apiService == null) {
                    new Api();
                }
            }
        }
        return apiService;
    }

    private Api() {
        BaseApi baseApi = new BaseApi();
        apiService = baseApi.getRetrofit(baseUrl).create(ApiService.class);
    }
}
