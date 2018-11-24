package com.juzhe.www.mvp.presenter;

import com.juzhe.www.bean.ProfitModel;
import com.juzhe.www.common.https.ProgressObserver;
import com.juzhe.www.common.https.rxUtils.RxUtil;
import com.juzhe.www.common.utils.Utils;
import com.juzhe.www.mvp.contract.OrderContract;
import com.juzhe.www.mvp.model.OrderModule;

/**
 * @package: com.juzhe.www.mvp.presenter
 * @user:xhkj
 * @date:2018/10/29
 * @description: 会员收益数据
 **/
public class OrderPresenter extends OrderContract.Presenter {

    @Override
    public void userProfit(String user_id, String user_channel_id) {
        OrderModule.getInstance(Utils.getContext()).userProfit(user_id, user_channel_id)
                .compose(RxUtil.observableIO2Main(getView()))
                .compose(RxUtil.hanResult())
                .subscribe(new ProgressObserver<ProfitModel>(this, true, "加载中...") {
                    @Override
                    public void onSuccess(ProfitModel result) {
                        getView().setUserProfit(result);//会员收益数据
                    }
                });
    }
}
