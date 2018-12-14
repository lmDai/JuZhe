package com.juzhe.www.mvp.presenter;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.alibaba.android.vlayout.DelegateAdapter;
import com.alibaba.android.vlayout.VirtualLayoutManager;
import com.alibaba.android.vlayout.layout.LinearLayoutHelper;
import com.alibaba.fastjson.JSON;
import com.blankj.utilcode.util.ToastUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.juzhe.www.Constant;
import com.juzhe.www.R;
import com.juzhe.www.bean.ClassfyModel;
import com.juzhe.www.bean.ComponentsBean;
import com.juzhe.www.bean.Compts;
import com.juzhe.www.bean.CutomHomeModel;
import com.juzhe.www.bean.Data;
import com.juzhe.www.bean.SubItemBean;
import com.juzhe.www.bean.UserModel;
import com.juzhe.www.common.https.ProgressObserver;
import com.juzhe.www.common.https.rxUtils.RxUtil;
import com.juzhe.www.common.utils.Utils;
import com.juzhe.www.mvp.contract.CustomeHomeFragmentContract;
import com.juzhe.www.mvp.model.MainModel;
import com.juzhe.www.mvp.model.PersonModule;
import com.juzhe.www.ui.activity.XWebViewActivity;
import com.juzhe.www.ui.adapter.BaseDelegateAdapter;
import com.juzhe.www.ui.adapter.FastEntranceAdapter;
import com.juzhe.www.ui.adapter.MenuAdapter;
import com.juzhe.www.ui.adapter.MenuSubItemAdapter;
import com.juzhe.www.ui.widget.GlideImageLoader;
import com.juzhe.www.ui.widget.MyLinearLayoutManager;
import com.juzhe.www.utils.GlideUtil;
import com.juzhe.www.utils.IntentUtils;
import com.juzhe.www.utils.SpacesItemDecoration;
import com.youth.banner.Banner;
import com.youth.banner.listener.OnBannerListener;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * @package: com.juzhe.www.mvp.presenter
 * @user:xhkj
 * @date:2018/10/29
 * @description:
 **/
public class CustomHomeFragmentPresenter extends CustomeHomeFragmentContract.Presenter {
    private int menuPosition;

    @Override
    public DelegateAdapter initRecyclerView(RecyclerView recyclerView) {
        //初始化
        //创建VirtualLayoutManager对象
        MyLinearLayoutManager layoutManager = new MyLinearLayoutManager(getView().getContext());
        layoutManager.setSmoothScrollbarEnabled(true);
        layoutManager.setAutoMeasureEnabled(true);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setNestedScrollingEnabled(false);

        //设置回收复用池大小，（如果一屏内相同类型的 View 个数比较多，需要设置一个合适的大小，防止来回滚动时重新创建 View）
        RecyclerView.RecycledViewPool viewPool = new RecyclerView.RecycledViewPool();
        recyclerView.setRecycledViewPool(viewPool);
        viewPool.setMaxRecycledViews(0, 20);

        //设置适配器
        DelegateAdapter delegateAdapter = new DelegateAdapter(new VirtualLayoutManager(getView().getContext()), true);
        recyclerView.setAdapter(delegateAdapter);
        return delegateAdapter;
    }

