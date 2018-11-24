package com.juzhe.www.mvp.contract;

import com.juzhe.www.common.https.BaseNoDataResponse;
import com.juzhe.www.common.https.BasePresenter;
import com.juzhe.www.common.https.IBaseView;

/**
 * @package: com.juzhe.www.mvp.contract
 * @user:xhkj
 * @date:2018/10/29
 * @description:登陆
 **/
public interface FeedBackContract {
    interface View extends IBaseView {

        void setFeedBackStatus(BaseNoDataResponse settingResult);
    }

    abstract class Presenter extends BasePresenter<View> {


        public abstract void feedBack(String content, String contact, String user_id, String user_channel_id);

    }
}
