package com.juzhe.www.ui.activity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.juzhe.www.R;
import com.juzhe.www.base.BaseMvpActivity;
import com.juzhe.www.bean.CodeModel;
import com.juzhe.www.common.https.BaseNoDataResponse;
import com.juzhe.www.common.mvp_senior.annotaions.CreatePresenterAnnotation;
import com.juzhe.www.mvp.contract.InputInvateInfoContract;
import com.juzhe.www.mvp.presenter.InputInvateInfoPresenter;
import com.juzhe.www.utils.IntentUtils;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 输入邀请信息
 */
@CreatePresenterAnnotation(InputInvateInfoPresenter.class)
public class InputInvateInfoActivity extends BaseMvpActivity<InputInvateInfoContract.View, InputInvateInfoPresenter> implements InputInvateInfoContract.View {

    @BindView(R.id.img_back)
    ImageView imgBack;
    @BindView(R.id.btn_next)
    Button btnNext;
    @BindView(R.id.edit_code)
    EditText editCode;
    private CodeModel codeModel;

    @Override
    protected int getLayout() {
        return R.layout.activity_input_invate_info;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
    }

    @Override
    protected void initImmersionBar() {
        super.initImmersionBar();
        mImmersionBar.statusBarView(R.id.top_view)
                .statusBarColor(R.color.colorWhite)
                .statusBarDarkFont(true, 0.2f)
                .init();
    }

    @Override
    protected void initEvent() {
        super.initEvent();
        editCode.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                btnNext.setEnabled(false);
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() > 6 && s.length() < 9) {
                    getMvpPresenter().getInvateInfo(s.toString());
                }
            }
        });
    }

    @OnClick({R.id.img_back, R.id.btn_next})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                finish();
                break;
            case R.id.btn_next:
                IntentUtils.get().goActivityKill(mContext, InputPhoneActivity.class, codeModel);
                break;
        }
    }

    @Override
    public void setCodeInfo(CodeModel codeInfo) {
        this.codeModel = codeInfo;
        btnNext.setEnabled(true);
    }

    @Override
    public void sendCodeSuccess(BaseNoDataResponse result) {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
