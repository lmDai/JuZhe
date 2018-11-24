package com.juzhe.www.ui.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.juzhe.www.Constant;
import com.juzhe.www.MyApplication;
import com.juzhe.www.R;
import com.juzhe.www.api.TaoBaoKeApi;
import com.juzhe.www.base.BaseMvpActivity;
import com.juzhe.www.bean.UpgradeModel;
import com.juzhe.www.bean.UserModel;
import com.juzhe.www.common.https.BaseApi;
import com.juzhe.www.common.https.rxUtils.RxEvent;
import com.juzhe.www.common.mvp_senior.annotaions.CreatePresenterAnnotation;
import com.juzhe.www.mvp.contract.UpgradeContract;
import com.juzhe.www.mvp.presenter.UpgradePresenter;
import com.juzhe.www.ui.adapter.FastEntranceAdapter;
import com.juzhe.www.utils.IntentUtils;
import com.juzhe.www.utils.SpacesItemDecoration;
import com.juzhe.www.utils.UserUtils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @package: com.juzhe.www.ui.activity
 * @user:xhkj
 * @date:2018/11/6
 * @description:会员中心
 **/
@CreatePresenterAnnotation(UpgradePresenter.class)
public class MemberActivity extends BaseMvpActivity<UpgradeContract.View, UpgradePresenter> implements UpgradeContract.View {
    @BindView(R.id.txt_level_name)
    TextView txtLevelName;
    @BindView(R.id.txt_describe)
    TextView txtDescribe;
    @BindView(R.id.btn_upgrade)
    Button btnUpgrade;
    @BindView(R.id.refresh_layout)
    SmartRefreshLayout refreshLayout;
    @BindView(R.id.recycler_entrance)
    RecyclerView recyclerEntrance;
    @BindView(R.id.txt_vip)
    TextView txtVip;
    @BindView(R.id.txt_info)
    TextView txtInfo;
    private UserModel userModel;

    @Override
    protected int getLayout() {
        return R.layout.activity_member;
    }


    @Override
    protected void initView(Bundle savedInstanceState) {
        userModel = UserUtils.getUser(mContext);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerEntrance.setLayoutManager(linearLayoutManager);
        recyclerEntrance.addItemDecoration(new SpacesItemDecoration(0));
        List<String> fruitList = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            fruitList.add(i + "");
        }
        FastEntranceAdapter adapter = new FastEntranceAdapter(fruitList);
        recyclerEntrance.setAdapter(adapter);
        getMvpPresenter().getUserUpgrade(userModel.getId(), userModel.getUser_channel_id());

    }

    @Override
    protected void initImmersionBar() {
        super.initImmersionBar();
        mImmersionBar.statusBarDarkFont(true, 0.2f)
                .init();
    }

    @Override
    protected void onResume() {
        super.onResume();
        getMvpPresenter().getUserInfo(userModel.getId(), userModel.getUser_channel_id());
        getMvpPresenter().getUserUpgrade(userModel.getId(), userModel.getUser_channel_id());
    }

    @Override
    protected void initEvent() {
        super.initEvent();
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                getMvpPresenter().getUserUpgrade(userModel.getId(), userModel.getUser_channel_id());
            }
        });
    }

    @Override
    public void setUpgrade(UpgradeModel models) {
        refreshLayout.finishRefresh();
        txtDescribe.setText(models.getDescribe());
        txtLevelName.setText(models.getLevel_name());
        btnUpgrade.setText("开通" + models.getUpgrade_level_name());
        txtVip.setText("升级至" + models.getUpgrade_level_name() + "每月权益预估");
        if (userModel.getLevel() == 1) {
            txtInfo.setVisibility(View.VISIBLE);
            txtInfo.setText("（提示：未升级为" + models.getUpgrade_level_name() + "之前不显示订单）");
        } else {
            txtInfo.setVisibility(View.GONE);
        }
    }

    @Override
    public void showError(Throwable throwable) {
        refreshLayout.finishRefresh(false);
    }

    @Override
    public void showPayPage(String page) {
        Bundle bundle = new Bundle();
        bundle.putString("link", BaseApi.getBaseUrl() + TaoBaoKeApi.UPGRADE_PAY + "?orderId=" + page);
        bundle.putInt("type", 1);
        IntentUtils.get().goActivity(mContext, WebViewActivity.class, bundle);

    }

    @Override
    public void setUserModel(UserModel userModel) {
        UserUtils.saveUserInfo(mContext, userModel);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick({R.id.btn_upgrade, R.id.img_me})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_upgrade:
                getMvpPresenter().getUpgradeApply(userModel.getId(), userModel.getUser_channel_id());
                break;
            case R.id.img_me:
                finish();
                break;
        }
    }
}
