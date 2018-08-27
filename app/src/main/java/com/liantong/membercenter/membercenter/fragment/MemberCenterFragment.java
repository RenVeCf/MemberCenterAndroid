package com.liantong.membercenter.membercenter.fragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import com.gyf.barlibrary.ImmersionBar;
import com.liantong.membercenter.membercenter.R;
import com.liantong.membercenter.membercenter.activity.GrowthValueActivity;
import com.liantong.membercenter.membercenter.activity.ModifyPersonalDataActivity;
import com.liantong.membercenter.membercenter.activity.VipManualActivity;
import com.liantong.membercenter.membercenter.base.BaseFragment;
import com.liantong.membercenter.membercenter.base.BaseResponse;
import com.liantong.membercenter.membercenter.bean.LoginBean;
import com.liantong.membercenter.membercenter.contract.LoginContract;
import com.liantong.membercenter.membercenter.presenter.LoginPresenter;
import com.liantong.membercenter.membercenter.utils.StringLinkUtils;
import com.liantong.membercenter.membercenter.utils.ToastUtil;
import com.liantong.membercenter.membercenter.common.view.TopView;
import com.trello.rxlifecycle2.android.FragmentEvent;

import java.io.FileOutputStream;
import java.util.Hashtable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.ObservableTransformer;

import static android.graphics.Color.BLACK;
import static android.graphics.Color.WHITE;

/**
 * 作者：rmy on 2017/12/27 16:36
 * 邮箱：942685687@qq.com
 */

public class MemberCenterFragment extends BaseFragment<LoginContract.View, LoginContract.Presenter> implements LoginContract.View {

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
    public LoginContract.Presenter createPresenter() {
        return new LoginPresenter(mContext);
    }

    @Override
    public LoginContract.View createView() {
        return this;
    }

    @Override
    public void init() {
        //防止状态栏和标题重叠
        ImmersionBar.setTitleBar(getActivity(), tvMemberCenterTop);
        tvToModifyPersonalData.setText(StringLinkUtils.checkAutoLink(getResources().getString(R.string.to_modify_personal_data), "修改个人资料"));
        tvToModifyPersonalData.setMovementMethod(LinkMovementMethod.getInstance());
        tvToVipManual.setText(StringLinkUtils.checkAutoLink(tvToVipManual.getText().toString(), "点此了解"));
        tvToVipManual.setMovementMethod(LinkMovementMethod.getInstance());

        try {
            Bitmap barCode = createBarCode("1215156156415", (int) getResources().getDimension(R.dimen.x820), (int) getResources().getDimension(R.dimen.y212));
            ivVipBarCode.setImageBitmap(barCode);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initListener() {

    }

    @Override
    public void initData() {

    }

    /**
     * 生成条形码图片
     *
     * @param str    要往二维码中写入的内容,需要utf-8格式
     * @param width  图片的宽
     * @param height 图片的高
     * @return 返回一个条形bitmap
     * @throws Exception
     */
    public static Bitmap createBarCode(String str, Integer width, Integer height) throws Exception {
        BitMatrix bitMatrix = new MultiFormatWriter().encode(str, BarcodeFormat.CODE_128, width, height, getEncodeHintMap());
        return BitMatrixToBitmap(bitMatrix);
    }

    /**
     * 获得设置好的编码参数
     *
     * @return 编码参数
     */

    private static Hashtable<EncodeHintType, Object> getEncodeHintMap() {
        Hashtable<EncodeHintType, Object> hints = new Hashtable<EncodeHintType, Object>();
        //设置编码为utf-8
        hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
        // 设置QR二维码的纠错级别——这里选择最高H级别
        hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
        return hints;
    }

    /**
     * BitMatrix转换成Bitmap
     *
     * @param matrix
     * @return
     */

    private static Bitmap BitMatrixToBitmap(BitMatrix matrix) {
        int width = matrix.getWidth();
        int height = matrix.getHeight();
        int[] pixels = new int[width * height];
        for (int y = 0; y < height; y++) {
            int offset = y * width;
            for (int x = 0; x < width; x++) {
                if (matrix.get(x, y)) {
                    pixels[offset + x] = BLACK; //上面图案的颜色
                } else {
                    pixels[offset + x] = WHITE;//底色
                }
            }
        }
        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        bitmap.setPixels(pixels, 0, width, 0, 0, width, height);
        return bitmap;
    }

    @Override
    public void resultLogin(BaseResponse<LoginBean> data) {
    }

    @Override
    public void resultCaptcha(BaseResponse data) {
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
