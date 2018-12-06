package com.juzhe.www.mvp.contract;

import com.juzhe.www.bean.PddDetailModel;
import com.juzhe.www.bean.PddPromotionModel;
import com.juzhe.www.common.https.BasePresenter;
import com.juzhe.www.common.https.IBaseView;


/**
 * @package: com.juzhe.www.mvp.contract
 * @user:xhkj
 * @date:2018/10/29
 * @description:注册
 **/
public interface PddDetailsContract {
    interface View extends IBaseView {
        void setResult(PddDetailModel result);

//        void orderConfirm(OrderConfirmModel orderConfirmModel);
//
        void setPromotionInfo(PddPromotionModel response);
    }

    abstract class Presenter extends BasePresenter<View> {
        public abstract void getPddDetails(String goods_id,  String user_chanel_id);

//        public abstract void orderConfirm(String item_id, String item_title,String pic,
//                                          String item_price, String item_end_price,
//                                          String tkrates, String tkmoney,
//                                          String user_id, String user_channel_id, String couponmoney);
//
        public abstract void getPddPromotion(String pid, String goods_id,  String user_channel_id);
    }
}
