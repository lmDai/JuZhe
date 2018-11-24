package com.juzhe.www;

/**
 * @package: com.juzhe.www
 * @user:xhkj
 * @date:2018/11/24
 * @description:
 **/
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import com.juzhe.www.ui.activity.LoginActivity;
import com.juzhe.www.utils.IntentUtils;
import com.juzhe.www.utils.SpUtils;

public class SplashActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        new Handler().postDelayed(new Runnable() {
            public void run() {
                if ((boolean) SpUtils.getParam(SplashActivity.this, Constant.isLOGIN, false)) {
                    IntentUtils.get().goActivityKill(SplashActivity.this, MainActivity.class);
                } else {
                    IntentUtils.get().goActivityKill(SplashActivity.this, LoginActivity.class);
                }
            }
        }, 0);
    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
    }
}
