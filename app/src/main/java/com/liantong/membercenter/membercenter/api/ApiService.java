package com.liantong.membercenter.membercenter.api;

import com.liantong.membercenter.membercenter.base.BaseResponse;
import com.liantong.membercenter.membercenter.bean.LoginBean;
import com.liantong.membercenter.membercenter.common.config.UrlConfig;

import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import retrofit2.http.QueryMap;

/**
 * api service
 */
public interface ApiService {

    //验证码
    @FormUrlEncoded
    @POST(UrlConfig.VERIFICATION_CODE)
    Observable<BaseResponse<LoginBean>> captcha(@FieldMap Map<String, String> map);

    //登录
    @FormUrlEncoded
    @POST(UrlConfig.LOGIN)
    Observable<BaseResponse<LoginBean>> login(@FieldMap Map<String, String> map);
}
