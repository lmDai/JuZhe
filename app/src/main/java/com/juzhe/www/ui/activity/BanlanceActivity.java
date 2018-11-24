package com.juzhe.www.ui.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.juzhe.www.R;
import com.juzhe.www.base.BaseMvpActivity;
import com.juzhe.www.bean.ExtractModel;
import com.juzhe.www.bean.UserModel;
import com.juzhe.www.common.mvp_senior.annotaions.CreatePresenterAnnotation;
import com.juzhe.www.mvp.contract.ExtractContract;
import com.juzhe.www.mvp.presenter.ExtractPresenter;
import com.juzhe.www.utils.IntentUtils;
import com.juzhe.www.utils.UserUtils;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 余额
 */
@CreatePresenterAnnotation(ExtractPresenter.class)
public class BanlanceActivity extends BaseMvpActivity<ExtractContract.View, ExtractPresenter> implements ExtractContract.View {


    @BindView(R.id.img_back)
    ImageView imgBack;
    @BindView(R.id.txt_title)
    TextView txtTitle;
    @BindView(R.id.txt_right)
    TextView txtRight;
    @BindView(R.id.btn_logout)
    Button btnLogout;
    @BindView(R.id.txt_balance)
    TextView txtBalance;
    @BindView(R.id.txt_own_commission)
    TextView txtOwnCommission;
    @BindView(R.id.txt_fans_commission)
    TextView txtFansCommission;
    @BindView(R.id.txt_wait_commission)
    TextView txtWaitCommission;
    private ExtractModel models;
    private UserModel userModel;

    @Override
    protected int getLayout() {
        return R.layout.activity_banlance;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        txtRight.setVisibility(View.VISIBLE);
        txtRight.setText("明细");
        txtTitle.setText(mContext.getString(R.string.title_balance));
        userModel=UserUtils.getUser(mContext);
        getMvpPresenter().getUserExtract(userModel.getId(), userModel.getUser_channel_id());
    }

    @Override
    protected void initImmersionBar() {
        super.initImmersionBar();
        mImmersionBar.titleBar(R.id.toolbar)
                .statusBarColor(R.color.colorWhite)
                .statusBarDarkFont(true, 0.2f)
                .init();
    }


    @OnClick({R.id.img_back, R.id.txt_right, R.id.btn_logout})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                finish();
                break;
            case R.id.txt_right:
                IntentUtils.get().goActivity(mContext, IncomeDetailsActivity.class);
                break;
            case R.id.btn_logout:
                IntentUtils.get().goActivity(mContext, WithdrawActivity.class);
                break;
        }
    }

    @Override
    public void setUserExtract(ExtractModel models) {
        this.models = models;
        txtBalance.setText("¥" + models.getBalance());
        txtOwnCommission.setText(models.getOwn_commission());
        txtFansCommission.setText(models.getFans_commission());
        txtWaitCommission.setText(models.getWait_commission());

    }

    @Override
    public void showError(Throwable throwable) {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}