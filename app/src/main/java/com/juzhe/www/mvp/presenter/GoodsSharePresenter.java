package com.juzhe.www.mvp.presenter;

import android.support.annotation.NonNull;

import com.juzhe.www.bean.GoodsShareModel;
import com.juzhe.www.common.https.ProgressObserver;
import com.juzhe.www.common.https.rxUtils.RxUtil;
import com.juzhe.www.common.utils.Utils;
import com.juzhe.www.mvp.contract.GoodsShareContract;
import com.juzhe.www.mvp.model.MainModel;


/**
 * @package: com.juzhe.www.mvp.presenter
 * @user:xhkj
 * @date:2018/10/29
 * @description: 注册
 **/
public class GoodsSharePresenter extends GoodsShareContract.Presenter {

    @Override
    public void goodsShare(String item_id, String user_id, String user_chanel_id) {
        MainModel.getInstance(Utils.getContext()).goodsShare(item_id, user_id, user_chanel_id)
                .compose(RxUtil.observableIO2Main(getView()))
                .compose(RxUtil.hanResult())
                .subscribe(new ProgressObserver<GoodsShareModel>(this, true, "加载中...") {
                    @Override
                    public void onSuccess(GoodsShareModel result) {
                        getView().setResult(result);//返回数据
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        super.onError(e);
                    }
                });
    }
}
