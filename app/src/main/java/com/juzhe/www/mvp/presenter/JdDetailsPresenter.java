package com.juzhe.www.mvp.presenter;

import android.support.annotation.NonNull;

import com.juzhe.www.bean.JDProductModel;
import com.juzhe.www.common.https.ProgressObserver;
import com.juzhe.www.common.https.rxUtils.RxUtil;
import com.juzhe.www.common.utils.Utils;
import com.juzhe.www.mvp.contract.JdDetailsContract;
import com.juzhe.www.mvp.model.MainModel;


/**
 * @package: com.juzhe.www.mvp.presenter
 * @user:xhkj
 * @date:2018/10/29
 * @description: 注册
 **/
public class JdDetailsPresenter extends JdDetailsContract.Presenter {
    @Override
    public void getJdDetails(String item_id, String discount_link, String user_id, String user_channel_id) {
        MainModel.getInstance(Utils.getContext()).getJDDetail(item_id, discount_link, user_id, user_channel_id)
                .compose(RxUtil.observableIO2Main(getView()))
                .compose(RxUtil.hanResult())
                .subscribe(new ProgressObserver<JDProductModel>(this, true, "加载中...") {
                    @Override
                    public void onSuccess(JDProductModel result) {
                        getView().setResult(result);//返回数据
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        super.onError(e);
                    }
                });
    }
}
