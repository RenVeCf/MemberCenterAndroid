package com.liantong.membercenter.membercenter.bean;

/**
 * 作者：rmy on 2017/12/27 10:31
 * 邮箱：942685687@qq.com
 */

public class LoginBean {

    /**
     * authorization_type : string
     * token : string
     */

    private String authorization_type;
    private String token;

    public String getAuthorization_type() {
        return authorization_type;
    }

    public void setAuthorization_type(String authorization_type) {
        this.authorization_type = authorization_type;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
