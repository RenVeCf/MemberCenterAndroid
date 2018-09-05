package com.liantong.membercenter.membercenter.activity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.UnderlineSpan;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.contrarywind.view.WheelView;
import com.google.gson.Gson;
import com.liantong.membercenter.membercenter.R;
import com.liantong.membercenter.membercenter.base.BaseActivity;
import com.liantong.membercenter.membercenter.bean.CityAddressBean;
import com.liantong.membercenter.membercenter.bean.ModifyPersonalDataBean;
import com.liantong.membercenter.membercenter.common.config.IConstants;
import com.liantong.membercenter.membercenter.common.view.TopView;
import com.liantong.membercenter.membercenter.contract.ModifyPersonalDataContract;
import com.liantong.membercenter.membercenter.presenter.ModifyPersonalDataPresenter;
import com.liantong.membercenter.membercenter.utils.ApplicationUtil;
import com.liantong.membercenter.membercenter.utils.SPUtil;
import com.liantong.membercenter.membercenter.utils.ToastUtil;
import com.liantong.membercenter.membercenter.utils.VerifyUtils;

import org.json.JSONArray;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.TreeMap;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.ObservableTransformer;

/**
 * Description ：修改个人资料
 * Author ： MengYang
 * Email ： 942685687@qq.com
 * Time ： 2018/8/27.
 */

public class ModifyPersonalDataActivity extends BaseActivity<ModifyPersonalDataContract.View, ModifyPersonalDataContract.Presenter> implements ModifyPersonalDataContract.View {

    @BindView(R.id.tv_modify_personal_data_top)
    TopView tvModifyPersonalDataTop;
    @BindView(R.id.tv_modify_personal_data_phone_num)
    TextView tvModifyPersonalDataPhoneNum;
    @BindView(R.id.tv_modify_personal_data_real_name)
    TextView tvModifyPersonalDataRealName;
    @BindView(R.id.ed_modify_personal_data_id_card)
    EditText edModifyPersonalDataIdCard;
    @BindView(R.id.ed_modify_personal_data_e_mail_address)
    EditText edModifyPersonalDataEMailAddress;
    @BindView(R.id.ll_modify_personal_data_sex)
    LinearLayout llModifyPersonalDataSex;
    @BindView(R.id.ll_modify_personal_data_detailed_address)
    LinearLayout llModifyPersonalDataDetailedAddress;
    @BindView(R.id.ed_modify_personal_data_door_num)
    EditText edModifyPersonalDataDoorNum;
    @BindView(R.id.cb_modify_personal_data)
    CheckBox cbModifyPersonalData;
    @BindView(R.id.bt_modify_personal_data)
    Button btModifyPersonalData;
    @BindView(R.id.wv_modify_personal_data)
    WheelView wvModifyPersonalData;
    @BindView(R.id.tv_modify_personal_data_sex)
    TextView tvModifyPersonalDataSex;
    @BindView(R.id.tv_modify_personal_data_detailed_address)
    TextView tvModifyPersonalDataDetailedAddress;

    private ArrayList<CityAddressBean> options1Items = new ArrayList<>();
    private ArrayList<ArrayList<String>> options2Items = new ArrayList<>();
    private ArrayList<ArrayList<ArrayList<String>>> options3Items = new ArrayList<>();

    @Override
    public int getLayoutId() {
        return R.layout.activity_modify_personal_data;
    }

    @Override
    public ModifyPersonalDataContract.Presenter createPresenter() {
        return new ModifyPersonalDataPresenter(this);
    }

    @Override
    public ModifyPersonalDataContract.View createView() {
        return this;
    }

    @Override
    public void init() {
        //将每个Activity加入到栈中
        ApplicationUtil.getManager().addActivity(this);
        tvModifyPersonalDataPhoneNum.setText(SPUtil.get(this, IConstants.PHONE, "").toString());
        tvModifyPersonalDataRealName.setText(SPUtil.get(this, IConstants.NAME, "").toString());
        cbModifyPersonalData.setText(getClickableSpan());
        //设置超链接可点击
        cbModifyPersonalData.setMovementMethod(LinkMovementMethod.getInstance());
    }

