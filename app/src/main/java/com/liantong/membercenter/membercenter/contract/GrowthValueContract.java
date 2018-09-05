package com.liantong.membercenter.membercenter.contract;

import com.liantong.membercenter.membercenter.base.BasePresenter;
import com.liantong.membercenter.membercenter.base.BaseView;
import com.liantong.membercenter.membercenter.bean.GrowthValueBean;

import java.util.TreeMap;

import io.reactivex.ObservableTransformer;

/**
 * Description ：GrowthValueContract  V 、P契约类
 * Author ： MengYang
 * Email ： 942685687@qq.com
 * Time ： 2018/8/26.
 */
public interface GrowthValueContract {

    interface View extends BaseView {
        //不同的Bean单独处理
        void getGrowthValueList(GrowthValueBean data);

        void setMsg(String msg);

        <T> ObservableTransformer<T, T> bindLifecycle();
    }

    abstract class Presenter extends BasePresenter<View> {
        public abstract void getGrowthValueList(TreeMap<String, String> map, boolean isDialog, boolean cancelable);
    }
}