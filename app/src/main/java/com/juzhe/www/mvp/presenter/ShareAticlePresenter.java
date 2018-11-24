package com.juzhe.www.mvp.presenter;

import android.support.annotation.NonNull;

import com.juzhe.www.bean.ArticleModel;
import com.juzhe.www.common.https.ProgressObserver;
import com.juzhe.www.common.https.rxUtils.RxUtil;
import com.juzhe.www.common.utils.Utils;
import com.juzhe.www.mvp.contract.ShareAticleContract;
import com.juzhe.www.mvp.model.SkillModule;

import java.util.List;

/**
 * @package: com.juzhe.www.mvp.presenter
 * @user:xhkj
 * @date:2018/10/29
 * @description: 注册
 **/

public class ShareAticlePresenter extends ShareAticleContract.Presenter {
    private int currentPage = 1;

    @Override
    public void shareAticle(int type, String user_id, String user_channel_id, boolean isRefresh) {
        if (isRefresh) {
            currentPage = 1;
        } else {
            ++currentPage;
        }
        SkillModule.getInstance(Utils.getContext()).shareAticle(type, currentPage,
                user_id, user_channel_id)
                .compose(RxUtil.observableIO2Main(getView()))
                .compose(RxUtil.hanResult())
                .subscribe(new ProgressObserver<List<ArticleModel>>(this, true, "加载中...") {
                    @Override
                    public void onSuccess(List<ArticleModel> result) {
                            getView().showAticleList(result, isRefresh);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        super.onError(e);
                        getView().showError(e, isRefresh);
                    }
                });
    }
}
