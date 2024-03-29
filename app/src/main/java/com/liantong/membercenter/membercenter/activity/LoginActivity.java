package com.liantong.membercenter.membercenter.activity;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gyf.barlibrary.ImmersionBar;
import com.liantong.membercenter.membercenter.R;
import com.liantong.membercenter.membercenter.base.BaseActivity;
import com.liantong.membercenter.membercenter.base.BaseResponse;
import com.liantong.membercenter.membercenter.bean.LoginBean;
import com.liantong.membercenter.membercenter.common.config.IConstants;
import com.liantong.membercenter.membercenter.common.view.TopView;
import com.liantong.membercenter.membercenter.contract.LoginContract;
import com.liantong.membercenter.membercenter.presenter.LoginPresenter;
import com.liantong.membercenter.membercenter.receiver.SmsBroadcastReceiver;
import com.liantong.membercenter.membercenter.utils.ApplicationUtil;
import com.liantong.membercenter.membercenter.utils.CountDownUtil;
import com.liantong.membercenter.membercenter.utils.LogUtils;
import com.liantong.membercenter.membercenter.utils.SPUtil;
import com.liantong.membercenter.membercenter.utils.StringLinkUtils;
import com.liantong.membercenter.membercenter.utils.ToastUtil;
import com.liantong.membercenter.membercenter.utils.VerifyUtils;
import com.liantong.membercenter.membercenter.utils.isClickUtil;

import java.util.TreeMap;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.ObservableTransformer;

/**
 * Description ：登录
 * Author ： MengYang
 * Email ： 942685687@qq.com
 * Time ： 2018/8/27.
 */

public class LoginActivity extends BaseActivity<LoginContract.View, LoginContract.Presenter> implements LoginContract.View {

    @BindView(R.id.tv_login_top)
    TopView tvLoginTop;
    @BindView(R.id.et_login_phone)
    EditText etLoginPhone;
    @BindView(R.id.et_login_captcha)
    EditText etLoginCaptcha;
    @BindView(R.id.tv_login_captcha)
    TextView tvLoginCaptcha;
    @BindView(R.id.cb_login)
    CheckBox cbLogin;
    @BindView(R.id.bt_login)
    Button btLogin;
    @BindView(R.id.ll_login_register)
    LinearLayout llLoginRegister;

    private SmsBroadcastReceiver smsReceiver;
    private long firstTime = 0;
    public static int BC_SMS_RECEIVE = 0;

    @Override
    public int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    public LoginContract.Presenter createPresenter() {
        return new LoginPresenter(this);
    }

    @Override
    public LoginContract.View createView() {
        return this;
    }

    @Override
    public void init() {
        //将每个Activity加入到栈中
        ApplicationUtil.getManager().addActivity(this);
        //防止状态栏和标题重叠
        ImmersionBar.setTitleBar(this, tvLoginTop);
        //自动登录
        if (!SPUtil.get(this, IConstants.TOKEN, "").equals("")) {
            startActivity(new Intent(this, MemberCenterActivity.class));
            finish();
        }

        //如果注册那边填写了信息，直接拿过来用，提高用户体验度
        etLoginPhone.setText(getIntent().getStringExtra("registerPhoneFlag"));
        etLoginCaptcha.setText(getIntent().getStringExtra("registerCaptchaFlag"));

        //从字符串中获取要变为超链接的字符串
        cbLogin.setText(StringLinkUtils.checkAutoLink(this, getResources().getString(R.string.vip_card_declarations), getResources().getString(R.string.privacy_policy)));
        cbLogin.setMovementMethod(LinkMovementMethod.getInstance());

        //动态注册广播接收器
        smsReceiver = new SmsBroadcastReceiver(handler);
        IntentFilter intentFilter = new IntentFilter("android.provider.Telephony.SMS_RECEIVED");
        registerReceiver(smsReceiver, intentFilter);
    }

    @Override
    public void initListener() {
        etLoginPhone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                //输入文本之前的状态
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //输入文字中的状态，count是一次性输入字符数
            }

