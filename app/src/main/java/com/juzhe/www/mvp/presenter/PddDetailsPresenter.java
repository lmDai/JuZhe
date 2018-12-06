package com.juzhe.www.mvp.presenter;

import android.support.annotation.NonNull;

import com.juzhe.www.bean.PddDetailModel;
import com.juzhe.www.bean.PddPromotionModel;
import com.juzhe.www.common.https.ProgressObserver;
import com.juzhe.www.common.https.rxUtils.RxUtil;
import com.juzhe.www.common.utils.Utils;
import com.juzhe.www.mvp.contract.PddDetailsContract;
import com.juzhe.www.mvp.model.MainModel;


/**
 * @package: com.juzhe.www.mvp.presenter
 * @user:xhkj
 * @date:2018/10/29
 * @description: 注册
 **/
public class PddDetailsPresenter extends PddDetailsContract.Presenter {
    @Override
    public void getPddDetails(String goods_id, String user_chanel_id) {
        MainModel.getInstance(Utils.getContext()).getPddDetails(goods_id, user_chanel_id)
                .compose(RxUtil.observableIO2Main(getView()))
                .compose(RxUtil.hanResult())
                .subscribe(new ProgressObserver<PddDetailModel>(this, true, "加载中...") {
                    @Override
                    public void onSuccess(PddDetailModel result) {
                        getView().setResult(result);//返回数据
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        super.onError(e);
                    }
                });
    }

    //    @Override
//    public void orderConfirm(String item_id, String pic, String item_title, String item_price,
//                             String item_end_price, String tkrates, String tkmoney,
//                             String user_id, String user_channel_id, String couponmoney) {
//        MainModel.getInstance(Utils.getContext()).orderConfirm(item_id, pic, item_title, item_price,
//                item_end_price, tkrates, tkmoney, user_id, user_channel_id, couponmoney)
//                .compose(RxUtil.observableIO2Main(getView()))
//                .compose(RxUtil.hanResult())
//                .subscribe(new ProgressObserver<OrderConfirmModel>(this, true, "加载中...") {
//                    @Override
//                    public void onSuccess(OrderConfirmModel result) {
//                        getView().orderConfirm(result);//返回数据
//                    }
//
//                    @Override
//                    public void onError(@NonNull Throwable e) {
//                        super.onError(e);
//                    }
//                });
//    }
//
    @Override
    public void getPddPromotion(String user_id, String goods_id, String user_channel_id) {
        MainModel.getInstance(Utils.getContext()).getPddPromotion(user_id, goods_id,
                user_channel_id)
                .compose(RxUtil.observableIO2Main(getView()))
                .compose(RxUtil.hanResult())
                .subscribe(new ProgressObserver<PddPromotionModel>(this, true, "加载中...") {
                    @Override
                    public void onSuccess(PddPromotionModel result) {
                        getView().setPromotionInfo(result);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        super.onError(e);
                    }
                });
    }
}
