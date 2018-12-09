package com.juzhe.www.ui.activity.product;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.juzhe.www.R;
import com.juzhe.www.base.BaseMvpActivity;
import com.juzhe.www.bean.JDProductModel;
import com.juzhe.www.bean.UserModel;
import com.juzhe.www.common.mvp_senior.annotaions.CreatePresenterAnnotation;
import com.juzhe.www.mvp.contract.ProductListJDContract;
import com.juzhe.www.mvp.presenter.ProductListJDPresenter;
import com.juzhe.www.ui.adapter.ProductListJdAdapter;
import com.juzhe.www.utils.RecyclerViewUtils;
import com.juzhe.www.utils.UserUtils;
import com.kepler.jd.Listener.OpenAppAction;
import com.kepler.jd.login.KeplerApiManager;
import com.kepler.jd.sdk.bean.KelperTask;
import com.kepler.jd.sdk.bean.KeplerAttachParameter;
import com.kepler.jd.sdk.exception.KeplerBufferOverflowException;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import org.json.JSONException;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

@CreatePresenterAnnotation(ProductListJDPresenter.class)
public class ProductListJDActivity extends BaseMvpActivity<ProductListJDContract.View, ProductListJDPresenter> implements ProductListJDContract.View {

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
    private ProductListJdAdapter productAdapter;
    private UserModel userModel;
    KeplerAttachParameter mKeplerAttachParameter = new KeplerAttachParameter();//这个是即时性参数  可以设置
    Handler mHandler;
    KelperTask mKelperTask;
    public static final int timeOut = 15;
    OpenAppAction mOpenAppAction = new OpenAppAction() {
        @Override
        public void onStatus(final int status) {
            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    if (status == OpenAppAction.OpenAppAction_start) {//开始状态未必一定执行，

                    } else {
                        mKelperTask = null;

                    }
                }
            });
        }
    };

    @Override
    protected int getLayout() {
        return R.layout.activity_product_list_jd;
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
        txtTitle.setText("京东");
        mHandler = new Handler();
        userModel = UserUtils.getUser(mContext);
        productAdapter = new ProductListJdAdapter(R.layout.item_product, userModel.getLevel());
        RecyclerViewUtils.initLinerLayoutRecyclerView(recyclerView, mContext);
        recyclerView.setAdapter(productAdapter);
        getMvpPresenter().getProductListOther(userModel.getId(), userModel.getUser_channel_id(), true);
    }

    @Override
    protected void initEvent() {
        super.initEvent();
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                getMvpPresenter().getProductListOther(userModel.getId(), userModel.getUser_channel_id(), true);
            }
        });
        productAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                getMvpPresenter().getProductListOther(userModel.getId(), userModel.getUser_channel_id(), false);
            }
        }, recyclerView);
        productAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
//                Bundle bundle = new Bundle();
//                bundle.putParcelable("item", productAdapter.getData().get(position));
//                IntentUtils.get().goActivity(mContext, JdDetailsActivity.class, bundle);
                JDProductModel jdProductModel = productAdapter.getItem(position);
                getMvpPresenter().getJdDetails(jdProductModel.getCouponmoney() + "", jdProductModel.getItem_id(), jdProductModel.getDiscount_link(), userModel.getId(), userModel.getUser_channel_id());
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
    public void showProductList(List<JDProductModel> models, boolean isRefresh) {
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
    public void setResult(JDProductModel result) {
        try {
            mKelperTask = KeplerApiManager.getWebViewService().openJDUrlPage(result.getLink(), mKeplerAttachParameter, this, mOpenAppAction, timeOut);
        } catch (KeplerBufferOverflowException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


}
