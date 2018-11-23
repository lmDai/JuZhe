package com.juzhe.www.mvp.presenter;

import com.juzhe.www.common.https.BaseNoDataResponse;
import com.juzhe.www.common.https.ProgressObserver;
import com.juzhe.www.common.https.rxUtils.RxUtil;
import com.juzhe.www.common.utils.Utils;
import com.juzhe.www.mvp.contract.FeedBackContract;
import com.juzhe.www.mvp.model.PersonModule;

/**
 * @package: com.bestsoft.mvp.presenter
 * @user:xhkj
 * @date:2018/10/29
 * @description:
 **/
public class FeedBackPresenter extends FeedBackContract.Presenter {

    @Override
    public void feedBack(String content,String contact,String user_id, String user_channel_id) {
        PersonModule.getInstance(Utils.getContext()).userFeedBack(content,contact,user_id, user_channel_id)
                .compose(RxUtil.observableIO2Main(getView()))
                .compose(RxUtil.handNoResponseResult())
                .subscribe(new ProgressObserver<BaseNoDataResponse>(this, true, "请稍候...") {
                    @Override
                    public void onSuccess(BaseNoDataResponse result) {
                        getView().setFeedBackStatus(result);
                    }
                });
    }
}
