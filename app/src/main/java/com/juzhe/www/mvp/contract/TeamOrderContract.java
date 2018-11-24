package com.juzhe.www.mvp.contract;

import com.juzhe.www.bean.TeamOrderModel;
import com.juzhe.www.common.https.BasePresenter;
import com.juzhe.www.common.https.IBaseView;

import java.util.List;

/**
 * @package: com.juzhe.www.mvp.contract
 * @user:xhkj
 * @date:2018/10/29
 * @description:会员团队订单
 **/
public interface TeamOrderContract {
    interface View extends IBaseView {
        void showTeamOrders(List<TeamOrderModel> models, boolean isRefresh);
        void showError(Throwable throwable, boolean isRefresh);
    }

    abstract class Presenter extends BasePresenter<View> {
        public abstract void getTeamOrders(int order_status, int order_type,String user_id, String user_channel_id,
                                            boolean isRefresh);
    }
}
