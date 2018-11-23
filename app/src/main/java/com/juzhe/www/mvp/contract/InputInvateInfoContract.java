package com.juzhe.www.mvp.contract;

import com.juzhe.www.bean.CodeModel;
import com.juzhe.www.common.https.BaseNoDataResponse;
import com.juzhe.www.common.https.BasePresenter;
import com.juzhe.www.common.https.IBaseView;

/**
 * @package: com.bestsoft.mvp.contract
 * @user:xhkj
 * @date:2018/10/29
 * @description:获取邀请人信息
 **/
public interface InputInvateInfoContract {
    interface View extends IBaseView {
        void setCodeInfo(CodeModel codeInfo);

        void sendCodeSuccess(BaseNoDataResponse result);//验证码发送成功

    }

    abstract class Presenter extends BasePresenter<View> {
        public abstract void getInvateInfo(String invite_code);

        public abstract void sendSmsCode(String phone, int type, String user_channel_id);


    }
}
