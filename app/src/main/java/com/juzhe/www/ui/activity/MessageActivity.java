package com.juzhe.www.ui.activity;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.juzhe.www.R;
import com.juzhe.www.base.BaseActivity;
import com.juzhe.www.bean.MessageModel;
import com.juzhe.www.ui.adapter.MessageAdapter;
import com.juzhe.www.utils.RecyclerViewUtils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class MessageActivity extends BaseActivity {


    @BindView(R.id.img_back)
    ImageView imgBack;
    @BindView(R.id.txt_title)
    TextView txtTitle;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.refresh_layout)
    SmartRefreshLayout refreshLayout;
    private MessageAdapter messageAdapter;

    @Override
    protected int getLayout() {
        return R.layout.activity_message;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        messageAdapter = new MessageAdapter(R.layout.item_message);
        RecyclerViewUtils.initLinerLayoutRecyclerView(recyclerView, mContext);
        recyclerView.setAdapter(messageAdapter);
        initData();
    }

    private void initData() {
        List<MessageModel> modelList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            modelList.add(new MessageModel());
        }
        messageAdapter.setNewData(modelList);
    }

    @Override
    protected void initEvent() {
        super.initEvent();
        messageAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                //todo 加载更多
                List<MessageModel> modelList = new ArrayList<>();
                for (int i = 0; i < 10; i++) {
                    modelList.add(new MessageModel());
                }
                RecyclerViewUtils.handleAdapter(messageAdapter, refreshLayout, modelList, false);
            }
        }, recyclerView);
    }

    @Override
    protected void initImmersionBar() {
        super.initImmersionBar();
        mImmersionBar.titleBar(R.id.toolbar)
                .statusBarColor(R.color.colorWhite)
                .statusBarDarkFont(true, 0.2f)
                .init();
    }


    @OnClick(R.id.img_back)
    public void onViewClicked() {
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
