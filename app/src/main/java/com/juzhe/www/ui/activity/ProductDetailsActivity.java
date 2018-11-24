package com.juzhe.www.ui.activity;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.baichuan.android.trade.AlibcTrade;
import com.alibaba.baichuan.android.trade.callback.AlibcTradeCallback;
import com.alibaba.baichuan.android.trade.constants.AlibcConstants;
import com.alibaba.baichuan.android.trade.model.AlibcShowParams;
import com.alibaba.baichuan.android.trade.model.AlibcTaokeParams;
import com.alibaba.baichuan.android.trade.model.OpenType;
import com.alibaba.baichuan.android.trade.model.TradeResult;
import com.alibaba.baichuan.android.trade.page.AlibcBasePage;
import com.alibaba.baichuan.android.trade.page.AlibcPage;
import com.blankj.utilcode.util.ToastUtils;
import com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView;
import com.juzhe.www.R;
import com.juzhe.www.base.BaseMvpActivity;
import com.juzhe.www.bean.OrderConfirmModel;
import com.juzhe.www.bean.ProductModel;
import com.juzhe.www.bean.UserModel;
import com.juzhe.www.common.https.BaseNoDataResponse;
import com.juzhe.www.common.mvp_senior.annotaions.CreatePresenterAnnotation;
import com.juzhe.www.mvp.contract.ProductDetailsContract;
import com.juzhe.www.mvp.presenter.ProductDetailsPresenter;
import com.juzhe.www.ui.widget.GlideImageLoader;
import com.juzhe.www.utils.GlideUtil;
import com.juzhe.www.utils.IntentUtils;
import com.juzhe.www.utils.UserUtils;
import com.youth.banner.Banner;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 商品详情
 */
