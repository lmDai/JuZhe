package com.juzhe.www.ui.activity;

import android.os.Bundle;
import android.telephony.PhoneNumberFormattingTextWatcher;
import android.text.Editable;
import android.text.Html;
import android.text.InputFilter;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.util.RegexUtils;
import com.blankj.utilcode.util.ToastUtils;
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

import static com.juzhe.www.utils.IntentUtils.OPEN_ACTIVITY_KEY;

/**
 * 输入邀请信息
 */
@CreatePresenterAnnotation(InputInvateInfoPresenter.class)
public class InputPhoneActivity extends BaseMvpActivity<InputInvateInfoContract.View, InputInvateInfoPresenter> implements InputInvateInfoContract.View {


    @BindView(R.id.img_back)
    ImageView imgBack;
    @BindView(R.id.btn_next)
    Button btnNext;
    @BindView(R.id.edit_code)
    EditText editCode;
    @BindView(R.id.txt_info)
    TextView txtInfo;
    private boolean isUserInput;
    private CodeModel codeModel;//上个页面传递过来的

    @Override
    protected int getLayout() {
        return R.layout.activity_input_phone;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        String html = "注册代表你已经同意<font color='red'>《巨折App用户协议》";
        txtInfo.setText(Html.fromHtml(html));

        editCode.setInputType(InputType.TYPE_CLASS_NUMBER);
        editCode.setFilters(new InputFilter[]{
                new InputFilter.LengthFilter(13)
        });
    }

    @Override
    protected void getIntentData() {
        super.getIntentData();
        codeModel = getIntent().getParcelableExtra(OPEN_ACTIVITY_KEY);
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
        editCode.addTextChangedListener(new PhoneNumberFormattingTextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                isUserInput = after == 1;
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().trim().equals("")) return;
                String startString = s.toString().replace(" ", "");
                if (isUserInput) {
                    if (startString.length() > 3) {
                        StringBuilder sb = new StringBuilder();
                        for (int i = 0; i < startString.length(); i++) {
                            if (i == 3 || i == 7) {
                                sb.append(" " + startString.charAt(i));
                            } else {
                                sb.append(startString.charAt(i));
                            }
                        }
                        editCode.setText(sb.toString());
                        editCode.setSelection(sb.toString().length());
                    }
                    isUserInput = false;
                } else {//删除一个或者多个
                    if (!isLegalInput(s)) {
                        StringBuilder sb = new StringBuilder();
                        for (int i = 0; i < startString.length(); i++) {
                            if (i == 3 || i == 7) {
                                sb.append(" " + startString.charAt(i));
                            } else {
                                sb.append(startString.charAt(i));
                            }
                        }
                        editCode.setText(sb.toString());
                        editCode.setSelection(start);
                    } else {
                        if ((s.length() == 4) || s.length() == 9
                                && String.valueOf(s.charAt(s.length() - 1)).equals(" ")) {
                            s = s.subSequence(0, s.length() - 1);
                            editCode.setText(s.toString());
                            editCode.setSelection(s.length());
                        }

                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() == 13) {
                    if (RegexUtils.isMobileExact(s.toString().replace(" ", "")))
                        btnNext.setEnabled(true);
                } else {

                    btnNext.setEnabled(false);
                }
            }
        });
    }

    /**
     * 验证输入合法性
     *
     * @param s
     * @return
     */
    private boolean isLegalInput(CharSequence s) {
        String start = s.toString().replace(" ", "");
        if (start.length() <= 3 && start.length() == s.length()) {
            return true;
        } else if (start.length() <= 7 && String.valueOf(s.charAt(3)).equals(" ")
                && (s.length() - start.length()) == 1) {
            return true;
        } else if (start.length() <= 11
                && String.valueOf(s.charAt(3)).equals(" ")
                && String.valueOf(s.charAt(8)).equals(" ")
                && (s.length() - start.length() == 2)) {
            return true;
        }
        return false;
    }

    @OnClick({R.id.img_back, R.id.btn_next})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                finish();
                break;
            case R.id.btn_next://发送短信验证码
                getMvpPresenter().sendSmsCode(editCode.getText().toString().replace(" ", ""), 1, codeModel.getUser_channel_id());
                break;
        }
    }

    @Override
    public void setCodeInfo(CodeModel codeInfo) {

    }

    @Override
    public void sendCodeSuccess(BaseNoDataResponse result) {
        ToastUtils.showShort(result.getMsg());
        if (result.getErrorcode() == 0) {
            Bundle bundle = new Bundle();
            bundle.putParcelable("codeMode", codeModel);
            bundle.putString("phone", editCode.getText().toString().replace(" ", ""));
            IntentUtils.get().goActivity(mContext, InputCodeActivity.class, bundle);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
