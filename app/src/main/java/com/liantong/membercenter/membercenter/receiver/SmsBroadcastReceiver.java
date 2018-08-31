package com.liantong.membercenter.membercenter.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.telephony.SmsMessage;

import com.liantong.membercenter.membercenter.activity.LoginActivity;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Description ：
 * Author ： MengYang
 * Email ： 942685687@qq.com
 * Time ： 2018/8/29.
 */
public class SmsBroadcastReceiver extends BroadcastReceiver {

    private Context context;
    private Handler handler;
    private int codeLength = 0;

    public SmsBroadcastReceiver(Context context, Handler handler) {
        this.context = context;
        this.handler = handler;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle bundle = intent.getExtras();
        Object[] objects = (Object[]) bundle.get("pdus");
        if (objects != null) {
            String phone = null;
            StringBuffer content = new StringBuffer();
            for (int i = 0; i < objects.length; i++) {
                SmsMessage sms = SmsMessage.createFromPdu((byte[]) objects[i]);
                phone = sms.getDisplayOriginatingAddress();
                content.append(sms.getDisplayMessageBody());
            }
            CodeAndSend(content.toString());
        }
    }

    private void CodeAndSend(String content) {
        Pattern pattern = Pattern.compile("(\\d{6})");//连续6个数字的验证码
        Matcher matcher = pattern.matcher(content);
        if (matcher.find()) {
            String code = matcher.group(0);
            handler.obtainMessage(LoginActivity.BC_SMS_RECEIVE, code).sendToTarget();
        }
    }
}
