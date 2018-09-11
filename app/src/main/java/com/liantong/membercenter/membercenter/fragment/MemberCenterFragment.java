package com.liantong.membercenter.membercenter.fragment;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.text.method.LinkMovementMethod;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gyf.barlibrary.ImmersionBar;
import com.liantong.membercenter.membercenter.R;
import com.liantong.membercenter.membercenter.activity.GrowthValueActivity;
import com.liantong.membercenter.membercenter.activity.LoginActivity;
import com.liantong.membercenter.membercenter.base.BaseFragment;
import com.liantong.membercenter.membercenter.bean.UserInfoBean;
import com.liantong.membercenter.membercenter.common.config.IConstants;
import com.liantong.membercenter.membercenter.common.view.TopView;
import com.liantong.membercenter.membercenter.contract.MemberCenterContract;
import com.liantong.membercenter.membercenter.presenter.MemberCenterPresenter;
import com.liantong.membercenter.membercenter.utils.ApplicationUtil;
import com.liantong.membercenter.membercenter.utils.BarCodeUtil;
import com.liantong.membercenter.membercenter.utils.SPUtil;
import com.liantong.membercenter.membercenter.utils.StringLinkUtils;
import com.liantong.membercenter.membercenter.utils.ToastUtil;
import com.liantong.membercenter.membercenter.utils.isClickUtil;
import com.trello.rxlifecycle2.android.FragmentEvent;

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
    @BindView(R.id.iv_top_out)
    ImageView ivTopOut;
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
        //将字符串中的部分字符串变为跳转链接
        tvToModifyPersonalData.setText(StringLinkUtils.checkAutoLinkFragment(MemberCenterFragment.this, getResources().getString(R.string.to_modify_personal_data), "修改个人资料"));
        //允许作为跳转链接
        tvToModifyPersonalData.setMovementMethod(LinkMovementMethod.getInstance());
    }

    @Override
    public void initListener() {

    }

    @Override
    public void initData() {
        getPresenter().getUserInfo(true, false);
    }

    @Override
    public void getUserInfo(UserInfoBean data) {
        //将用户信息暂存
        SPUtil.put(getContext(), IConstants.PHONE, data.getMobile());
        SPUtil.put(getContext(), IConstants.NAME, data.getName());
        SPUtil.put(getContext(), IConstants.ADDRESS, data.getAddress());
        SPUtil.put(getContext(), IConstants.ADDRESS_DETAIL, data.getAddress_detail());
        SPUtil.put(getContext(), IConstants.EMAIL, data.getEmail());
        SPUtil.put(getContext(), IConstants.GENDER, data.getGender());
        SPUtil.put(getContext(), IConstants.IDCARD_NO, data.getIdcard_no());

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

        //将字符串中的部分字符串变为跳转链接
        tvToVipManual.setText(StringLinkUtils.checkAutoLink(getActivity(), tvToVipManual.getText().toString(), "点此了解"));
        //允许作为跳转链接
        tvToVipManual.setMovementMethod(LinkMovementMethod.getInstance());
        tvVipCardNumTop.setText(data.getMember_code());
        tvVipCardNumCenter.setText(data.getMember_code());
        tvVipCardNumBottom.setText(data.getMember_code());

        try {
            //设置条形码生成的大小
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == IConstants.REQUEST_CODE && resultCode == IConstants.RESULT_CODE) {
            getPresenter().getUserInfo(true, false);
        }
    }

    /**
     * 退出登录的底部弹出框
     */
    private void setDialog() {
        final Dialog mCameraDialog = new Dialog(getActivity(), R.style.BottomDialog);
        //Dialog布局
        LinearLayout root = (LinearLayout) LayoutInflater.from(getActivity()).inflate(R.layout.dialog_out, null);
        //初始化视图
        //确定
        root.findViewById(R.id.bt_out_to_login_sure).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //清除所有临时储存
                SPUtil.clear(ApplicationUtil.getContext());
                ApplicationUtil.getContext().startActivity(new Intent(ApplicationUtil.getContext(), LoginActivity.class));
                mCameraDialog.dismiss();
                getActivity().finish();
            }
        });
        //取消
        root.findViewById(R.id.bt_out_to_login_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCameraDialog.dismiss();
            }
        });
        mCameraDialog.setContentView(root);
        Window dialogWindow = mCameraDialog.getWindow();
        dialogWindow.setGravity(Gravity.BOTTOM); //设置弹出方式
        WindowManager.LayoutParams lp = dialogWindow.getAttributes(); //获取对话框当前的参数值
        lp.x = 0; // 新位置X坐标
        lp.y = 0; // 新位置Y坐标
        lp.width = (int) getResources().getDisplayMetrics().widthPixels; // 宽度
        root.measure(0, 0);
        lp.height = root.getMeasuredHeight();

        lp.alpha = 9f; //透明度
        dialogWindow.setAttributes(lp);
        mCameraDialog.show();
    }

    @OnClick({R.id.tv_growth_value, R.id.iv_top_out})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_growth_value:
                if (isClickUtil.isFastClick()) {
                    startActivity(new Intent(getActivity(), GrowthValueActivity.class));
                }
                break;
            case R.id.iv_top_out:
                setDialog();
                break;
        }
    }
}
