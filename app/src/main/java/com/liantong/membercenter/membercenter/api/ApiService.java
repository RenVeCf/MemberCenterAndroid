package com.liantong.membercenter.membercenter.api;

import com.liantong.membercenter.membercenter.base.BaseResponse;
import com.liantong.membercenter.membercenter.bean.AcitvitiesBean;
import com.liantong.membercenter.membercenter.bean.CouponDetailsBean;
import com.liantong.membercenter.membercenter.bean.CouponListBean;
import com.liantong.membercenter.membercenter.bean.GrowthValueBean;
import com.liantong.membercenter.membercenter.bean.LoginBean;
import com.liantong.membercenter.membercenter.bean.UserInfoBean;
import com.liantong.membercenter.membercenter.common.config.UrlConfig;

import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.QueryMap;

/**
 * Description ：请求配置
 * Author ： MengYang
 * Email ： 942685687@qq.com
 * Time ： 2018/8/27.
 */
public interface ApiService {

    //验证码
    @FormUrlEncoded
    @POST(UrlConfig.VERIFICATION_CODE)
    Observable<BaseResponse> captcha(@FieldMap Map<String, String> map);

    //登录
    @FormUrlEncoded
    @POST(UrlConfig.LOGIN)
    Observable<LoginBean> login(@FieldMap Map<String, String> map);

    //获取用户信息
    @GET(UrlConfig.GET_USER_INFO)
    Observable<UserInfoBean> getUserInfo();

    //获取用户成长值记录列表
    @GET(UrlConfig.GROWTH_VALUE_LIST)
    Observable<GrowthValueBean> getGrowthValueList(@QueryMap Map<String, String> map);

    //获取券列表
    @GET(UrlConfig.COUPON_LIST)
    Observable<CouponListBean> getCouponList();

    //获取券详情
    @GET(UrlConfig.COUPON_DETAILS)
    Observable<CouponDetailsBean> getCouponDetails();

    //获取活动列表
    @GET(UrlConfig.ACTIVITIES)
    Observable<AcitvitiesBean> getActivities();
}
