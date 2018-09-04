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
import com.liantong.membercenter.membercenter.bean.RegisterBean;
import com.liantong.membercenter.membercenter.common.config.IConstants;
import com.liantong.membercenter.membercenter.common.view.TopView;
import com.liantong.membercenter.membercenter.contract.RegisterContract;
import com.liantong.membercenter.membercenter.presenter.RegisterPresenter;
import com.liantong.membercenter.membercenter.receiver.SmsBroadcastReceiver;
import com.liantong.membercenter.membercenter.utils.ApplicationUtil;
import com.liantong.membercenter.membercenter.utils.CountDownUtil;
import com.liantong.membercenter.membercenter.utils.LogUtils;
import com.liantong.membercenter.membercenter.utils.SPUtil;
import com.liantong.membercenter.membercenter.utils.StringLinkUtils;
import com.liantong.membercenter.membercenter.utils.ToastUtil;
import com.liantong.membercenter.membercenter.utils.VerifyUtils;

import java.util.TreeMap;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.ObservableTransformer;

/**
 * Description ：注册
 * Author ： MengYang
 * Email ： 942685687@qq.com
 * Time ： 2018/8/27.
 */

public class RegisterActivity extends BaseActivity<RegisterContract.View, RegisterContract.Presenter> implements RegisterContract.View {

    @BindView(R.id.tv_register_top)
    TopView tvRegisterTop;
    @BindView(R.id.et_register_phone)
    EditText etRegisterPhone;
    @BindView(R.id.et_register_name)
    EditText etRegisterName;
    @BindView(R.id.et_register_captcha)
    EditText etRegisterCaptcha;
    @BindView(R.id.tv_register_captcha)
    TextView tvRegisterCaptcha;
    @BindView(R.id.cb_register)
    CheckBox cbRegister;
    @BindView(R.id.bt_register)
    Button btRegister;
    @BindView(R.id.ll_register_login)
    LinearLayout llRegisterLogin;

    boolean isName = false; //用户名格式判断
    private long firstTime = 0;
    private SmsBroadcastReceiver smsReceiver;

    @Override
    public int getLayoutId() {
        return R.layout.activity_register;
    }

    @Override
    public RegisterContract.Presenter createPresenter() {
        return new RegisterPresenter(this);
    }

    @Override
    public RegisterContract.View createView() {
        return this;
    }

    @Override
    public void init() {
        //将每个Activity加入到栈中
        ApplicationUtil.getManager().addActivity(this);
        //防止状态栏和标题重叠
        ImmersionBar.setTitleBar(this, tvRegisterTop);
        //从字符串中获取要变为超链接的字符串
        cbRegister.setText(StringLinkUtils.checkAutoLink(this, getResources().getString(R.string.vip_card_declarations), getResources().getString(R.string.privacy_policy)));
        cbRegister.setMovementMethod(LinkMovementMethod.getInstance());

        smsReceiver = new SmsBroadcastReceiver(this, handler);
        IntentFilter intentFilter = new IntentFilter("android.provider.Telephony.SMS_RECEIVED");
        registerReceiver(smsReceiver, intentFilter);
    }

    @Override
    public void initListener() {
        etRegisterPhone.addTextChangedListener(new TextWatcher() {
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
                if (etRegisterPhone.getText().toString().trim().length() == 11 && !VerifyUtils.isMobileNumber(etRegisterPhone.getText().toString().trim())) {
                    ToastUtil.showShortToast(getString(R.string.error_phone_num));
                }
            }
        });

