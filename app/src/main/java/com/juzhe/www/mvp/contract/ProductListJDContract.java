package com.juzhe.www.mvp.contract;

import com.juzhe.www.bean.JDProductModel;
import com.juzhe.www.common.https.BasePresenter;
import com.juzhe.www.common.https.IBaseView;

import java.util.List;

/**
 * @package: com.juzhe.www.mvp.contract
 * @user:xhkj
 * @date:2018/10/29
 * @description:注册
 **/
public interface ProductListJDContract {
    interface View extends IBaseView {
        void showProductList(List<JDProductModel> models, boolean isRefresh);
        void showError(Throwable throwable, boolean isRefresh);
        void setResult(JDProductModel result);
    }

    abstract class Presenter extends BasePresenter<View> {
        public abstract void getProductListOther(String user_id, String user_channel_id, boolean isRefresh);
        public abstract void getJdDetails(String couponmoney,String item_id, String discount_link, String user_id, String user_channel_id);
    }
}
