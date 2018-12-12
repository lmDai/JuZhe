package com.juzhe.www.mvp.presenter;

import android.annotation.SuppressLint;
import android.content.res.TypedArray;
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
import com.alibaba.android.vlayout.layout.ColumnLayoutHelper;
import com.alibaba.android.vlayout.layout.LinearLayoutHelper;
import com.alibaba.fastjson.JSON;
import com.blankj.utilcode.util.ToastUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.juzhe.www.Constant;
import com.juzhe.www.R;
import com.juzhe.www.bean.AdvertModel;
import com.juzhe.www.bean.ClassfyModel;
import com.juzhe.www.bean.CutomHomeModel;
import com.juzhe.www.bean.IconModel;
import com.juzhe.www.bean.UserModel;
import com.juzhe.www.common.https.ProgressObserver;
import com.juzhe.www.common.https.rxUtils.RxUtil;
import com.juzhe.www.common.utils.Utils;
import com.juzhe.www.mvp.contract.CustomeHomeFragmentContract;
import com.juzhe.www.mvp.contract.HomeFragmentContract;
import com.juzhe.www.mvp.model.MainModel;
import com.juzhe.www.mvp.model.PersonModule;
import com.juzhe.www.ui.activity.person.IntroductionActivity;
import com.juzhe.www.ui.adapter.BaseDelegateAdapter;
import com.juzhe.www.ui.adapter.ChildAdapter;
import com.juzhe.www.ui.adapter.FastEntranceAdapter;
import com.juzhe.www.ui.adapter.IconAdapter;
import com.juzhe.www.ui.adapter.MenuAdapter;
import com.juzhe.www.ui.adapter.MenuSubItemAdapter;
import com.juzhe.www.ui.adapter.OnePlusAdapter;
import com.juzhe.www.ui.widget.GlideImageLoader;
import com.juzhe.www.ui.widget.MyLinearLayoutManager;
import com.juzhe.www.utils.GlideUtil;
import com.juzhe.www.utils.IntentUtils;
import com.juzhe.www.utils.SpacesItemDecoration;
import com.youth.banner.Banner;
import com.youth.banner.listener.OnBannerListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * @package: com.juzhe.www.mvp.presenter
 * @user:xhkj
 * @date:2018/10/29
 * @description:
 **/
public class CustomHomeFragmentPresenter extends CustomeHomeFragmentContract.Presenter {

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

