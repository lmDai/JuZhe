package com.juzhe.www.mvp.contract;

import com.juzhe.www.bean.ExtractModel;
import com.juzhe.www.common.https.BasePresenter;
import com.juzhe.www.common.https.IBaseView;

/**
 * @package: com.juzhe.www.mvp.contract
 * @user:xhkj
 * @date:2018/10/29
 * @description:
 **/
public interface ExtractContract {
    interface View extends IBaseView {
        void setUserExtract(ExtractModel models);

        void showError(Throwable throwable);
    }

    abstract class Presenter extends BasePresenter<View> {
        public abstract void getUserExtract(String user_id, String user_channel_id);
    }
}
