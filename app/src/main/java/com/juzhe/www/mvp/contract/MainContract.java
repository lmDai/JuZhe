package com.juzhe.www.mvp.contract;

import com.juzhe.www.bean.NavModel;
import com.juzhe.www.common.https.BasePresenter;
import com.juzhe.www.common.https.IBaseView;

import java.util.List;

/**
 * @package: com.juzhe.www.mvp.contract
 * @user:xhkj
 * @date:2018/10/29
 * @description:主页
 **/
public interface MainContract {
    interface View extends IBaseView {
        void setViewNav(List<NavModel> models);

    }

    abstract class Presenter extends BasePresenter<View> {
        public abstract void getViewNav();
    }
}
