package com.juzhe.www.mvp.contract;

import com.juzhe.www.bean.UserModel;
import com.juzhe.www.common.https.BaseNoDataResponse;
import com.juzhe.www.common.https.BasePresenter;
import com.juzhe.www.common.https.IBaseView;

/**
 * @package: com.juzhe.www.mvp.contract
 * @user:xhkj
 * @date:2018/10/29
 * @description:登陆
 **/
public interface PhoneLoginContract {
    interface View extends IBaseView {
        void sendCodeSuccess(BaseNoDataResponse result);//验证码发送成功

        void loginSuccess(UserModel userModel);
    }

    abstract class Presenter extends BasePresenter<View> {
        public abstract void login(String phone, String code);

        public abstract void sendSmsCode(String phone, int type, String user_channel_id);
    }
}
