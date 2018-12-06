package com.juzhe.www.ui.activity.person;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.AppUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.juzhe.www.R;
import com.juzhe.www.base.BaseMvpActivity;
import com.juzhe.www.bean.UserModel;
import com.juzhe.www.common.https.BaseNoDataResponse;
import com.juzhe.www.common.mvp_senior.annotaions.CreatePresenterAnnotation;
import com.juzhe.www.mvp.contract.KalManContract;
import com.juzhe.www.mvp.presenter.KalManPresenter;
import com.juzhe.www.ui.activity.login.LoginActivity;
import com.juzhe.www.ui.widget.ClearEditText;
import com.juzhe.www.utils.AppManager;
import com.juzhe.www.utils.GlideUtil;
import com.juzhe.www.utils.IntentUtils;
import com.juzhe.www.utils.SharePreUtils;
import com.juzhe.www.utils.UserUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 卡密兑换
 */
@CreatePresenterAnnotation(KalManPresenter.class)
public class KalManActivity extends BaseMvpActivity<KalManContract.View, KalManPresenter> implements KalManContract.View {

    @BindView(R.id.img_back)
    ImageView imgBack;
    @BindView(R.id.txt_title)
    TextView txtTitle;
    @BindView(R.id.txt_right)
    TextView txtRight;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.edit_card_number)
    ClearEditText editCardNumber;
    @BindView(R.id.edit_secret_key)
    ClearEditText editSecretKey;
    @BindView(R.id.btn_recharge)
    Button btnRecharge;
    private UserModel userModel;

    @Override
    protected int getLayout() {
        return R.layout.activity_kal_man;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        txtTitle.setText(mContext.getString(R.string.title_recharge));
        userModel = UserUtils.getUser(mContext);
        getMvpPresenter().getUserInfo(userModel.getId(), userModel.getUser_channel_id());
    }

    @Override
    protected void initEvent() {
        super.initEvent();
    }

    @Override
    protected void initImmersionBar() {
        super.initImmersionBar();
        mImmersionBar.titleBar(R.id.toolbar)
                .statusBarColor(R.color.colorWhite)
                .statusBarDarkFont(true, 0.2f)
                .init();
    }

    @OnClick({R.id.img_back, R.id.btn_recharge})
    public void onViewClicked(View view) {

        switch (view.getId()) {
            case R.id.img_back:
                finish();
                break;
            case R.id.btn_recharge:
                String cardNumber = editCardNumber.getText().toString();
                String secretKey = editSecretKey.getText().toString();
                if (TextUtils.isEmpty(cardNumber) || TextUtils.isEmpty(secretKey)) {
                    ToastUtils.showShort("请检查卡号/密钥是否输入");
                    return;
                }
                getMvpPresenter().userKalMan(cardNumber, secretKey, userModel.getId(), userModel.getUser_channel_id());
                break;

        }
    }

    @Override
    public void setUserModel(UserModel userModel) {
        UserUtils.saveUserInfo(mContext, userModel);
    }

    @Override
    public void userKalMan(BaseNoDataResponse settingResult) {
        ToastUtils.showShort(settingResult.getMsg());
        if (settingResult.getErrorcode() == 0) {
            getMvpPresenter().getUserInfo(userModel.getId(), userModel.getUser_channel_id());
            finish();
        }
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
}
