package com.juzhe.www.ui.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.flyco.tablayout.SlidingTabLayout;
import com.juzhe.www.R;
import com.juzhe.www.base.BaseMvpFragment;
import com.juzhe.www.bean.AdvertModel;
import com.juzhe.www.bean.ClassfyModel;
import com.juzhe.www.bean.IconModel;
import com.juzhe.www.bean.UserModel;
import com.juzhe.www.common.mvp_senior.annotaions.CreatePresenterAnnotation;
import com.juzhe.www.mvp.contract.HomeFragmentContract;
import com.juzhe.www.mvp.presenter.HomeFragmentPresenter;
import com.juzhe.www.ui.activity.XWebViewActivity;
import com.juzhe.www.ui.activity.person.IntroductionActivity;
import com.juzhe.www.ui.activity.person.MessageActivity;
import com.juzhe.www.ui.activity.person.PersonalActivity;
import com.juzhe.www.ui.activity.product.ProductDetailsActivity;
import com.juzhe.www.ui.activity.product.ProductListActivity;
import com.juzhe.www.ui.activity.product.ProductListJDActivity;
import com.juzhe.www.ui.activity.product.ProductListPddActivity;
import com.juzhe.www.ui.activity.product.SearchActivity;
import com.juzhe.www.ui.activity.WebViewActivity;
import com.juzhe.www.ui.adapter.BasePagerAdapter;
import com.juzhe.www.ui.adapter.ChildAdapter;
import com.juzhe.www.ui.adapter.FastEntranceAdapter;
import com.juzhe.www.ui.adapter.IconAdapter;
import com.juzhe.www.ui.widget.FiltPopuWindow;
import com.juzhe.www.ui.widget.GlideImageLoader;
import com.juzhe.www.utils.IntentUtils;
import com.juzhe.www.utils.SpacesItemDecoration;
import com.juzhe.www.utils.TextFontUtils;
import com.juzhe.www.utils.UserUtils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.youth.banner.Banner;
import com.youth.banner.listener.OnBannerListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * @package: com.juzhe.www.ui.fragment
 * @user:xhkj
 * @date:2018/10/31
 * @description:首页
 **/
@CreatePresenterAnnotation(HomeFragmentPresenter.class)
public class HomeFragment extends BaseMvpFragment<HomeFragmentContract.View, HomeFragmentPresenter> implements HomeFragmentContract.View, OnBannerListener {
    @BindView(R.id.img_me)
    ImageView imgMe;
    @BindView(R.id.txt_title)
    TextView txtTitle;
    @BindView(R.id.img_message)
    ImageView imgMessage;
    @BindView(R.id.tabs)
    SlidingTabLayout tabs;
    @BindView(R.id.viewpager)
    ViewPager viewpager;
    @BindView(R.id.appbar_layout)
    AppBarLayout appBarLayout;
    @BindView(R.id.refresh_layout)
    SmartRefreshLayout refreshLayout;
    @BindView(R.id.btn_select)
    ImageButton btnSelect;
    @BindView(R.id.banner)
    Banner banner;
    @BindView(R.id.txt_info_title)
    TextView txtInfoTitle;
    @BindView(R.id.recycler_entrance)
    RecyclerView recyclerEntrance;
    @BindView(R.id.recycler_icon)
    RecyclerView recyclerIcon;
    @BindView(R.id.txt_minute)
    TextView txtMinute;
    @BindView(R.id.txt_app_name)
    TextView txtAppName;
    @BindView(R.id.recycler_child)
    RecyclerView recyclerChild;
    Unbinder unbinder;
    private int position;
    private BasePagerAdapter myAdapter;
    private List<ClassfyModel> classfiy;
    private IconAdapter iconAdapter;
    private ChildAdapter childAdapter;
    private UserModel userModel;
    private boolean isRefresh = true;
    private List<AdvertModel> model;


    @Override
    protected int getLayout() {
        return R.layout.fragment_home;
    }

