package com.juzhe.www.mvp.contract;

import com.juzhe.www.bean.ProfitModel;
import com.juzhe.www.common.https.BasePresenter;
import com.juzhe.www.common.https.IBaseView;

/**
 * @package: com.bestsoft.mvp.contract
 * @user:xhkj
 * @date:2018/10/29
 * @description:注册
 **/
public interface OrderContract {
    interface View extends IBaseView {
        void setUserProfit(ProfitModel result);
    }

    abstract class Presenter extends BasePresenter<View> {
        public abstract void userProfit(String user_id, String user_channel_id);
    }
}
