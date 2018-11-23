package com.juzhe.www.api;

import com.juzhe.www.bean.AdvertModel;
import com.juzhe.www.bean.ApplyModel;
import com.juzhe.www.bean.ArticleModel;
import com.juzhe.www.bean.ChartModel;
import com.juzhe.www.bean.ClassfyModel;
import com.juzhe.www.bean.CodeModel;
import com.juzhe.www.bean.ExtractModel;
import com.juzhe.www.bean.GoodsShareModel;
import com.juzhe.www.bean.IconModel;
import com.juzhe.www.bean.IncomeDetailModel;
import com.juzhe.www.bean.KeyWordModel;
import com.juzhe.www.bean.OrderConfirmModel;
import com.juzhe.www.bean.OrderModel;
import com.juzhe.www.bean.ProductModel;
import com.juzhe.www.bean.ProfitModel;
import com.juzhe.www.bean.TeamOrderModel;
import com.juzhe.www.bean.TeamProfitModel;
import com.juzhe.www.bean.ThirdLoginModel;
import com.juzhe.www.bean.UpgradeModel;
import com.juzhe.www.bean.UserModel;
import com.juzhe.www.bean.VersionModel;
import com.juzhe.www.bean.WithDrawModel;
import com.juzhe.www.common.https.BaseNoDataResponse;
import com.juzhe.www.common.https.BasePageResponse;
import com.juzhe.www.common.https.BaseResponse;
import com.juzhe.www.mvp.model.ShareInviteTempModel;

import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;
import retrofit2.http.Streaming;
import retrofit2.http.Url;

/**
 * @package: com.bestsoft.api
 * @user:xhkj
 * @date:2018/10/29
 * @description:
 **/
public interface TaoBaoKeService {
    //全部标签
    @POST(TaoBaoKeApi.TAG)
    Observable<BaseResponse<List<String>>> getTagList();

    //登录
    @FormUrlEncoded
    @POST(TaoBaoKeApi.LOGIN)
    Observable<BaseResponse<UserModel>> login(@FieldMap Map<String, Object> requestMap);

    //全部标签
    @POST(TaoBaoKeApi.TAG)
    @FormUrlEncoded
    Observable<BaseResponse<List<ClassfyModel>>> getIconClassify(@Field("channel_id") String channel_id);

    //邀请码验证
    @POST(TaoBaoKeApi.CODE)
    @FormUrlEncoded
    Observable<BaseResponse<CodeModel>> validateInviteCode(@FieldMap Map<String, Object> map);

    //发送短信验证码
    @POST(TaoBaoKeApi.SEND_SMS_CODE)
    @FormUrlEncoded
    Observable<BaseNoDataResponse> sendSmsCode(@FieldMap Map<String, Object> map);

    //会员注册
    @POST(TaoBaoKeApi.USER_REGISTER)
    @FormUrlEncoded
    Observable<BaseResponse<UserModel>> userRegister(@FieldMap Map<String, Object> map);

    //类目商品列表
    @POST(TaoBaoKeApi.HAO_LIST)
    @FormUrlEncoded
    Observable<BasePageResponse<List<ProductModel>>> getGoodHaoList(@FieldMap Map<String, Object> map);

    //商品关键字
    @POST(TaoBaoKeApi.HAO_SEARCH)
    @FormUrlEncoded
    Observable<BasePageResponse<List<ProductModel>>> getGoodsSearch(@FieldMap Map<String, Object> map);

    //获取热搜关键词
    @POST(TaoBaoKeApi.HOT_KEYWORD)
    @FormUrlEncoded
    Observable<BaseResponse<List<KeyWordModel>>> getHotKeyWord(@FieldMap Map<String, Object> map);

    //商品详情
    @POST(TaoBaoKeApi.HAO_DETAIL)
    @FormUrlEncoded
    Observable<BaseResponse<ProductModel>> getGoodDetail(@FieldMap Map<String, Object> map);

    //会员信息
    @POST(TaoBaoKeApi.USER_INFO)
    @FormUrlEncoded
    Observable<BaseResponse<UserModel>> getUserInfo(@FieldMap Map<String, Object> map);

    //下单
    @FormUrlEncoded
    @POST(TaoBaoKeApi.ORDER_CONFIRM)
    Observable<BaseResponse<OrderConfirmModel>> orderConfirm(@FieldMap Map<String, Object> map);

    //下单
    @FormUrlEncoded
    @POST(TaoBaoKeApi.ORDER_PAY_CONFIRM)
    Observable<BaseNoDataResponse> orderPayConfirm(@FieldMap Map<String, Object> map);

    //会员收益数据
    @FormUrlEncoded
    @POST(TaoBaoKeApi.USER_PROFIT)
    Observable<BaseResponse<ProfitModel>> userProfit(@FieldMap Map<String, Object> map);

    //会员订单数据
    @FormUrlEncoded
    @POST(TaoBaoKeApi.USER_ORDER)
    Observable<BaseResponse<List<OrderModel>>> userOrder(@FieldMap Map<String, Object> map);

