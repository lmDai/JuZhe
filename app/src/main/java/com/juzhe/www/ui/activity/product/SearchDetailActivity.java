package com.juzhe.www.ui.activity.product;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
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
import com.juzhe.www.mvp.contract.SearchDetailsContract;
import com.juzhe.www.mvp.presenter.SearchDetailsPresenter;
import com.juzhe.www.ui.activity.product.ProductDetailsActivity;
import com.juzhe.www.ui.adapter.ProductAdapter;
import com.juzhe.www.ui.widget.DropdownButton;
import com.juzhe.www.ui.widget.ItemClickListener;
import com.juzhe.www.ui.widget.ListPopu;
import com.juzhe.www.utils.IntentUtils;
import com.juzhe.www.utils.RecyclerViewUtils;
import com.juzhe.www.utils.UserUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

@CreatePresenterAnnotation(SearchDetailsPresenter.class)
public class SearchDetailActivity extends BaseMvpActivity<SearchDetailsContract.View, SearchDetailsPresenter> implements SearchDetailsContract.View {


    @BindView(R.id.choose_comprehensive)
    TextView chooseComprehensive;
    @BindView(R.id.choose_post_coupon)
    DropdownButton choosePostCoupon;
    @BindView(R.id.choose_sales_volume)
    DropdownButton chooseSalesVolume;
    @BindView(R.id.choose_voucher_denomination)
    DropdownButton chooseVoucherDenomination;
    @BindView(R.id.recycler_product)
    RecyclerView recyclerProduct;
    @BindView(R.id.img_back)
    ImageView imgBack;
    @BindView(R.id.edit_search)
    EditText editSearch;
    @BindView(R.id.txt_search)
    TextView txtSearch;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    private String keyWord;
    private String sort = "";
    private ProductAdapter productAdapter;
    private ListPopu listPopu;
    private UserModel userModel;

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
        userModel=UserUtils.getUser(mContext);
        Bundle bundle = getIntent().getBundleExtra("bundle");
        keyWord = bundle.getString("keyword");
        editSearch.setText(keyWord);
        txtSearch.setVisibility(View.GONE);
        productAdapter = new ProductAdapter(R.layout.item_product, userModel.getLevel());
        RecyclerViewUtils.initLinerLayoutRecyclerView(recyclerProduct, mContext);
        recyclerProduct.setAdapter(productAdapter);
        chooseComprehensive.setText("最新");
        choosePostCoupon.setText("销量");
        chooseSalesVolume.setText("佣金");
        chooseVoucherDenomination.setText("筛选");
        initData(true);
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


    @OnClick({R.id.img_back, R.id.choose_comprehensive, R.id.choose_post_coupon, R.id.choose_sales_volume, R.id.choose_voucher_denomination})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                finish();
                break;
            case R.id.choose_comprehensive:
                sort = "0";
                initData(true);
                break;
            case R.id.choose_post_coupon:
                List<SelectModel> classfiy = new ArrayList<>();
                classfiy.add(new SelectModel("7", "月销量(从低到高)", false));
                classfiy.add(new SelectModel("4", "月销量(从高到低)", false));
                classfiy.add(new SelectModel("10", "全天销量(从低到高)", false));
                classfiy.add(new SelectModel("9", "全天销量(从高到低)", false));
                classfiy.add(new SelectModel("12", "近2小时销量(从低到高)", false));
                classfiy.add(new SelectModel("11", "近2小时销量(从高到低)", false));
                showPopupWindow(classfiy);
                break;
            case R.id.choose_sales_volume:
                List<SelectModel> classfiy1 = new ArrayList<>();
                classfiy1.add(new SelectModel("8", "佣金比例(从低到高)", false));
                classfiy1.add(new SelectModel("5", "佣金比例(从高到低)", false));
                showPopupWindow(classfiy1);
                break;
            case R.id.choose_voucher_denomination:
                List<SelectModel> classfiy2 = new ArrayList<>();
                classfiy2.add(new SelectModel("1", "券后价格(从低到高)", false));
                classfiy2.add(new SelectModel("2", "券后价格(从高到低)", false));
                classfiy2.add(new SelectModel("8", "优惠券领取量(从低到高)", false));
                classfiy2.add(new SelectModel("13", "优惠券领取量(从高到低)", false));
                showPopupWindow(classfiy2);
                break;
        }
    }

    public void showPopupWindow(List<SelectModel> classfiy) {
        listPopu = new ListPopu(mContext, classfiy, R.layout.item_filter);
        listPopu.showPopupWindow(chooseComprehensive);
        listPopu.setOnItemClickListener(new ItemClickListener() {
            @Override
            public void onItemClick(Object obj, int position) {
                listPopu.dismiss();
                listPopu = null;
                sort = classfiy.get(position).getId();
                initData(true);
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
