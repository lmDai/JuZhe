package com.juzhe.www.ui.activity;

import android.os.Bundle;

import com.juzhe.www.R;
import com.juzhe.www.base.BaseActivity;

/**
 * @package: com.juzhe.www.ui.activity
 * @user:xhkj
 * @date:2018/11/6
 * @description: 他正在买
 **/
public class TheyPurchaseActivity extends BaseActivity {
    @Override
    protected int getLayout() {
        return R.layout.activity_they_purchase;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {

    }

    @Override
    protected void initImmersionBar() {
        super.initImmersionBar();
        mImmersionBar.titleBar(R.id.toolbar)
                .statusBarColor(R.color.colorWhite)
                .statusBarDarkFont(true, 0.2f)
                .init();
    }

}
