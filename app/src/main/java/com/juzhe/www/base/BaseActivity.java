package com.juzhe.www.base;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import com.blankj.utilcode.util.LogUtils;
import com.gyf.barlibrary.ImmersionBar;
import com.juzhe.www.MyApplication;
import com.juzhe.www.R;
import com.juzhe.www.bean.UserModel;
import com.juzhe.www.common.https.rxUtils.RxEvent;
import com.juzhe.www.utils.AppManager;
import com.juzhe.www.utils.KeyboardUtils;
import com.juzhe.www.utils.TextFontUtils;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * @package: com.bestsoft.base
 * @user:xhkj
 * @date:2018/10/29
 * @description:
 **/
public abstract class BaseActivity extends RxAppCompatActivity {
    protected Activity mContext;
    protected Unbinder mUnBinder;
    protected ImmersionBar mImmersionBar;
    protected UserModel userModel;
    private TextView txtTitle;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
        if (getLayout() != 0) {
            setContentView(getLayout());
        }
        mContext = this;
        mUnBinder = ButterKnife.bind(this);
        //初始化沉浸式
        if (isImmersionBarEnabled())
            initImmersionBar();
        getIntentData();
        initView(savedInstanceState);
        txtTitle = findViewById(R.id.txt_title);
        if (txtTitle != null)
            TextFontUtils.setTextTypeDTr(mContext, txtTitle);
        initEvent();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mUnBinder != null)
            mUnBinder.unbind();
        if (mImmersionBar != null)
            mImmersionBar.destroy();  //在BaseActivity里销毁
        AppManager.getAppManager().finishActivity(this);
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void refreshData(RxEvent messageEvent) {
        if (messageEvent.getCode() == 1) {
            init();
            LogUtils.i("user_level"+userModel.getLevel());
        }
    }

    protected void initImmersionBar() {
        //在BaseActivity里初始化
        mImmersionBar = ImmersionBar.with(this);
        mImmersionBar.init();
    }

    /**
     * 是否可以使用沉浸式
     * Is immersion bar enabled boolean.
     *
     * @return the boolean
     */
    protected boolean isImmersionBarEnabled() {
        return true;
    }

    protected abstract int getLayout();

    protected abstract void initView(Bundle savedInstanceState);

    /**
     * 初始化
     */
    protected void init() {
        AppManager.getAppManager().addActivity(this);
        userModel = MyApplication.mApplication.getUser();
    }

    protected void initEvent() {
    }

    protected void getIntentData() {
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                View view = getCurrentFocus();
                KeyboardUtils.clickBlankArea2HideSoftInput(view, ev, mContext);//调用方法判断是否需要隐藏键盘
                if (KeyboardUtils.isFastDoubleClick()) {
                    return true;
                }
                break;
            default:
                break;
        }
        return super.dispatchTouchEvent(ev);
    }
}
