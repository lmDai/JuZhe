package com.juzhe.www.mvp.contract;

import com.juzhe.www.bean.ArticleModel;
import com.juzhe.www.common.https.BasePresenter;
import com.juzhe.www.common.https.IBaseView;

import java.util.List;

/**
 * @package: com.juzhe.www.mvp.contract
 * @user:xhkj
 * @date:2018/10/29
 * @description:注册
 **/
public interface ShareAticleContract {
    interface View extends IBaseView {
        void showAticleList(List<ArticleModel> models, boolean isRefresh);

        void showError(Throwable throwable, boolean isRefresh);
    }

    abstract class Presenter extends BasePresenter<View> {
        public abstract void shareAticle(int type, String user_id, String user_channel_id,
                                         boolean isRefresh);
    }
}
