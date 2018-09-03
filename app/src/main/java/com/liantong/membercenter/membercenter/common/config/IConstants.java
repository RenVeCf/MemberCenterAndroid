package com.liantong.membercenter.membercenter.common.config;

/**
 * Description ：
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
    String NAME = "name";
    String PHONE = "phone";

    /**
     * requestCode
     * 请求码
     */
    int REQUEST_CODE = 90;


    /**
     * resultCode
     * 返回码
     */
    int RESULT_CODE = 1000;
}
