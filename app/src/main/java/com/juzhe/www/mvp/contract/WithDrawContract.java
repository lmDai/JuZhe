package com.juzhe.www.mvp.contract;

import com.juzhe.www.bean.UserModel;
import com.juzhe.www.bean.WithDrawModel;
import com.juzhe.www.common.https.BasePresenter;
import com.juzhe.www.common.https.IBaseView;

/**
 * @package: com.bestsoft.mvp.contract
 * @user:xhkj
 * @date:2018/10/29
 * @description:登陆
 **/
public interface WithDrawContract {
    interface View extends IBaseView {
        void setUserModel(UserModel userModel);

        void withDrawApply(WithDrawModel settingResult);

    }

    abstract class Presenter extends BasePresenter<View> {
        public abstract void getUserInfo(String user_id, String user_channel_id);
        public abstract void withDrawApply(String amount,String type,String user_id,String user_channel_id);
    }
}
