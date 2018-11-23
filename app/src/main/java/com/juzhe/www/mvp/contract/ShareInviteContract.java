package com.juzhe.www.mvp.contract;

import com.juzhe.www.common.https.BasePresenter;
import com.juzhe.www.common.https.IBaseView;
import com.juzhe.www.mvp.model.ShareInviteTempModel;

import java.util.List;

/**
 * @package: com.bestsoft.mvp.contract
 * @user:xhkj
 * @date:2018/10/29
 * @description:登陆
 **/
public interface ShareInviteContract {
    interface View extends IBaseView {

        void shareInviteTemp(List<ShareInviteTempModel> settingResult);
    }

    abstract class Presenter extends BasePresenter<View> {


        public abstract void shareInviteTemp(String user_id, String user_channel_id);

    }
}
