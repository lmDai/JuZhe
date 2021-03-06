package com.juzhe.www.mvp.presenter;

import android.support.annotation.NonNull;

import com.juzhe.www.bean.PddListModel;
import com.juzhe.www.bean.PddPromotionModel;
import com.juzhe.www.bean.ProductModel;
import com.juzhe.www.common.https.BasePageResponse;
import com.juzhe.www.common.https.ProgressObserver;
import com.juzhe.www.common.https.rxUtils.RxUtil;
import com.juzhe.www.common.utils.Utils;
import com.juzhe.www.mvp.contract.SearchDetailsContract;
import com.juzhe.www.mvp.model.MainModel;

import java.util.List;

/**
 * @package: com.juzhe.www.mvp.presenter
 * @user:xhkj
 * @date:2018/10/29
 * @description: 会员升级
 **/
public class SearchDetailsPresenter extends SearchDetailsContract.Presenter {
    private String currentPage = "1";

    @Override
    public void getGoodsSearch(String keyword, String sort, String user_id, String user_channel_id, int user_level, boolean isRefresh) {
        if (isRefresh) {
            currentPage = "1";
        }
        MainModel.getInstance(Utils.getContext()).getGoodsSearch(keyword, sort, currentPage + "", user_id, user_channel_id, user_level)
                .compose(RxUtil.observableIO2Main(getView()))
                .compose(RxUtil.handNoResponseResult())
                .subscribe(new ProgressObserver<BasePageResponse<List<ProductModel>>>(this, true, "加载中...") {
                    @Override
                    public void onSuccess(BasePageResponse<List<ProductModel>> result) {
                        if (result.getErrorcode() == 0) {
                            currentPage = result.getNext();
                            getView().setProductList(result.getData(), isRefresh);//注册成功
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


    @Override
    public void getProductListOther(String sort, String keyWord, String user_id, String user_channel_id, boolean isRefresh) {
        if (isRefresh) {
            currentPage = "1";
        }
        MainModel.getInstance(Utils.getContext()).getPddList(sort, keyWord, user_id, currentPage,
                user_channel_id)
                .compose(RxUtil.observableIO2Main(getView()))
                .compose(RxUtil.handNoResponseResult())
                .subscribe(new ProgressObserver<BasePageResponse<List<PddListModel>>>(this, true, "加载中...") {
                    @Override
                    public void onSuccess(BasePageResponse<List<PddListModel>> result) {
                        if (result.getErrorcode() == 0) {
                            currentPage = result.getNext();
                            getView().showProductList(result.getData(), isRefresh);
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
