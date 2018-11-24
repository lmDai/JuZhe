package com.juzhe.www.mvp.presenter;

import com.juzhe.www.bean.ThirdLoginModel;
import com.juzhe.www.bean.UserModel;
import com.juzhe.www.common.https.ProgressObserver;
import com.juzhe.www.common.https.rxUtils.RxUtil;
import com.juzhe.www.common.utils.Utils;
import com.juzhe.www.mvp.contract.LoginContract;
import com.juzhe.www.mvp.model.LoginModel;

/**
 * @package: com.bestsoft.mvp.presenter
 * @user:xhkj
 * @date:2018/10/29
 * @description:
 **/
public class LoginPresenter extends LoginContract.Presenter {

    @Override
    public void thirdLogin(String type, String openid, String user_id, String user_channel_id) {
        LoginModel.getInstance(Utils.getContext()).thirdLogin(type, openid, user_id, user_channel_id)
                .compose(RxUtil.observableIO2Main(getView()))
                .compose(RxUtil.handNoResponseResult())
                .subscribe(new ProgressObserver<ThirdLoginModel<UserModel>>(this, true, "登录中...") {
                    @Override
                    public void onSuccess(ThirdLoginModel<UserModel> result) {
                        getView().loginSuccess(result);
                    }
                });
    }
}