    //搜索样式一
    public BaseDelegateAdapter initSearch1(Compts searchBar) {
        return new BaseDelegateAdapter(getView().getContext(), new LinearLayoutHelper(), R.layout.item_search, 1, Constant.viewType.search_1) {
            @Override
            public void onBindViewHolder(BaseViewHolder holder, int position) {
                super.onBindViewHolder(holder, position);
                ImageView imgLeft = holder.getView(R.id.img_left);
                ImageView imgRight = holder.getView(R.id.img_right);
                LinearLayout llSearch = holder.getView(R.id.ll_search);
                LinearLayout llMainSearch = holder.getView(R.id.ll_main_search);
                GlideUtil.into(getView().getContext(), searchBar.getLeft().getImage(), imgLeft);
                GlideUtil.into(getView().getContext(), searchBar.getRight().getImage(), imgRight);
                GlideUtil.showImageViewLinearLayout(getView().getContext(),
                        searchBar.getBackground().getImage(), llMainSearch);
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ToastUtils.showShort("searchbar");
                    }
                });
            }
        };
    }

    //搜索样式二
    public BaseDelegateAdapter initSearch2(Compts searchBar) {
        return new BaseDelegateAdapter(getView().getContext(), new LinearLayoutHelper(), R.layout.item_search_2, 1, Constant.viewType.search_2) {
            @Override
            public void onBindViewHolder(BaseViewHolder holder, int position) {
                super.onBindViewHolder(holder, position);
                ImageView imgRight = holder.getView(R.id.img_right);
                LinearLayout llSearch = holder.getView(R.id.ll_search);
                LinearLayout llMainSearch = holder.getView(R.id.ll_main_search);
                GlideUtil.into(getView().getContext(), searchBar.getRight().getImage(), imgRight);
                GlideUtil.showImageViewLinearLayout(getView().getContext(),
                        searchBar.getBackground().getImage(), llMainSearch);
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ToastUtils.showShort("searchbar");
                    }
                });
            }
        };
    }

    //banner
    public BaseDelegateAdapter initBanner(List<ComponentsBean> urls) {
        return new BaseDelegateAdapter(getView().getContext(), new LinearLayoutHelper(), R.layout.layout_home_banner1, 1, Constant.viewType.banner_1) {
            @Override
            public void onBindViewHolder(BaseViewHolder holder, int position) {
                super.onBindViewHolder(holder, position);
                Banner banner = holder.getView(R.id.banner);
                List<String> images = new ArrayList();
                if (urls != null && urls.size() > 0) {
                    for (ComponentsBean bannerBean : urls) {
                        images.add(bannerBean.getImage());
                    }
                }
                banner.setOnBannerListener(new OnBannerListener() {
                    @Override
                    public void OnBannerClick(int position) {
                        ToastUtils.showShort(position + urls.get(position).getImage());
                        Bundle bundle = new Bundle();
                        bundle.putString("link", urls.get(position).getImage());
                        IntentUtils.get().goActivity(getView().getContext(), XWebViewActivity.class, bundle);
                    }
                });
                banner.setImages(images)
                        .setImageLoader(new GlideImageLoader())
                        .start();
            }
        };
    }


    public BaseDelegateAdapter initFastEntrace() {
        return new BaseDelegateAdapter(getView().getContext(), new LinearLayoutHelper(), R.layout.layout_home_entrace, 1, Constant.viewType.fast_entrance) {

            @Override
            public void onViewRecycled(BaseViewHolder holder) {
                if (holder.itemView instanceof ViewPager) {
                    ((ViewPager) holder.itemView).setAdapter(null);
                }
            }

            @Override
            public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                if (viewType == 1)
                    return new BaseViewHolder(
                            LayoutInflater.from(getView().getContext()).inflate(R.layout.layout_home_entrace, parent, false));

                return super.onCreateViewHolder(parent, viewType);
            }

            @Override
            public int getItemViewType(int position) {
                return 1;
            }

            @Override
            protected void onBindViewHolderWithOffset(BaseViewHolder holder, int position, int offsetTotal) {

            }

            @Override
            public void onBindViewHolder(BaseViewHolder holder, int position) {
                if (holder.itemView instanceof RecyclerView) {
                    RecyclerView recyclerView = (RecyclerView) holder.itemView;
                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getView().getContext());
                    linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
                    recyclerView.setLayoutManager(linearLayoutManager);
                    recyclerView.addItemDecoration(new SpacesItemDecoration(0));
                    List<String> fruitList = new ArrayList<>();
                    for (int i = 0; i < 10; i++) {
                        fruitList.add(i + "");
                    }
                    FastEntranceAdapter adapter = new FastEntranceAdapter(fruitList);
                    recyclerView.setAdapter(adapter);
                    adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                        @Override
                        public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                            ToastUtils.showShort(adapter.getData().get(position).toString() + "initFastEntrace");
                        }
                    });
                }
            }

        };
    }

    //菜单样式一
    public BaseDelegateAdapter initMenu1(List<ComponentsBean> menu) {
        return new BaseDelegateAdapter(getView().getContext(), new LinearLayoutHelper(), R.layout.layout_menu1, 1, Constant.viewType.menu_3) {
            @Override
            public void onBindViewHolder(BaseViewHolder holder, int position) {
                if (holder.itemView instanceof LinearLayout) {
                    RecyclerView recyclerIcon = holder.getView(R.id.recycler_icon);
                    //设置主菜单
                    GridLayoutManager gridLayoutManager = new GridLayoutManager(getView().getContext(), menu.size());
                    gridLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                    recyclerIcon.setLayoutManager(gridLayoutManager);
                    recyclerIcon.addItemDecoration(new SpacesItemDecoration(0));
                    MenuAdapter iconAdapter = new MenuAdapter(R.layout.item_menu);
                    recyclerIcon.setAdapter(iconAdapter);
                    iconAdapter.setNewData(menu);
                    //主菜单点击事件
                    iconAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                        @Override
                        public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                            ToastUtils.showShort(iconAdapter.getData().get(position).getTitle());
                        }
                    });
                }
            }
        };
    }

    //菜单样式一
    public BaseDelegateAdapter initMenu2(List<ComponentsBean> menu) {
        return new BaseDelegateAdapter(getView().getContext(), new LinearLayoutHelper(), R.layout.layout_menu1, 1, Constant.viewType.menu_3) {
            @Override
            public void onBindViewHolder(BaseViewHolder holder, int position) {
                if (holder.itemView instanceof LinearLayout) {
                    RecyclerView recyclerIcon = holder.getView(R.id.recycler_icon);

                    //设置主菜单
                    GridLayoutManager gridLayoutManager = new GridLayoutManager(getView().getContext(), menu.size());
                    gridLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                    recyclerIcon.setLayoutManager(gridLayoutManager);
                    recyclerIcon.addItemDecoration(new SpacesItemDecoration(0));
                    MenuAdapter iconAdapter = new MenuAdapter(R.layout.item_menu);
                    recyclerIcon.setAdapter(iconAdapter);
                    iconAdapter.setNewData(menu);

                    //主菜单点击事件
                    iconAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                        @Override
                        public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                            ToastUtils.showShort(iconAdapter.getData().get(position).getTitle());
                        }
                    });
                }
            }
        };
    }

    //菜单样式三
    public BaseDelegateAdapter initMenu3(List<ComponentsBean> menu) {
        return new BaseDelegateAdapter(getView().getContext(), new LinearLayoutHelper(), R.layout.layout_menu, 1, Constant.viewType.menu_3) {
            @Override
            public void onBindViewHolder(BaseViewHolder holder, int position) {
                if (holder.itemView instanceof LinearLayout) {
                    RecyclerView recyclerIcon = holder.getView(R.id.recycler_icon);
                    RecyclerView recyclerChild = holder.getView(R.id.recycler_child);
                    //设置主菜单
                    GridLayoutManager gridLayoutManager = new GridLayoutManager(getView().getContext(), menu.size());
                    gridLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                    recyclerIcon.setLayoutManager(gridLayoutManager);
                    recyclerIcon.addItemDecoration(new SpacesItemDecoration(0));
                    MenuAdapter iconAdapter = new MenuAdapter(R.layout.item_menu);
                    recyclerIcon.setAdapter(iconAdapter);
                    iconAdapter.setNewData(menu);
                    //设置子菜单
                    GridLayoutManager childManager = new GridLayoutManager(getView().getContext(), 5);
                    childManager.setOrientation(LinearLayoutManager.VERTICAL);
                    recyclerChild.setLayoutManager(childManager);
                    recyclerChild.addItemDecoration(new SpacesItemDecoration(0));
                    recyclerChild.setNestedScrollingEnabled(false);
                    MenuSubItemAdapter childAdapter = new MenuSubItemAdapter(R.layout.item_menu);
                    recyclerChild.setAdapter(childAdapter);
                    childAdapter.setNewData(menu.get(0).getSubItem());
                    //主菜单点击事件
                    iconAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                        @Override
                        public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                            ToastUtils.showShort(iconAdapter.getData().get(position).getTitle());
                            setChildRecyclerview(position, recyclerChild, childAdapter, iconAdapter.getData().get(position).getSubItem());
                        }
                    });
                }
            }
        };
    }

    private void setChildRecyclerview(int position, RecyclerView recyclerViewChild, MenuSubItemAdapter adapter, List<SubItemBean> subItem) {
        if (menuPosition == position) {
            recyclerViewChild.setVisibility(recyclerViewChild.getVisibility() == View.VISIBLE ? View.GONE : View.VISIBLE);
        } else {
            recyclerViewChild.setVisibility(View.VISIBLE);
        }
        adapter.setNewData(subItem);
        this.menuPosition = position;
    }

    //广告图样式一
    public DelegateAdapter.Adapter initAdvert1(List<ComponentsBean> advert) {
        return new BaseDelegateAdapter(getView().getContext(), new LinearLayoutHelper(), R.layout.advert_item1, 1, Constant.viewType.advert_1) {
            @Override
            public void onBindViewHolder(BaseViewHolder holder, int position) {
                super.onBindViewHolder(holder, position);
                ImageView imgaview = holder.getView(R.id.img);
                GlideUtil.loadIntoUseFitWidth(getView().getContext(), advert.get(0).getImage(), imgaview);

            }
        };
    }

    //广告图样式二
    public DelegateAdapter.Adapter initAdvert2(List<ComponentsBean> advert) {
        return new BaseDelegateAdapter(getView().getContext(), new LinearLayoutHelper(), R.layout.item_puzzle_two, 1, Constant.viewType.advert_2) {
            @Override
            public void onBindViewHolder(BaseViewHolder holder, int position) {
                super.onBindViewHolder(holder, position);
                ImageView imgaview = holder.getView(R.id.img);
                ImageView imgTwo = holder.getView(R.id.img_two);
                GlideUtil.loadIntoUseFitWidth(getView().getContext(), advert.get(0).getImage(), imgaview);
                GlideUtil.loadIntoUseFitWidth(getView().getContext(), advert.get(1).getImage(), imgTwo);
            }
        };
    }

    //广告图样式三
    public DelegateAdapter.Adapter initAdvert3(List<ComponentsBean> advert) {
        return new BaseDelegateAdapter(getView().getContext(), new LinearLayoutHelper(), R.layout.item_puzzle_threee, 1, Constant.viewType.advert_3) {
            @Override
            public void onBindViewHolder(BaseViewHolder holder, int position) {
                super.onBindViewHolder(holder, position);
                ImageView imgaview = holder.getView(R.id.img);
                ImageView imgTwo = holder.getView(R.id.img_two);
                ImageView imgThree = holder.getView(R.id.img_three);
                GlideUtil.loadIntoUseFitWidth(getView().getContext(), advert.get(0).getImage(), imgaview);
                GlideUtil.loadIntoUseFitWidth(getView().getContext(), advert.get(1).getImage(), imgTwo);
                GlideUtil.loadIntoUseFitWidth(getView().getContext(), advert.get(2).getImage(), imgThree);
            }
        };
    }

    //广告图样式四
    public DelegateAdapter.Adapter initAdvert4(List<ComponentsBean> advert) {
        return new BaseDelegateAdapter(getView().getContext(), new LinearLayoutHelper(), R.layout.advert_item4, 1, Constant.viewType.advert_4) {
            @Override
            public void onBindViewHolder(BaseViewHolder holder, int position) {
                super.onBindViewHolder(holder, position);
                ImageView imgaview = holder.getView(R.id.img);
                ImageView imgTwo = holder.getView(R.id.img_two);
                ImageView imgThree = holder.getView(R.id.img_three);
                GlideUtil.loadIntoUseFitWidth(getView().getContext(), advert.get(0).getImage(), imgaview);
                GlideUtil.loadIntoUseFitWidth(getView().getContext(), advert.get(1).getImage(), imgTwo);
                GlideUtil.loadIntoUseFitWidth(getView().getContext(), advert.get(2).getImage(), imgThree);
            }
        };
    }

    /**
     * 分类列表
     */
    @Override
    public void getIconClassify() {
        MainModel.getInstance(Utils.getContext()).getIconClassify(Constant.channel_id)
                .compose(RxUtil.observableIO2Main(getView()))
                .compose(RxUtil.hanResult())
                .subscribe(new ProgressObserver<List<ClassfyModel>>(this, true, "加载中...") {
                    @Override
                    public void onSuccess(List<ClassfyModel> result) {
                        getView().setClassfiy(result);
                    }
                });
    }


    @Override
    public void getCutomData() {
        List<DelegateAdapter.Adapter> mAdapter = new LinkedList<>();
        MainModel.getInstance(Utils.getContext()).getViewIndex()
                .compose(RxUtil.observableIO2Main(getView()))
                .compose(RxUtil.handNoResponseResult())
                .subscribe(new ProgressObserver<CutomHomeModel>(this, true, "加载中...") {
                    @Override
                    public void onSuccess(CutomHomeModel result) {
                        for (Data dataBean : result.getData()) {
                            switch (dataBean.getKey()) {
                                case Constant.components.search_1:
                                    mAdapter.add(initSearch1(dataBean.getCompts()));
                                    break;
                                case Constant.components.search_2:
                                    mAdapter.add(initSearch2(dataBean.getCompts()));
                                    break;
                                case Constant.components.banner_1:
                                    mAdapter.add(initBanner(dataBean.getComponents()));
                                    break;
                                case Constant.components.menu_1:
                                    mAdapter.add(initMenu1(dataBean.getComponents()));
                                    break;
                                case Constant.components.menu_2:
                                    mAdapter.add(initMenu2(dataBean.getComponents()));
                                    break;
                                case Constant.components.menu_3:
                                    mAdapter.add(initMenu3(dataBean.getComponents()));
                                    break;
                                case Constant.components.advert_1:
                                    mAdapter.add(initAdvert1(dataBean.getComponents()));
                                    break;
                                case Constant.components.advert_2:
                                    mAdapter.add(initAdvert2(dataBean.getComponents()));
                                    break;
                                case Constant.components.advert_3:
                                    mAdapter.add(initAdvert3(dataBean.getComponents()));
                                    break;
                                case Constant.components.advert_4:
                                    mAdapter.add(initAdvert4(dataBean.getComponents()));
                                    break;
                            }
                        }
                        mAdapter.add(initFastEntrace());
                        getView().setHomeDelegateAdapter(mAdapter);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        super.onError(e);
                        List<DelegateAdapter.Adapter> mAdapter = new LinkedList<>();
                        mAdapter.add(initFastEntrace());
                        getView().setHomeDelegateAdapter(mAdapter);
                    }
                });
    }

    @Override
    public void getUserInfo(String user_id, String user_channel_id) {
        PersonModule.getInstance(Utils.getContext()).getUserInfo(user_id, user_channel_id)
                .compose(RxUtil.observableIO2Main(getView()))
                .compose(RxUtil.hanResult())
                .subscribe(new ProgressObserver<UserModel>(this, true, "加载中...") {
                    @Override
                    public void onSuccess(UserModel result) {
                        getView().setUserModel(result);
                    }

                });
    }

}
