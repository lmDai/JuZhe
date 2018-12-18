package com.juzhe.www.mvp.model;

import android.content.Context;

import com.juzhe.www.api.TaoBaoKeService;
import com.juzhe.www.bean.AdvertModel;
import com.juzhe.www.bean.ClassfyModel;
import com.juzhe.www.bean.CutomHomeModel;
import com.juzhe.www.bean.GoodsShareModel;
import com.juzhe.www.bean.IconModel;
import com.juzhe.www.bean.JDProductModel;
import com.juzhe.www.bean.JdSearchModel;
import com.juzhe.www.bean.KeyWordModel;
import com.juzhe.www.bean.NavModel;
import com.juzhe.www.bean.OrderConfirmModel;
import com.juzhe.www.bean.PddDetailModel;
import com.juzhe.www.bean.PddListModel;
import com.juzhe.www.bean.PddPromotionModel;
import com.juzhe.www.bean.ProductModel;
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
public class MainModel {
    private static MainModel musicModel;
    private TaoBaoKeService mApiService;

    public MainModel(Context context) {
        mApiService = RetrofitManager.getInstance().getRetrofitService(TaoBaoKeService.class);
    }

    public static MainModel getInstance(Context context) {
        if (musicModel == null) {
            musicModel = new MainModel(context);
        }
        return musicModel;
    }

    /**
     * 分类列表
     *
     * @param channel_id
     * @return
     */
    public Observable<BaseResponse<List<ClassfyModel>>> getIconClassify(String channel_id) {
        Observable<BaseResponse<List<ClassfyModel>>> classify = mApiService.getIconClassify(channel_id);
        return classify;
    }

    /**
     * 类目商品列表
     *
     * @return
     */
    public Observable<BasePageResponse<List<ProductModel>>> getGoodHaoList(String key, String sort, String page
            , String user_id, String user_channel_id, int user_level) {
        Map<String, Object> requestMap = InterceptUtils.getRequstMap();
        requestMap.put("key", key);
        requestMap.put("sort", sort);
        requestMap.put("page", page);
        requestMap.put("user_id", user_id);
        requestMap.put("user_channel_id", user_channel_id);
        requestMap.put("user_level", user_level);
        Observable<BasePageResponse<List<ProductModel>>> classify = mApiService.getGoodHaoList(requestMap);
        return classify;
    }

    /**
     * 类目商品详情
     *
     * @return
     */
    public Observable<BaseResponse<ProductModel>> getHaoDetail(String item_id, String user_id, String user_channel_id) {
        Map<String, Object> requestMap = InterceptUtils.getRequstMap();
        requestMap.put("item_id", item_id);
        requestMap.put("user_id", user_id);
        requestMap.put("user_channel_id", user_channel_id);
        Observable<BaseResponse<ProductModel>> detail = mApiService.getGoodDetail(requestMap);
        return detail;
    }

    public Observable<BaseResponse<OrderConfirmModel>> orderConfirm(String item_id, String pic, String item_title,
                                                                    String item_price, String item_end_price,
                                                                    String tkrates, String tkmoney,
                                                                    String user_id, String user_channel_id,
                                                                    String couponmoney) {
        Map<String, Object> requestMap = InterceptUtils.getRequstMap();
        requestMap.put("item_id", item_id);
        requestMap.put("item_pic", pic);
        requestMap.put("item_title", item_title);
        requestMap.put("item_price", item_price);
        requestMap.put("item_end_price", item_end_price);
        requestMap.put("tkrates", tkrates);
        requestMap.put("tkmoney", tkmoney);
        requestMap.put("user_id", user_id);
        requestMap.put("user_channel_id", user_channel_id);
        requestMap.put("couponmoney", couponmoney);
        Observable<BaseResponse<OrderConfirmModel>> orderConfirm = mApiService.orderConfirm(requestMap);
        return orderConfirm;
    }

    public Observable<BaseNoDataResponse> orderPayConfirm(String order_id, String third_number,
                                                          String user_id, String user_channel_id) {
        Map<String, Object> requestMap = InterceptUtils.getRequstMap();
        requestMap.put("order_id", order_id);
        requestMap.put("third_number", third_number);
        requestMap.put("user_id", user_id);
        requestMap.put("user_channel_id", user_channel_id);
        Observable<BaseNoDataResponse> orderPayConfirm = mApiService.orderPayConfirm(requestMap);
        return orderPayConfirm;
    }

    /**
     * 商品关键字搜索
     *
     * @return
     */
    public Observable<BasePageResponse<List<ProductModel>>> getGoodsSearch(String keyword, String sort, String page
            , String user_id, String user_channel_id, int user_level) {
        Map<String, Object> requestMap = InterceptUtils.getRequstMap();
        requestMap.put("keyword", keyword);
        requestMap.put("sort", sort);
        requestMap.put("page", page);
        requestMap.put("user_id", user_id);
        requestMap.put("user_channel_id", user_channel_id);
        requestMap.put("user_level", user_level);
        Observable<BasePageResponse<List<ProductModel>>> classify = mApiService.getGoodsSearch(requestMap);
        return classify;
    }

    /**
     * 获取热搜关键字
     *
     * @return
     */
    public Observable<BaseResponse<List<KeyWordModel>>> getHotKeyWord(String user_id, String user_channel_id) {
        Map<String, Object> requestMap = InterceptUtils.getRequstMap();
        requestMap.put("user_id", user_id);
        requestMap.put("user_channel_id", user_channel_id);
        Observable<BaseResponse<List<KeyWordModel>>> classify = mApiService.getHotKeyWord(requestMap);
        return classify;
    }

