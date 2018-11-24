package com.juzhe.www.mvp.presenter;

import com.juzhe.www.bean.UserModel;
import com.juzhe.www.bean.VersionModel;
import com.juzhe.www.common.https.BaseNoDataResponse;
import com.juzhe.www.common.https.ProgressObserver;
import com.juzhe.www.common.https.rxUtils.RxUtil;
import com.juzhe.www.common.utils.Utils;
import com.juzhe.www.mvp.contract.PersonalContract;
import com.juzhe.www.mvp.model.PersonModule;

/**
 * @package: com.juzhe.www.mvp.presenter
 * @user:xhkj
 * @date:2018/10/29
 * @description:
 **/
public class PersonalPresenter extends PersonalContract.Presenter {

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
    public void userSettingTaobao(String user_id, String user_channel_id) {
        PersonModule.getInstance(Utils.getContext()).userSettingTaobao(user_id, user_channel_id)
                .compose(RxUtil.observableIO2Main(getView()))
                .compose(RxUtil.handNoResponseResult())
                .subscribe(new ProgressObserver<BaseNoDataResponse>(this, true, "设置中...") {
                    @Override
                    public void onSuccess(BaseNoDataResponse result) {
                        getView().userSettingTaobao(result);
                    }
                });
    }

    @Override
    public void userVersion(String version, String user_id, String user_channel_id) {
        PersonModule.getInstance(Utils.getContext()).userVersion(version, user_id, user_channel_id)
                .compose(RxUtil.observableIO2Main(getView()))
                .compose(RxUtil.hanResult())
                .subscribe(new ProgressObserver<VersionModel>(this, true, "检查更新...") {
                    @Override
                    public void onSuccess(VersionModel result) {
                        getView().setUserVersion(result);
                    }
                });
    }
}
