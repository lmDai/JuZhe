package com.juzhe.www.ui.fragment;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.juzhe.www.R;
import com.juzhe.www.base.BaseMvpFragment;
import com.juzhe.www.bean.OrderModel;
import com.juzhe.www.bean.UserModel;
import com.juzhe.www.common.mvp_senior.annotaions.CreatePresenterAnnotation;
import com.juzhe.www.mvp.contract.OrderListContract;
import com.juzhe.www.mvp.presenter.OrderListPresenter;
import com.juzhe.www.ui.adapter.OrderListAdapter;
import com.juzhe.www.utils.RecyclerViewUtils;
import com.juzhe.www.utils.UserUtils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import java.util.List;

import butterknife.BindView;

/**
 * @package: com.juzhe.www.ui.fragment
 * @user:xhkj
 * @date:2018/11/2
 * @description: 订单列表
 **/
@CreatePresenterAnnotation(OrderListPresenter.class)
public class OrderListFragment extends BaseMvpFragment<OrderListContract.View, OrderListPresenter> implements OrderListContract.View {
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.refresh_layout)
    SmartRefreshLayout refreshLayout;
    private OrderListAdapter orderAdapter;
    private static final String TYPE = "type";
    public int tag;//1.待支付订单，2.已支付订单，3.退款订单，4.完成订单，5.失效订单 0.全部
    private UserModel userModel;

    public OrderListFragment newInstance(int tag) {
        OrderListFragment orderListFragment = new OrderListFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(TYPE, tag);
        orderListFragment.setArguments(bundle);
        return orderListFragment;
    }

    @Override
    protected int getLayout() {
        return R.layout.fragment_order_list;
    }


    @Override
    protected void initView(LayoutInflater inflater) {
        super.initView(inflater);
        Bundle bundle = getArguments();
        if (bundle != null) {
            tag = bundle.getInt(TYPE);
        }
        userModel = UserUtils.getUser(mContext);
        orderAdapter = new OrderListAdapter(R.layout.item_order, tag);
        addHeader();//添加头部
        RecyclerViewUtils.initHeaderRecyclerView(recyclerView, mContext);
        recyclerView.setAdapter(orderAdapter);
    }

    @Override
    protected void lazyFetchData() {
        super.lazyFetchData();
        getMvpPresenter().getUserOrder(tag + "", userModel.getId(), userModel.getUser_channel_id(), true);
    }

    @Override
    protected void initEvent() {
        super.initEvent();
        orderAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                getMvpPresenter().getUserOrder(tag + "", userModel.getId(), userModel.getUser_channel_id(), false);
            }
        }, recyclerView);
    }

    private void addHeader() {
        View view = LayoutInflater.from(mContext).inflate(R.layout.order_list_head, null, false);
        orderAdapter.addHeaderView(view);
    }

    @Override
    public void showOrderList(List<OrderModel> models, boolean isRefresh) {
        RecyclerViewUtils.handleNormalAdapter(orderAdapter, models, isRefresh);
    }

    @Override
    public void showError(Throwable throwable, boolean isRefresh) {
        RecyclerViewUtils.handError(orderAdapter, isRefresh);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}
