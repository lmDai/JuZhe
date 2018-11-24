package com.juzhe.www.ui.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.blankj.utilcode.util.LogUtils;
import com.juzhe.www.Constant;
import com.juzhe.www.MainActivity;
import com.juzhe.www.MyApplication;
import com.juzhe.www.R;
import com.juzhe.www.base.BaseMvpActivity;
import com.juzhe.www.bean.ThirdLoginModel;
import com.juzhe.www.bean.UserModel;
import com.juzhe.www.common.mvp_senior.annotaions.CreatePresenterAnnotation;
import com.juzhe.www.mvp.contract.LoginContract;
import com.juzhe.www.mvp.presenter.LoginPresenter;
import com.juzhe.www.utils.DialogListener;
import com.juzhe.www.utils.DialogUtils;
import com.juzhe.www.utils.IntentUtils;
import com.juzhe.www.utils.RuntimeRationale;
import com.juzhe.www.utils.SpUtils;
import com.juzhe.www.utils.TextFontUtils;
import com.juzhe.www.utils.UserUtils;
import com.yanzhenjie.permission.Action;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.Permission;
import com.yanzhenjie.permission.Setting;

import java.util.HashMap;
import java.util.List;

import androidx.annotation.Nullable;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.tencent.qq.QQ;
import cn.sharesdk.wechat.friends.Wechat;
import io.reactivex.annotations.NonNull;

@CreatePresenterAnnotation(LoginPresenter.class)
public class LoginActivity extends BaseMvpActivity<LoginContract.View, LoginPresenter> implements LoginContract.View {

    @BindView(R.id.top_view)
    View topView;
    @BindView(R.id.txt_info)
    TextView txtInfo;
    @BindView(R.id.view_center)
    View viewCenter;
    @BindView(R.id.btn_wechat_login)
    Button btnWechatLogin;
    @BindView(R.id.txt_wechat)
    TextView txtWechat;
    @BindView(R.id.ll_wechat_login)
    LinearLayout llWechatLogin;
    @BindView(R.id.txt_phone)
    TextView txtPhone;
    @BindView(R.id.ll_phone)
    LinearLayout llPhone;
    @BindView(R.id.txt_qq)
    TextView txtQq;
    @BindView(R.id.ll_qq)
    LinearLayout llQq;
    @BindView(R.id.ll_other)
    LinearLayout llOther;
    public static String type = "";//wechat，qq
    public static String unionid = "";
    public static String nickName = "";
    public static String headimgurl = "";
    private UserModel userModel;


    @Override
    protected int getLayout() {
        return R.layout.activity_login;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {

        requestPermission(Permission.READ_PHONE_STATE, Permission.WRITE_EXTERNAL_STORAGE, Permission.READ_EXTERNAL_STORAGE);
        TextFontUtils.setTextTypeRTW(mContext, txtInfo);
        userModel = UserUtils.getUser(mContext);
    }

    /**
     * Request permissions.
     */
    private void requestPermission(String... permissions) {
        AndPermission.with(this)
                .runtime()
                .permission(permissions)
                .rationale(new RuntimeRationale())
                .onGranted(new Action<List<String>>() {
                    @Override
                    public void onAction(List<String> permissions) {

                    }
                })
                .onDenied(new Action<List<String>>() {
                    @Override
                    public void onAction(@NonNull List<String> permissions) {

                        if (AndPermission.hasAlwaysDeniedPermission(LoginActivity.this, permissions)) {
                            showSettingDialog(LoginActivity.this, permissions);
                        }
                    }
                })
                .start();
    }

    /**
     * Display setting dialog.
     */
    public void showSettingDialog(Context context, final List<String> permissions) {
        List<String> permissionNames = Permission.transformText(context, permissions);
        String message = context.getString(R.string.message_permission_always_failed, TextUtils.join("\n", permissionNames));

        new AlertDialog.Builder(context)
                .setCancelable(false)
                .setTitle(R.string.title_dialog)
                .setMessage(message)
                .setPositiveButton(R.string.setting, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        setPermission();
                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                })
                .show();
    }

    /**
     * Set permissions.
     */
    private void setPermission() {
        AndPermission.with(this)
                .runtime()
                .setting()
                .onComeback(new Setting.Action() {
                    @Override
                    public void onAction() {

                    }
                })
                .start();
    }

    @Override
    protected void initImmersionBar() {
        super.initImmersionBar();
        mImmersionBar.statusBarView(R.id.top_view)
                .statusBarColor(R.color.colorWhite)
                .statusBarDarkFont(true, 0.2f)
                .init();
    }

