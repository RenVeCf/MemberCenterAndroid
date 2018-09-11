package com.liantong.membercenter.membercenter.common.config;

/**
 * Description ：公共配置类
 * Author ： MengYang
 * Email ： 942685687@qq.com
 * Time ： 2018/8/26.
 */
public interface IConstants {
    /**
     * 包名
     */
    String PACKAGE_NAME = "com.liantong.membercenter.membercenter";

    /**
     * SharedPreferences
     * 共享参数
     */
    Boolean FIRST_APP = true; //第一次进应用
    Boolean IS_LOGIN = false; //已经登录
    String TOKEN = "token"; //token
    String AUTHORIZATION_TYPE = "token_type"; //token type
    String NAME = "name"; //用户真实姓名
    String PHONE = "phone"; //用户手机号码
    String ADDRESS = "address"; //用户地址（省市区）
    String ADDRESS_DETAIL = "address_detail"; //用户详细地址
    String EMAIL = "email"; //用户邮箱
    String GENDER = "gender"; //用户性别
    String IDCARD_NO = "idcard_no"; //用户身份证号
    Boolean IS_REFRESH = false; //券列表是否刷新

    /**
     * requestCode
     * 请求码
     */
    int REQUEST_CODE = 90;


    /**
     * resultCode
     * 返回码
     */
    int RESULT_CODE = 0;
}
