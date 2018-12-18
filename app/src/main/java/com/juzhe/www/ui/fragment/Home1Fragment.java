package com.juzhe.www.ui.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
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
import android.widget.PopupWindow;
import android.widget.TextView;

import com.alibaba.android.vlayout.DelegateAdapter;
import com.blankj.utilcode.util.ToastUtils;
import com.flyco.tablayout.SlidingTabLayout;
import com.juzhe.www.R;
import com.juzhe.www.base.BaseMvpFragment;
import com.juzhe.www.bean.ClassfyModel;
import com.juzhe.www.bean.UserModel;
import com.juzhe.www.common.mvp_senior.annotaions.CreatePresenterAnnotation;
import com.juzhe.www.mvp.contract.CustomeHomeFragmentContract;
import com.juzhe.www.mvp.presenter.CustomHomeFragmentPresenter;
import com.juzhe.www.ui.activity.person.IntroductionActivity;
import com.juzhe.www.ui.activity.person.MessageActivity;
import com.juzhe.www.ui.activity.person.PersonalActivity;
import com.juzhe.www.ui.activity.product.SearchActivity;
import com.juzhe.www.ui.adapter.BasePagerAdapter;
import com.juzhe.www.ui.widget.FiltPopuWindow;
import com.juzhe.www.utils.IntentUtils;
import com.juzhe.www.utils.TextFontUtils;
import com.juzhe.www.utils.UserUtils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * @package: com.juzhe.www.ui.fragment
 * @user:xhkj
 * @date:2018/10/31
 * @description:首页
 **/
@CreatePresenterAnnotation(CustomHomeFragmentPresenter.class)
public class Home1Fragment extends BaseMvpFragment<CustomeHomeFragmentContract.View, CustomHomeFragmentPresenter> implements CustomeHomeFragmentContract.View {

    @BindView(R.id.recycler_home)
    RecyclerView recyclerHome;
    @BindView(R.id.tabs)
    SlidingTabLayout tabs;
    @BindView(R.id.btn_select)
    ImageButton btnSelect;
    @BindView(R.id.appbar_layout)
    AppBarLayout appBarLayout;
    @BindView(R.id.viewpager)
    ViewPager viewpager;
    @BindView(R.id.refresh_layout)
    SmartRefreshLayout refreshLayout;
    Unbinder unbinder;
    private List<DelegateAdapter.Adapter> mAdapters;
    private BasePagerAdapter myAdapter;
    private int position;
    private DelegateAdapter delegateAdapter;
    private List<ClassfyModel> classfiy;
    private UserModel userModel;

    @Override
    protected int getLayout() {
        return R.layout.fragment_home1;
    }

    @Override
    protected void initView(LayoutInflater inflater) {
        super.initView(inflater);
        userModel = UserUtils.getUser(mContext);
        mAdapters = new LinkedList<>();
        initRecyclerView();
    }

    @Override
    protected void initImmersionBar() {
        super.initImmersionBar();
        mImmersionBar.statusBarView(R.id.top_view)
                .statusBarColor(R.color.colorWhite)
                .statusBarDarkFont(true, 0.2f)
                .init();
    }

    private void initRecyclerView() {
        recyclerHome.setNestedScrollingEnabled(false);
        delegateAdapter = getMvpPresenter().initRecyclerView(recyclerHome);
    }
//R.id.img_me, R.id.img_message,
    @OnClick({ R.id.btn_select})
    public void onViewClicked(View view) {
        switch (view.getId()) {
//            case R.id.img_me://跳转个人中心
//                IntentUtils.get().goActivity(mContext, PersonalActivity.class);
//                break;
//            case R.id.img_message:
//                IntentUtils.get().goActivity(mContext, MessageActivity.class);
//                break;
            case R.id.btn_select:
                btnSelect.setImageResource(R.drawable.ic_close);
                showFilterPopu();
                break;
        }
    }

    /**
     * 显示筛选框
     */
    public void showFilterPopu() {
        if (classfiy != null && classfiy.size() > 0) {
            appBarLayout.setExpanded(false, false);
            appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
                @Override
                public void onOffsetChanged(AppBarLayout appBarLayout, int i) {
                    if (Math.abs(i) >= appBarLayout.getTotalScrollRange()) {
                        FiltPopuWindow mFilter = new FiltPopuWindow.Builder(mContext, new FiltPopuWindow.Builder.OnCloseListener() {
                            @Override
                            public void onClick(FiltPopuWindow popupWindow, ClassfyModel confirm) {
                                if (popupWindow.isShowing()) popupWindow.dismiss();
                                if (classfiy.contains(confirm))
                                    viewpager.setCurrentItem(classfiy.indexOf(confirm));
                            }
                        }).setColumnCount(4)//设置列数，测试2.3.4.5没问题
                                .setDataSource(classfiy)
                                .setColorBg(R.color.colorWhite)
                                //所有的属性设置必须在build之前，不然无效
                                .build()
                                .createPop();
                        mFilter.showAsDropDown(tabs);
                        mFilter.setOnDismissListener(new PopupWindow.OnDismissListener() {
                            @Override
                            public void onDismiss() {
                                btnSelect.setImageResource(R.drawable.ic_more_classify);
                            }
                        });
                        appBarLayout.removeOnOffsetChangedListener(this);
                    }
                }
            });
        } else {
            ToastUtils.showShort("暂无分类信息");
        }
    }

    /**
     * 分类列表
     *
     * @param classfiy
     */
    @Override
    public void setClassfiy(List<ClassfyModel> classfiy) {
        this.classfiy = classfiy;
        List<Fragment> mFragments = new ArrayList<>();
        List<String> titles = new ArrayList<>();
        for (ClassfyModel classfyModel : classfiy) {
            titles.add(classfyModel.getName());
            mFragments.add(new ProductListFragment().newInstance(classfyModel.getKey()));
        }
        initTabViewPager(mFragments, titles);
    }

    @Override
    public void setHomeDelegateAdapter(List<DelegateAdapter.Adapter> homeDelegateAdapter) {
        delegateAdapter.notifyDataSetChanged();
        delegateAdapter.setAdapters(homeDelegateAdapter);
        recyclerHome.requestLayout();
    }

    @Override
    public void setUserModel(UserModel user) {
        UserUtils.saveUserInfo(mContext, user);
        refreshLayout.finishRefresh();
    }

    @Override
    protected void initEvent() {
        super.initEvent();
        getMvpPresenter().getCutomData();
        getMvpPresenter().getIconClassify();
        if (userModel != null)
            getMvpPresenter().getUserInfo(userModel.getId(), userModel.getUser_channel_id());
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                if (myAdapter != null) {
                    ((ProductListFragment) myAdapter.getItem(position)).lazyFetchData();
                }
                getMvpPresenter().getUserInfo(userModel.getId(), userModel.getUser_channel_id());
                getMvpPresenter().getCutomData();
            }
        });
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
