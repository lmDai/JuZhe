package com.juzhe.www.mvp.contract;

import com.juzhe.www.bean.PddListModel;
import com.juzhe.www.bean.PddPromotionModel;
import com.juzhe.www.common.https.BasePresenter;
import com.juzhe.www.common.https.IBaseView;

import java.util.List;

/**
 * @package: com.juzhe.www.mvp.contract
 * @user:xhkj
 * @date:2018/10/29
 * @description:注册
 **/
public interface ProductListPddContract {
    interface View extends IBaseView {
        void showProductList(List<PddListModel> models, boolean isRefresh);
        void showError(Throwable throwable, boolean isRefresh);
        void setPromotionInfo(PddPromotionModel response);
    }

    abstract class Presenter extends BasePresenter<View> {
        public abstract void getProductListOther(String user_id,String user_channel_id, boolean isRefresh);
        public abstract void getPddPromotion(String pid, String goods_id,  String user_channel_id);
    }
}
