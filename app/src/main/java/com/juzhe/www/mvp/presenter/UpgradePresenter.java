package com.juzhe.www.mvp.presenter;

import android.support.annotation.NonNull;

import com.juzhe.www.bean.ApplyModel;
import com.juzhe.www.bean.UpgradeModel;
import com.juzhe.www.bean.UserModel;
import com.juzhe.www.common.https.ProgressObserver;
import com.juzhe.www.common.https.rxUtils.RxUtil;
import com.juzhe.www.common.utils.Utils;
import com.juzhe.www.mvp.contract.UpgradeContract;
import com.juzhe.www.mvp.model.MemberModule;
import com.juzhe.www.mvp.model.PersonModule;

import java.io.IOException;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import okhttp3.ResponseBody;

/**
 * @package: com.juzhe.www.mvp.presenter
 * @user:xhkj
 * @date:2018/10/29
 * @description: 会员升级
 **/
public class UpgradePresenter extends UpgradeContract.Presenter {

    @Override
    public void getUserInfo(String user_id, String user_channel_id) {
        PersonModule.getInstance(Utils.getContext()).getUserInfo(user_id, user_channel_id)
                .compose(RxUtil.observableIO2Main(getView()))
                .compose(RxUtil.hanResult())
                .subscribe(new ProgressObserver<UserModel>(this, false, "加载中...") {
                    @Override
                    public void onSuccess(UserModel result) {
                        getView().setUserModel(result);
                    }

                });
    }

    @Override
    public void getUserUpgrade(String user_id, String user_channel_id) {
        MemberModule.getInstance(Utils.getContext()).userUpgrade(user_id, user_channel_id)
                .compose(RxUtil.observableIO2Main(getView()))
                .compose(RxUtil.hanResult())
                .subscribe(new ProgressObserver<UpgradeModel>(this, true, "请稍后...") {
                    @Override
                    public void onSuccess(UpgradeModel result) {
                        getView().setUpgrade(result);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        super.onError(e);
                        getView().showError(e);
                    }
                });
    }

    @Override
    public void getUpgradeApply(String user_id, String user_channel_id) {
        MemberModule.getInstance(Utils.getContext()).upgradeApply(user_id, user_channel_id)
                .compose(RxUtil.observableIO2Main(getView()))
                .compose(RxUtil.hanResult())
                .subscribe(new ProgressObserver<ApplyModel>(this, true, "请稍后...") {
                    @Override
                    public void onSuccess(ApplyModel result) {
                        getView().showPayPage(result.getOrderId());
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        super.onError(e);
                        getView().showError(e);
                    }
                });
    }

    @Override
    public void getUpgradePay(String orderId) {
        MemberModule.getInstance(Utils.getContext(), 1).upgradePay(orderId)
                .compose(RxUtil.observableIO2Main(getView()))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ResponseBody>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(ResponseBody s) {

                        try {
                            getView().showPayPage(s.string());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}
