package com.liantong.membercenter.membercenter.contract;

import com.liantong.membercenter.membercenter.base.BasePresenter;
import com.liantong.membercenter.membercenter.base.BaseView;
import com.liantong.membercenter.membercenter.bean.ModifyPersonalDataBean;

import java.util.TreeMap;

import io.reactivex.ObservableTransformer;

/**
 * 作者：rmy on 2017/12/27 10:30
 * 邮箱：942685687@qq.com
 * LoginContract  V 、P契约类
 */
public interface ModifyPersonalDataContract {

    interface View extends BaseView {
        //不同的Bean单独处理
        void getModifyPersonalData(ModifyPersonalDataBean data);

        void setMsg(String msg);

        <T> ObservableTransformer<T, T> bindLifecycle();
    }

    abstract class Presenter extends BasePresenter<View> {
        public abstract void getModifyPersonalData(TreeMap<String, String> map, boolean isDialog, boolean cancelable);
    }
}