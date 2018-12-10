package com.juzhe.www.mvp.presenter;

import android.support.annotation.NonNull;

import com.blankj.utilcode.util.ToastUtils;
import com.juzhe.www.bean.JDProductModel;
import com.juzhe.www.common.https.BasePageResponse;
import com.juzhe.www.common.https.ProgressObserver;
import com.juzhe.www.common.https.rxUtils.RxUtil;
import com.juzhe.www.common.utils.Utils;
import com.juzhe.www.mvp.contract.ProductListJDContract;
import com.juzhe.www.mvp.model.MainModel;

import java.util.List;

/**
 * @package: com.juzhe.www.mvp.presenter
 * @user:xhkj
 * @date:2018/10/29
 * @description: 注册
 **/
public class ProductListJDPresenter extends ProductListJDContract.Presenter {
    private String currentPage = "1";

    @Override
    public void getProductListOther(String user_id, String user_channel_id, boolean isRefresh) {
        if (isRefresh) {
            currentPage = "1";
        }
        MainModel.getInstance(Utils.getContext()).getJDList(currentPage,
                user_id, user_channel_id)
                .compose(RxUtil.observableIO2Main(getView()))
                .compose(RxUtil.handNoResponseResult())
                .subscribe(new ProgressObserver<BasePageResponse<List<JDProductModel>>>(this, true, "加载中...") {
                    @Override
                    public void onSuccess(BasePageResponse<List<JDProductModel>> result) {
                        if (result.getErrorcode() == 0) {
                            currentPage = result.getNext();
                            getView().showProductList(result.getData(), isRefresh);//注册成功
                        } else {
                            ToastUtils.showShort(result.getMsg());
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
    public void getJdDetails(String couponmoney, String item_id, String discount_link, String user_id, String user_channel_id) {
        MainModel.getInstance(Utils.getContext()).getJDGaoYong(couponmoney, item_id, discount_link, user_id, user_channel_id)
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
