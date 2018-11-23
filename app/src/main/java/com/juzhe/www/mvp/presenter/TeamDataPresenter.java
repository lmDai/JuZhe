package com.juzhe.www.mvp.presenter;

import android.support.annotation.NonNull;

import com.juzhe.www.bean.TeamProfitModel;
import com.juzhe.www.common.https.ProgressObserver;
import com.juzhe.www.common.https.rxUtils.RxUtil;
import com.juzhe.www.common.utils.Utils;
import com.juzhe.www.mvp.contract.TeamDataContract;
import com.juzhe.www.mvp.model.TeamModule;

/**
 * @package: com.bestsoft.mvp.presenter
 * @user:xhkj
 * @date:2018/10/29
 * @description: 会员升级
 **/
public class TeamDataPresenter extends TeamDataContract.Presenter {
    @Override
    public void getUserTeamProfit(String user_id, String user_channel_id) {
        TeamModule.getInstance(Utils.getContext()).userTeamProfit(user_id, user_channel_id)
                .compose(RxUtil.observableIO2Main(getView()))
                .compose(RxUtil.hanResult())
                .subscribe(new ProgressObserver<TeamProfitModel>(this, true, "请稍后...") {
                    @Override
                    public void onSuccess(TeamProfitModel result) {
                        getView().setTeamData(result);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        super.onError(e);
                        getView().showError(e);
                    }
                });
    }
}
