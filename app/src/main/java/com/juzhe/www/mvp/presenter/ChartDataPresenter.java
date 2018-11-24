package com.juzhe.www.mvp.presenter;

import android.support.annotation.NonNull;
import android.util.Log;

import com.juzhe.www.bean.ChartModel;
import com.juzhe.www.common.https.ProgressObserver;
import com.juzhe.www.common.https.rxUtils.RxUtil;
import com.juzhe.www.common.utils.Utils;
import com.juzhe.www.mvp.contract.ChartDataContract;
import com.juzhe.www.mvp.model.TeamModule;

/**
 * @package: com.juzhe.www.mvp.presenter
 * @user:xhkj
 * @date:2018/10/29
 * @description: 会员升级
 **/
public class ChartDataPresenter extends ChartDataContract.Presenter {
    @Override
    public void getUserChart(String user_id, String user_channel_id) {
        TeamModule.getInstance(Utils.getContext()).userChart(user_id, user_channel_id)
                .compose(RxUtil.observableIO2Main(getView()))
                .compose(RxUtil.hanResult())
                .subscribe(new ProgressObserver<ChartModel>(this, true, "加载中...") {
                    @Override
                    public void onSuccess(ChartModel result) {
                        getView().setChartData(result);
                        Log.i("single","ChartModel");
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Log.i("single", "ChartModel"+e.getMessage());
                        super.onError(e);
                        getView().showError(e);
                    }
                });
    }
}
