package com.juzhe.www.ui.activity.product;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.juzhe.www.R;
import com.juzhe.www.base.BaseMvpActivity;
import com.juzhe.www.bean.ProductModel;
import com.juzhe.www.bean.SelectModel;
import com.juzhe.www.bean.UserModel;
import com.juzhe.www.common.mvp_senior.annotaions.CreatePresenterAnnotation;
import com.juzhe.www.common.widget.dropmenu.DropDownMenu;
import com.juzhe.www.mvp.contract.SearchDetailsContract;
import com.juzhe.www.mvp.presenter.SearchDetailsPresenter;
import com.juzhe.www.ui.adapter.DropMenuAdapter;
import com.juzhe.www.ui.adapter.ProductAdapter;
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
    private int constellationPosition = 0;
    private DropMenuAdapter saleAdapter;//销量
    private DropMenuAdapter commisionAdapter;//佣金
    private DropMenuAdapter dropMenuAdapter;//筛选
    private List<View> popupViews = new ArrayList<>();
    private RecyclerView recyclerProduct;

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
        keyWord = bundle.getString("keyword");
        editSearch.setText(keyWord);
        txtSearch.setVisibility(View.GONE);
        initDropMenu();
        productAdapter = new ProductAdapter(R.layout.item_product, userModel.getLevel());
        RecyclerViewUtils.initLinerLayoutRecyclerView(recyclerProduct, mContext);
        recyclerProduct.setAdapter(productAdapter);
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

    @Override
    protected void initEvent() {
        super.initEvent();
        productAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                initData(false);
            }
        }, recyclerProduct);
        productAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                ProductModel productModel = productAdapter.getItem(position);
                Bundle bundle = new Bundle();
                bundle.putString("item_id", productModel.getItem_id());
                IntentUtils.get().goActivity(mContext, ProductDetailsActivity.class, bundle);
            }
        });
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
        getMvpPresenter().getGoodsSearch(keyWord, sort, userModel.getId(), userModel.getUser_channel_id(), userModel.getLevel(), isRefresh);
    }

    @Override
    public void setProductList(List<ProductModel> models, boolean isRefresh) {
        RecyclerViewUtils.handleNormalAdapter(productAdapter, models, isRefresh);
    }

    @Override
    public void showError(Throwable throwable, boolean isRefresh) {
        RecyclerViewUtils.handError(productAdapter, isRefresh);
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
}
