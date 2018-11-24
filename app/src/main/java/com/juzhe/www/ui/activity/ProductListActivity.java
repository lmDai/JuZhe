package com.juzhe.www.ui.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.juzhe.www.R;
import com.juzhe.www.base.BaseMvpActivity;
import com.juzhe.www.bean.ProductModel;
import com.juzhe.www.bean.UserModel;
import com.juzhe.www.common.mvp_senior.annotaions.CreatePresenterAnnotation;
import com.juzhe.www.mvp.contract.ProductListContract;
import com.juzhe.www.mvp.presenter.ProductListPresenter;
import com.juzhe.www.ui.adapter.ProductAdapter;
import com.juzhe.www.utils.IntentUtils;
import com.juzhe.www.utils.RecyclerViewUtils;
import com.juzhe.www.utils.UserUtils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

@CreatePresenterAnnotation(ProductListPresenter.class)
public class ProductListActivity extends BaseMvpActivity<ProductListContract.View, ProductListPresenter> implements ProductListContract.View {

    @BindView(R.id.img_back)
    ImageView imgBack;
    @BindView(R.id.txt_title)
    TextView txtTitle;
    @BindView(R.id.txt_right)
    TextView txtRight;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.refresh_layout)
    SmartRefreshLayout refreshLayout;
    private String name;
    private String key;
    private ProductAdapter productAdapter;
    private UserModel userModel;

    @Override
    protected int getLayout() {
        return R.layout.activity_product_list;
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
        Bundle bundle = getIntent().getBundleExtra("bundle");
        name = bundle.getString("name");
        key = bundle.getString("key");
        txtTitle.setText(name);
        userModel=UserUtils.getUser(mContext);
        productAdapter = new ProductAdapter(R.layout.item_product, userModel.getLevel());
        RecyclerViewUtils.initLinerLayoutRecyclerView(recyclerView, mContext);
        recyclerView.setAdapter(productAdapter);
        getMvpPresenter().getGoodHaoList(key, "", userModel.getId(), userModel.getUser_channel_id(), userModel.getLevel(), true);
    }

    @Override
    protected void initEvent() {
        super.initEvent();
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                getMvpPresenter().getGoodHaoList(key, "", userModel.getId(), userModel.getUser_channel_id(), userModel.getLevel(), true);
            }
        });
        productAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                getMvpPresenter().getGoodHaoList(key, "", userModel.getId(), userModel.getUser_channel_id(), userModel.getLevel(), false);
            }
        }, recyclerView);
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

    @OnClick(R.id.img_back)
    public void onViewClicked() {
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }


    @Override
    public void showProductList(List<ProductModel> models, boolean isRefresh) {
        RecyclerViewUtils.handleAdapter(productAdapter, refreshLayout, models, isRefresh);
    }

    @Override
    public void showError(Throwable throwable, boolean isRefresh) {
        if (isRefresh) {
            refreshLayout.finishRefresh(false);
        } else {
            productAdapter.loadMoreComplete();
        }
        RecyclerViewUtils.handError(productAdapter, isRefresh);
    }
}
