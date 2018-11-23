package com.juzhe.www.mvp.contract;

import com.juzhe.www.bean.GoodsShareModel;
import com.juzhe.www.common.https.BasePresenter;
import com.juzhe.www.common.https.IBaseView;


/**
 * @package: com.bestsoft.mvp.contract
 * @user:xhkj
 * @date:2018/10/29
 * @description:注册
 **/
public interface GoodsShareContract {
    interface View extends IBaseView {
        void setResult(GoodsShareModel result);

    }

    abstract class Presenter extends BasePresenter<View> {
        public abstract void goodsShare(String item_id, String user_id, String user_chanel_id);
    }
}
