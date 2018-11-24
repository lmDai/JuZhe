package com.juzhe.www;

/**
 * @package: com.juzhe.www
 * @user:xhkj
 * @date:2018/11/24
 * @description:
 **/

import android.content.Intent;
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
        if (!isTaskRoot()) {
            Intent intent = getIntent();
            String action = intent.getAction();
            if (intent.hasCategory(Intent.CATEGORY_LAUNCHER) && action != null && action.equals(Intent.ACTION_MAIN)) {
                finish();
                return;
            }
        }
        new Handler().postDelayed(new Runnable() {
            public void run() {
                if ((boolean) SharePreUtils.get(SplashActivity.this, Constant.isLOGIN, false)) {
                    IntentUtils.get().goActivityKill(SplashActivity.this, MainActivity.class);
                } else {
                    IntentUtils.get().goActivityKill(SplashActivity.this, LoginActivity.class);
                }
            }
        }, 1000);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_splash;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {

    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
    }
}