    /**
     * 获取可点击的SpannableString
     *
     * @return
     */
    private SpannableString getClickableSpan() {
        SpannableString spannableString = new SpannableString(getResources().getString(R.string.modify_personal_data_privacy_policy));
        //设置下划线文字
        spannableString.setSpan(new UnderlineSpan(), 5, 9, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        //设置文字的单击事件
        spannableString.setSpan(new ClickableSpan() {
            @Override
            public void onClick(View widget) {
                ModifyPersonalDataActivity.this.startActivity(new Intent(ModifyPersonalDataActivity.this, VipManualActivity.class));
            }

            @Override
            public void updateDrawState(@NonNull TextPaint ds) {
                //super.updateDrawState(ds);
                ds.setUnderlineText(false);
            }
        }, 5, 9, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        //设置文字的前景色
        spannableString.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.tx_link)), 5, 9, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        //设置下划线文字
        spannableString.setSpan(new UnderlineSpan(), 23, 27, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        //设置文字的单击事件
        spannableString.setSpan(new ClickableSpan() {
            @Override
            public void onClick(View widget) {
                ModifyPersonalDataActivity.this.startActivity(new Intent(ModifyPersonalDataActivity.this, VipCardDeclarationsActivity.class));
            }

            @Override
            public void updateDrawState(@NonNull TextPaint ds) {
                //super.updateDrawState(ds);
                ds.setUnderlineText(false);
            }
        }, 23, 27, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        //设置文字的前景色
        spannableString.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.tx_link)), 23, 27, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return spannableString;
    }

