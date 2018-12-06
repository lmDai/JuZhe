package com.juzhe.www.ui.activity.login;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.blankj.utilcode.util.ToastUtils;
import com.juzhe.www.Constant;
import com.juzhe.www.MainActivity;
import com.juzhe.www.R;
import com.juzhe.www.base.BaseMvpActivity;
import com.juzhe.www.bean.CodeModel;
import com.juzhe.www.bean.UserModel;
import com.juzhe.www.common.mvp_senior.annotaions.CreatePresenterAnnotation;
import com.juzhe.www.mvp.contract.RegisterContract;
import com.juzhe.www.mvp.presenter.RegisterPresenter;
import com.juzhe.www.ui.activity.login.LoginActivity;
import com.juzhe.www.ui.widget.VerifyCodeView;
import com.juzhe.www.utils.IntentUtils;
import com.juzhe.www.utils.SharePreUtils;
import com.juzhe.www.utils.UserUtils;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 输入邀请信息
 */
@CreatePresenterAnnotation(RegisterPresenter.class)
public class InputCodeActivity extends BaseMvpActivity<RegisterContract.View, RegisterPresenter> implements RegisterContract.View {


    @BindView(R.id.img_back)
    ImageView imgBack;
    @BindView(R.id.btn_next)
    Button btnNext;
    @BindView(R.id.verify_code_view)
    VerifyCodeView verifyCodeView;
    private String phone;//手机号
    private CodeModel codeModel;
    private String nickName = LoginActivity.nickName;
    private String headimgurl = LoginActivity.headimgurl;
    private String openid = LoginActivity.unionid;
    private String type = LoginActivity.type;//注册方式WeChat，QQ  普通注册为空''

    @Override
    protected int getLayout() {
        return R.layout.activity_input_code;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {


    }

    @Override
    protected void getIntentData() {
        super.getIntentData();
        Bundle bundle = getIntent().getBundleExtra("bundle");
        if (bundle != null) {
            phone = bundle.getString("phone");
            codeModel = bundle.getParcelable("codeMode");
        }
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
        verifyCodeView.setInputCompleteListener(new VerifyCodeView.InputCompleteListener() {
            @Override
            public void inputComplete() {
                btnNext.setEnabled(true);
            }

            @Override
            public void invalidContent() {
                btnNext.setEnabled(false);
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
                //todo 跳转首页
                if (codeModel == null) {
                    ToastUtils.showShort("邀请码不正确");
                    return;
                }
                getMvpPresenter().userRegister(nickName, headimgurl, openid, type, phone, verifyCodeView.getEditContent(), codeModel.getUser_channel_id()
                        , codeModel.getPid());
                break;
        }
    }

    @Override
    public void registerSuccess(UserModel result) {
        SharePreUtils.put(mContext, Constant.isLOGIN, true);
        UserUtils.saveUserInfo(mContext,result);
        IntentUtils.get().goActivityKill(mContext, MainActivity.class);//手机号登录
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
