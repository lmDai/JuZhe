package com.juzhe.www.mvp.presenter;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.alibaba.android.vlayout.DelegateAdapter;
import com.alibaba.android.vlayout.VirtualLayoutManager;
import com.alibaba.android.vlayout.layout.LinearLayoutHelper;
import com.blankj.utilcode.util.ToastUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.juzhe.www.Constant;
import com.juzhe.www.R;
import com.juzhe.www.bean.ClassfyModel;
import com.juzhe.www.bean.ComponentsBean;
import com.juzhe.www.bean.CutomHomeModel;
import com.juzhe.www.bean.Data;
import com.juzhe.www.bean.Mode;
import com.juzhe.www.bean.SubItemBean;
import com.juzhe.www.bean.UserModel;
import com.juzhe.www.common.https.ProgressObserver;
import com.juzhe.www.common.https.rxUtils.RxUtil;
import com.juzhe.www.common.utils.Utils;
import com.juzhe.www.mvp.contract.CustomeHomeFragmentContract;
import com.juzhe.www.mvp.model.MainModel;
import com.juzhe.www.mvp.model.PersonModule;
import com.juzhe.www.ui.activity.XWebViewActivity;
import com.juzhe.www.ui.activity.product.ProductListActivity;
import com.juzhe.www.ui.activity.product.SearchActivity;
import com.juzhe.www.ui.adapter.Advert5Adapter;
import com.juzhe.www.ui.adapter.BaseDelegateAdapter;
import com.juzhe.www.ui.adapter.FastEntranceAdapter;
import com.juzhe.www.ui.adapter.MenuAdapter;
import com.juzhe.www.ui.adapter.MenuSubItemAdapter;
import com.juzhe.www.ui.widget.GlideImageLoader;
import com.juzhe.www.ui.widget.MyLinearLayoutManager;
import com.juzhe.www.utils.Advert5ItemDecoration;
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
    public BaseDelegateAdapter initSearch1(List<ComponentsBean> searchBar) {
        return new BaseDelegateAdapter(getView().getContext(), new LinearLayoutHelper(), R.layout.item_search, 1, Constant.viewType.search_1) {
            @Override
            public void onBindViewHolder(BaseViewHolder holder, int position) {
                super.onBindViewHolder(holder, position);
                ImageView imgLeft = holder.getView(R.id.img_left);
                ImageView imgRight = holder.getView(R.id.img_right);
                LinearLayout llSearch = holder.getView(R.id.ll_search);
                LinearLayout llMainSearch = holder.getView(R.id.ll_main_search);
                ComponentsBean componentsLeft = searchBar.get(0);//左
                ComponentsBean componentsCenter = searchBar.get(1);//中
                ComponentsBean componentsRight = searchBar.get(2);//右
                if (componentsLeft != null) {
                    GlideUtil.into(getView().getContext(), componentsLeft.getImage(), imgLeft);
                    imgLeft.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Bundle bundle = new Bundle();
                            Mode mode = componentsLeft.getMode();
                            if (mode != null) {
                                if (TextUtils.equals(mode.getType(), Constant.mode.CATE_GORY)) {
                                    bundle.putString("name", componentsLeft.getTitle());
                                    bundle.putString("key", mode.getValue());
                                    IntentUtils.get().goActivity(getView().getContext(), ProductListActivity.class, bundle);//商品列表
                                } else if (TextUtils.equals(mode.getType(), Constant.mode.H5)) {
                                    bundle.putString("link", mode.getValue());
                                    IntentUtils.get().goActivity(getView().getContext(), XWebViewActivity.class, bundle);
                                }
                            }
                        }
                    });
                }
                if (componentsRight != null) {
                    GlideUtil.into(getView().getContext(), componentsRight.getImage(), imgRight);
                    imgRight.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Bundle bundle = new Bundle();
                            Mode mode = componentsRight.getMode();
                            if (mode != null) {
                                if (TextUtils.equals(mode.getType(), Constant.mode.CATE_GORY)) {
                                    bundle.putString("name", componentsRight.getTitle());
                                    bundle.putString("key", mode.getValue());
                                    IntentUtils.get().goActivity(getView().getContext(), ProductListActivity.class, bundle);//商品列表
                                } else if (TextUtils.equals(mode.getType(), Constant.mode.H5)) {
                                    bundle.putString("link", mode.getValue());
                                    IntentUtils.get().goActivity(getView().getContext(), XWebViewActivity.class, bundle);
                                }
                            }
                        }
                    });
                }
                if (componentsCenter != null) {
                    GlideUtil.showImageViewLinearLayout(getView().getContext(),
                            componentsCenter.getImage(), llMainSearch);
                    llSearch.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Bundle bundle = new Bundle();
                            Mode mode = componentsCenter.getMode();
                            if (mode != null) {
                                if (TextUtils.equals(mode.getType(), Constant.mode.CATE_GORY)) {
                                    bundle.putString("name", componentsCenter.getTitle());
                                    bundle.putString("key", mode.getValue());
                                    IntentUtils.get().goActivity(getView().getContext(), ProductListActivity.class, bundle);//商品列表
                                } else if (TextUtils.equals(mode.getType(), Constant.mode.H5)) {
                                    bundle.putString("link", mode.getValue());
                                    IntentUtils.get().goActivity(getView().getContext(), XWebViewActivity.class, bundle);
                                }
                            }
                        }
                    });
                }
            }
        };
    }

    //搜索样式二
    public BaseDelegateAdapter initSearch2(List<ComponentsBean> searchBar) {
        return new BaseDelegateAdapter(getView().getContext(), new LinearLayoutHelper(), R.layout.item_search_2, 1, Constant.viewType.search_2) {
            @Override
            public void onBindViewHolder(BaseViewHolder holder, int position) {
                super.onBindViewHolder(holder, position);
                ImageView imgRight = holder.getView(R.id.img_right);
                LinearLayout llSearch = holder.getView(R.id.ll_search);
                LinearLayout llMainSearch = holder.getView(R.id.ll_main_search);
                ComponentsBean componentsRight = searchBar.get(1);
                ComponentsBean componentsCenter = searchBar.get(0);
                if (componentsRight != null) {
                    GlideUtil.into(getView().getContext(), componentsRight.getImage(), imgRight);
                    imgRight.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Bundle bundle = new Bundle();
                            Mode mode = componentsRight.getMode();
                            if (mode != null) {
                                if (TextUtils.equals(mode.getType(), Constant.mode.CATE_GORY)) {
                                    bundle.putString("name", componentsRight.getTitle());
                                    bundle.putString("key", mode.getValue());
                                    IntentUtils.get().goActivity(getView().getContext(), ProductListActivity.class, bundle);//商品列表
                                } else if (TextUtils.equals(mode.getType(), Constant.mode.H5)) {
                                    bundle.putString("link", mode.getValue());
                                    IntentUtils.get().goActivity(getView().getContext(), XWebViewActivity.class, bundle);
                                }
                            }
                        }
                    });
                }
                if (componentsCenter != null) {
                    GlideUtil.showImageViewLinearLayout(getView().getContext(),
                            componentsCenter.getImage(), llMainSearch);
                    llSearch.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Bundle bundle = new Bundle();
                            Mode mode = componentsCenter.getMode();
                            if (mode != null) {
                                if (TextUtils.equals(mode.getType(), Constant.mode.CATE_GORY)) {
                                    bundle.putString("name", componentsCenter.getTitle());
                                    bundle.putString("key", mode.getValue());
                                    IntentUtils.get().goActivity(getView().getContext(), ProductListActivity.class, bundle);//商品列表
                                } else if (TextUtils.equals(mode.getType(), Constant.mode.H5)) {
                                    bundle.putString("link", mode.getValue());
                                    IntentUtils.get().goActivity(getView().getContext(), XWebViewActivity.class, bundle);
                                }
                            }
                        }
                    });
                }
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
                        ComponentsBean componentsBean = urls.get(position);
                        if (componentsBean != null) {
                            Mode mode = componentsBean.getMode();
                            if (mode != null) {
                                Bundle bundle = new Bundle();
                                if (TextUtils.equals(mode.getType(), Constant.mode.CATE_GORY)) {
                                    bundle.putString("name", componentsBean.getTitle());
                                    bundle.putString("key", mode.getValue());
                                    IntentUtils.get().goActivity(getView().getContext(), ProductListActivity.class, bundle);//商品列表
                                } else if (TextUtils.equals(mode.getType(), Constant.mode.H5)) {
                                    bundle.putString("link", mode.getValue());
                                    IntentUtils.get().goActivity(getView().getContext(), XWebViewActivity.class, bundle);
                                }
                            }
                        }
                    }
                });
                banner.setImages(images)
                        .setImageLoader(new GlideImageLoader())
                        .start();
            }
        };
    }

    public BaseDelegateAdapter initFastEntrceTitle() {
        return new BaseDelegateAdapter(getView().getContext(), new LinearLayoutHelper(), R.layout.base_view_title, 1, Constant.viewType.fast_entrance_title) {
            @Override
            public void onBindViewHolder(BaseViewHolder holder, int position) {
                super.onBindViewHolder(holder, position);
                holder.setText(R.id.tv_title, "快速入口");
            }
        };
    }

    public BaseDelegateAdapter initFastEntrace(List<ComponentsBean> advert5) {
        return new BaseDelegateAdapter(getView().getContext(), new LinearLayoutHelper(), R.layout.layout_home_entrace, 1, Constant.viewType.advert_5) {
            @Override
            public void onBindViewHolder(BaseViewHolder holder, int position) {
                if (holder.itemView instanceof RecyclerView) {
                    RecyclerView recyclerView = (RecyclerView) holder.itemView;
                    if (recyclerView.getLayoutManager() == null) {
                        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getView().getContext());
                        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
                        recyclerView.setLayoutManager(linearLayoutManager);
                        recyclerView.addItemDecoration(new Advert5ItemDecoration(20));
                    }
                    Advert5Adapter advert5Adapter = new Advert5Adapter(R.layout.item_advert5);
                    recyclerView.setAdapter(advert5Adapter);
                    advert5Adapter.setNewData(advert5);
                    advert5Adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                        @Override
                        public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                            ComponentsBean componentsBean = advert5Adapter.getData().get(position);
                            if (componentsBean != null) {
                                Mode mode = componentsBean.getMode();
                                if (mode != null) {
                                    Bundle bundle = new Bundle();
                                    if (TextUtils.equals(mode.getType(), Constant.mode.CATE_GORY)) {
                                        bundle.putString("name", componentsBean.getTitle());
                                        bundle.putString("key", mode.getValue());
                                        IntentUtils.get().goActivity(getView().getContext(), ProductListActivity.class, bundle);//商品列表
                                    } else if (TextUtils.equals(mode.getType(), Constant.mode.H5)) {
                                        bundle.putString("link", mode.getValue());
                                        IntentUtils.get().goActivity(getView().getContext(), XWebViewActivity.class, bundle);
                                    }
                                }
                            }
                        }
                    });
                }
            }

        };
    }

    //菜单样式一
    public BaseDelegateAdapter initMenu1(List<ComponentsBean> menu) {
        return new BaseDelegateAdapter(getView().getContext(), new LinearLayoutHelper(), R.layout.layout_menu1, 1, Constant.viewType.menu_1) {
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
                            ComponentsBean menuItem = iconAdapter.getData().get(position);
                            if (menuItem != null) {
                                Bundle bundle = new Bundle();
                                Mode mode = menuItem.getMode();
                                if (mode != null) {
                                    if (TextUtils.equals(mode.getType(), Constant.mode.CATE_GORY)) {
                                        bundle.putString("name", menuItem.getTitle());
                                        bundle.putString("key", mode.getValue());
                                        IntentUtils.get().goActivity(getView().getContext(), ProductListActivity.class, bundle);//商品列表
                                    } else if (TextUtils.equals(mode.getType(), Constant.mode.H5)) {
                                        bundle.putString("link", mode.getValue());
                                        IntentUtils.get().goActivity(getView().getContext(), XWebViewActivity.class, bundle);
                                    }
                                }
                            }

                        }
                    });
                }
            }
        };
    }

    //菜单样式一
    public BaseDelegateAdapter initMenu2(List<ComponentsBean> menu) {
        return new BaseDelegateAdapter(getView().getContext(), new LinearLayoutHelper(), R.layout.layout_menu1, 1, Constant.viewType.menu_2) {
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
                            ComponentsBean menuItem = iconAdapter.getData().get(position);
                            if (menuItem != null) {
                                Bundle bundle = new Bundle();
                                Mode mode = menuItem.getMode();
                                if (mode != null) {
                                    if (TextUtils.equals(mode.getType(), Constant.mode.CATE_GORY)) {
                                        bundle.putString("name", menuItem.getTitle());
                                        bundle.putString("key", mode.getValue());
                                        IntentUtils.get().goActivity(getView().getContext(), ProductListActivity.class, bundle);//商品列表
                                    } else if (TextUtils.equals(mode.getType(), Constant.mode.H5)) {
                                        bundle.putString("link", mode.getValue());
                                        IntentUtils.get().goActivity(getView().getContext(), XWebViewActivity.class, bundle);
                                    }
                                }
                            }
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
                            ComponentsBean menuItem = iconAdapter.getData().get(position);
                            if (menuItem != null) {
                                List<SubItemBean> subItemList = menuItem.getSubItem();
                                if (subItemList != null && subItemList.size() > 0) {
                                    setChildRecyclerview(position, recyclerChild, childAdapter, iconAdapter.getData().get(position).getSubItem());
                                } else {
                                    setChildRecyclerview(position, recyclerChild, childAdapter, iconAdapter.getData().get(position).getSubItem());
                                    Mode mode = menuItem.getMode();
                                    if (mode != null) {
                                        Bundle bundle = new Bundle();
                                        if (TextUtils.equals(mode.getType(), Constant.mode.CATE_GORY)) {
                                            bundle.putString("name", menuItem.getTitle());
                                            bundle.putString("key", mode.getValue());
                                            IntentUtils.get().goActivity(getView().getContext(), ProductListActivity.class, bundle);//商品列表
                                        } else if (TextUtils.equals(mode.getType(), Constant.mode.H5)) {
                                            bundle.putString("link", mode.getValue());
                                            IntentUtils.get().goActivity(getView().getContext(), XWebViewActivity.class, bundle);
                                        }
                                    }
                                }
                            }
                        }
                    });
                    childAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                        @Override
                        public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                            SubItemBean subItemBean = childAdapter.getData().get(position);
                            Mode mode = subItemBean.getMode();
                            if (mode != null) {
                                Bundle bundle = new Bundle();
                                if (TextUtils.equals(mode.getType(), Constant.mode.CATE_GORY)) {
                                    bundle.putString("name", subItemBean.getTitle());
                                    bundle.putString("key", mode.getValue());
                                    IntentUtils.get().goActivity(getView().getContext(), ProductListActivity.class, bundle);//商品列表
                                } else if (TextUtils.equals(mode.getType(), Constant.mode.H5)) {
                                    bundle.putString("link", mode.getValue());
                                    IntentUtils.get().goActivity(getView().getContext(), XWebViewActivity.class, bundle);
                                }
                            }
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
                if (advert != null && advert.size() > 0) {
                    ComponentsBean advertBean = advert.get(0);
                    if (advertBean != null) {
                        GlideUtil.loadIntoUseFitWidth(getView().getContext(), advertBean.getImage(), imgaview);
                        imgaview.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Mode mode = advertBean.getMode();
                                if (mode != null) {
                                    Bundle bundle = new Bundle();
                                    if (TextUtils.equals(mode.getType(), Constant.mode.CATE_GORY)) {
                                        bundle.putString("name", advertBean.getTitle());
                                        bundle.putString("key", mode.getValue());
                                        IntentUtils.get().goActivity(getView().getContext(), ProductListActivity.class, bundle);//商品列表
                                    } else if (TextUtils.equals(mode.getType(), Constant.mode.H5)) {
                                        bundle.putString("link", mode.getValue());
                                        IntentUtils.get().goActivity(getView().getContext(), XWebViewActivity.class, bundle);
                                    }
                                }
                            }
                        });
                    }
                }
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
                if (advert != null && advert.size() > 1) {
                    ComponentsBean advertBean = advert.get(0);
                    ComponentsBean advert2 = advert.get(1);
                    if (advertBean != null) {
                        GlideUtil.loadIntoUseFitWidth(getView().getContext(), advertBean.getImage(), imgaview);
                        GlideUtil.loadIntoUseFitWidth(getView().getContext(), advert2.getImage(), imgTwo);
                        imgaview.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Mode mode = advertBean.getMode();
                                if (mode != null) {
                                    Bundle bundle = new Bundle();
                                    if (TextUtils.equals(mode.getType(), Constant.mode.CATE_GORY)) {
                                        bundle.putString("name", advertBean.getTitle());
                                        bundle.putString("key", mode.getValue());
                                        IntentUtils.get().goActivity(getView().getContext(), ProductListActivity.class, bundle);//商品列表
                                    } else if (TextUtils.equals(mode.getType(), Constant.mode.H5)) {
                                        bundle.putString("link", mode.getValue());
                                        IntentUtils.get().goActivity(getView().getContext(), XWebViewActivity.class, bundle);
                                    }
                                }
                            }
                        });
                        imgTwo.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Mode mode = advert2.getMode();
                                if (mode != null) {
                                    Bundle bundle = new Bundle();
                                    if (TextUtils.equals(mode.getType(), Constant.mode.CATE_GORY)) {
                                        bundle.putString("name", advert2.getTitle());
                                        bundle.putString("key", mode.getValue());
                                        IntentUtils.get().goActivity(getView().getContext(), ProductListActivity.class, bundle);//商品列表
                                    } else if (TextUtils.equals(mode.getType(), Constant.mode.H5)) {
                                        bundle.putString("link", mode.getValue());
                                        IntentUtils.get().goActivity(getView().getContext(), XWebViewActivity.class, bundle);
                                    }
                                }
                            }
                        });
                    }
                }
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
                if (advert != null && advert.size() > 2) {
                    ComponentsBean advertBean = advert.get(0);
                    ComponentsBean advert2 = advert.get(1);
                    ComponentsBean advert3 = advert.get(2);
                    if (advertBean != null) {
                        GlideUtil.loadIntoUseFitWidth(getView().getContext(), advertBean.getImage(), imgaview);
                        GlideUtil.loadIntoUseFitWidth(getView().getContext(), advert2.getImage(), imgTwo);
                        GlideUtil.loadIntoUseFitWidth(getView().getContext(), advert3.getImage(), imgThree);
                        imgaview.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Mode mode = advertBean.getMode();
                                if (mode != null) {
                                    Bundle bundle = new Bundle();
                                    if (TextUtils.equals(mode.getType(), Constant.mode.CATE_GORY)) {
                                        bundle.putString("name", advertBean.getTitle());
                                        bundle.putString("key", mode.getValue());
                                        IntentUtils.get().goActivity(getView().getContext(), ProductListActivity.class, bundle);//商品列表
                                    } else if (TextUtils.equals(mode.getType(), Constant.mode.H5)) {
                                        bundle.putString("link", mode.getValue());
                                        IntentUtils.get().goActivity(getView().getContext(), XWebViewActivity.class, bundle);
                                    }
                                }
                            }
                        });
                        imgTwo.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Mode mode = advert2.getMode();
                                if (mode != null) {
                                    Bundle bundle = new Bundle();
                                    if (TextUtils.equals(mode.getType(), Constant.mode.CATE_GORY)) {
                                        bundle.putString("name", advert2.getTitle());
                                        bundle.putString("key", mode.getValue());
                                        IntentUtils.get().goActivity(getView().getContext(), ProductListActivity.class, bundle);//商品列表
                                    } else if (TextUtils.equals(mode.getType(), Constant.mode.H5)) {
                                        bundle.putString("link", mode.getValue());
                                        IntentUtils.get().goActivity(getView().getContext(), XWebViewActivity.class, bundle);
                                    }
                                }
                            }
                        });
                        imgThree.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Mode mode = advert3.getMode();
                                if (mode != null) {
                                    Bundle bundle = new Bundle();
                                    if (TextUtils.equals(mode.getType(), Constant.mode.CATE_GORY)) {
                                        bundle.putString("name", advert3.getTitle());
                                        bundle.putString("key", mode.getValue());
                                        IntentUtils.get().goActivity(getView().getContext(), ProductListActivity.class, bundle);//商品列表
                                    } else if (TextUtils.equals(mode.getType(), Constant.mode.H5)) {
                                        bundle.putString("link", mode.getValue());
                                        IntentUtils.get().goActivity(getView().getContext(), XWebViewActivity.class, bundle);
                                    }
                                }
                            }
                        });
                    }
                }
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
                if (advert != null && advert.size() > 2) {
                    ComponentsBean advertBean = advert.get(0);
                    ComponentsBean advert2 = advert.get(1);
                    ComponentsBean advert3 = advert.get(2);
                    if (advertBean != null) {
                        GlideUtil.loadIntoUseFitWidth(getView().getContext(), advertBean.getImage(), imgaview);
                        GlideUtil.loadIntoUseFitWidth(getView().getContext(), advert2.getImage(), imgTwo);
                        GlideUtil.loadIntoUseFitWidth(getView().getContext(), advert3.getImage(), imgThree);
                        imgaview.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Mode mode = advertBean.getMode();
                                if (mode != null) {
                                    Bundle bundle = new Bundle();
                                    if (TextUtils.equals(mode.getType(), Constant.mode.CATE_GORY)) {
                                        bundle.putString("name", advertBean.getTitle());
                                        bundle.putString("key", mode.getValue());
                                        IntentUtils.get().goActivity(getView().getContext(), ProductListActivity.class, bundle);//商品列表
                                    } else if (TextUtils.equals(mode.getType(), Constant.mode.H5)) {
                                        bundle.putString("link", mode.getValue());
                                        IntentUtils.get().goActivity(getView().getContext(), XWebViewActivity.class, bundle);
                                    }
                                }
                            }
                        });
                        imgTwo.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Mode mode = advert2.getMode();
                                if (mode != null) {
                                    Bundle bundle = new Bundle();
                                    if (TextUtils.equals(mode.getType(), Constant.mode.CATE_GORY)) {
                                        bundle.putString("name", advert2.getTitle());
                                        bundle.putString("key", mode.getValue());
                                        IntentUtils.get().goActivity(getView().getContext(), ProductListActivity.class, bundle);//商品列表
                                    } else if (TextUtils.equals(mode.getType(), Constant.mode.H5)) {
                                        bundle.putString("link", mode.getValue());
                                        IntentUtils.get().goActivity(getView().getContext(), XWebViewActivity.class, bundle);
                                    }
                                }
                            }
                        });
                        imgThree.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Mode mode = advert3.getMode();
                                if (mode != null) {
                                    Bundle bundle = new Bundle();
                                    if (TextUtils.equals(mode.getType(), Constant.mode.CATE_GORY)) {
                                        bundle.putString("name", advert3.getTitle());
                                        bundle.putString("key", mode.getValue());
                                        IntentUtils.get().goActivity(getView().getContext(), ProductListActivity.class, bundle);//商品列表
                                    } else if (TextUtils.equals(mode.getType(), Constant.mode.H5)) {
                                        bundle.putString("link", mode.getValue());
                                        IntentUtils.get().goActivity(getView().getContext(), XWebViewActivity.class, bundle);
                                    }
                                }
                            }
                        });
                    }
                }
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

    /**
     * 获取自定义首页
     */
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
                                    mAdapter.add(initSearch1(dataBean.getComponents()));
                                    break;
                                case Constant.components.search_2:
                                    mAdapter.add(initSearch2(dataBean.getComponents()));
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
                                case Constant.components.advert_5:
                                    mAdapter.add(initFastEntrace(dataBean.getComponents()));
                            }
                        }
//                        mAdapter.add(initFastEntrceTitle());
//                        mAdapter.add(initFastEntrace());
                        getView().setHomeDelegateAdapter(mAdapter);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        super.onError(e);
//                        List<DelegateAdapter.Adapter> mAdapter = new LinkedList<>();
//                        mAdapter.add(initFastEntrceTitle());
//                        mAdapter.add(initFastEntrace());
//                        getView().setHomeDelegateAdapter(mAdapter);
                    }
                });
    }

    /**
     * 获取用户数据
     *
     * @param user_id
     * @param user_channel_id
     */
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