        etRegisterName.addTextChangedListener(new TextWatcher() {
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
                //只能输入中文且长度小于等于10 或 只能输入英文且长度小于等于20
                if (VerifyUtils.isChinese(etRegisterName.getText().toString()) == true && etRegisterName.getText().toString().trim().length() <= 10) {
                    isName = true;
                } else if (etRegisterName.getText().toString().length() <= 20 && !etRegisterName.getText().toString().startsWith(" ")) {
                    if (!VerifyUtils.isHasTwinSpace(etRegisterName.getText().toString())) {
                        if (VerifyUtils.isEnglish(etRegisterName.getText().toString().replace(" ", "")))
                            isName = true;
                        else {
                            isName = false;
                            ToastUtil.showShortToast(getResources().getString(R.string.error_name));
                        }
                    } else {
                        isName = false;
                        ToastUtil.showShortToast(getResources().getString(R.string.error_name));
                    }
                } else {
                    isName = false;
                    ToastUtil.showShortToast(getResources().getString(R.string.error_name));
                }
            }
        });

        etRegisterCaptcha.addTextChangedListener(new TextWatcher() {
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
                if (etRegisterCaptcha.getText().toString().trim().length() == 6 && !VerifyUtils.isNumeric(etRegisterCaptcha.getText().toString().trim())) {
                    ToastUtil.showShortToast(getString(R.string.error_phone_num));
                }
            }
        });
    }

    @Override
    public void initData() {

    }

    @Override
    public void getRegister(RegisterBean data) {
        SPUtil.put(this, IConstants.AUTHORIZATION_TYPE, data.getAuthorization_type());
        SPUtil.put(this, IConstants.TOKEN, data.getToken());
        LogUtils.i("rmy", "token = " + data.getToken());
        startActivity(new Intent(this, RegisterNextActivity.class).putExtra("getTicket_name", data.getTicket_name()));
        finish();
    }

    @Override
    public void resultCaptcha(BaseResponse data) {
        if (data.getStatus().equals("1")) {
            new CountDownUtil(tvRegisterCaptcha)
                    .setCountDownMillis(60_000L)//倒计时60000ms
                    .setCountDownColor(R.color.bg_captcha, R.color.tx_bottom_navigation)//不同状态字体颜色
                    .start();
        }
    }

    @Override
    public void setMsg(String msg) {

    }

    @Override
    public <T> ObservableTransformer<T, T> bindLifecycle() {
        return this.bindToLifecycle();
    }

    private Handler handler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            if (msg.what == LoginActivity.BC_SMS_RECEIVE) {
                String code = (String) msg.obj;
                //更新UI
                etRegisterCaptcha.setText(code);
            }
        }
    };

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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(smsReceiver);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == IConstants.REQUEST_CODE && resultCode == IConstants.RESULT_CODE) {
            cbRegister.setChecked(true);
        }
    }

    @OnClick({R.id.tv_register_captcha, R.id.bt_register, R.id.ll_register_login})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_register_captcha:
                //手机号码的长度判断
                if (!etRegisterPhone.getText().toString().trim().equals("") && etRegisterPhone.getText().toString().trim().length() == 11 && VerifyUtils.isMobileNumber(etRegisterPhone.getText().toString().trim())) {
                    TreeMap<String, String> captchaMap = new TreeMap<>();
                    //获取手机号码
                    captchaMap.put("mobile", etRegisterPhone.getText().toString().trim());
                    //平台标识码
                    captchaMap.put("delegate_code", "2010006");
                    getPresenter().captcha(captchaMap, true, true);
                } else {
                    ToastUtil.showShortToast(getString(R.string.error_phone_num));
                }
                break;
            case R.id.bt_register:
                //手机号码的长度判断，验证码的长度判断，复选框状态
                if (etRegisterPhone.getText().toString().trim().length() == 11 && isName == true && etRegisterCaptcha.getText().toString().trim().length() == 6 && VerifyUtils.isNumeric(etRegisterCaptcha.getText().toString().trim()) && cbRegister.isChecked() == true) {
                    TreeMap<String, String> loginMap = new TreeMap<>();
                    //获取手机号码
                    loginMap.put("mobile", etRegisterPhone.getText().toString().trim());
                    //获取姓名
                    loginMap.put("name", etRegisterName.getText().toString().trim());
                    //获取验证码
                    loginMap.put("captcha", etRegisterCaptcha.getText().toString().trim());
                    //平台标识码
                    loginMap.put("delegate_code", "2010006");
                    getPresenter().getRegister(loginMap, true, true);
                } else {
                    ToastUtil.showShortToast(getString(R.string.error_login));
                }
                break;
            case R.id.ll_register_login:
                //跳登录
                startActivity(new Intent(this, LoginActivity.class));
                finish();
                break;
        }
    }
}
