package com.juzhe.www.mvp.presenter;

import com.juzhe.www.bean.NavModel;
import com.juzhe.www.common.https.ProgressObserver;
import com.juzhe.www.common.https.rxUtils.RxUtil;
import com.juzhe.www.common.utils.Utils;
import com.juzhe.www.mvp.contract.MainContract;
import com.juzhe.www.mvp.model.MainModel;

import java.util.List;

/**
 * @package: com.juzhe.www.mvp.presenter
 * @user:xhkj
 * @date:2018/10/29
 * @description: 主页
 **/
public class MainPresenter extends MainContract.Presenter {


    @Override
    public void getViewNav() {
        MainModel.getInstance(Utils.getContext()).getViewNav()
                .compose(RxUtil.observableIO2Main(getView()))
                .compose(RxUtil.handNoResponseResult())
                .subscribe(new ProgressObserver<List<NavModel>>(this, true, "加载中...") {
                    @Override
                    public void onSuccess(List<NavModel> result) {
                        getView().setViewNav(result);
                    }
                });

    }
}
