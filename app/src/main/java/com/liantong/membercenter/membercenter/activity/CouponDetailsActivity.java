package com.liantong.membercenter.membercenter.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.gyf.barlibrary.ImmersionBar;
import com.liantong.membercenter.membercenter.R;
import com.liantong.membercenter.membercenter.base.BaseActivity;
import com.liantong.membercenter.membercenter.bean.CouponDetailsBtUrlBean;
import com.liantong.membercenter.membercenter.common.config.IConstants;
import com.liantong.membercenter.membercenter.common.view.TopView;
import com.liantong.membercenter.membercenter.contract.CouponDetailsBtUrlContract;
import com.liantong.membercenter.membercenter.presenter.CouponDetailsBtUrlPresenter;
import com.liantong.membercenter.membercenter.utils.ApplicationUtil;
import com.liantong.membercenter.membercenter.utils.BarCodeUtil;

import java.util.TreeMap;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.ObservableTransformer;

/**
 * Description ：卡券详情
 * Author ： MengYang
 * Email ： 942685687@qq.com
 * Time ： 2018/8/27.
 */

public class CouponDetailsActivity extends BaseActivity<CouponDetailsBtUrlContract.View, CouponDetailsBtUrlContract.Presenter> implements CouponDetailsBtUrlContract.View {

    @BindView(R.id.tv_coupon_details_top)
    TopView tvCouponDetailsTop;
    @BindView(R.id.tv_coupon_details_type)
    TextView tvCouponDetailsType;
    @BindView(R.id.tv_coupon_details_name)
    TextView tvCouponDetailsName;
    @BindView(R.id.bt_coupon_details)
    Button btCouponDetails;
    @BindView(R.id.tv_coupon_details_time)
    TextView tvCouponDetailsTime;
    @BindView(R.id.tv_coupon_details_desc)
    TextView tvCouponDetailsDesc;
    @BindView(R.id.tv_top_title)
    TextView tvTopTitle;
    @BindView(R.id.tv_coupon_details_use_bar_code)
    TextView tvCouponDetailsUseBarCode;
    @BindView(R.id.iv_coupon_details_bar_code)
    ImageView ivCouponDetailsBarCode;
    @BindView(R.id.tv_coupon_details_code)
    TextView tvCouponDetailsCode;

    private String ticketType = "";
    private String ticketName = "";
    private String couponNo = "";
    private String useDate = "";
    private String ticketDesc = "";
    private String RightTemplateCode = "";
    private String CreateTime = "";
    private String CouponCode = "";
    private boolean isClick = false;

    @Override
    public int getLayoutId() {
        return R.layout.activity_coupon_details;
    }

    @Override
    public CouponDetailsBtUrlContract.Presenter createPresenter() {
        return new CouponDetailsBtUrlPresenter(this);
    }

    @Override
    public CouponDetailsBtUrlContract.View createView() {
        return this;
    }

    @Override
    public void init() {
        //将每个Activity加入到栈中
        ApplicationUtil.getManager().addActivity(this);
        //防止状态栏和标题重叠
        ImmersionBar.setTitleBar(this, tvCouponDetailsTop);
        ticketType = getIntent().getStringExtra("ticket_type");
        switch (ticketType) {
            case "1":
                tvTopTitle.setText(getResources().getString(R.string.offset_coupon));
                tvCouponDetailsType.setText(getResources().getString(R.string.offset_coupon));
                break;
            case "2":
                tvTopTitle.setText(getResources().getString(R.string.group_bug_coupon));
                tvCouponDetailsType.setText(getResources().getString(R.string.group_bug_coupon));
                break;
            case "3":
                tvTopTitle.setText(getResources().getString(R.string.cash_coupon));
                tvCouponDetailsType.setText(getResources().getString(R.string.cash_coupon));
                break;
            case "4":
                tvTopTitle.setText(getResources().getString(R.string.exchange_coupon));
                tvCouponDetailsType.setText(getResources().getString(R.string.exchange_coupon));
                break;
            case "5":
                tvTopTitle.setText(getResources().getString(R.string.discount_coupon));
                tvCouponDetailsType.setText(getResources().getString(R.string.discount_coupon));
                break;
        }

        ticketName = getIntent().getStringExtra("ticket_name");
        tvCouponDetailsName.setText(ticketName);
        couponNo = getIntent().getStringExtra("coupon_no");
        useDate = getResources().getString(R.string.effective_date) + getIntent().getStringExtra("use_date");
        tvCouponDetailsTime.setText(useDate);
        ticketDesc = getIntent().getStringExtra("ticket_desc");
        ticketDesc = ticketDesc.replace("<p>", "").replace("</p>", "");
        tvCouponDetailsDesc.setText(ticketDesc);
        RightTemplateCode = getIntent().getStringExtra("right_template_code");
        CreateTime = getIntent().getStringExtra("create_time");
        CouponCode = getIntent().getStringExtra("coupon_code");

        if (!couponNo.equals("") && couponNo != null) {
            try {
                Bitmap barCode = BarCodeUtil.createBarCode(couponNo, (int) getResources().getDimension(R.dimen.x1020), (int) getResources().getDimension(R.dimen.y252));
                ivCouponDetailsBarCode.setImageBitmap(barCode);
            } catch (Exception e) {
                e.printStackTrace();
            }
            tvCouponDetailsCode.setText(couponNo);
            btCouponDetails.setVisibility(View.GONE);
            tvCouponDetailsUseBarCode.setVisibility(View.VISIBLE);
            ivCouponDetailsBarCode.setVisibility(View.VISIBLE);
            tvCouponDetailsCode.setVisibility(View.VISIBLE);
        } else {
            btCouponDetails.setVisibility(View.VISIBLE);
            tvCouponDetailsUseBarCode.setVisibility(View.GONE);
            ivCouponDetailsBarCode.setVisibility(View.GONE);
            tvCouponDetailsCode.setVisibility(View.GONE);
        }
    }

    @Override
    public void initListener() {

    }

    @Override
    public void initData() {

    }

    @Override
    public void getCouponDetailsBtUrl(CouponDetailsBtUrlBean data) {
        isClick = true;
        startActivity(new Intent(this, WebViewActivity.class).putExtra("land_page", data.getUrl()));
    }

    @Override
    public void setMsg(String msg) {

    }

    @Override
    public <T> ObservableTransformer<T, T> bindLifecycle() {
        return this.bindToLifecycle();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (isClick)
            setResult(IConstants.RESULT_CODE);
    }

    @OnClick(R.id.bt_coupon_details)
    public void onViewClicked() {
        TreeMap<String, String> map = new TreeMap<>();
        map.put("right_template_code", RightTemplateCode);
        map.put("create_time", CreateTime);
        map.put("coupon_code", CouponCode);
        getPresenter().getCouponDetailsBtUrl(map, true, true);
    }
}
