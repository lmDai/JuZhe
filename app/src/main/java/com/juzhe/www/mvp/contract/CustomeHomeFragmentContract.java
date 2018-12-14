package com.juzhe.www.mvp.contract;


import android.content.Context;
import android.support.v7.widget.RecyclerView;

import com.alibaba.android.vlayout.DelegateAdapter;
import com.juzhe.www.bean.AdvertModel;
import com.juzhe.www.bean.ClassfyModel;
import com.juzhe.www.bean.IconModel;
import com.juzhe.www.bean.UserModel;
import com.juzhe.www.common.https.BasePresenter;
import com.juzhe.www.common.https.IBaseView;
import com.juzhe.www.ui.adapter.BaseDelegateAdapter;

import java.util.List;

/**
 * 首页
 */
public interface CustomeHomeFragmentContract {


    interface View extends IBaseView {
        Context getContext();

        void setUserModel(UserModel userModel);

        void setClassfiy(List<ClassfyModel> classfiy);

        void setHomeDelegateAdapter(List<DelegateAdapter.Adapter> homeDelegateAdapter);

    }


    abstract class Presenter extends BasePresenter<View> {
        public abstract DelegateAdapter initRecyclerView(RecyclerView recyclerView);

        public abstract void getIconClassify();//获取所有标签

        public abstract void getCutomData();

        public abstract void getUserInfo(String user_id, String user_channel_id);
    }


}