    @OnClick({R.id.ll_wechat_login, R.id.ll_qq, R.id.ll_phone, R.id.btn_wechat_login})
    public void onViewClicked(View view) {
        Platform plat = null;
        switch (view.getId()) {
            case R.id.ll_wechat_login:
                plat = ShareSDK.getPlatform(Wechat.NAME);
//                if (plat.isAuthValid()) {
//                    plat.removeAccount(true);
//                }
                plat.SSOSetting(true);
                plat.setPlatformActionListener(new PlatformActionListener() {
                    @Override
                    public void onComplete(Platform platform, int i, HashMap<String, Object> hashMap) {
                        LogUtils.i("onComplete" + platform.getDb().getUserId() + hashMap.get("nickname") + hashMap.get("figureurl"));
                        unionid = platform.getDb().getUserId();
                        nickName = platform.getDb().getUserName();//获取用户名字
                        headimgurl = platform.getDb().getUserIcon(); //获取用户头像

                        type = "wechat";
                        LoginActivity.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (userModel != null) {
                                    getMvpPresenter().thirdLogin(type, unionid, userModel.getId(), userModel.getUser_channel_id());
                                } else {
                                    getMvpPresenter().thirdLogin(type, unionid, "", "");

                                }
                            }
                        });

                    }

                    @Override
                    public void onError(Platform platform, int i, Throwable throwable) {
                        LogUtils.i("onError" + platform + throwable.getMessage());
                    }

                    @Override
                    public void onCancel(Platform platform, int i) {
                        LogUtils.i("onCancel" + platform);
                    }
                });
                plat.showUser(null);
                break;
            case R.id.ll_qq:
                plat = ShareSDK.getPlatform(QQ.NAME);
//                if (plat.isAuthValid()) {
//                    plat.removeAccount(true);
//                }
                plat.SSOSetting(true);
                plat.setPlatformActionListener(new PlatformActionListener() {
                    @Override
                    public void onComplete(Platform platform, int i, HashMap<String, Object> hashMap) {
                        LogUtils.i("onComplete" + platform.getDb().getUserId() + hashMap.get("nickname") + hashMap.get("figureurl"));
                        unionid = platform.getDb().getUserId();
                        nickName = (String) hashMap.get("nickname");
                        headimgurl = (String) hashMap.get("figureurl_qq_2");
                        type = "QQ";
                        LoginActivity.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (userModel != null) {
                                    getMvpPresenter().thirdLogin(type, unionid, userModel.getId(), userModel.getUser_channel_id());
                                } else {
                                    getMvpPresenter().thirdLogin(type, unionid, "", "");
                                }
                            }
                        });
                    }

                    @Override
                    public void onError(Platform platform, int i, Throwable throwable) {
                        LogUtils.i("onError" + platform + throwable.getMessage());
                    }

                    @Override
                    public void onCancel(Platform platform, int i) {
                        LogUtils.i("onCancel" + platform);
                    }
                });
                plat.showUser(null);
                break;
            case R.id.ll_phone:
                IntentUtils.get().goActivity(mContext, PhoneLoginActivity.class);//手机号登录
                break;
            case R.id.btn_wechat_login:
                IntentUtils.get().goActivity(mContext, InputInvateInfoActivity.class);//注册页面
                break;
        }
    }

    @Override
    public void loginSuccess(ThirdLoginModel<UserModel> model) {
        if (model.getErrorcode() == 2) {
            showRegisterDialog();
        } else if (model.getErrorcode() == 0) {
            SpUtils.setParam(mContext, Constant.isLOGIN, true);
            Log.i("single", JSON.toJSONString(model.getData()) + "");
            UserUtils.saveUserInfo(mContext, model.getData());
            Log.i("single", JSON.toJSONString(UserUtils.getUser(mContext)));
            IntentUtils.get().goActivityKill(mContext, MainActivity.class);//手机号登录
        }
    }

    public void showRegisterDialog() {
        DialogUtils.showPromptDialog(mContext, "你还没有注册或者未绑定微信", "提示", "去注册", "知道了", new DialogListener() {
            @Override
            public void onClick(boolean confirm) {
                if (confirm)
                    //todo 跳转注册页面
                    IntentUtils.get().goActivity(mContext, InputInvateInfoActivity.class);
            }
        });

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (!isTaskRoot()) {
            Intent intent = getIntent();
            String action = intent.getAction();
            if (intent.hasCategory(Intent.CATEGORY_LAUNCHER) && action != null && action.equals(Intent.ACTION_MAIN)) {
                finish();
                return;
            }
        }
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