    public BaseDelegateAdapter initSearch(List<CutomHomeModel.DataBean.SearchBarBean> searchBar) {
        return new BaseDelegateAdapter(getView().getContext(), new LinearLayoutHelper(), R.layout.item_search, 1, Constant.viewType.typeTitle) {
            @Override
            public void onBindViewHolder(BaseViewHolder holder, int position) {
                super.onBindViewHolder(holder, position);
                ImageView imgLeft = holder.getView(R.id.img_left);
                ImageView imgRight = holder.getView(R.id.img_right);
                CutomHomeModel.DataBean.SearchBarBean searchBarBean = searchBar.get(0);
                if (searchBarBean.getLeft() != null) {
                    GlideUtil.into(getView().getContext(), searchBarBean.getLeft().getImage(), imgLeft);
                } else {
                    imgLeft.setVisibility(View.GONE);
                }
                if (searchBarBean.getRight() != null) {
                    GlideUtil.into(getView().getContext(), searchBarBean.getRight().getImage(), imgRight);
                } else {
                    imgRight.setVisibility(View.GONE);
                }
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ToastUtils.showShort("searchbar");
                    }
                });
            }
        };
    }

    public BaseDelegateAdapter initBanner(List<CutomHomeModel.DataBean.BannerBean> urls) {
        return new BaseDelegateAdapter(getView().getContext(), new LinearLayoutHelper(), R.layout.layout_home_banner, 1, Constant.viewType.typeTitle) {
            @Override
            public void onBindViewHolder(BaseViewHolder holder, int position) {
                super.onBindViewHolder(holder, position);
                Banner banner = holder.getView(R.id.banner);

                List<String> images = new ArrayList();
                if (urls != null && urls.size() > 0) {
                    for (CutomHomeModel.DataBean.BannerBean bannerBean : urls) {
                        images.add(bannerBean.getImage());
                    }
                }
                banner.setOnBannerListener(new OnBannerListener() {
                    @Override
                    public void OnBannerClick(int position) {
                        ToastUtils.showShort(position + urls.get(position).getImage());
                    }
                });
                banner.setImages(images)
                        .setImageLoader(new GlideImageLoader())
                        .start();
            }
        };
    }

    public BaseDelegateAdapter initFastEntrace() {
        return new BaseDelegateAdapter(getView().getContext(), new LinearLayoutHelper(), R.layout.layout_home_entrace, 1, Constant.viewType.typeList) {

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

    public BaseDelegateAdapter initMenu(List<CutomHomeModel.DataBean.MenuBean> menu) {
        return new BaseDelegateAdapter(getView().getContext(), new LinearLayoutHelper(), R.layout.layout_menu, 1, Constant.viewType.typeList) {
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
                            LayoutInflater.from(getView().getContext()).inflate(R.layout.layout_menu, parent, false));

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
                if (holder.itemView instanceof LinearLayout) {
                    RecyclerView recyclerIcon = holder.getView(R.id.recycler_icon);
                    RecyclerView recyclerChild = holder.getView(R.id.recycler_child);
                    GridLayoutManager gridLayoutManager = new GridLayoutManager(getView().getContext(), 5);
                    gridLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                    recyclerIcon.setLayoutManager(gridLayoutManager);
                    recyclerIcon.addItemDecoration(new SpacesItemDecoration(0));
                    MenuAdapter iconAdapter = new MenuAdapter(R.layout.item_menu);
                    recyclerIcon.setAdapter(iconAdapter);
                    iconAdapter.setNewData(menu);
                    iconAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                        @Override
                        public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                            ToastUtils.showShort(iconAdapter.getData().get(position).getTitle());
                        }
                    });
                    GridLayoutManager childManager = new GridLayoutManager(getView().getContext(), 5);
                    childManager.setOrientation(LinearLayoutManager.VERTICAL);
                    recyclerChild.setLayoutManager(childManager);
                    recyclerChild.addItemDecoration(new SpacesItemDecoration(0));
                    recyclerChild.setNestedScrollingEnabled(false);
                    MenuSubItemAdapter childAdapter = new MenuSubItemAdapter(R.layout.item_menu);
                    recyclerChild.setAdapter(childAdapter);
                    childAdapter.setNewData(menu.get(0).getSubItem());
                }
            }

        };
    }

    public DelegateAdapter.Adapter initAdvert(List<CutomHomeModel.DataBean.AdvertBean> advert) {
        int type = 1;
        if (advert.size() == 1) {
            type = 1;
        } else if (advert.size() == 2) {
            type = 2;
        } else if (advert.size() == 3) {
            type = 3;
        }
        return new OnePlusAdapter(getView().getContext(), new LinearLayoutHelper(), 1, type, advert);
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

        String data = "{\"result\":true,\"data\":[{\"searchBar\":[{\"left\":{\"image\":\"http://guoxue.gz.bcebos.com/ccw/active/10月28日/4.png\",\"mode\":{\"type\":\"haodanku\",\"id\":11}},\"center\":{\"backgroundImage\":\"\",\"foregroundImage\":\"\",\"mode\":{\"type\":\"haodanku\",\"id\":22}},\"right\":{\"image\":\"http://guoxue.gz.bcebos.com/ccw/active/10月28日/7.png\",\"mode\":{\"type\":\"haodanku\",\"id\":33}}}]},{\"banner\":[{\"image\":\"http://guoxue.gz.bcebos.com/ccw/active/10月28日/4.png\",\"mode\":{\"type\":\"haodanku\",\"id\":99}},{\"image\":\"http://guoxue.gz.bcebos.com/ccw/active/10月28日/4.png\",\"mode\":{\"type\":\"haodanku\",\"id\":98}},{\"image\":\"http://guoxue.gz.bcebos.com/ccw/active/10月28日/7.png\",\"mode\":{\"type\":\"haodanku\",\"id\":97}}]},{\"searchBar\":[{\"center\":{\"backgroundImage\":\"\",\"foregroundImage\":\"\",\"mode\":{\"type\":\"haodanku\",\"id\":22}},\"right\":{\"image\":\"http://guoxue.gz.bcebos.com/ccw/active/10月28日/7.png\",\"mode\":{\"type\":\"haodanku\",\"id\":33}}}]},{\"menu\":[{\"image\":\"http://guoxue.cdn.bcebos.com/resources/image/default_avatar.jpg\",\"title\":\"女装1\",\"subItem\":[{\"image\":\"http://guoxue.cdn.bcebos.com/resources/image/default_avatar.jpg\",\"title\":\"sub女装1\",\"mode\":{\"type\":\"haodanku\",\"id\":97}},{\"image\":\"http://guoxue.cdn.bcebos.com/resources/image/default_avatar.jpg\",\"title\":\"sub女装2\",\"mode\":{\"type\":\"haodanku\",\"id\":97}},{\"image\":\"http://guoxue.cdn.bcebos.com/resources/image/default_avatar.jpg\",\"title\":\"sub女装3\",\"mode\":{\"type\":\"haodanku\",\"id\":97}},{\"image\":\"http://guoxue.cdn.bcebos.com/resources/image/default_avatar.jpg\",\"title\":\"sub女装4\",\"mode\":{\"type\":\"haodanku\",\"id\":97}},{\"image\":\"http://guoxue.cdn.bcebos.com/resources/image/default_avatar.jpg\",\"title\":\"sub女装5\",\"mode\":{\"type\":\"haodanku\",\"id\":97}}]},{\"image\":\"http://guoxue.cdn.bcebos.com/resources/image/default_avatar.jpg\",\"title\":\"女装1\",\"subItem\":[{\"image\":\"http://guoxue.cdn.bcebos.com/resources/image/default_avatar.jpg\",\"title\":\"sub女装1\",\"mode\":{\"type\":\"haodanku\",\"id\":97}},{\"image\":\"http://guoxue.cdn.bcebos.com/resources/image/default_avatar.jpg\",\"title\":\"sub女装2\",\"mode\":{\"type\":\"haodanku\",\"id\":97}},{\"image\":\"http://guoxue.cdn.bcebos.com/resources/image/default_avatar.jpg\",\"title\":\"sub女装3\",\"mode\":{\"type\":\"haodanku\",\"id\":97}},{\"image\":\"http://guoxue.cdn.bcebos.com/resources/image/default_avatar.jpg\",\"title\":\"sub女装4\",\"mode\":{\"type\":\"haodanku\",\"id\":97}},{\"image\":\"http://guoxue.cdn.bcebos.com/resources/image/default_avatar.jpg\",\"title\":\"sub女装5\",\"mode\":{\"type\":\"haodanku\",\"id\":97}}]}]},{\"advert\":[{\"image\":\"http://guoxue.cdn.bcebos.com/resources/image/default_avatar.jpg\",\"title\":\"女装\",\"mode\":{\"type\":\"haodanku\",\"id\":97}},{\"image\":\"http://guoxue.cdn.bcebos.com/resources/image/default_avatar.jpg\",\"title\":\"女装\",\"mode\":{\"type\":\"haodanku\",\"id\":97}},{\"image\":\"http://guoxue.cdn.bcebos.com/resources/image/default_avatar.jpg\",\"title\":\"女装\",\"mode\":{\"type\":\"haodanku\",\"id\":97}}]},{\"banner\":[{\"image\":\"http://guoxue.gz.bcebos.com/ccw/active/10月28日/4.png\",\"mode\":{\"type\":\"haodanku\",\"id\":99}},{\"image\":\"http://guoxue.gz.bcebos.com/ccw/active/10月28日/4.png\",\"mode\":{\"type\":\"haodanku\",\"id\":98}},{\"image\":\"http://guoxue.gz.bcebos.com/ccw/active/10月28日/7.png\",\"mode\":{\"type\":\"haodanku\",\"id\":97}}]}]}";
        CutomHomeModel homeModel = JSON.parseObject(data, CutomHomeModel.class);
        List<DelegateAdapter.Adapter> mAdapter = new LinkedList<>();
        Log.i("single", homeModel.getData().size() + "");
        for (CutomHomeModel.DataBean dataBean : homeModel.getData()) {
            if (dataBean.getSearchBar() != null) {
                mAdapter.add(initSearch(dataBean.getSearchBar()));
            } else if (dataBean.getBanner() != null) {
                mAdapter.add(initBanner(dataBean.getBanner()));
            } else if (dataBean.getMenu() != null) {
                mAdapter.add(initMenu(dataBean.getMenu()));
            } else if (dataBean.getAdvert() != null) {
                mAdapter.add(initAdvert(dataBean.getAdvert()));
            }
        }
        getView().setHomeDelegateAdapter(mAdapter);
    }

}
