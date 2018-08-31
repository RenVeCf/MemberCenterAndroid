package com.liantong.membercenter.membercenter.bean;

import java.util.List;

/**
 * Description ：
 * Author ： MengYang
 * Email ： 942685687@qq.com
 * Time ： 2018/8/29.
 */
public class GrowthValueBean {

    private int current_page; //当前页码
    private int total; //总记录条数
    private int total_page; //总页数
    private List<GrowthValueItemBean> list;

    public static class GrowthValueItemBean {

        private String id; //逻辑id
        private String channel; //渠道：经验值渠道：1-登录；2-签到；3-活动；4-消费；99-其他
        private String create_time; //创建时间(2018-07-18)
        private String desc; //描述
        private String exp; //需变更的经验值
        private String flow_type; //流水类型, 0-增加, 1-减少
        private String members_id; //会员逻辑id
        private String member_code; //会员号
        private String mobile; //会员手机号

        public void setId(String id) {
            this.id = id;
        }

        public String getId() {
            return id;
        }

        public void setChannel(String channel) {
            this.channel = channel;
        }

        public String getChannel() {
            return channel;
        }

        public void setCreate_time(String create_time) {
            this.create_time = create_time;
        }

        public String getCreate_time() {
            return create_time;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }

        public String getDesc() {
            return desc;
        }

        public void setExp(String exp) {
            this.exp = exp;
        }

        public String getExp() {
            return exp;
        }

        public void setFlow_type(String flow_type) {
            this.flow_type = flow_type;
        }

        public String getFlow_type() {
            return flow_type;
        }

        public void setMembers_id(String members_id) {
            this.members_id = members_id;
        }

        public String getMembers_id() {
            return members_id;
        }

        public void setMember_code(String member_code) {
            this.member_code = member_code;
        }

        public String getMember_code() {
            return member_code;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public String getMobile() {
            return mobile;
        }
    }

    public void setCurrent_page(int current_page) {
        this.current_page = current_page;
    }

    public int getCurrent_page() {
        return current_page;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal_page(int total_page) {
        this.total_page = total_page;
    }

    public int getTotal_page() {
        return total_page;
    }

    public void setList(List<GrowthValueItemBean> list) {
        this.list = list;
    }

    public List<GrowthValueItemBean> getList() {
        return list;
    }
}
