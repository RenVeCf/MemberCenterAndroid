package com.liantong.membercenter.membercenter.bean;

import java.util.List;

/**
 * Created by Mengyang on 2018/8/23
 */
public class CouponListBean {

    private List<TicketListBean> ticket_list;

    public List<TicketListBean> getTicket_list() {
        return ticket_list;
    }

    public void setTicket_list(List<TicketListBean> ticket_list) {
        this.ticket_list = ticket_list;
    }

    public static class TicketListBean {
        /**
         * coupon_code : 1000000038917343
         * coupon_no : 1063722182
         * coupon_status : 3
         * create_time : 2018-07-26 09:21:04
         * redeem_way :
         * right_template_code : 1000000000019605
         * ticket_desc : <p>1、该券限指定新品手机使用（指定新品如遇营业厅缺货，以营业厅实际可销售新品为准）；</p>

         <p>2、该券单笔订单限使用一张，折扣券不可叠加使用；</p>

         <p>3、该券不与厅内其他优惠同享；</p>

         <p>4、该券限上海联通智慧生活馆、中国联通智慧生活体验店使用；</p>

         <p>具体门店信息请咨询10010。</p>

         <p>5、用户若发生质量问题退货，门店退还用户用券后实际支付金额；</p>

         <p>6、该券有效期至领券日起顺延30天。</p>
         * ticket_name : 新品手机150元折扣券
         * ticket_type : 5
         * use_date : 2018-07-26 ~ 2018-08-25
         * wechat_template_code : pnHr_jo6anO1KJMk7lyH6GDa0qjI
         */

        private String coupon_code;
        private String coupon_no;
        private String coupon_status;
        private String create_time;
        private String redeem_way;
        private String right_template_code;
        private String ticket_desc;
        private String ticket_name;
        private String ticket_type;
        private String use_date;
        private String wechat_template_code;

        public String getCoupon_code() {
            return coupon_code;
        }

        public void setCoupon_code(String coupon_code) {
            this.coupon_code = coupon_code;
        }

        public String getCoupon_no() {
            return coupon_no;
        }

        public void setCoupon_no(String coupon_no) {
            this.coupon_no = coupon_no;
        }

        public String getCoupon_status() {
            return coupon_status;
        }

        public void setCoupon_status(String coupon_status) {
            this.coupon_status = coupon_status;
        }

        public String getCreate_time() {
            return create_time;
        }

        public void setCreate_time(String create_time) {
            this.create_time = create_time;
        }

        public String getRedeem_way() {
            return redeem_way;
        }

        public void setRedeem_way(String redeem_way) {
            this.redeem_way = redeem_way;
        }

        public String getRight_template_code() {
            return right_template_code;
        }

        public void setRight_template_code(String right_template_code) {
            this.right_template_code = right_template_code;
        }

        public String getTicket_desc() {
            return ticket_desc;
        }

        public void setTicket_desc(String ticket_desc) {
            this.ticket_desc = ticket_desc;
        }

        public String getTicket_name() {
            return ticket_name;
        }

        public void setTicket_name(String ticket_name) {
            this.ticket_name = ticket_name;
        }

        public String getTicket_type() {
            return ticket_type;
        }

        public void setTicket_type(String ticket_type) {
            this.ticket_type = ticket_type;
        }

        public String getUse_date() {
            return use_date;
        }

        public void setUse_date(String use_date) {
            this.use_date = use_date;
        }

        public String getWechat_template_code() {
            return wechat_template_code;
        }

        public void setWechat_template_code(String wechat_template_code) {
            this.wechat_template_code = wechat_template_code;
        }
    }
}
