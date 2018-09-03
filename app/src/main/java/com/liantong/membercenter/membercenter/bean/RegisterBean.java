package com.liantong.membercenter.membercenter.bean;

/**
 * Description ：
 * Author ： MengYang
 * Email ： 942685687@qq.com
 * Time ： 2018/9/3.
 */
public class RegisterBean {
    /**
     * {
     * "authorization_type":"Bearer",
     * "delegate_code":"2010006",
     * "member_code":"5975958171597",
     * "status":"1",
     * "token":""
     * ticket_name:
     * }
     */

    String authorization_type;
    String delegate_code;
    String is_got_ticket;
    String is_shanghai;
    String member_code;
    String status;
    String token;
    String ticket_name;

    public String getTicket_name() {
        return ticket_name;
    }

    public void setTicket_name(String ticket_name) {
        this.ticket_name = ticket_name;
    }

    public String getAuthorization_type() {
        return authorization_type;
    }

    public void setAuthorization_type(String authorization_type) {
        this.authorization_type = authorization_type;
    }

    public String getDelegate_code() {
        return delegate_code;
    }

    public void setDelegate_code(String delegate_code) {
        this.delegate_code = delegate_code;
    }

    public String getIs_got_ticket() {
        return is_got_ticket;
    }

    public void setIs_got_ticket(String is_got_ticket) {
        this.is_got_ticket = is_got_ticket;
    }

    public String getIs_shanghai() {
        return is_shanghai;
    }

    public void setIs_shanghai(String is_shanghai) {
        this.is_shanghai = is_shanghai;
    }

    public String getMember_code() {
        return member_code;
    }

    public void setMember_code(String member_code) {
        this.member_code = member_code;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