    public void showPickerView() {
        OptionsPickerView pvOptions = new OptionsPickerBuilder(this, new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                //返回的分别是三个级别的选中位置
                String city = options1Items.get(options1).getPickerViewText() +
                        options2Items.get(options1).get(options2) +
                        options3Items.get(options1).get(options2).get(options3);
                tvModifyPersonalDataDetailedAddress.setText(city);
                tvModifyPersonalDataDetailedAddress.setTextColor(getResources().getColor(R.color.black));
            }
        })
                .setTitleText("")
                .setCancelText(getResources().getString(R.string.cancel))
                .setSubmitText(getResources().getString(R.string.sure))
                .setOutSideCancelable(true)
                .setTextColorCenter(Color.BLACK)
                .setDividerColor(getResources().getColor(R.color.bg_line))
                .setContentTextSize(16)
                .setDecorView((ViewGroup) getWindow().getDecorView().findViewById(android.R.id.content))
                .build();
        pvOptions.setPicker(options1Items, options2Items, options3Items);//三级选择器
        pvOptions.show();
    }

    private void initJsonData() {//解析数据
        /**
         * 注意：assets 目录下的Json文件仅供参考，实际使用可自行替换文件
         * 关键逻辑在于循环体
         *
         * */
        String JsonData = getJson(this, "province.json");//获取assets目录下的json文件数据

        ArrayList<CityAddressBean> jsonBean = parseData(JsonData);//用Gson 转成实体

        /**
         * 添加省份数据
         *
         * 注意：如果是添加的JavaBean实体，则实体类需要实现 IPickerViewData 接口，
         * PickerView会通过getPickerViewText方法获取字符串显示出来。
         */
        options1Items = jsonBean;

        for (int i = 0; i < jsonBean.size(); i++) {//遍历省份
            ArrayList<String> CityList = new ArrayList<>();//该省的城市列表（第二级）
            ArrayList<ArrayList<String>> Province_AreaList = new ArrayList<>();//该省的所有地区列表（第三极）

            for (int c = 0; c < jsonBean.get(i).getCityList().size(); c++) {//遍历该省份的所有城市
                String CityName = jsonBean.get(i).getCityList().get(c).getName();
                CityList.add(CityName);//添加城市
                ArrayList<String> City_AreaList = new ArrayList<>();//该城市的所有地区列表

                //如果无地区数据，建议添加空字符串，防止数据为null 导致三个选项长度不匹配造成崩溃
                if (jsonBean.get(i).getCityList().get(c).getArea() == null
                        || jsonBean.get(i).getCityList().get(c).getArea().size() == 0) {
                    City_AreaList.add("");
                } else {
                    City_AreaList.addAll(jsonBean.get(i).getCityList().get(c).getArea());
                }
                Province_AreaList.add(City_AreaList);//添加该省所有地区数据
            }

            /**
             * 添加城市数据
             */
            options2Items.add(CityList);

            /**
             * 添加地区数据
             */
            options3Items.add(Province_AreaList);
        }
    }

    public String getJson(Context context, String fileName) {

        StringBuilder stringBuilder = new StringBuilder();
        try {
            AssetManager assetManager = context.getAssets();
            BufferedReader bf = new BufferedReader(new InputStreamReader(
                    assetManager.open(fileName)));
            String line;
            while ((line = bf.readLine()) != null) {
                stringBuilder.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stringBuilder.toString();
    }


    public ArrayList<CityAddressBean> parseData(String result) {//Gson 解析
        ArrayList<CityAddressBean> detail = new ArrayList<>();
        try {
            JSONArray data = new JSONArray(result);
            Gson gson = new Gson();
            for (int i = 0; i < data.length(); i++) {
                CityAddressBean entity = gson.fromJson(data.optJSONObject(i).toString(), CityAddressBean.class);
                detail.add(entity);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return detail;
    }

    private void setDialog() {
        final Dialog mCameraDialog = new Dialog(this, R.style.BottomDialog);
        LinearLayout root = (LinearLayout) LayoutInflater.from(this).inflate(R.layout.dialog_bottom, null);
        //初始化视图
        root.findViewById(R.id.bt_man).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tvModifyPersonalDataSex.setText(getResources().getString(R.string.man));
                tvModifyPersonalDataSex.setTextColor(getResources().getColor(R.color.black));
                mCameraDialog.dismiss();
            }
        });
        root.findViewById(R.id.bt_woman).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tvModifyPersonalDataSex.setText(getResources().getString(R.string.woman));
                tvModifyPersonalDataSex.setTextColor(getResources().getColor(R.color.black));
                mCameraDialog.dismiss();
            }
        });
        root.findViewById(R.id.bt_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCameraDialog.dismiss();
            }
        });
        mCameraDialog.setContentView(root);
        Window dialogWindow = mCameraDialog.getWindow();
        dialogWindow.setGravity(Gravity.BOTTOM);
        WindowManager.LayoutParams lp = dialogWindow.getAttributes(); // 获取对话框当前的参数值
        lp.x = 0; // 新位置X坐标
        lp.y = 0; // 新位置Y坐标
        lp.width = (int) getResources().getDisplayMetrics().widthPixels; // 宽度
        root.measure(0, 0);
        lp.height = root.getMeasuredHeight();

        lp.alpha = 9f; // 透明度
        dialogWindow.setAttributes(lp);
        mCameraDialog.show();
    }

    @Override
    public void initListener() {

    }

    @Override
    public void initData() {
        initJsonData();
    }

    @Override
    public void getModifyPersonalData(ModifyPersonalDataBean data) {
        setResult(IConstants.RESULT_CODE);
        finish();
    }

    @Override
    public void setMsg(String msg) {

    }

    @Override
    public <T> ObservableTransformer<T, T> bindLifecycle() {
        return this.bindToLifecycle();
    }

    @OnClick({R.id.ll_modify_personal_data_sex, R.id.ll_modify_personal_data_detailed_address, R.id.bt_modify_personal_data})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_modify_personal_data_sex:
                setDialog();
                break;
            case R.id.ll_modify_personal_data_detailed_address:
                showPickerView();
                break;
            case R.id.bt_modify_personal_data:
                if (!edModifyPersonalDataEMailAddress.getText().toString().equals("")) {
                    if (!VerifyUtils.isEmail(edModifyPersonalDataEMailAddress.getText().toString()))
                        ToastUtil.showShortToast(getResources().getString(R.string.email_error));
                }
                if (!edModifyPersonalDataIdCard.getText().toString().equals("")) {
                    if (!VerifyUtils.isChineseCard(edModifyPersonalDataIdCard.getText().toString()))
                        ToastUtil.showShortToast(getResources().getString(R.string.id_card_error));
                } else {
                    TreeMap<String, String> map = new TreeMap<>();
                    if (!tvModifyPersonalDataDetailedAddress.getText().toString().equals(""))
                        map.put("address", tvModifyPersonalDataDetailedAddress.getText().toString());
                    if (!edModifyPersonalDataDoorNum.getText().toString().equals(""))
                        map.put("address_detail", edModifyPersonalDataDoorNum.getText().toString());
                    if (!edModifyPersonalDataEMailAddress.getText().toString().equals(""))
                        map.put("email", edModifyPersonalDataEMailAddress.getText().toString());
                    if (tvModifyPersonalDataSex.getText().toString().equals(getResources().getString(R.string.man)))
                        map.put("gender", "0");
                    else if (tvModifyPersonalDataSex.getText().toString().equals(getResources().getString(R.string.woman)))
                        map.put("gender", "1");
                    if (!edModifyPersonalDataIdCard.getText().toString().equals(""))
                        map.put("idcard_no", edModifyPersonalDataIdCard.getText().toString());
                    if (!tvModifyPersonalDataRealName.getText().toString().equals(""))
                        map.put("name", tvModifyPersonalDataRealName.getText().toString());
                    getPresenter().getModifyPersonalData(map, true, true);
                }
                break;
        }
    }
}
