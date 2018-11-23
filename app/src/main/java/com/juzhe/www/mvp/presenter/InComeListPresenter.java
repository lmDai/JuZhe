package com.juzhe.www.mvp.presenter;

import android.support.annotation.NonNull;

import com.juzhe.www.bean.IncomeDetailModel;
import com.juzhe.www.common.https.BasePageResponse;
import com.juzhe.www.common.https.ProgressObserver;
import com.juzhe.www.common.https.rxUtils.RxUtil;
import com.juzhe.www.common.utils.Utils;
import com.juzhe.www.mvp.contract.InComeListContract;
import com.juzhe.www.mvp.model.PersonModule;

import java.util.List;

/**
 * @package: com.bestsoft.mvp.presenter
 * @user:xhkj
 * @date:2018/10/29
 * @description: 注册
 **/
public class InComeListPresenter extends InComeListContract.Presenter {
    private int currentPage = 1;

    @Override
    public void getUserBills(String type, String user_id, String user_channel_id, boolean isRefresh) {
        if (isRefresh) {
            currentPage = 1;
        } else {
            ++currentPage;
        }
        PersonModule.getInstance(Utils.getContext()).userBill(type, currentPage,
                user_id, user_channel_id)
                .compose(RxUtil.observableIO2Main(getView()))
                .compose(RxUtil.handNoResponseResult())
                .subscribe(new ProgressObserver<BasePageResponse<List<IncomeDetailModel>>>(this, true, "请稍后...") {
                    @Override
                    public void onSuccess(BasePageResponse<List<IncomeDetailModel>> result) {
                        if (result.getErrorcode() == 0) {
                            getView().showUserBillList(result.getData(), result.getTotal_income(), isRefresh);//注册成功
                        } else {
                            getView().showError(new Throwable(), isRefresh);
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        super.onError(e);
                        getView().showError(e, isRefresh);
                    }
                });
    }
}
