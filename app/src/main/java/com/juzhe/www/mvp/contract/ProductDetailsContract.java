package com.juzhe.www.mvp.contract;

import com.juzhe.www.bean.OrderConfirmModel;
import com.juzhe.www.bean.ProductModel;
import com.juzhe.www.common.https.BaseNoDataResponse;
import com.juzhe.www.common.https.BasePresenter;
import com.juzhe.www.common.https.IBaseView;


/**
 * @package: com.juzhe.www.mvp.contract
 * @user:xhkj
 * @date:2018/10/29
 * @description:注册
 **/
public interface ProductDetailsContract {
    interface View extends IBaseView {
        void setResult(ProductModel result);

        void orderConfirm(OrderConfirmModel orderConfirmModel);

        void orderPayConfirm(BaseNoDataResponse response);
    }

    abstract class Presenter extends BasePresenter<View> {
        public abstract void getHaoDetail(String item_id, String user_id, String user_chanel_id);

        public abstract void orderConfirm(String item_id, String item_title,String pic,
                                          String item_price, String item_end_price,
                                          String tkrates, String tkmoney,
                                          String user_id, String user_channel_id, String couponmoney);

        public abstract void oderPayConfirm(String order_id, String third_number, String user_id, String user_channel_id);
    }
}
