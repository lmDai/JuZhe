package com.juzhe.www.mvp.presenter;

import android.support.annotation.NonNull;

import com.juzhe.www.bean.OrderModel;
import com.juzhe.www.common.https.ProgressObserver;
import com.juzhe.www.common.https.rxUtils.RxUtil;
import com.juzhe.www.common.utils.Utils;
import com.juzhe.www.mvp.contract.OrderListContract;
import com.juzhe.www.mvp.model.OrderModule;

import java.util.List;

/**
 * @package: com.juzhe.www.mvp.presenter
 * @user:xhkj
 * @date:2018/10/29
 * @description: 会员订单
 **/
public class OrderListPresenter extends OrderListContract.Presenter {
    private int currentPage = 1;

    @Override
    public void getUserOrder(String order_status, String user_id, String user_channel_id, boolean isRefresh) {
        if (isRefresh) {
            currentPage = 1;
        } else {
            ++currentPage;
        }
        OrderModule.getInstance(Utils.getContext()).userOrder(order_status, currentPage + "",
                user_id, user_channel_id)
                .compose(RxUtil.observableIO2Main(getView()))
                .compose(RxUtil.hanResult())
                .subscribe(new ProgressObserver<List<OrderModel>>(this, true, "请稍后...") {
                    @Override
                    public void onSuccess(List<OrderModel> result) {
                        getView().showOrderList(result, isRefresh);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        super.onError(e);
                        getView().showError(e, isRefresh);
                    }
                });
    }
}