    public Observable<BaseResponse<List<AdvertModel>>> homeAdavert(String user_id, String user_channel_id) {
        Map<String, Object> requestMap = InterceptUtils.getRequstMap();
        requestMap.put("user_id", user_id);
        requestMap.put("user_channel_id", user_channel_id);
        Observable<BaseResponse<List<AdvertModel>>> homeAdavert = mApiService.homeAdavert(requestMap);
        return homeAdavert;
    }

    public Observable<BaseResponse<List<IconModel>>> homeIconpage(String user_id, String user_channel_id) {
        Map<String, Object> requestMap = InterceptUtils.getRequstMap();
        requestMap.put("user_id", user_id);
        requestMap.put("user_channel_id", user_channel_id);
        Observable<BaseResponse<List<IconModel>>> homeIconpage = mApiService.homeIconpage(requestMap);
        return homeIconpage;
    }

    public Observable<BaseResponse<GoodsShareModel>> goodsShare(String item_id, String user_id, String user_channel_id) {
        Map<String, Object> requestMap = InterceptUtils.getRequstMap();
        requestMap.put("item_id", item_id);
        requestMap.put("user_id", user_id);
        requestMap.put("user_channel_id", user_channel_id);
        Observable<BaseResponse<GoodsShareModel>> goodsShare = mApiService.goodsShare(requestMap);
        return goodsShare;
    }

    public Observable<BasePageResponse<List<JDProductModel>>> getJDList(String page, String user_id, String user_channel_id) {
        Map<String, Object> requestMap = InterceptUtils.getRequstMap();
        requestMap.put("page", page);
        requestMap.put("user_id", user_id);
        requestMap.put("user_channel_id", user_channel_id);
        Observable<BasePageResponse<List<JDProductModel>>> getJDList = mApiService.getJDList(requestMap);
        return getJDList;
    }

    public Observable<BaseResponse<JDProductModel>> getJDDetail(String item_id, String discount_link, String user_id, String user_channel_id) {
        Map<String, Object> requestMap = InterceptUtils.getRequstMap();
        requestMap.put("item_id", item_id);
        requestMap.put("discount_link", discount_link);
        requestMap.put("user_id", user_id);
        requestMap.put("user_channel_id", user_channel_id);
        Observable<BaseResponse<JDProductModel>> getJDDetail = mApiService.getJDDetail(requestMap);
        return getJDDetail;
    }

    public Observable<BaseResponse<JDProductModel>> getJDGaoYong(String couponmoney, String item_id, String discount_link, String user_id, String user_channel_id) {
        Map<String, Object> requestMap = InterceptUtils.getRequstMap();
        requestMap.put("couponmoney", couponmoney);
        requestMap.put("item_id", item_id);
        requestMap.put("discount_link", discount_link);
        requestMap.put("user_id", user_id);
        requestMap.put("user_channel_id", user_channel_id);
        Observable<BaseResponse<JDProductModel>> getJDGaoYong = mApiService.getJDGaoYong(requestMap);
        return getJDGaoYong;
    }

    public Observable<BasePageResponse<List<PddListModel>>> getPddList(String sort, String keyWord, String user_id, String page, String user_channel_id) {
        Map<String, Object> requestMap = InterceptUtils.getRequstMap();
        requestMap.put("sort", sort);
        requestMap.put("user_id", user_id);
        requestMap.put("keyword", keyWord);
        requestMap.put("page", page);
        requestMap.put("user_channel_id", user_channel_id);
        Observable<BasePageResponse<List<PddListModel>>> getPddList = mApiService.getPddList(requestMap);
        return getPddList;
    }

    public Observable<BaseResponse<PddDetailModel>> getPddDetails(String goods_id, String user_channel_id) {
        Map<String, Object> requestMap = InterceptUtils.getRequstMap();
        requestMap.put("goods_id", goods_id);
        requestMap.put("user_channel_id", user_channel_id);
        Observable<BaseResponse<PddDetailModel>> getPddDetails = mApiService.getPddDetails(requestMap);
        return getPddDetails;
    }

    public Observable<BaseResponse<PddPromotionModel>> getPddPromotion(String user_id, String goods_id, String user_channel_id) {
        Map<String, Object> requestMap = InterceptUtils.getRequstMap();
        requestMap.put("user_id", user_id);
        requestMap.put("goods_id", goods_id);
        requestMap.put("user_channel_id", user_channel_id);
        Observable<BaseResponse<PddPromotionModel>> getPddPromotion = mApiService.getPddPromotion(requestMap);
        return getPddPromotion;
    }

    /**
     * 商品关键字搜索
     *
     * @return
     */
    public Observable<BaseResponse<JdSearchModel>> getJdPddSearch(String type, String keyword, String sort, String page
            , String user_id, String user_channel_id, int user_level) {
        Map<String, Object> requestMap = InterceptUtils.getRequstMap();
        requestMap.put("keyword", keyword);
        requestMap.put("type", type);
        requestMap.put("sort", sort);
        requestMap.put("page", page);
        requestMap.put("user_id", user_id);
        requestMap.put("user_channel_id", user_channel_id);
        requestMap.put("user_level", user_level);
        Observable<BaseResponse<JdSearchModel>> getJdPddSearch = mApiService.getJdPddSearch(requestMap);
        return getJdPddSearch;
    }


    /**
     * 首页定制化
     *
     * @return
     */
    public Observable<CutomHomeModel> getViewIndex() {
        Observable<CutomHomeModel> getViewIndex = mApiService.getViewIndex();
        return getViewIndex;
    }

    public Observable<List<NavModel>> getViewNav() {
        Observable<List<NavModel>> getViewNav = mApiService.getViewNav();
        return getViewNav;
    }
}
