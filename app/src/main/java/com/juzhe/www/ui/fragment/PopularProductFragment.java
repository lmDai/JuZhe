package com.juzhe.www.ui.fragment;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.juzhe.www.R;
import com.juzhe.www.base.BaseFragment;
import com.juzhe.www.bean.PopularProductModel;
import com.juzhe.www.ui.activity.TheyPurchaseActivity;
import com.juzhe.www.ui.adapter.PopularProductAdapter;
import com.juzhe.www.utils.IntentUtils;
import com.juzhe.www.utils.RecyclerViewUtils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * @package: com.bestsoft.ui.fragment
 * @user:xhkj
 * @date:2018/11/2
 * @description: 商品列表
 **/
public class PopularProductFragment extends BaseFragment {
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.refresh_layout)
    SmartRefreshLayout refreshLayout;
    private PopularProductAdapter productAdapter;


    @Override
    protected int getLayout() {
        return R.layout.fragment_popular_product;
    }


    @Override
    protected void initView(LayoutInflater inflater) {
        super.initView(inflater);
        productAdapter = new PopularProductAdapter(R.layout.item_popular_product);
        RecyclerViewUtils.initLinerLayoutRecyclerView(recyclerView, mContext);
        recyclerView.setAdapter(productAdapter);
        initData();
    }

    private void initData() {
        List<PopularProductModel> modelList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            modelList.add(new PopularProductModel());
        }
        productAdapter.setNewData(modelList);
    }

    @Override
    protected void initEvent() {
        super.initEvent();
        productAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                //todo 加载更多
                List<PopularProductModel> modelList = new ArrayList<>();
                for (int i = 0; i < 10; i++) {
                    modelList.add(new PopularProductModel());
                }
                RecyclerViewUtils.handleNormalAdapter(productAdapter, modelList, false);
            }
        }, recyclerView);
        productAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                IntentUtils.get().goActivity(mContext, TheyPurchaseActivity.class);
            }
        });
    }
}