            @Override
            public void afterTextChanged(Editable s) {
                //输入文字后的状态
                //如果输入长度等于11位但并非是正常的手机号码
                if (etLoginPhone.getText().toString().trim().length() == 11 && !VerifyUtils.isMobileNumber(etLoginPhone.getText().toString().trim())) {
                    ToastUtil.showShortToast(getString(R.string.error_phone_num));
                }
            }
        });

        etLoginCaptcha.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                //输入文本之前的状态
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //输入文字中的状态，count是一次性输入字符数
            }

            @Override
            public void afterTextChanged(Editable s) {
                //输入文字后的状态
                //如果输入长度等于6位但并非是纯数字
                if (etLoginCaptcha.getText().toString().trim().length() == 6 && !VerifyUtils.isNumeric(etLoginCaptcha.getText().toString().trim())) {
                    ToastUtil.showShortToast(getString(R.string.error_captcha_num));
                }
            }
        });
    }

    @Override
    public void initData() {

    }

    /**
     * 登录成功的处理
     *
     * @param data
     */
    @Override
    public void resultLogin(LoginBean data) {
        SPUtil.put(this, IConstants.AUTHORIZATION_TYPE, data.getAuthorization_type());
        SPUtil.put(this, IConstants.TOKEN, data.getToken());
        SPUtil.put(this, String.valueOf(IConstants.IS_LOGIN), true);
        LogUtils.i("rmy", "token = " + data.getToken());
        startActivity(new Intent(this, MemberCenterActivity.class));
        finish();
    }

    /**
     * 验证码成功的处理
     *
     * @param data
     */
    @Override
    public void resultCaptcha(BaseResponse data) {
        //“1”是验证码发送成功
        if (data.getStatus().equals("1")) {
            //验证码倒计时60内不能重新发送
            new CountDownUtil(tvLoginCaptcha)
                    .setCountDownMillis(60_000L)//倒计时60000ms
                    .setCountDownColor(R.color.bg_captcha, R.color.tx_bottom_navigation)//不同状态字体颜色
                    .setOnClickListener(new View.OnClickListener() {
                        //重新获取验证码
                        @Override
                        public void onClick(View v) {
                            //手机号码的长度判断
                            if (!etLoginPhone.getText().toString().trim().equals("") && etLoginPhone.getText().toString().trim().length() == 11 && VerifyUtils.isMobileNumber(etLoginPhone.getText().toString().trim())) {
                                TreeMap<String, String> captchaMap = new TreeMap<>();
                                //获取手机号码
                                captchaMap.put("mobile", etLoginPhone.getText().toString().trim());
                                //平台标识码
                                captchaMap.put("delegate_code", "2010006");
                                getPresenter().captcha(captchaMap, true, false);
                            } else {
                                ToastUtil.showShortToast(getString(R.string.error_phone_num));
                            }
                        }
                    })
                    .start();
        }
    }

    @Override
    public <T> ObservableTransformer<T, T> bindLifecycle() {
        //        return this.bindUntilEvent(ActivityEvent.PAUSE);//绑定到Activity的pause生命周期（在pause销毁请求）
        return this.bindToLifecycle();//绑定activity，与activity生命周期一样
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == IConstants.REQUEST_CODE && resultCode == IConstants.RESULT_CODE) {
            cbLogin.setChecked(true);
        }
    }

    /**
     * 验证码自动填写
     */
    private Handler handler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            if (msg.what == BC_SMS_RECEIVE) {
                String code = (String) msg.obj;
                //更新UI
                etLoginCaptcha.setText(code);
            }
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //注销掉广播
        unregisterReceiver(smsReceiver);
    }

    /**
     * 双击退出程序
     */
    @Override
    public void onBackPressed() {
        long secondTime = System.currentTimeMillis();
        if (secondTime - firstTime > 2000) {
            ToastUtil.showShortToast(getResources().getString(R.string.click_out_again));
            firstTime = secondTime;
        } else {
            ApplicationUtil.getManager().exitApp();
        }
    }

    @OnClick({R.id.tv_login_captcha, R.id.bt_login, R.id.ll_login_register})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_login_captcha:
                //手机号码的长度判断
                if (!etLoginPhone.getText().toString().trim().equals("") && etLoginPhone.getText().toString().trim().length() == 11 && VerifyUtils.isMobileNumber(etLoginPhone.getText().toString().trim())) {
                    TreeMap<String, String> captchaMap = new TreeMap<>();
                    //获取手机号码
                    captchaMap.put("mobile", etLoginPhone.getText().toString().trim());
                    //平台标识码
                    captchaMap.put("delegate_code", "2010006");
                    getPresenter().captcha(captchaMap, true, false);
                } else {
                    ToastUtil.showShortToast(getString(R.string.error_phone_num));
                }
                break;
            case R.id.bt_login:
                if (isClickUtil.isFastClick()) {
                    //手机号码的长度判断，验证码的长度判断，复选框状态
                    if (etLoginPhone.getText().toString().trim().length() == 11 && VerifyUtils.isMobileNumber(etLoginPhone.getText().toString().trim()) && etLoginCaptcha.getText().toString().trim().length() == 6 && VerifyUtils.isNumeric(etLoginCaptcha.getText().toString().trim()) && cbLogin.isChecked() == true) {
                        TreeMap<String, String> loginMap = new TreeMap<>();
                        //获取手机号码
                        loginMap.put("mobile", etLoginPhone.getText().toString().trim());
                        //获取验证码
                        loginMap.put("captcha", etLoginCaptcha.getText().toString().trim());
                        //平台标识码
                        loginMap.put("delegate_code", "2010006");
                        getPresenter().login(loginMap, true, false);
                    } else if (cbLogin.isChecked() == false) {
                        ToastUtil.showShortToast(getString(R.string.error_check_box));
                    } else if (etLoginPhone.getText().toString().trim().equals("") || etLoginPhone.getText().toString().trim().length() != 11 || !VerifyUtils.isMobileNumber(etLoginPhone.getText().toString().trim())) {
                        ToastUtil.showShortToast(getString(R.string.error_phone_num));
                    } else if (etLoginCaptcha.getText().toString().trim().length() != 6) {
                        ToastUtil.showLongToast(getResources().getString(R.string.six_length_captcha));
                    } else {
                        ToastUtil.showShortToast(getString(R.string.error_login));
                    }
                }
                break;
            case R.id.ll_login_register:
                if (isClickUtil.isFastClick()) {
                    String loginPhoneFlag = etLoginPhone.getText().toString().trim();
                    String loginCaptchaFlag = etLoginCaptcha.getText().toString().trim();
                    //跳注册
                    startActivity(new Intent(this, RegisterActivity.class).putExtra("loginPhoneFlag", loginPhoneFlag).putExtra("loginCaptchaFlag", loginCaptchaFlag));
                    finish();
                }
                break;
        }
    }
}
