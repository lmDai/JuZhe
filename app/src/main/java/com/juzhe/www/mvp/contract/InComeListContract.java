package com.juzhe.www.mvp.contract;

import com.juzhe.www.bean.IncomeDetailModel;
import com.juzhe.www.common.https.BasePresenter;
import com.juzhe.www.common.https.IBaseView;

import java.util.List;

/**
 * @package: com.bestsoft.mvp.contract
 * @user:xhkj
 * @date:2018/10/29
 * @description:注册
 **/
public interface InComeListContract {
    interface View extends IBaseView {
        void showUserBillList(List<IncomeDetailModel> models, String total, boolean isRefresh);

        void showError(Throwable throwable, boolean isRefresh);
    }

    abstract class Presenter extends BasePresenter<View> {
        public abstract void getUserBills(String type, String user_id, String user_channel_id,
                                          boolean isRefresh);
    }
}
