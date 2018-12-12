package com.juzhe.www.ui.fragment;

import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.android.vlayout.DelegateAdapter;
import com.flyco.tablayout.SlidingTabLayout;
import com.juzhe.www.R;
import com.juzhe.www.base.BaseMvpFragment;
import com.juzhe.www.bean.ClassfyModel;
import com.juzhe.www.common.mvp_senior.annotaions.CreatePresenterAnnotation;
import com.juzhe.www.mvp.contract.CustomeHomeFragmentContract;
import com.juzhe.www.mvp.presenter.CustomHomeFragmentPresenter;
import com.juzhe.www.ui.adapter.BasePagerAdapter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * @package: com.juzhe.www.ui.fragment
 * @user:xhkj
 * @date:2018/10/31
 * @description:首页
 **/
@CreatePresenterAnnotation(CustomHomeFragmentPresenter.class)
public class Home1Fragment extends BaseMvpFragment<CustomeHomeFragmentContract.View, CustomHomeFragmentPresenter> implements CustomeHomeFragmentContract.View {
    @BindView(R.id.img_me)
    ImageView imgMe;
    @BindView(R.id.txt_title)
    TextView txtTitle;
    @BindView(R.id.img_message)
    ImageView imgMessage;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.recycler_home)
    RecyclerView recyclerHome;
    @BindView(R.id.tabs)
    SlidingTabLayout tabs;
    @BindView(R.id.btn_select)
    ImageButton btnSelect;
    @BindView(R.id.appbar_layout)
    AppBarLayout appbarLayout;
    @BindView(R.id.viewpager)
    ViewPager viewpager;
    @BindView(R.id.refresh_layout)
    SmartRefreshLayout refreshLayout;
    Unbinder unbinder;
    private List<DelegateAdapter.Adapter> mAdapters;
    private BasePagerAdapter myAdapter;
    private int position;
    private DelegateAdapter delegateAdapter;

    @Override
    protected int getLayout() {
        return R.layout.fragment_home1;
    }

    @Override
    protected void initView(LayoutInflater inflater) {
        super.initView(inflater);
        mAdapters = new LinkedList<>();
        initRecyclerView();
    }

    @Override
    protected void initImmersionBar() {
        super.initImmersionBar();
        mImmersionBar
                .statusBarDarkFont(true, 0.2f)
                .init();
    }

    private void initRecyclerView() {
        recyclerHome.setNestedScrollingEnabled(false);
        delegateAdapter = getMvpPresenter().initRecyclerView(recyclerHome);
    }


    /**
     * 分类列表
     *
     * @param classfiy
     */
    @Override
    public void setClassfiy(List<ClassfyModel> classfiy) {
        List<Fragment> mFragments = new ArrayList<>();
        List<String> titles = new ArrayList<>();
        for (ClassfyModel classfyModel : classfiy) {
            titles.add(classfyModel.getName());
            mFragments.add(new ProductListFragment());
        }
        initTabViewPager(mFragments, titles);
    }

    @Override
    public void setHomeDelegateAdapter(List<DelegateAdapter.Adapter> homeDelegateAdapter) {
        delegateAdapter.setAdapters(homeDelegateAdapter);
    }


    @Override
    protected void initEvent() {
        super.initEvent();
        getMvpPresenter().getCutomData();
        getMvpPresenter().getIconClassify();
    }

    private void initTabViewPager(List<Fragment> mFragments, List<String> mTitleList) {
        FragmentManager supportFragmentManager = getChildFragmentManager();
        myAdapter = new BasePagerAdapter(supportFragmentManager, mFragments, mTitleList);
        viewpager.setAdapter(myAdapter);
        viewpager.setOffscreenPageLimit(mFragments.size());
        tabs.setViewPager(viewpager);
        viewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                Home1Fragment.this.position = position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
