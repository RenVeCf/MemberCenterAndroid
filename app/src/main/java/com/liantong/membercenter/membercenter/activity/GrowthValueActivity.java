package com.liantong.membercenter.membercenter.activity;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.gyf.barlibrary.ImmersionBar;
import com.liantong.membercenter.membercenter.R;
import com.liantong.membercenter.membercenter.adapter.GrowthValueAdapter;
import com.liantong.membercenter.membercenter.base.BaseActivity;
import com.liantong.membercenter.membercenter.bean.GrowthValueBean;
import com.liantong.membercenter.membercenter.common.view.TopView;
import com.liantong.membercenter.membercenter.contract.GrowthValueContract;
import com.liantong.membercenter.membercenter.presenter.GrowthValuePresenter;
import com.liantong.membercenter.membercenter.utils.ApplicationUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import butterknife.BindView;
import io.reactivex.ObservableTransformer;

/**
 * Description ：成长值
 * Author ： MengYang
 * Email ： 942685687@qq.com
 * Time ： 2018/8/27.
 */

public class GrowthValueActivity extends BaseActivity<GrowthValueContract.View, GrowthValueContract.Presenter> implements GrowthValueContract.View, BaseQuickAdapter.RequestLoadMoreListener {

    @BindView(R.id.srl_growth_value)
    SwipeRefreshLayout srlGrowthValue;
    @BindView(R.id.rv_growth_value)
    RecyclerView rvGrowthValue;
    @BindView(R.id.tv_growth_value_top)
    TopView tvGrowthValueTop;

    private GrowthValueAdapter mGrowthValueAdapter;
    private List<GrowthValueBean.GrowthValueItemBean> mGrowthValueBean;
    private int pageNum = 1; //请求页数

    @Override
    public int getLayoutId() {
        return R.layout.activity_growth_value;
    }

    @Override
    public GrowthValueContract.Presenter createPresenter() {
        return new GrowthValuePresenter(this);
    }

    @Override
    public GrowthValueContract.View createView() {
        return this;
    }

    @Override
    public void init() {
        //将每个Activity加入到栈中
        ApplicationUtil.getManager().addActivity(this);
        //防止状态栏和标题重叠
        ImmersionBar.setTitleBar(this, tvGrowthValueTop);

        //设置RecyclerView方向和是否反转
        LinearLayoutManager GrowthValueList = new LinearLayoutManager(ApplicationUtil.getContext(), LinearLayoutManager.VERTICAL, false);
        rvGrowthValue.setLayoutManager(GrowthValueList);
        rvGrowthValue.setHasFixedSize(true); //item如果一样的大小，可以设置为true让RecyclerView避免重新计算大小
        rvGrowthValue.setItemAnimator(new DefaultItemAnimator()); //默认动画

        //初始化数据
        mGrowthValueBean = new ArrayList<>();
        mGrowthValueAdapter = new GrowthValueAdapter(mGrowthValueBean);
        rvGrowthValue.setAdapter(mGrowthValueAdapter);
    }

    @Override
    public void initListener() {
        //下拉刷新
        srlGrowthValue.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                pageNum = 1;
                initData();
                srlGrowthValue.setRefreshing(false);
            }
        });
    }

    @Override
    public void initData() {
        TreeMap<String, String> map = new TreeMap<>();
        map.put("page", pageNum + ""); //请求页码
        map.put("page_size", "20"); //请求每页条数
        getPresenter().getGrowthValueList(map, true, false);
    }

    @Override
    public void getGrowthValueList(GrowthValueBean data) {
        if (data.getTotal() > 0) {
            if (pageNum == 1) {
                //如果是第一页，那么清除数据，从新添加
                mGrowthValueBean.clear();
                mGrowthValueBean.addAll(data.getList());

                if (data.getTotal_page() > 0) {
                    pageNum += 1; //如果总页数大于0，下回请求就把页数加1
                    mGrowthValueAdapter = new GrowthValueAdapter(mGrowthValueBean);
                    rvGrowthValue.setAdapter(mGrowthValueAdapter);
                    //上拉加载
                    mGrowthValueAdapter.setOnLoadMoreListener(this, rvGrowthValue);
                } else {
                    mGrowthValueAdapter.loadMoreEnd();//完成所有加载
                }
            } else {
                if (data.getList().size() > 0) { //如果item大于0
                    pageNum += 1;
                    mGrowthValueAdapter.addData(data.getList()); //追加数据
                    mGrowthValueAdapter.loadMoreComplete(); //完成本次
                } else {
                    mGrowthValueAdapter.loadMoreEnd(); //完成所有加载
                }
            }
        } else {
            mGrowthValueAdapter.loadMoreEnd(); //完成所有加载
        }
    }

    @Override
    public void setMsg(String msg) {

    }

    @Override
    public <T> ObservableTransformer<T, T> bindLifecycle() {
        return this.bindToLifecycle();
    }

    @Override
    public void onLoadMoreRequested() {
        initData();
    }
}
