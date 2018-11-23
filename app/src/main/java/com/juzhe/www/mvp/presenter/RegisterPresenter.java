package com.juzhe.www.mvp.presenter;

import com.juzhe.www.bean.UserModel;
import com.juzhe.www.common.https.ProgressObserver;
import com.juzhe.www.common.https.rxUtils.RxUtil;
import com.juzhe.www.common.utils.Utils;
import com.juzhe.www.mvp.contract.RegisterContract;
import com.juzhe.www.mvp.model.LoginModel;

/**
 * @package: com.bestsoft.mvp.presenter
 * @user:xhkj
 * @date:2018/10/29
 * @description: 注册
 **/
public class RegisterPresenter extends RegisterContract.Presenter {

    @Override
    public void userRegister(String nickName,String headimgurl,String openid,String type,String phone, String smscode, String user_chanel_id, String pid) {
        LoginModel.getInstance(Utils.getContext()).userRegister(nickName,headimgurl,openid,type,phone, smscode, user_chanel_id, pid)
                .compose(RxUtil.observableIO2Main(getView()))
                .compose(RxUtil.hanResult())
                .subscribe(new ProgressObserver<UserModel>(this, true, "请稍后...") {
                    @Override
                    public void onSuccess(UserModel result) {
                        getView().registerSuccess(result);//注册成功
                    }
                });
    }
}
