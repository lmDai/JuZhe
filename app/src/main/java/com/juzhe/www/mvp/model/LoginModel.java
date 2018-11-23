package com.juzhe.www.mvp.model;

import android.content.Context;

import com.juzhe.www.api.TaoBaoKeService;
import com.juzhe.www.bean.CodeModel;
import com.juzhe.www.bean.ThirdLoginModel;
import com.juzhe.www.bean.UserModel;
import com.juzhe.www.common.https.BaseNoDataResponse;
import com.juzhe.www.common.https.BaseResponse;
import com.juzhe.www.common.https.RetrofitManager;
import com.juzhe.www.common.https.intercept.InterceptUtils;

import java.util.List;
import java.util.Map;

import io.reactivex.Observable;

/**
 * @package: com.bestsoft.mvp.model
 * @user:xhkj
 * @date:2018/10/29
 * @description:
 **/
public class LoginModel {
    private static LoginModel musicModel;
    private TaoBaoKeService mApiService;

    public LoginModel(Context context) {
        mApiService = RetrofitManager.getInstance().getRetrofitService(TaoBaoKeService.class);
    }

    public static LoginModel getInstance(Context context) {
        if (musicModel == null) {
            musicModel = new LoginModel(context);
        }
        return musicModel;
    }

    public Observable<BaseResponse<List<String>>> getTag() {
        Observable<BaseResponse<List<String>>> book = mApiService.getTagList();
        return book;
    }

    public Observable<BaseResponse<CodeModel>> validateInviteCode(String inviteCode) {
        Map<String, Object> requestMap = InterceptUtils.getRequstMap();
        requestMap.put("invite_code", inviteCode);
        Observable<BaseResponse<CodeModel>> codeInfo = mApiService.validateInviteCode(requestMap);
        return codeInfo;
    }

    public Observable<BaseNoDataResponse> sendSmsCode(String phone, int type, String user_channel_id) {
        Map<String, Object> requestMap = InterceptUtils.getRequstMap();
        requestMap.put("phone", phone);
        requestMap.put("type", type);
        requestMap.put("user_channel_id", user_channel_id);
        Observable<BaseNoDataResponse> codeInfo = mApiService.sendSmsCode(requestMap);
        return codeInfo;
    }

    public Observable<BaseResponse<UserModel>> userRegister(String nickName,String headimgurl,String openid,String type,String phone, String smscode, String user_chanel_id, String pid) {
        Map<String, Object> requestMap = InterceptUtils.getRequstMap();
        requestMap.put("phone", phone);
        requestMap.put("smscode", smscode);
        requestMap.put("user_channel_id", user_chanel_id);
        requestMap.put("nickname", nickName);
        requestMap.put("headimgurl", headimgurl);
        requestMap.put("openid", openid);
        requestMap.put("type", type);
        Observable<BaseResponse<UserModel>> register = mApiService.userRegister(requestMap);
        return register;
    }

    public Observable<BaseResponse<UserModel>> login(String phone, String smscode) {
        Map<String, Object> requestMap = InterceptUtils.getRequstMap();
        requestMap.put("phone", phone);
        requestMap.put("smscode", smscode);
        Observable<BaseResponse<UserModel>> loinModel = mApiService.login(requestMap);
        return loinModel;
    }

    public Observable<ThirdLoginModel> thirdLogin(String type, String openid, String user_id, String user_channel_id) {
        Map<String, Object> requestMap = InterceptUtils.getRequstMap();
        requestMap.put("type", type);
        requestMap.put("openid", openid);
        requestMap.put("user_id", user_id);
        requestMap.put("user_channel_id", user_channel_id);
        Observable<ThirdLoginModel> thirdLogin = mApiService.thirdLogin(requestMap);
        return thirdLogin;
    }
    public Observable<BaseResponse<String>> bindThird(String nickname, String headimgurl,String type,String openid, String user_id, String user_channel_id) {
        Map<String, Object> requestMap = InterceptUtils.getRequstMap();
        requestMap.put("nickname", nickname);
        requestMap.put("headimgurl", headimgurl);
        requestMap.put("type", type);
        requestMap.put("openid", openid);
        requestMap.put("user_id", user_id);
        requestMap.put("user_channel_id", user_channel_id);
        Observable<BaseResponse<String>> thirdLogin = mApiService.bindThrid(requestMap);
        return thirdLogin;
    }
}
