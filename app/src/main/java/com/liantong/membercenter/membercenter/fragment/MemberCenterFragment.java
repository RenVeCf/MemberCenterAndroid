package com.liantong.membercenter.membercenter.fragment;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.gyf.barlibrary.ImmersionBar;
import com.liantong.membercenter.membercenter.R;
import com.liantong.membercenter.membercenter.activity.GrowthValueActivity;
import com.liantong.membercenter.membercenter.base.BaseFragment;
import com.liantong.membercenter.membercenter.bean.UserInfoBean;
import com.liantong.membercenter.membercenter.common.config.IConstants;
import com.liantong.membercenter.membercenter.common.view.TopView;
import com.liantong.membercenter.membercenter.contract.MemberCenterContract;
import com.liantong.membercenter.membercenter.presenter.MemberCenterPresenter;
import com.liantong.membercenter.membercenter.utils.BarCodeUtil;
import com.liantong.membercenter.membercenter.utils.SPUtil;
import com.liantong.membercenter.membercenter.utils.StringLinkUtils;
import com.liantong.membercenter.membercenter.utils.ToastUtil;
import com.trello.rxlifecycle2.android.FragmentEvent;

import java.util.TreeMap;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.ObservableTransformer;

/**
 * Description ：会员中心
 * Author ： MengYang
 * Email ： 942685687@qq.com
 * Time ： 2018/8/27.
 */

public class MemberCenterFragment extends BaseFragment<MemberCenterContract.View, MemberCenterContract.Presenter> implements MemberCenterContract.View {

    @BindView(R.id.tv_member_center_top)
    TopView tvMemberCenterTop;
    @BindView(R.id.tv_growth_value)
    TextView tvGrowthValue;
    @BindView(R.id.iv_vip_lv_img)
    ImageView ivVipLvImg;
    @BindView(R.id.tv_vip_card_num_top)
    TextView tvVipCardNumTop;
    @BindView(R.id.iv_vip_bar_code)
    ImageView ivVipBarCode;
    @BindView(R.id.tv_vip_card_num_center)
    TextView tvVipCardNumCenter;
    @BindView(R.id.tv_vip_card_num_bottom)
    TextView tvVipCardNumBottom;
    @BindView(R.id.tv_to_modify_personal_data)
    TextView tvToModifyPersonalData;
    @BindView(R.id.tv_vip_lv)
    TextView tvVipLv;
    @BindView(R.id.tv_to_vip_manual)
    TextView tvToVipManual;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_member_center;
    }

    @Override
    public MemberCenterContract.Presenter createPresenter() {
        return new MemberCenterPresenter(mContext);
    }

    @Override
    public MemberCenterContract.View createView() {
        return this;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public void init() {
        //防止状态栏和标题重叠
        ImmersionBar.setTitleBar(getActivity(), tvMemberCenterTop);
        tvToModifyPersonalData.setText(StringLinkUtils.checkAutoLink(getActivity(), getResources().getString(R.string.to_modify_personal_data), "修改个人资料"));
        tvToModifyPersonalData.setMovementMethod(LinkMovementMethod.getInstance());
    }

    @Override
    public void initListener() {

    }

    @Override
    public void initData() {
        TreeMap<String, String> captchaMap = new TreeMap<>();
        getPresenter().getUserInfo(captchaMap, true, true);
    }

    @Override
    public void getUserInfo(UserInfoBean data) {
        SPUtil.put(getContext(), IConstants.PHONE, data.getMobile());
        SPUtil.put(getContext(), IConstants.NAME, data.getName());

        switch (data.getMember_level()) {
            case "W1":
                ivVipLvImg.setImageResource(R.drawable.ic_w1_vip);
                tvVipLv.setText(getResources().getString(R.string.w1_vip));
                tvToVipManual.setText("当前" + data.getExp() + "成长值，距离W2级会员还有" + data.getNext_level_exp() + "成长值（点此了解会员等级说明）");
                break;
            case "W2":
                ivVipLvImg.setImageResource(R.drawable.ic_w2_vip);
                tvVipLv.setText(getResources().getString(R.string.w2_vip));
                tvToVipManual.setText("当前" + data.getExp() + "成长值，距离W3级会员还有" + data.getNext_level_exp() + "成长值（点此了解会员等级说明）");
                break;
            case "W3":
                ivVipLvImg.setImageResource(R.drawable.ic_w3_vip);
                tvVipLv.setText(getResources().getString(R.string.w3_vip));
                tvToVipManual.setText("当前" + data.getExp() + "成长值（点此了解会员等级说明）");

                break;
        }
        tvToVipManual.setText(StringLinkUtils.checkAutoLink(getActivity(), tvToVipManual.getText().toString(), "点此了解"));
        tvToVipManual.setMovementMethod(LinkMovementMethod.getInstance());
        tvVipCardNumTop.setText(data.getMember_code());
        tvVipCardNumCenter.setText(data.getMember_code());
        tvVipCardNumBottom.setText(data.getMember_code());

        try {
            Bitmap barCode = BarCodeUtil.createBarCode(data.getMember_code(), (int) getResources().getDimension(R.dimen.x1020), (int) getResources().getDimension(R.dimen.y252));
            ivVipBarCode.setImageBitmap(barCode);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void setMsg(String msg) {
        ToastUtil.showShortToast(msg);
    }

    @Override
    public <T> ObservableTransformer<T, T> bindLifecycle() {
        return this.bindUntilEvent(FragmentEvent.PAUSE);
    }

    @OnClick({R.id.tv_growth_value})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_growth_value:
                startActivity(new Intent(getActivity(), GrowthValueActivity.class));
                break;
        }
    }
}
