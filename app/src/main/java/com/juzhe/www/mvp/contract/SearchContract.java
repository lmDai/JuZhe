package com.juzhe.www.mvp.contract;

import com.juzhe.www.bean.JdSearchModel;
import com.juzhe.www.bean.KeyWordModel;
import com.juzhe.www.bean.PddListModel;
import com.juzhe.www.bean.PddPromotionModel;
import com.juzhe.www.common.https.BasePresenter;
import com.juzhe.www.common.https.IBaseView;

import java.util.List;

/**
 * @package: com.juzhe.www.mvp.contract
 * @user:xhkj
 * @date:2018/10/29
 * @description:会员升级
 **/
public interface SearchContract {
    interface View extends IBaseView {
        void setHotKeyWords(List<KeyWordModel> models);

        void showError(Throwable throwable);

        void setJdPddResult(JdSearchModel result);


    }

    abstract class Presenter extends BasePresenter<View> {
        public abstract void getHotKeyWord(String user_id, String user_channel_id);

        public abstract void getJdPddSearch(String type, String keyword, String sort
                , String user_id, String user_channel_id, int user_level);


    }
}
