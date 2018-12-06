package com.juzhe.www.ui.activity.login;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.util.RegexUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.juzhe.www.Constant;
import com.juzhe.www.MainActivity;
import com.juzhe.www.R;
import com.juzhe.www.base.BaseMvpActivity;
import com.juzhe.www.bean.UserModel;
import com.juzhe.www.common.https.BaseNoDataResponse;
import com.juzhe.www.common.mvp_senior.annotaions.CreatePresenterAnnotation;
import com.juzhe.www.mvp.contract.PhoneLoginContract;
import com.juzhe.www.mvp.presenter.PhoneLoginPresenter;
import com.juzhe.www.ui.widget.ClearEditText;
import com.juzhe.www.utils.IntentUtils;
import com.juzhe.www.utils.SharePreUtils;
import com.juzhe.www.utils.UserUtils;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;

@CreatePresenterAnnotation(PhoneLoginPresenter.class)
public class PhoneLoginActivity extends BaseMvpActivity<PhoneLoginContract.View, PhoneLoginPresenter> implements PhoneLoginContract.View {

    @BindView(R.id.img_back)
    ImageView imgBack;
    @BindView(R.id.edit_phone)
    ClearEditText editPhone;
    @BindView(R.id.edit_code)
    ClearEditText editCode;
    @BindView(R.id.txt_count)
    TextView txtCount;
    @BindView(R.id.btn_next)
    Button btnNext;
    private Disposable mDisposable;

    @Override
    protected void initImmersionBar() {
        super.initImmersionBar();
        mImmersionBar.statusBarView(R.id.top_view)
                .statusBarColor(R.color.colorWhite)
                .statusBarDarkFont(true, 0.2f)
                .init();
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_phone_login;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {

    }

    @Override
    protected void initEvent() {
        super.initEvent();
        editPhone.addTextChangedListener(new MyTextWatcher());
        editCode.addTextChangedListener(new MyTextWatcher());
    }

    @OnClick({R.id.img_back, R.id.txt_count, R.id.btn_next})
    public void onViewClicked(View view) {
        String userName = editPhone.getText().toString();
        String code = editCode.getText().toString();
        switch (view.getId()) {
            case R.id.img_back:
                finish();
                break;
            case R.id.txt_count:
                if (!RegexUtils.isMobileExact(userName)) {
                    ToastUtils.showShort("请输入正确的手机号");
                    return;
                }
                countdown(60);//倒计时60s
                getMvpPresenter().sendSmsCode(userName, 2, "");
                break;
            case R.id.btn_next:
                getMvpPresenter().login(userName, code);
                break;
        }
    }

    @Override
    public void sendCodeSuccess(BaseNoDataResponse result) {
        ToastUtils.showShort(result.getMsg());
    }

    @Override
    public void loginSuccess(UserModel userModel) {
        SharePreUtils.put(mContext, Constant.isLOGIN, true);
        UserUtils.saveUserInfo(mContext, userModel);
        IntentUtils.get().goActivityKill(mContext, MainActivity.class);//手机号登录
    }

    public class MyTextWatcher implements TextWatcher {

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            boolean username = editPhone.getText().toString().length() > 0;
            boolean code = editCode.getText().toString().length() > 0;
            if (username && code) {
                btnNext.setEnabled(true);
            } else {
                btnNext.setEnabled(false);
            }
        }
    }

    public void countdown(int time) {
        // 倒计时 10s
        mDisposable = Flowable.intervalRange(0, time, 0, 1, TimeUnit.SECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) throws Exception {
                        txtCount.setEnabled(false);
                        txtCount.setText(String.valueOf(time - aLong) + " s");
                    }
                })
                .doOnComplete(new Action() {
                    @Override
                    public void run() throws Exception {
                        txtCount.setEnabled(true);
                        txtCount.setText("获取验证码");
                    }
                })
                .subscribe();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mDisposable != null) {
            mDisposable.dispose();
        }
    }
}
