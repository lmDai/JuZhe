package com.juzhe.www.mvp.contract;

import com.juzhe.www.bean.UpgradeModel;
import com.juzhe.www.bean.UserModel;
import com.juzhe.www.common.https.BasePresenter;
import com.juzhe.www.common.https.IBaseView;

/**
 * @package: com.juzhe.www.mvp.contract
 * @user:xhkj
 * @date:2018/10/29
 * @description:会员升级
 **/
public interface UpgradeContract {
    interface View extends IBaseView {
        void setUpgrade(UpgradeModel models);

        void showError(Throwable throwable);

        void showPayPage(String page);
        void setUserModel(UserModel userModel);
    }

    abstract class Presenter extends BasePresenter<View> {
        public abstract void getUserUpgrade(String user_id, String user_channel_id);
        public abstract void getUpgradeApply(String user_id,String user_channel_id);
        public abstract void getUpgradePay(String orderId);
        public abstract void getUserInfo(String user_id, String user_channel_id);
    }
}
