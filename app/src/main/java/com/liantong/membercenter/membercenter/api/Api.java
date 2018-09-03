package com.liantong.membercenter.membercenter.api;

import com.liantong.membercenter.membercenter.base.BaseApi;

/**
 * Description ：处理请求链接的地方
 * Author ： MengYang
 * Email ： 942685687@qq.com
 * Time ： 2018/8/27.
 */

public class Api {
    //域名
    private String baseUrl = "https://fx.10010sh.cn/mc/v1/"; //测试
    //private String baseUrl = "https://api.10010sh.cn/mc/v1/"; //正式
//    private String baseUrl = "http://192.168.2.4:8080/mc/v1/"; //吴江
//    private String baseUrl = "http://192.168.2.102:8080/mc/v1/"; //吴江2

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
