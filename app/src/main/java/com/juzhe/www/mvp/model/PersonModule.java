package com.juzhe.www.mvp.model;

import android.content.Context;

import com.juzhe.www.api.TaoBaoKeService;
import com.juzhe.www.bean.ExtractModel;
import com.juzhe.www.bean.IncomeDetailModel;
import com.juzhe.www.bean.UserModel;
import com.juzhe.www.bean.VersionModel;
import com.juzhe.www.bean.WithDrawModel;
import com.juzhe.www.common.https.BaseNoDataResponse;
import com.juzhe.www.common.https.BasePageResponse;
import com.juzhe.www.common.https.BaseResponse;
import com.juzhe.www.common.https.RetrofitManager;
import com.juzhe.www.common.https.intercept.InterceptUtils;

import java.util.List;
import java.util.Map;

import io.reactivex.Observable;

/**
 * @package: com.juzhe.www.mvp.model
 * @user:xhkj
 * @date:2018/10/29
 * @description:
 **/
public class PersonModule {
    private static PersonModule musicModel;
    private TaoBaoKeService mApiService;

    public PersonModule(Context context) {
        mApiService = RetrofitManager.getInstance().getRetrofitService(TaoBaoKeService.class);
    }

    public static PersonModule getInstance(Context context) {
        if (musicModel == null) {
            musicModel = new PersonModule(context);
        }
        return musicModel;
    }

    public Observable<BaseResponse<UserModel>> getUserInfo(String user_id, String user_channel_id) {
        Map<String, Object> requestMap = InterceptUtils.getRequstMap();
        requestMap.put("user_id", user_id);
        requestMap.put("user_channel_id", user_channel_id);
        Observable<BaseResponse<UserModel>> detail = mApiService.getUserInfo(requestMap);
        return detail;
    }

    public Observable<BaseResponse<ExtractModel>> userExtract(String user_id, String user_channel_id) {
        Map<String, Object> requestMap = InterceptUtils.getRequstMap();
        requestMap.put("user_id", user_id);
        requestMap.put("user_channel_id", user_channel_id);
        Observable<BaseResponse<ExtractModel>> userExtract = mApiService.userExtract(requestMap);
        return userExtract;
    }

    public Observable<BaseNoDataResponse> userSettingTaobao(String user_id, String user_channel_id) {
        Map<String, Object> requestMap = InterceptUtils.getRequstMap();
        requestMap.put("user_id", user_id);
        requestMap.put("user_channel_id", user_channel_id);
        Observable<BaseNoDataResponse> userSettingTaobao = mApiService.userSettingTaobao(requestMap);
        return userSettingTaobao;
    }

    public Observable<BaseNoDataResponse> userSettingAlipay(String alipay_account, String real_name, String user_id, String user_channel_id) {
        Map<String, Object> requestMap = InterceptUtils.getRequstMap();
        requestMap.put("alipay_account", alipay_account);
        requestMap.put("real_name", real_name);
        requestMap.put("user_id", user_id);
        requestMap.put("user_channel_id", user_channel_id);
        Observable<BaseNoDataResponse> userSettingTaobao = mApiService.userSettingAlipay(requestMap);
        return userSettingTaobao;
    }

    public Observable<BaseResponse<List<ShareInviteTempModel>>> shareInviteTemp(String user_id, String user_channel_id) {
        Map<String, Object> requestMap = InterceptUtils.getRequstMap();
        requestMap.put("user_id", user_id);
        requestMap.put("user_channel_id", user_channel_id);
        Observable<BaseResponse<List<ShareInviteTempModel>>> shareInviteTemp = mApiService.shareInviteTemp(requestMap);
        return shareInviteTemp;
    }

    public Observable<BaseNoDataResponse> userFeedBack(String content, String contact_way, String user_id, String user_channel_id) {
        Map<String, Object> requestMap = InterceptUtils.getRequstMap();
        requestMap.put("content", content);
        requestMap.put("contact_way", contact_way);
        requestMap.put("user_id", user_id);
        requestMap.put("user_channel_id", user_channel_id);
        Observable<BaseNoDataResponse> userFeedBack = mApiService.userFeedBack(requestMap);
        return userFeedBack;
    }

    public Observable<BasePageResponse<List<IncomeDetailModel>>> userBill(String type, int page, String user_id, String user_channel_id) {
        Map<String, Object> requestMap = InterceptUtils.getRequstMap();
        requestMap.put("type", type);
        requestMap.put("page", page);
        requestMap.put("user_id", user_id);
        requestMap.put("user_channel_id", user_channel_id);
        Observable<BasePageResponse<List<IncomeDetailModel>>> userFeedBack = mApiService.userBill(requestMap);
        return userFeedBack;
    }

    public Observable<BaseResponse<VersionModel>> userVersion(String version, String user_id, String user_channel_id) {
        Map<String, Object> requestMap = InterceptUtils.getRequstMap();
        requestMap.put("version", version);
        requestMap.put("user_id", user_id);
        requestMap.put("user_channel_id", user_channel_id);
        Observable<BaseResponse<VersionModel>> userVersion = mApiService.userVersion(requestMap);
        return userVersion;
    }

    public Observable<BaseResponse<WithDrawModel>> withdrawApply(String amount, String type, String user_id, String user_channel_id) {
        Map<String, Object> requestMap = InterceptUtils.getRequstMap();
        requestMap.put("user_id", user_id);
        requestMap.put("user_channel_id", user_channel_id);
        requestMap.put("amount", amount);
        requestMap.put("type", type);
        Observable<BaseResponse<WithDrawModel>> withdrawApply = mApiService.withdrawApply(requestMap);
        return withdrawApply;
    }

    public Observable<BaseNoDataResponse> untyingAlipay(String user_id, String user_channel_id) {
        Map<String, Object> requestMap = InterceptUtils.getRequstMap();
        requestMap.put("user_id", user_id);
        requestMap.put("user_channel_id", user_channel_id);
        Observable<BaseNoDataResponse> untyingAlipay = mApiService.untyingAlipay(requestMap);
        return untyingAlipay;
    }
}
