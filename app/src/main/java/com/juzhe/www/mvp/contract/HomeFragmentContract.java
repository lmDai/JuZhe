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
public interface HomeFragmentContract {


    interface View extends IBaseView {

        void setOnclick(int position);

        Context getContext();

        void setClassfiy(List<ClassfyModel> classfiy);

        void setAdvert(List<AdvertModel> model);
        void setUserModel(UserModel userModel);
        void setIconPage(List<IconModel> iconPage);
    }


    abstract class Presenter extends BasePresenter<View> {
        public abstract DelegateAdapter initRecyclerView(RecyclerView recyclerView);

        public abstract BaseDelegateAdapter initGvMenu();//初始化九宫格

        public abstract BaseDelegateAdapter initTitle();//初始化标题

        public abstract BaseDelegateAdapter initSearch();//初始化搜索框

        public abstract BaseDelegateAdapter initBanner();//初始化banner

        public abstract BaseDelegateAdapter initFastEntrceTitle();//初始化快速入口

        public abstract BaseDelegateAdapter initFastEntrace();//初始化快速入口内容

        public abstract void getIconClassify();//获取所有标签

        public abstract void getAdvert(String user_id, String user_channel_id);//广告

        public abstract void getIconpage(String user_id, String user_channel_id);
        public abstract void getUserInfo(String user_id, String user_channel_id);
    }


}
