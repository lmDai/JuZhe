package com.juzhe.www.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;

import com.blankj.utilcode.util.LogUtils;
import com.juzhe.www.common.https.BasePresenter;
import com.juzhe.www.common.https.IBaseView;
import com.juzhe.www.common.mvp_senior.factory.IMvpPresenterFactroy;
import com.juzhe.www.common.mvp_senior.factory.IPresenterProxyFactroy;
import com.juzhe.www.common.mvp_senior.factory.MvpPresenterFactroyImpl;
import com.juzhe.www.common.mvp_senior.factory.PresenterProxyFactroyImpl;
import com.juzhe.www.ui.widget.LoadingDialog;

import io.reactivex.ObservableTransformer;

/**
 * @package: com.juzhe.www.base
 * @user:xhkj
 * @date:2018/10/29
 * @description:Activity基类 TODO: 需要创建presenter，必需要添加注解@CreatePresenterAnnotation(xxx.class)
 **/
public abstract class BaseMvpActivity<V extends IBaseView, P extends BasePresenter<V>> extends BaseActivity
        implements IPresenterProxyFactroy<V, P>, IBaseView {
    private static final String PRESENTER_SAVE_KEY = "presenter_save_key";


    /**
     * 绑定生命周期
     */
    @Override
    public <T> ObservableTransformer<T, T> bindLifeycle() {
        return this.bindToLifecycle();
    }


    /**
     * 创建被代理对象,传入默认Presenter的工厂
     */
    private PresenterProxyFactroyImpl<V, P> mProxy = new PresenterProxyFactroyImpl<>(MvpPresenterFactroyImpl.<V, P>creater(getClass()));
    private LoadingDialog mLoadingDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            mProxy.onRestoreInstanceState(savedInstanceState.getBundle(PRESENTER_SAVE_KEY));
        }
        mLoadingDialog = new LoadingDialog(this);
        mProxy.onCreate((V) this);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.e("perfect-mvp", "V onDestroy = ");
        mProxy.onDestroy();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.e("perfect-mvp", "V onSaveInstanceState");
        outState.putBundle(PRESENTER_SAVE_KEY, mProxy.onSaveInstanceState());
    }

    @Override
    public void setPresenterFactory(IMvpPresenterFactroy<V, P> presenterFactory) {
        Log.e("perfect-mvp", "V setPresenterFactory");
        mProxy.setPresenterFactory(presenterFactory);
    }

    @Override
    public IMvpPresenterFactroy<V, P> getPresenterFactory() {
        Log.e("perfect-mvp", "V getPresenterFactory");
        return mProxy.getPresenterFactory();
    }

    @Override
    public P getMvpPresenter() {
        Log.e("perfect-mvp", "V getMvpPresenter");
        return mProxy.getMvpPresenter();
    }


    @Override
    public void showProgress(String message) {
        if (mLoadingDialog != null) {
            mLoadingDialog.setMessage(message).show();
        } else {
            mLoadingDialog = new LoadingDialog(this);
            mLoadingDialog.setMessage(message).show();
        }
    }

    @Override
    public void hideProgress() {
        if (mLoadingDialog != null) {
            mLoadingDialog.dismiss();
            mLoadingDialog = null;
        }
    }

}