    @Override
    protected void initView(LayoutInflater inflater) {
        super.initView(inflater);
        initRecyclerView();
        userModel = UserUtils.getUser(mContext);
        initData();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerEntrance.setLayoutManager(linearLayoutManager);
        recyclerEntrance.addItemDecoration(new SpacesItemDecoration(0));
        List<String> fruitList = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            fruitList.add(i + "");
        }
        FastEntranceAdapter adapter = new FastEntranceAdapter(fruitList);
        recyclerEntrance.setAdapter(adapter);
        TextFontUtils.setTextTypeHaiPai(mContext, txtMinute);
        TextFontUtils.setTextTypeHaiPai(mContext, txtAppName);
        TextFontUtils.setTextTypeDTr(mContext, txtInfoTitle);
        TextFontUtils.setTextTypeDTr(mContext, txtTitle);
    }

    /**
     * 初始化数据
     */
    private void initData() {
        getMvpPresenter().getIconClassify();//获取分类列表
        getMvpPresenter().getUserInfo(userModel.getId(), userModel.getUser_channel_id());
        getMvpPresenter().getAdvert(userModel.getId(), userModel.getUser_channel_id());
        getMvpPresenter().getIconpage(userModel.getId(), userModel.getUser_channel_id());
    }

    @Override
    protected void initEvent() {
        super.initEvent();
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                isRefresh = false;
                if (myAdapter != null) {
                    ((ProductListFragment) myAdapter.getItem(position)).lazyFetchData();
                }
                getMvpPresenter().getUserInfo(userModel.getId(), userModel.getUser_channel_id());
                getMvpPresenter().getAdvert(userModel.getId(), userModel.getUser_channel_id());
                getMvpPresenter().getIconpage(userModel.getId(), userModel.getUser_channel_id());
            }
        });
        iconAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                IconModel iconModel = iconAdapter.getData().get(position);
                if (position == iconAdapter.getData().size() - 1) {
                    recyclerChild.setVisibility(recyclerChild.getVisibility() == View.VISIBLE ? View.GONE : View.VISIBLE);
                } else {
                    Bundle bundle = new Bundle();
                    bundle.putString("name", iconModel.getName());
                    bundle.putString("key", iconModel.getKey());
                    IntentUtils.get().goActivity(mContext, ProductListActivity.class, bundle);
                }
            }
        });
        childAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                switch (position) {
                    case 0:
                        IntentUtils.get().goActivity(mContext, ProductListJDActivity.class);
                        break;
                    case 1:
                        IntentUtils.get().goActivity(mContext, ProductListPddActivity.class);
                        break;
                }

            }
        });

    }

    @Override
    protected void initImmersionBar() {
        super.initImmersionBar();
        mImmersionBar.titleBar(R.id.toolbar)
                .init();
    }

    @OnClick({R.id.img_me, R.id.img_message, R.id.btn_select, R.id.ll_search, R.id.txt_app_name})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_me://跳转个人中心
                IntentUtils.get().goActivity(mContext, PersonalActivity.class);
                break;
            case R.id.img_message:
                IntentUtils.get().goActivity(mContext, MessageActivity.class);
                break;
            case R.id.btn_select:
                btnSelect.setImageResource(R.drawable.ic_close);
                showFilterPopu();
                break;
            case R.id.ll_search:
                IntentUtils.get().goActivity(mContext, SearchActivity.class);
                break;
            case R.id.txt_app_name:
                IntentUtils.get().goActivity(getView().getContext(), IntroductionActivity.class);
                break;
        }
    }

    /**
     * 显示筛选框
     */
    public void showFilterPopu() {
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
    }


    private void initRecyclerView() {
        recyclerChild.setNestedScrollingEnabled(false);
        GridLayoutManager childManager = new GridLayoutManager(mContext, 5);
        childManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerChild.setLayoutManager(childManager);
        recyclerChild.addItemDecoration(new SpacesItemDecoration(0));
        recyclerChild.setNestedScrollingEnabled(false);
        childAdapter = new ChildAdapter(R.layout.item_menu);
        recyclerChild.setAdapter(childAdapter);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(mContext, 5);
        gridLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerIcon.setLayoutManager(gridLayoutManager);
        recyclerIcon.addItemDecoration(new SpacesItemDecoration(0));
        iconAdapter = new IconAdapter(R.layout.item_menu);
        recyclerIcon.setAdapter(iconAdapter);
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
                HomeFragment.this.position = position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    public void setOnclick(int position) {

    }

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
    public void setAdvert(List<AdvertModel> model) {
        this.model = model;
        List images = new ArrayList();
        for (AdvertModel advertModel : model) {
            images.add(advertModel.getImage());
        }
        banner.setOnBannerListener(this);
        banner.setImages(images)
                .setImageLoader(new GlideImageLoader())
                .start();
    }

    @Override
    public void setUserModel(UserModel user) {
        txtTitle.setText("¥" + user.getTotal_income());
        UserUtils.saveUserInfo(mContext, user);
        refreshLayout.finishRefresh();
    }

    @Override
    public void setIconPage(List<IconModel> iconPage) {
        iconPage.add(new IconModel("其他", "京东", "http://res.jiruan.ltd/20181203/5c04f27f65baf-49839402390885.jpg"));
        iconAdapter.setNewData(iconPage);
        refreshLayout.finishRefresh();
        List<IconModel> iconModels = new ArrayList<>();
        iconModels.add(new IconModel("京东", "京东", "http://5b0988e595225.cdn.sohucs.com/images/20170903/814f6e0a4cbf40f6ad607d212f369424.jpeg"));
        iconModels.add(new IconModel("拼多多", "拼多多", "http://file16.zk71.com/File/CorpProductImages/2017/08/08/0_wenxia-1008_0_20170808155021.jpg"));

        childAdapter.setNewData(iconModels);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void OnBannerClick(int position) {
        Log.i("single", position + "dsdfads");
        Bundle bundle = new Bundle();
        switch (model.get(position).getType()) {
            case 1:
                bundle.putString("link", model.get(position).getLink());
                IntentUtils.get().goActivity(mContext, XWebViewActivity.class, bundle);
                break;
            case 2:
                bundle.putString("item_id", model.get(position).getLink());
                IntentUtils.get().goActivity(mContext, ProductDetailsActivity.class, bundle);
                break;
            case 3:
                break;
        }
    }
}
