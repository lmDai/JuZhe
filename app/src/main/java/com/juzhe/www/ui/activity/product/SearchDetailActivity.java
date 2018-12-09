package com.juzhe.www.ui.activity.product;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.juzhe.www.R;
import com.juzhe.www.base.BaseMvpActivity;
import com.juzhe.www.bean.PddListModel;
import com.juzhe.www.bean.PddPromotionModel;
import com.juzhe.www.bean.ProductModel;
import com.juzhe.www.bean.SelectModel;
import com.juzhe.www.bean.UserModel;
import com.juzhe.www.common.mvp_senior.annotaions.CreatePresenterAnnotation;
import com.juzhe.www.common.widget.dropmenu.DropDownMenu;
import com.juzhe.www.mvp.contract.SearchDetailsContract;
import com.juzhe.www.mvp.presenter.SearchDetailsPresenter;
import com.juzhe.www.ui.adapter.DropMenuAdapter;
import com.juzhe.www.ui.adapter.ProductAdapter;
import com.juzhe.www.ui.adapter.ProductListPddAdapter;
import com.juzhe.www.ui.widget.ListPopu;
import com.juzhe.www.utils.IntentUtils;
import com.juzhe.www.utils.RecyclerViewUtils;
import com.juzhe.www.utils.UserUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

@CreatePresenterAnnotation(SearchDetailsPresenter.class)
public class SearchDetailActivity extends BaseMvpActivity<SearchDetailsContract.View, SearchDetailsPresenter> implements SearchDetailsContract.View {

