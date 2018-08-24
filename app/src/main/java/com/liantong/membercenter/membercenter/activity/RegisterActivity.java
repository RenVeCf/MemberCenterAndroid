package com.liantong.membercenter.membercenter.activity;

import android.content.Intent;
import android.text.Editable;
import android.text.TextWatcher;
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
import com.liantong.membercenter.membercenter.contract.RegisterContract;
import com.liantong.membercenter.membercenter.presenter.RegisterPresenter;
import com.liantong.membercenter.membercenter.utils.CountDownUtil;
import com.liantong.membercenter.membercenter.utils.LogUtils;
import com.liantong.membercenter.membercenter.utils.ToastUtil;
import com.liantong.membercenter.membercenter.utils.TopView;
import com.liantong.membercenter.membercenter.utils.VerifyUtils;

import java.util.TreeMap;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.ObservableTransformer;

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
    @BindView(R.id.tv_register_declarations)
    TextView tvRegisterDeclarations;
    @BindView(R.id.bt_register)
    Button btRegister;
    @BindView(R.id.ll_register_login)
    LinearLayout llRegisterLogin;

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
        //防止状态栏和标题重叠
        ImmersionBar.setTitleBar(this, tvRegisterTop);
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
                if (etRegisterPhone.getText().toString().trim().length() == 11 && !VerifyUtils.isMobileNumber(etRegisterPhone.getText().toString().trim())) {
                    ToastUtil.showShortToast(getString(R.string.error_phone_num));
                    etRegisterPhone.setText("");
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
                if (VerifyUtils.isChinese(etRegisterName.getText().toString().trim()) == true && etRegisterName.getText().toString().trim().length() <= 10) {

                }
                if (VerifyUtils.isEnglish(etRegisterName.getText().toString().trim()) == true && etRegisterName.getText().toString().trim().length() <= 20) {

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
                if (etRegisterCaptcha.getText().toString().trim().length() == 6 && !VerifyUtils.isNumeric(etRegisterCaptcha.getText().toString().trim())) {
                    ToastUtil.showShortToast(getString(R.string.error_phone_num));
                    etRegisterCaptcha.setText("");
                }
            }
        });
    }

    @Override
    public void initData() {

    }

    @Override
    public void resultRegister(BaseResponse<LoginBean> data) {

    }

    @Override
    public void resultCaptcha(BaseResponse data) {
        if (data.getStatus().equals("1")) {
            new CountDownUtil(tvRegisterCaptcha)
                    .setCountDownMillis(60_000L)//倒计时60000ms
                    .setCountDownColor(R.color.bg_captcha, R.color.tx_bottom_navigation)//不同状态字体颜色
                    .setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            LogUtils.i("rmy", "发送成功");
                        }
                    })
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

    @OnClick({R.id.tv_register_captcha, R.id.bt_register, R.id.ll_register_login})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_register_captcha:
                if (!etRegisterPhone.getText().toString().trim().equals("") && etRegisterPhone.getText().toString().trim().length() == 11) {
                    TreeMap<String, String> captchaMap = new TreeMap<>();
                    captchaMap.put("mobile", etRegisterPhone.getText().toString().trim());
                    captchaMap.put("delegate_code", "2010006");
                    getPresenter().captcha(captchaMap, true, true);
                } else {
                    ToastUtil.showShortToast(getString(R.string.error_phone_num));
                }
                break;
            case R.id.bt_register:
                startActivity(new Intent(this, RegisterNextActivity.class));
                finish();
                break;
            case R.id.ll_register_login:
                startActivity(new Intent(this, LoginActivity.class));
                finish();
                break;
        }
    }
}
