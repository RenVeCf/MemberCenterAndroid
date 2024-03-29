package com.liantong.membercenter.membercenter.common.config;

/**
 * Description ：URL 配置
 * Author ： MengYang
 * Email ： 942685687@qq.com
 * Time ： 2018/8/26.
 */
public interface UrlConfig {
    /**
     * 登陆
     */
    String VERIFICATION_CODE = "member_center/captcha"; //验证码
    String LOGIN = "member_center/login"; //登陆
    String REGISTER = "member_center/register_app"; //注册

    /**
     * 主页
     */
    String GET_USER_INFO = "member_center/info"; //获取or提交用户信息
    String GROWTH_VALUE_LIST = "member_center/exp"; //获取用户成长值记录列表
    String COUPON_LIST = "member_center/tickets"; //获取券列表
    String COUPON_DETAILS = "member_center/ticket"; //获取券详情
    String COUPON_DETAILS_BT_URL = "member_center/unif_token"; //券详情中立即使用按钮跳转的URL
    String ACTIVITIES = "member_center/activities"; //获取活动列表
}
