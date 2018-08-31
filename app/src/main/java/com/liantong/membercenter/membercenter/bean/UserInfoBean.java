package com.liantong.membercenter.membercenter.bean;

/**
 * Description ：
 * Author ： MengYang
 * Email ： 942685687@qq.com
 * Time ： 2018/8/28.
 */
public class UserInfoBean {

    /**
     * {"address":"","address_detail":"","channel":"","create_time":"2018-08-28 15:03:56","degree_of_education":"","email":"","exp":"50","gender":"","id":"452","idcard_no":"","is_vip":"0","member_code":"6905439765650","member_level":"W1","mobile":"18502994087","name":"任梦阳","next_level":"W2","next_level_exp":"1950","status":"1","update_time":"2018-08-28 15:03:57"}
     */

    private String address; //地址
    private String address_detail; //详细地址
    private String create_time; //创建时间，格式：2018-06-19 06:31:30
    private String degree_of_education; //教育程度
    private String email; //邮箱
    private String exp; //经验值
    private String gender; //性别，0 - 男；1 - 女
    private String id; //会员逻辑id
    private String idcard_no; //身份证号
    private String is_vip; //是否为付费会员
    private String member_code; //会员编号
    private String member_level; //会员等级
    private String mobile; //手机号码
    private String name; //会员姓名
    private String next_level_exp; //距离下一等级所差经验

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAddress_detail() {
        return address_detail;
    }

    public void setAddress_detail(String address_detail) {
        this.address_detail = address_detail;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    public String getDegree_of_education() {
        return degree_of_education;
    }

    public void setDegree_of_education(String degree_of_education) {
        this.degree_of_education = degree_of_education;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getExp() {
        return exp;
    }

    public void setExp(String exp) {
        this.exp = exp;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIdcard_no() {
        return idcard_no;
    }

    public void setIdcard_no(String idcard_no) {
        this.idcard_no = idcard_no;
    }

    public String getIs_vip() {
        return is_vip;
    }

    public void setIs_vip(String is_vip) {
        this.is_vip = is_vip;
    }

    public String getMember_code() {
        return member_code;
    }

    public void setMember_code(String member_code) {
        this.member_code = member_code;
    }

    public String getMember_level() {
        return member_level;
    }

    public void setMember_level(String member_level) {
        this.member_level = member_level;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNext_level_exp() {
        return next_level_exp;
    }

    public void setNext_level_exp(String next_level_exp) {
        this.next_level_exp = next_level_exp;
    }
}