    @BindView(R.id.img_back)
    ImageView imgBack;
    @BindView(R.id.edit_search)
    EditText editSearch;
    @BindView(R.id.txt_search)
    TextView txtSearch;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.dropDownMenu)
    DropDownMenu dropDownMenu;
    private String keyWord;
    private String sort = "";
    private ProductAdapter productAdapter;
    private ListPopu listPopu;
    private UserModel userModel;
    private String headers[] = {"最新", "销量", "佣金", "筛选"};
    private String header[] = {"综合", "销量", "价格", "筛选"};
    private int constellationPosition = 0;
    private DropMenuAdapter saleAdapter;//销量
    private DropMenuAdapter commisionAdapter;//佣金
    private DropMenuAdapter dropMenuAdapter;//筛选
    private List<View> popupViews = new ArrayList<>();
    private RecyclerView recyclerProduct;
    private String type;


    private ProductListPddAdapter pddAdapter;

    @Override
    protected int getLayout() {
        return R.layout.activity_search_detail;
    }

    @Override
    protected void initImmersionBar() {
        super.initImmersionBar();
        mImmersionBar.titleBar(R.id.toolbar)
                .statusBarColor(R.color.colorWhite)
                .statusBarDarkFont(true, 0.2f)
                .init();
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        userModel = UserUtils.getUser(mContext);
        Bundle bundle = getIntent().getBundleExtra("bundle");

        if (bundle != null) {
            keyWord = bundle.getString("keyword");
            if (bundle.containsKey("type")) {
                type = bundle.getString("type");
            }
        }
        editSearch.setText(keyWord);
        editSearch.setEnabled(false);
        txtSearch.setVisibility(View.GONE);

        if (TextUtils.equals(type, "1")) {
            initDropMenu();
            productAdapter = new ProductAdapter(R.layout.item_product, userModel.getLevel());
            RecyclerViewUtils.initLinerLayoutRecyclerView(recyclerProduct, mContext);
            recyclerProduct.setAdapter(productAdapter);
        } else if (TextUtils.equals(type, "3")) {

            initFileter();
            pddAdapter = new ProductListPddAdapter(R.layout.item_product);
            RecyclerViewUtils.initLinerLayoutRecyclerView(recyclerProduct, mContext);
            recyclerProduct.setAdapter(pddAdapter);
        }
        initData(true);


    }

    private void initDropMenu() {
        //设置筛选条件 销量
        List<SelectModel> classfiy = new ArrayList<>();
        classfiy.add(new SelectModel("7", "月销量(从低到高)", false));
        classfiy.add(new SelectModel("4", "月销量(从高到低)", false));
        classfiy.add(new SelectModel("10", "全天销量(从低到高)", false));
        classfiy.add(new SelectModel("9", "全天销量(从高到低)", false));
        classfiy.add(new SelectModel("12", "近2小时销量(从低到高)", false));
        classfiy.add(new SelectModel("11", "近2小时销量(从高到低)", false));
        final View saleView = getLayoutInflater().inflate(R.layout.layout_recyclerview, null);
        RecyclerView saleRecycler = ButterKnife.findById(saleView, R.id.recycler);
        saleAdapter = new DropMenuAdapter(R.layout.item_filter);
        RecyclerViewUtils.initLinerLayoutRecyclerView(saleRecycler, mContext);
        saleRecycler.setAdapter(saleAdapter);
        saleAdapter.setNewData(classfiy);
        //佣金
        List<SelectModel> classfiy1 = new ArrayList<>();
        classfiy1.add(new SelectModel("8", "佣金比例(从低到高)", false));
        classfiy1.add(new SelectModel("5", "佣金比例(从高到低)", false));
        final View commsionView = getLayoutInflater().inflate(R.layout.layout_recyclerview, null);
        RecyclerView commsionRecycler = ButterKnife.findById(commsionView, R.id.recycler);
        commisionAdapter = new DropMenuAdapter(R.layout.item_filter);
        RecyclerViewUtils.initLinerLayoutRecyclerView(commsionRecycler, mContext);
        commsionRecycler.setAdapter(commisionAdapter);
        commisionAdapter.setNewData(classfiy1);
        //筛选
        List<SelectModel> classfiy2 = new ArrayList<>();
        classfiy2.add(new SelectModel("1", "券后价格(从低到高)", false));
        classfiy2.add(new SelectModel("2", "券后价格(从高到低)", false));
        classfiy2.add(new SelectModel("8", "优惠券领取量(从低到高)", false));
        classfiy2.add(new SelectModel("13", "优惠券领取量(从高到低)", false));

        final View selectView = getLayoutInflater().inflate(R.layout.layout_recyclerview, null);
        RecyclerView selectRecycler = ButterKnife.findById(selectView, R.id.recycler);
        dropMenuAdapter = new DropMenuAdapter(R.layout.item_filter);
        RecyclerViewUtils.initLinerLayoutRecyclerView(selectRecycler, mContext);
        selectRecycler.setAdapter(dropMenuAdapter);
        dropMenuAdapter.setNewData(classfiy2);

        //init popupViews
        popupViews.add(new View(mContext));
        popupViews.add(saleView);
        popupViews.add(commsionView);
        popupViews.add(selectView);

        View containerView = LayoutInflater.from(this).inflate(R.layout.layout_filter_content, null);
        recyclerProduct = ButterKnife.findById(containerView, R.id.recycler);

        //init dropdownview
        dropDownMenu.setDropDownMenu(Arrays.asList(headers), popupViews, containerView);
    }

    private void initFileter() {
        //设置筛选条件 销量
        List<SelectModel> classfiy = new ArrayList<>();
        classfiy.add(new SelectModel("5", "按销量升序", false));
        classfiy.add(new SelectModel("6", "按销量降序", false));
        final View saleView = getLayoutInflater().inflate(R.layout.layout_recyclerview, null);
        RecyclerView saleRecycler = ButterKnife.findById(saleView, R.id.recycler);
        saleAdapter = new DropMenuAdapter(R.layout.item_filter);
        RecyclerViewUtils.initLinerLayoutRecyclerView(saleRecycler, mContext);
        saleRecycler.setAdapter(saleAdapter);
        saleAdapter.setNewData(classfiy);
        //佣金
        List<SelectModel> classfiy1 = new ArrayList<>();
        classfiy1.add(new SelectModel("7", "优惠券金额排序升序", false));
        classfiy1.add(new SelectModel("8", "优惠券金额排序降序", false));
        final View commsionView = getLayoutInflater().inflate(R.layout.layout_recyclerview, null);
        RecyclerView commsionRecycler = ButterKnife.findById(commsionView, R.id.recycler);
        commisionAdapter = new DropMenuAdapter(R.layout.item_filter);
        RecyclerViewUtils.initLinerLayoutRecyclerView(commsionRecycler, mContext);
        commsionRecycler.setAdapter(commisionAdapter);
        commisionAdapter.setNewData(classfiy1);
        //筛选
        List<SelectModel> classfiy2 = new ArrayList<>();
        classfiy2.add(new SelectModel("3", "按价格升序", false));
        classfiy2.add(new SelectModel("4", "按价格降序", false));

        final View selectView = getLayoutInflater().inflate(R.layout.layout_recyclerview, null);
        RecyclerView selectRecycler = ButterKnife.findById(selectView, R.id.recycler);
        dropMenuAdapter = new DropMenuAdapter(R.layout.item_filter);
        RecyclerViewUtils.initLinerLayoutRecyclerView(selectRecycler, mContext);
        selectRecycler.setAdapter(dropMenuAdapter);
        dropMenuAdapter.setNewData(classfiy2);

        //init popupViews
        popupViews.add(new View(mContext));
        popupViews.add(saleView);
        popupViews.add(commsionView);
        popupViews.add(selectView);

        View containerView = LayoutInflater.from(this).inflate(R.layout.layout_filter_content, null);
        recyclerProduct = ButterKnife.findById(containerView, R.id.recycler);

        dropDownMenu.setDropDownMenu(Arrays.asList(header), popupViews, containerView);
    }

    @Override
    protected void initEvent() {
        super.initEvent();

        if (pddAdapter != null && TextUtils.equals(type, "3")) {
            pddAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
//                Bundle bundle = new Bundle();
//                bundle.putString("item_id", productAdapter.getData().get(position).getGoods_id());
//                IntentUtils.get().goActivity(mContext, PddDetailsActivity.class, bundle);
                    getMvpPresenter().getPddPromotion(userModel.getId(), pddAdapter.getData().get(position).getGoods_id(), userModel.getUser_channel_id());
                }
            });
            pddAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
                @Override
                public void onLoadMoreRequested() {
                    initData(false);
                }
            }, recyclerProduct);
        }
        if (TextUtils.equals(type, "1")) {
            productAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
                @Override
                public void onLoadMoreRequested() {
                    initData(false);
                }
            }, recyclerProduct);
            productAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                    if (TextUtils.equals(type, "1")) {
                        ProductModel productModel = productAdapter.getItem(position);
                        Bundle bundle = new Bundle();
                        bundle.putString("item_id", productModel.getItem_id());
                        IntentUtils.get().goActivity(mContext, ProductDetailsActivity.class, bundle);
                    }
                }
            });
        }
        saleAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                SelectModel selectModel = saleAdapter.getData().get(position);
                sort = selectModel.getId();
                initData(true);
                dropDownMenu.closeMenu();
            }
        });
        commisionAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                SelectModel selectModel = commisionAdapter.getData().get(position);
                sort = selectModel.getId();
                initData(true);
                dropDownMenu.closeMenu();
            }
        });
        dropMenuAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                SelectModel selectModel = dropMenuAdapter.getData().get(position);
                sort = selectModel.getId();
                initData(true);
                dropDownMenu.closeMenu();
            }
        });
        dropDownMenu.setListener(new DropDownMenu.OnTabClickListener() {
            @Override
            public void onClick() {
                sort = "0";
                initData(true);
                if (dropDownMenu.isShowing()) {
                    dropDownMenu.closeMenu();
                }
            }
        });
    }

    private void initData(boolean isRefresh) {
        if (TextUtils.equals(type, "1")) {
            getMvpPresenter().getGoodsSearch(keyWord, sort, userModel.getId(), userModel.getUser_channel_id(), userModel.getLevel(), isRefresh);
        } else {
            getMvpPresenter().getProductListOther(sort, keyWord, userModel.getId(), userModel.getUser_channel_id(), isRefresh);
        }
    }

    @Override
    public void setProductList(List<ProductModel> models, boolean isRefresh) {
        RecyclerViewUtils.handleNormalAdapter(productAdapter, models, isRefresh);
    }

    @Override
    public void setPromotionInfo(PddPromotionModel response) {
        boolean hasInstalled = IntentUtils.checkHasInstalledApp(mContext, "com.xunmeng.pinduoduo");
        if (hasInstalled) {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(response.getMobile_url()));
            startActivity(intent);
        } else {
            Bundle bundle = new Bundle();
            bundle.putString("link", response.getWe_app_web_view_url());
            IntentUtils.get().goActivity(mContext, WebViewPddDetailsActivity.class, bundle);
        }
    }

    //, R.id.choose_comprehensive
    @OnClick({R.id.img_back})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                finish();
                break;
//            case R.id.choose_comprehensive:
//                sort = "0";
//                initData(true);
//                if (dropDownMenu.isShowing()) {
//                    dropDownMenu.closeMenu();
//                }
//                break;
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @Override
    public void showProductList(List<PddListModel> models, boolean isRefresh) {
        RecyclerViewUtils.handleNormalAdapter(pddAdapter, models, isRefresh);
    }

    @Override
    public void showError(Throwable throwable, boolean isRefresh) {
        if (TextUtils.equals(type, "1")) {
            RecyclerViewUtils.handError(productAdapter, isRefresh);
        } else if (TextUtils.equals(type, "3")) {
            RecyclerViewUtils.handError(pddAdapter, isRefresh);
        }
    }
}
