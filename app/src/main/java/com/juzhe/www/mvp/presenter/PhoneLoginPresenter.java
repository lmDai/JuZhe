package com.juzhe.www.mvp.presenter;

import com.juzhe.www.bean.UserModel;
import com.juzhe.www.common.https.BaseNoDataResponse;
import com.juzhe.www.common.https.ProgressObserver;
import com.juzhe.www.common.https.rxUtils.RxUtil;
import com.juzhe.www.common.utils.Utils;
import com.juzhe.www.mvp.contract.PhoneLoginContract;
import com.juzhe.www.mvp.model.LoginModel;

/**
 * @package: com.juzhe.www.mvp.presenter
 * @user:xhkj
 * @date:2018/10/29
 * @description:
 **/
public class PhoneLoginPresenter extends PhoneLoginContract.Presenter {

    @Override
    public void login(String phone, String code) {
        LoginModel.getInstance(Utils.getContext()).login(phone, code)
                .compose(RxUtil.observableIO2Main(getView()))
                .compose(RxUtil.hanResult())
                .subscribe(new ProgressObserver<UserModel>(this, true, "登录中...") {
                    @Override
                    public void onSuccess(UserModel result) {
                        getView().loginSuccess(result);
                    }
                });
    }
    @Override
    public void sendSmsCode(String phone, int type, String user_channel_id) {
        LoginModel.getInstance(Utils.getContext()).sendSmsCode(phone, type, user_channel_id)
                .compose(RxUtil.observableIO2Main(getView()))
                .compose(RxUtil.handNoResponseResult())
                .subscribe(new ProgressObserver<BaseNoDataResponse>(this, true, "发送中...") {
                    @Override
                    public void onSuccess(BaseNoDataResponse result) {
                        getView().sendCodeSuccess(result);//发送验证码成功
                    }
                });
    }
}
