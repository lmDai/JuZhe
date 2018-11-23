package com.juzhe.www.mvp.presenter;

import com.juzhe.www.bean.UserModel;
import com.juzhe.www.common.https.BaseNoDataResponse;
import com.juzhe.www.common.https.ProgressObserver;
import com.juzhe.www.common.https.rxUtils.RxUtil;
import com.juzhe.www.common.utils.Utils;
import com.juzhe.www.mvp.contract.MyAlipayContract;
import com.juzhe.www.mvp.model.PersonModule;

/**
 * @package: com.bestsoft.mvp.presenter
 * @user:xhkj
 * @date:2018/10/29
 * @description:
 **/
public class MyAlipayPresenter extends MyAlipayContract.Presenter {

    @Override
    public void userSettingAliPay(String account, String name, String user_id, String user_channel_id) {
        PersonModule.getInstance(Utils.getContext()).userSettingAlipay(account, name, user_id, user_channel_id)
                .compose(RxUtil.observableIO2Main(getView()))
                .compose(RxUtil.handNoResponseResult())
                .subscribe(new ProgressObserver<BaseNoDataResponse>(this, true, "设置中...") {
                    @Override
                    public void onSuccess(BaseNoDataResponse result) {
                        getView().userSettingAlipay(result);
                    }
                });
    }

    @Override
    public void untyingAlipay(String user_id, String user_channel_id) {
        PersonModule.getInstance(Utils.getContext()).untyingAlipay(user_id, user_channel_id)
                .compose(RxUtil.observableIO2Main(getView()))
                .compose(RxUtil.handNoResponseResult())
                .subscribe(new ProgressObserver<BaseNoDataResponse>(this, true, "解绑中...") {
                    @Override
                    public void onSuccess(BaseNoDataResponse result) {
                        getView().showUnbindInfo(result);
                    }

                });
    }

    @Override
    public void getUserInfo(String user_id, String user_channel_id) {
        PersonModule.getInstance(Utils.getContext()).getUserInfo(user_id, user_channel_id)
                .compose(RxUtil.observableIO2Main(getView()))
                .compose(RxUtil.hanResult())
                .subscribe(new ProgressObserver<UserModel>(this, true, "加载中...") {
                    @Override
                    public void onSuccess(UserModel result) {
                        getView().setUserModel(result);
                    }

                });
    }
}
