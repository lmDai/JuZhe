package com.juzhe.www.mvp.presenter;

import com.juzhe.www.bean.CodeModel;
import com.juzhe.www.common.https.BaseNoDataResponse;
import com.juzhe.www.common.https.ProgressObserver;
import com.juzhe.www.common.https.rxUtils.RxUtil;
import com.juzhe.www.common.utils.Utils;
import com.juzhe.www.mvp.contract.InputInvateInfoContract;
import com.juzhe.www.mvp.model.LoginModel;

/**
 * @package: com.bestsoft.mvp.presenter
 * @user:xhkj
 * @date:2018/10/29
 * @description: 邀请码验证
 **/
public class InputInvateInfoPresenter extends InputInvateInfoContract.Presenter {
    @Override
    public void getInvateInfo(String invite_code) {
        LoginModel.getInstance(Utils.getContext()).validateInviteCode(invite_code)
                .compose(RxUtil.observableIO2Main(getView()))
                .compose(RxUtil.hanResult())
                .subscribe(new ProgressObserver<CodeModel>(this, true, "加载中...") {
                    @Override
                    public void onSuccess(CodeModel result) {
                        getView().setCodeInfo(result);
                    }
                });
    }

    @Override
    public void sendSmsCode(String phone, int type, String user_channel_id) {
        LoginModel.getInstance(Utils.getContext()).sendSmsCode(phone, type, user_channel_id)
                .compose(RxUtil.observableIO2Main(getView()))
                .compose(RxUtil.handNoResponseResult())
                .subscribe(new ProgressObserver<BaseNoDataResponse>(this, true, "发送中...") {
                    @Override
                    public void onSuccess(BaseNoDataResponse result) {
                        getView().sendCodeSuccess(result);//发送验证码成功
                    }
                });
    }
}
