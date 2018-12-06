package com.juzhe.www.mvp.contract;

import com.juzhe.www.bean.JDProductModel;
import com.juzhe.www.common.https.BasePresenter;
import com.juzhe.www.common.https.IBaseView;


/**
 * @package: com.juzhe.www.mvp.contract
 * @user:xhkj
 * @date:2018/10/29
 * @description:注册
 **/
public interface JdDetailsContract {
    interface View extends IBaseView {
        void setResult(JDProductModel result);
    }

    abstract class Presenter extends BasePresenter<View> {
        public abstract void getJdDetails(String item_id, String discount_link, String user_id, String user_channel_id);

    }
}
