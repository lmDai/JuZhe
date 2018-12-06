package com.juzhe.www.mvp.presenter;

import com.juzhe.www.bean.UserModel;
import com.juzhe.www.bean.VersionModel;
import com.juzhe.www.common.https.BaseNoDataResponse;
import com.juzhe.www.common.https.ProgressObserver;
import com.juzhe.www.common.https.rxUtils.RxUtil;
import com.juzhe.www.common.utils.Utils;
import com.juzhe.www.mvp.contract.KalManContract;
import com.juzhe.www.mvp.contract.PersonalContract;
import com.juzhe.www.mvp.model.PersonModule;

/**
 * @package: com.juzhe.www.mvp.presenter
 * @user:xhkj
 * @date:2018/10/29
 * @description:
 **/
public class KalManPresenter extends KalManContract.Presenter {

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

    @Override
    public void userKalMan(String card_number, String secret_key, String user_id, String user_channel_id) {
        PersonModule.getInstance(Utils.getContext()).userKalMan(card_number, secret_key, user_id, user_channel_id)
                .compose(RxUtil.observableIO2Main(getView()))
                .compose(RxUtil.handNoResponseResult())
                .subscribe(new ProgressObserver<BaseNoDataResponse>(this, true, "加载中...") {
                    @Override
                    public void onSuccess(BaseNoDataResponse result) {
                        getView().userKalMan(result);
                    }
                });
    }
}
