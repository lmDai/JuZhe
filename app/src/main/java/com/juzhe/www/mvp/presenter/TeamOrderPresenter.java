package com.juzhe.www.mvp.presenter;

import android.support.annotation.NonNull;

import com.juzhe.www.bean.TeamOrderModel;
import com.juzhe.www.common.https.ProgressObserver;
import com.juzhe.www.common.https.rxUtils.RxUtil;
import com.juzhe.www.common.utils.Utils;
import com.juzhe.www.mvp.contract.TeamOrderContract;
import com.juzhe.www.mvp.model.TeamModule;

import java.util.List;

/**
 * @package: com.bestsoft.mvp.presenter
 * @user:xhkj
 * @date:2018/10/29
 * @description: 会员团队订单
 **/
public class TeamOrderPresenter extends TeamOrderContract.Presenter {
    private int currentPage = 1;

    @Override
    public void getTeamOrders(int order_status, int order_type, String user_id, String user_channel_id, boolean isRefresh) {
        if (isRefresh) {
            currentPage = 1;
        } else {
            ++currentPage;
        }
        TeamModule.getInstance(Utils.getContext()).userTeamOrder(order_status, order_type, currentPage,
                user_id, user_channel_id)
                .compose(RxUtil.observableIO2Main(getView()))
                .compose(RxUtil.hanResult())
                .subscribe(new ProgressObserver<List<TeamOrderModel>>(this, true, "请稍后...") {
                    @Override
                    public void onSuccess(List<TeamOrderModel> result) {
                        getView().showTeamOrders(result, isRefresh);//注册成功
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        super.onError(e);
                        getView().showError(e, isRefresh);
                    }
                });
    }
}
