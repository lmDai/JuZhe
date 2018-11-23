package com.juzhe.www.common.https;

import android.support.annotation.NonNull;

import com.juzhe.www.common.R;
import com.juzhe.www.common.https.exception.ApiException;
import com.juzhe.www.common.https.exception.ExceptionUtil;
import com.juzhe.www.common.utils.Utils;
import com.blankj.utilcode.util.NetworkUtils;
import com.blankj.utilcode.util.ToastUtils;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * @package: com.bestsoft.common.https
 * @user:xhkj
 * @date:2018/10/29
 * @description: 预处理服务器返回数据
 **/
public abstract class BaseObserver<T> implements Observer<T> {
    @Override
    public void onSubscribe(Disposable d) {
        if (!NetworkUtils.isConnected()) {
            ToastUtils.showShort(Utils.getString(R.string.NO_NET_CONNECTED));
//            onComplete();
        }
    }

    @Override
    public final void onNext(T result) {
        onSuccess(result);
    }

    @Override
    public void onError(@NonNull Throwable e) {
        String msg;
        int code = ExceptionUtil.exceptionHandler(e);
        if (e instanceof ApiException) {
            msg = e.getMessage();
        } else {
            msg = ExceptionUtil.getMsg(code);
        }
        onFailure(e, code, msg);
    }

    @Override
    public void onComplete() {

    }

    public abstract void onSuccess(T result);

    public void onFailure(Throwable e, int code, String errorMsg) {
        ToastUtils.showShort(errorMsg);
    }
}
