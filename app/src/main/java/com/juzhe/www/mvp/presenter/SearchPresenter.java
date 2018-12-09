package com.juzhe.www.mvp.presenter;

import android.support.annotation.NonNull;

import com.juzhe.www.bean.JdSearchModel;
import com.juzhe.www.bean.KeyWordModel;
import com.juzhe.www.bean.PddListModel;
import com.juzhe.www.bean.PddPromotionModel;
import com.juzhe.www.common.https.BasePageResponse;
import com.juzhe.www.common.https.ProgressObserver;
import com.juzhe.www.common.https.rxUtils.RxUtil;
import com.juzhe.www.common.utils.Utils;
import com.juzhe.www.mvp.contract.SearchContract;
import com.juzhe.www.mvp.model.MainModel;

import java.util.List;

/**
 * @package: com.juzhe.www.mvp.presenter
 * @user:xhkj
 * @date:2018/10/29
 * @description: 会员升级
 **/
public class SearchPresenter extends SearchContract.Presenter {
    @Override
    public void getHotKeyWord(String user_id, String user_channel_id) {
        MainModel.getInstance(Utils.getContext()).getHotKeyWord(user_id, user_channel_id)
                .compose(RxUtil.observableIO2Main(getView()))
                .compose(RxUtil.hanResult())
                .subscribe(new ProgressObserver<List<KeyWordModel>>(this, true, "加载中...") {
                    @Override
                    public void onSuccess(List<KeyWordModel> result) {
                        getView().setHotKeyWords(result);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        super.onError(e);
                        getView().showError(e);
                    }
                });
    }

    @Override
    public void getJdPddSearch(String type, String keyword, String sort, String user_id, String user_channel_id, int user_level) {
        MainModel.getInstance(Utils.getContext()).getJdPddSearch(type, keyword, sort, "1", user_id, user_channel_id, user_level)
                .compose(RxUtil.observableIO2Main(getView()))
                .compose(RxUtil.hanResult())
                .subscribe(new ProgressObserver<JdSearchModel>(this, true, "加载中...") {
                    @Override
                    public void onSuccess(JdSearchModel result) {
                        getView().setJdPddResult(result);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        super.onError(e);
                    }
                });
    }

}
