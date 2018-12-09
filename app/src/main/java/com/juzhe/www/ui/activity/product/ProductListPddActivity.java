package com.juzhe.www.ui.activity.product;

import android.content.Intent;
import android.net.Uri;
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
import com.juzhe.www.bean.PddListModel;
import com.juzhe.www.bean.PddPromotionModel;
import com.juzhe.www.bean.UserModel;
import com.juzhe.www.common.mvp_senior.annotaions.CreatePresenterAnnotation;
import com.juzhe.www.mvp.contract.ProductListPddContract;
import com.juzhe.www.mvp.presenter.ProductListPddPresenter;
import com.juzhe.www.ui.adapter.ProductListPddAdapter;
import com.juzhe.www.utils.IntentUtils;
import com.juzhe.www.utils.RecyclerViewUtils;
import com.juzhe.www.utils.UserUtils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 拼多多列表
 */
@CreatePresenterAnnotation(ProductListPddPresenter.class)
public class ProductListPddActivity extends BaseMvpActivity<ProductListPddContract.View, ProductListPddPresenter> implements ProductListPddContract.View {

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
    private ProductListPddAdapter productAdapter;
    private UserModel userModel;

    @Override
    protected int getLayout() {
        return R.layout.activity_product_list_pdd;
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
        txtTitle.setText("拼多多");
        userModel = UserUtils.getUser(mContext);
        productAdapter = new ProductListPddAdapter(R.layout.item_product);
        RecyclerViewUtils.initLinerLayoutRecyclerView(recyclerView, mContext);
        recyclerView.setAdapter(productAdapter);
        getMvpPresenter().getProductListOther(userModel.getId(),userModel.getUser_channel_id(), true);
    }

    @Override
    protected void initEvent() {
        super.initEvent();
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                getMvpPresenter().getProductListOther(userModel.getId(),userModel.getUser_channel_id(), true);
            }
        });
        productAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                getMvpPresenter().getProductListOther(userModel.getId(),userModel.getUser_channel_id(), false);
            }
        }, recyclerView);
        productAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
//                Bundle bundle = new Bundle();
//                bundle.putString("item_id", productAdapter.getData().get(position).getGoods_id());
//                IntentUtils.get().goActivity(mContext, PddDetailsActivity.class, bundle);
                getMvpPresenter().getPddPromotion(userModel.getId(), productAdapter.getData().get(position).getGoods_id(), userModel.getUser_channel_id());
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
    public void showProductList(List<PddListModel> models, boolean isRefresh) {
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
}
