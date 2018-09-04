package com.liantong.membercenter.membercenter.utils;

import android.app.Activity;
import android.content.Intent;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.view.View;

import com.liantong.membercenter.membercenter.R;
import com.liantong.membercenter.membercenter.activity.ModifyPersonalDataActivity;
import com.liantong.membercenter.membercenter.activity.VipCardDeclarationsActivity;
import com.liantong.membercenter.membercenter.activity.VipManualActivity;
import com.liantong.membercenter.membercenter.common.config.IConstants;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Description ：
 * Author ： MengYang
 * Email ： 942685687@qq.com
 * Time ： 2018/8/26.
 */
public class StringLinkUtils {

    public static SpannableStringBuilder mSsb;

    public static CharSequence checkAutoLink(Activity activity, String content, String link) {
        mSsb = new SpannableStringBuilder(content);
        Pattern pattern = Pattern.compile(link);//根据正则匹配出带有超链接的文字
        Matcher matcher = pattern.matcher(mSsb);
        while (matcher.find()) {
            setClickableSpan(activity, mSsb, matcher);
        }
        return mSsb;
    }

    private static void setClickableSpan(final Activity activity, final SpannableStringBuilder clickableHtmlBuilder, final Matcher matcher) {
        int start = matcher.start();
        int end = matcher.end();
        ClickableSpan clickableSpan = new ClickableSpan() {
            public void onClick(View view) {
                switch (view.getId()) {
                    case R.id.tv_to_modify_personal_data:
                        activity.startActivityForResult(new Intent(ApplicationUtil.getContext(), ModifyPersonalDataActivity.class), IConstants.REQUEST_CODE);
                        break;
                    case R.id.tv_to_vip_manual:
                        ApplicationUtil.getContext().startActivity(new Intent(ApplicationUtil.getContext(), VipManualActivity.class));
                        break;
                    case R.id.cb_modify_personal_data:
                        break;
                    case R.id.cb_register:
                    case R.id.cb_login:
                        activity.startActivityForResult(new Intent(ApplicationUtil.getContext(), VipCardDeclarationsActivity.class), IConstants.REQUEST_CODE);
                        break;
                }
            }

            @Override
            public void updateDrawState(TextPaint ds) {
                ds.setUnderlineText(false);//当传入true时超链接下会有一条下划线
            }
        };

        //设置超链接文本的颜色
        mSsb.setSpan(new ForegroundColorSpan(ApplicationUtil.getContext().getResources().getColor(R.color.tx_link)), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        clickableHtmlBuilder.setSpan(clickableSpan, start, end, Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
    }
}
