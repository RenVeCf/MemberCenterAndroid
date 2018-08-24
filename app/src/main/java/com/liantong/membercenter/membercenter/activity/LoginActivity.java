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
import com.liantong.membercenter.membercenter.contract.LoginContract;
import com.liantong.membercenter.membercenter.presenter.LoginPresenter;
import com.liantong.membercenter.membercenter.utils.CountDownUtil;
import com.liantong.membercenter.membercenter.utils.LogUtils;
import com.liantong.membercenter.membercenter.utils.ToastUtil;
import com.liantong.membercenter.membercenter.utils.TopView;
import com.liantong.membercenter.membercenter.utils.VerifyUtils;

import java.util.TreeMap;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.ObservableTransformer;

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
    @BindView(R.id.tv_login_declarations)
    TextView tvLoginDeclarations;
    @BindView(R.id.bt_login)
    Button btLogin;
    @BindView(R.id.ll_login_register)
    LinearLayout llLoginRegister;

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
        //防止状态栏和标题重叠
        ImmersionBar.setTitleBar(this, tvLoginTop);
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
                if (etLoginPhone.getText().toString().trim().length() == 11 && !VerifyUtils.isMobileNumber(etLoginPhone.getText().toString().trim())) {
                    ToastUtil.showShortToast(getString(R.string.error_phone_num));
                    etLoginPhone.setText("");
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
                if (etLoginCaptcha.getText().toString().trim().length() == 6 && !VerifyUtils.isNumeric(etLoginCaptcha.getText().toString().trim())) {
                    ToastUtil.showShortToast(getString(R.string.error_phone_num));
                    etLoginCaptcha.setText("");
                }
            }
        });
    }

    @Override
    public void initData() {

    }

    @Override
    public void resultLogin(BaseResponse<LoginBean> data) {
    }

    @Override
    public void resultCaptcha(BaseResponse data) {
        if (data.getStatus().equals("1")) {
            new CountDownUtil(tvLoginCaptcha)
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
        ToastUtil.showShortToast(msg);
    }

    @Override
    public <T> ObservableTransformer<T, T> bindLifecycle() {
        //        return this.bindUntilEvent(ActivityEvent.PAUSE);//绑定到Activity的pause生命周期（在pause销毁请求）
        return this.bindToLifecycle();//绑定activity，与activity生命周期一样
    }

    @OnClick({R.id.tv_login_captcha, R.id.bt_login, R.id.ll_login_register, R.id.tv_login_declarations})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_login_captcha:
                if (!etLoginPhone.getText().toString().trim().equals("") && etLoginPhone.getText().toString().trim().length() == 11) {
                    TreeMap<String, String> captchaMap = new TreeMap<>();
                    captchaMap.put("mobile", etLoginPhone.getText().toString().trim());
                    captchaMap.put("delegate_code", "2010006");
                    getPresenter().captcha(captchaMap, true, true);
                } else {
                    ToastUtil.showShortToast(getString(R.string.error_phone_num));
                }
                break;
            case R.id.bt_login:
                if (etLoginPhone.getText().toString().trim().length() == 11 && etLoginCaptcha.getText().toString().trim().length() == 6 && cbLogin.isChecked() == true) {
                    TreeMap<String, String> Loginmap = new TreeMap<>();
                    Loginmap.put("mobile", etLoginPhone.getText().toString().trim());
                    Loginmap.put("captcha", etLoginCaptcha.getText().toString().trim());
                    Loginmap.put("delegate_code", "2010006");
                    getPresenter().login(Loginmap, true, true);
                } else {
                    ToastUtil.showShortToast(getString(R.string.error_login));
                }
                break;
            case R.id.ll_login_register:
                startActivity(new Intent(this, RegisterActivity.class));
                finish();
                break;
            case R.id.tv_login_declarations:
                startActivity(new Intent(this, VipCardDeclarationsActivity.class));
                break;
        }
    }
}