@CreatePresenterAnnotation(ProductDetailsPresenter.class)
public class ProductDetailsActivity extends BaseMvpActivity<ProductDetailsContract.View, ProductDetailsPresenter> implements ProductDetailsContract.View {
    @BindView(R.id.banner)
    Banner banner;
    @BindView(R.id.txt_confirm)
    TextView txtConfirm;
    @BindView(R.id.img_back)
    ImageView imgBack;
    @BindView(R.id.txt_title)
    TextView txtTitle;
    @BindView(R.id.txt_right)
    TextView txtRight;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.txt_end_price)
    TextView txtEndPrice;
    @BindView(R.id.txt_upgrade)
    TextView txtUpgrade;
    @BindView(R.id.txt_sale)
    TextView txtSale;
    @BindView(R.id.txt_money)
    TextView txtMoney;
    @BindView(R.id.txt_tkmoney)
    TextView txtTkmoney;
    @BindView(R.id.txt_couponmoney)
    TextView txtCouponmoney;
    @BindView(R.id.txt_time)
    TextView txtTime;
    @BindView(R.id.txt_seller_nick)
    TextView txtSellerNick;
    @BindView(R.id.img_pic)
    SubsamplingScaleImageView imgPic;
    @BindView(R.id.txt_title_product)
    TextView txtTitleProduct;
    @BindView(R.id.btn_buy)
    Button btnBuy;
    @BindView(R.id.btn_share)
    Button btnShare;
    private String itemId;
    private ProductModel result;
    private int type = 0;//0立即领券，1 立即购买
    private OrderConfirmModel orderConfirmModel;
    private UserModel userModel;

    @Override
    protected int getLayout() {
        return R.layout.activity_product_details;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        txtTitle.setText("商品详情");

    }

    @Override
    protected void initImmersionBar() {
        super.initImmersionBar();
        mImmersionBar.titleBar(R.id.toolbar)
                .statusBarColor(R.color.colorWhite)
                .statusBarDarkFont(true, 0.2f)
                .init();
    }

    @Override
    protected void getIntentData() {
        super.getIntentData();
        Bundle bundle = getIntent().getBundleExtra("bundle");
        if (bundle != null) {
            itemId = bundle.getString("item_id");
        }
        userModel=UserUtils.getUser(mContext);
        getMvpPresenter().getHaoDetail(itemId, userModel.getId(), userModel.getUser_channel_id());
        if (userModel.getLevel() == 1) {
            txtUpgrade.setVisibility(View.GONE);
        } else {
            txtUpgrade.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void setResult(ProductModel result) {
        this.result = result;
        banner.setImages(result.getItem_pic())
                .setImageLoader(new GlideImageLoader())
                .start();
        txtEndPrice.setText("¥" + result.getItem_end_price());//商品券后价格
        txtUpgrade.setText("赚¥" + result.getUpgrade());
        txtSale.setText("已售" + result.getItem_sale() + "件");
        txtMoney.setText("原价：" + result.getItem_price());
        txtTkmoney.setText("现在升级VIP最高可得佣金¥" + result.getTkmoney());
        txtTitleProduct.setText(result.getItem_title());
        txtCouponmoney.setText(result.getCouponmoney() + "元优惠券");
        txtTime.setText("使用时间:" + result.getCouponstarttime() + "-" + result.getCouponendtime());
        txtSellerNick.setText(result.getSellernick());
        GlideUtil.intoLongImagView(mContext, result.getItem_pic_copy(), imgPic);
        txtUpgrade.setText("赚¥" + result.getCommission());
    }

    @Override
    public void orderConfirm(OrderConfirmModel orderConfirmModel) {
        this.orderConfirmModel = orderConfirmModel;
        openTaoBao();
    }

    @Override
    public void orderPayConfirm(BaseNoDataResponse response) {
        ToastUtils.showShort(response.getMsg());
    }

    public void openTaoBao() {
        //提供给三方传递配置参数
        if (result != null) {
            Map<String, String> exParams = new HashMap<>();
            exParams.put(AlibcConstants.ISV_CODE, "appisvcode");
            AlibcBasePage alibcBasePage = null;
            alibcBasePage = new AlibcPage(result.getCouponurl());
            //设置页面打开方式
            AlibcShowParams showParams = new AlibcShowParams(OpenType.Native, false);
            AlibcTaokeParams taokeParams = new AlibcTaokeParams(userModel.getTaobao_pid(), userModel.getTaobao_pid(), null);
            //使用百川sdk提供默认的Activity打开detail
            AlibcTrade.show(mContext, alibcBasePage, showParams, taokeParams, exParams,
                    new AlibcTradeCallback() {
                        @Override
                        public void onTradeSuccess(TradeResult tradeResult) {
                            //打开电商组件，用户操作中成功信息回调。tradeResult：成功信息（结果类型：加购，支付；支付结果）
                            if (orderConfirmModel != null) {
                                getMvpPresenter().oderPayConfirm(orderConfirmModel.getOrder_id(), String.valueOf(tradeResult.payResult.paySuccessOrders), userModel.getId(), userModel.getUser_channel_id());
                            }
                        }

                        @Override
                        public void onFailure(int code, String msg) {
                            //打开电商组件，用户操作中错误信息回调。code：错误码；msg：错误信息
                            Log.i("single", "onFailure" + msg + code);
                        }
                    });
        }
    }

    @OnClick({R.id.img_back, R.id.txt_confirm, R.id.btn_buy, R.id.btn_share})
    public void onViewClicked(View view) {
        String pic = result.getItem_pic() == null ? "" : result.getItem_pic().get(0);
        switch (view.getId()) {
            case R.id.img_back:
                finish();
                break;
            case R.id.txt_confirm:
//                type = 0;
//                openTaoBao();

                getMvpPresenter().orderConfirm(result.getItem_id(), pic, result.getItem_title(), result.getItem_price()
                        , result.getItem_end_price(), result.getTkrates(), result.getTkmoney() + "", userModel.getId(), userModel.getUser_channel_id()
                        , result.getCouponmoney());
                break;
            case R.id.btn_buy:
//                type = 1;
                getMvpPresenter().orderConfirm(result.getItem_id(), pic, result.getItem_title(), result.getItem_price()
                        , result.getItem_end_price(), result.getTkrates(), result.getTkmoney() + "", userModel.getId(), userModel.getUser_channel_id()
                        , result.getCouponmoney());
                break;
            case R.id.btn_share:

                Bundle bundle = new Bundle();
                bundle.putString("item_id", itemId);
                IntentUtils.get().goActivity(mContext, ProductDetails2Activity.class, bundle);
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
