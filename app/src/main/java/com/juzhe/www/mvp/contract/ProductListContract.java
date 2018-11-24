package com.juzhe.www.mvp.contract;

import com.juzhe.www.bean.ProductModel;
import com.juzhe.www.common.https.BasePresenter;
import com.juzhe.www.common.https.IBaseView;

import java.util.List;

/**
 * @package: com.juzhe.www.mvp.contract
 * @user:xhkj
 * @date:2018/10/29
 * @description:注册
 **/
public interface ProductListContract {
    interface View extends IBaseView {
        void showProductList(List<ProductModel> models, boolean isRefresh);
        void showError(Throwable throwable, boolean isRefresh);
    }

    abstract class Presenter extends BasePresenter<View> {
        public abstract void getGoodHaoList(String key, String sort,String user_id, String user_channel_id,int user_level,
                                            boolean isRefresh);
    }
}