    //会员订单数据
    @FormUrlEncoded
    @POST(TaoBaoKeApi.USER_UPGRADE)
    Observable<BaseResponse<UpgradeModel>> userUpgrade(@FieldMap Map<String, Object> map);

    //团队收益数据
    @FormUrlEncoded
    @POST(TaoBaoKeApi.TEAM_PROFIT)
    Observable<BaseResponse<TeamProfitModel>> userTeamProfit(@FieldMap Map<String, Object> map);

    //会员团队订单
    @FormUrlEncoded
    @POST(TaoBaoKeApi.TEAM_ORDER)
    Observable<BaseResponse<List<TeamOrderModel>>> userTeamOrder(@FieldMap Map<String, Object> map);

    //会员团队收益图表
    @FormUrlEncoded
    @POST(TaoBaoKeApi.USER_CHART)
    Observable<BaseResponse<ChartModel>> userChart(@FieldMap Map<String, Object> map);

    //首页广告
    @FormUrlEncoded
    @POST(TaoBaoKeApi.ADVERT_HOME)
    Observable<BaseResponse<List<AdvertModel>>> homeAdavert(@FieldMap Map<String, Object> map);

    //首页图标
    @FormUrlEncoded
    @POST(TaoBaoKeApi.ICON_PAGE)
    Observable<BaseResponse<List<IconModel>>> homeIconpage(@FieldMap Map<String, Object> map);

    //分享文章列表
    @FormUrlEncoded
    @POST(TaoBaoKeApi.SHARE_ARTICLE)
    Observable<BaseResponse<List<ArticleModel>>> shareAticle(@FieldMap Map<String, Object> map);

    //会员提现页面数据
    @FormUrlEncoded
    @POST(TaoBaoKeApi.USER_EXTRACT)
    Observable<BaseResponse<ExtractModel>> userExtract(@FieldMap Map<String, Object> map);

    //设置淘宝授权
    @POST(TaoBaoKeApi.USER_SETTING_TAOBAO)
    @FormUrlEncoded
    Observable<BaseNoDataResponse> userSettingTaobao(@FieldMap Map<String, Object> map);

    //设置淘宝授权
    @POST(TaoBaoKeApi.USER_SETTING_ALIPAY)
    @FormUrlEncoded
    Observable<BaseNoDataResponse> userSettingAlipay(@FieldMap Map<String, Object> map);

    //邀请分享图片
    @POST(TaoBaoKeApi.SHARE_INVITE_TEMP)
    @FormUrlEncoded
    Observable<BaseResponse<List<ShareInviteTempModel>>> shareInviteTemp(@FieldMap Map<String, Object> map);

    //会员反馈
    @POST(TaoBaoKeApi.USER_FEED_BACK)
    @FormUrlEncoded
    Observable<BaseNoDataResponse> userFeedBack(@FieldMap Map<String, Object> map);

    //会员明细列表
    @POST(TaoBaoKeApi.USER_BILL)
    @FormUrlEncoded
    Observable<BasePageResponse<List<IncomeDetailModel>>> userBill(@FieldMap Map<String, Object> map);

    //版本更新信息
    @POST(TaoBaoKeApi.USER_VERSION)
    @FormUrlEncoded
    Observable<BaseResponse<VersionModel>> userVersion(@FieldMap Map<String, Object> map);

    @Streaming
    @GET
    Observable<ResponseBody> executeDownload(@Header("Range") String range, @Url() String url);

    //版本更新信息
    @POST(TaoBaoKeApi.UPGRADE_APPLY)
    @FormUrlEncoded
    Observable<BaseResponse<ApplyModel>> upgradeApply(@FieldMap Map<String, Object> map);

    @GET(TaoBaoKeApi.UPGRADE_PAY)
    Observable<ResponseBody> upgradePay(@Query("orderId") String orderId);

    //第三方登录
    @POST(TaoBaoKeApi.THIRD_LOGIN)
    @FormUrlEncoded
    Observable<ThirdLoginModel> thirdLogin(@FieldMap Map<String, Object> map);

    //会员提现申请
    @POST(TaoBaoKeApi.WITHDRAW_APPLY)
    @FormUrlEncoded
    Observable<BaseResponse<WithDrawModel>> withdrawApply(@FieldMap Map<String, Object> map);

    //商品分享
    @POST(TaoBaoKeApi.GOODS_SHARE)
    @FormUrlEncoded
    Observable<BaseResponse<GoodsShareModel>> goodsShare(@FieldMap Map<String, Object> map);

    //商品分享
    @POST(TaoBaoKeApi.BIND_THIRD)
    @FormUrlEncoded
    Observable<BaseResponse<String>> bindThrid(@FieldMap Map<String, Object> map);

    //解绑支付宝
    @POST(TaoBaoKeApi.UNTYING_ALIPAY)
    @FormUrlEncoded
    Observable<BaseNoDataResponse> untyingAlipay(@FieldMap Map<String, Object> map);
}
