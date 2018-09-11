package com.liantong.membercenter.membercenter.common.view;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.liantong.membercenter.membercenter.R;
import com.liantong.membercenter.membercenter.utils.isClickUtil;

/**
 * Description : 公用标题栏
 * Author : rmy
 * Email : 942685687@qq.com
 * Time : 2017/11/loading1
 */

public class TopView extends RelativeLayout implements View.OnClickListener {

    private TextView tvTopTitle;
    private ImageView ivTopBack;
    private ImageView ivTopOut;

    //各icon是否显示
    private Boolean isBack;
    private Boolean isOut;
    private Context mContext;

    public TopView(Context context) {
        super(context);
        initValues(context);
    }

    public TopView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initValues(context);

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.TopView);
        tvTopTitle.setText(typedArray.getString(R.styleable.TopView_title));
        tvTopTitle.setTextColor(typedArray.getColor(R.styleable.TopView_title_color, getResources().getColor(R.color.black)));
        isBack = typedArray.getBoolean(R.styleable.TopView_is_back, false);
        isOut = typedArray.getBoolean(R.styleable.TopView_is_out, false);
        typedArray.recycle();

        ivTopBack.setVisibility(isBack ? View.VISIBLE : View.GONE);
        ivTopOut.setVisibility(isOut ? View.VISIBLE : View.GONE);
    }

    public TopView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initValues(context);
    }

    private void initValues(final Context context) {
        mContext = context;
        View.inflate(context, R.layout.top_view, this);
        tvTopTitle = (TextView) this.findViewById(R.id.tv_top_title);
        ivTopBack = (ImageView) this.findViewById(R.id.iv_top_back);
        ivTopOut = (ImageView) this.findViewById(R.id.iv_top_out);

        ivTopBack.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_top_back:
                if (mContext instanceof Activity && isClickUtil.isFastClick()) {
                    ((Activity) mContext).finish();
                    if (((Activity) mContext).getCurrentFocus() != null) {
                        ((InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(((Activity) mContext).getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                    }
                }
                break;
            default:
                break;
        }
    }
}
