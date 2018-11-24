package com.juzhe.www;

/**
 * @package: com.juzhe.www
 * @user:xhkj
 * @date:2018/11/24
 * @description:
 **/

import android.os.Bundle;
import android.os.Handler;

import com.juzhe.www.base.BaseActivity;
import com.juzhe.www.ui.activity.LoginActivity;
import com.juzhe.www.utils.IntentUtils;
import com.juzhe.www.utils.SharePreUtils;

public class SplashActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        new Handler().postDelayed(new Runnable() {
            public void run() {
                if ((boolean) SharePreUtils.get(SplashActivity.this, Constant.isLOGIN, false)) {
                    IntentUtils.get().goActivityKill(SplashActivity.this, MainActivity.class);
                } else {
                    IntentUtils.get().goActivityKill(SplashActivity.this, LoginActivity.class);
                }
            }
        }, 0);
    }

    @Override
    protected void initImmersionBar() {
        super.initImmersionBar();
        mImmersionBar
                .statusBarColor(R.color.colorWhite)
                .statusBarDarkFont(true, 0.2f)
                .init();
    }

    @Override
    protected int getLayout() {
        return 0;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {

    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
    }
}
