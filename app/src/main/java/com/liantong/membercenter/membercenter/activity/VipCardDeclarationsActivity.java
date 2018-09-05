package com.liantong.membercenter.membercenter.activity;

import android.widget.Button;

import com.gyf.barlibrary.ImmersionBar;
import com.liantong.membercenter.membercenter.R;
import com.liantong.membercenter.membercenter.base.BaseActivity;
import com.liantong.membercenter.membercenter.base.BasePresenter;
import com.liantong.membercenter.membercenter.base.BaseView;
import com.liantong.membercenter.membercenter.common.config.IConstants;
import com.liantong.membercenter.membercenter.common.view.TopView;
import com.liantong.membercenter.membercenter.utils.ApplicationUtil;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Description ：隐私政策
 * Author ： MengYang
 * Email ： 942685687@qq.com
 * Time ： 2018/8/27.
 */

public class VipCardDeclarationsActivity extends BaseActivity {
    @BindView(R.id.tv_vip_card_declarations_top)
    TopView tvVipCardDeclarationsTop;
    @BindView(R.id.bt_vip_card_declarations)
    Button btVipCardDeclarations;

    @Override
    public int getLayoutId() {
        return R.layout.activity_vip_card_declarations;
    }

    @Override
    public BasePresenter createPresenter() {
        return null;
    }

    @Override
    public BaseView createView() {
        return null;
    }

    @Override
    public void init() {
        //将每个Activity加入到栈中
        ApplicationUtil.getManager().addActivity(this);
        //防止状态栏和标题重叠
        ImmersionBar.setTitleBar(this, tvVipCardDeclarationsTop);
    }

    @Override
    public void initListener() {

    }

    @Override
    public void initData() {

    }

    @OnClick(R.id.bt_vip_card_declarations)
    public void onViewClicked() {
        setResult(IConstants.RESULT_CODE);
        finish();
    }
}
