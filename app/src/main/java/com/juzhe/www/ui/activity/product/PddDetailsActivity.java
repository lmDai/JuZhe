package com.juzhe.www.ui.activity.product;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.util.ActivityUtils;
import com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView;
import com.juzhe.www.MainActivity;
import com.juzhe.www.R;
import com.juzhe.www.base.BaseMvpActivity;
import com.juzhe.www.bean.PddDetailModel;
import com.juzhe.www.bean.PddPromotionModel;
import com.juzhe.www.bean.UserModel;
import com.juzhe.www.common.mvp_senior.annotaions.CreatePresenterAnnotation;
import com.juzhe.www.mvp.contract.PddDetailsContract;
import com.juzhe.www.mvp.presenter.PddDetailsPresenter;
import com.juzhe.www.ui.activity.MemberActivity;
import com.juzhe.www.ui.widget.GlideImageLoader;
import com.juzhe.www.utils.GlideUtil;
import com.juzhe.www.utils.IntentUtils;
import com.juzhe.www.utils.UserUtils;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 商品详情
 */
@CreatePresenterAnnotation(PddDetailsPresenter.class)
public class PddDetailsActivity extends BaseMvpActivity<PddDetailsContract.View, PddDetailsPresenter> implements PddDetailsContract.View {
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
    private PddDetailModel result;
    private UserModel userModel;
    @Override
    protected int getLayout() {
        return R.layout.activity_pdd_details;
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
        userModel = UserUtils.getUser(mContext);
        getMvpPresenter().getPddDetails(itemId, userModel.getUser_channel_id());
        if (userModel.getLevel() == 1) {
            txtUpgrade.setVisibility(View.GONE);
        } else {
            txtUpgrade.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void setResult(PddDetailModel result) {
        this.result = result;
        banner.setBannerStyle(BannerConfig.NUM_INDICATOR);
        banner.setImages(result.getGoods_gallery_urls())
                .setImageLoader(new GlideImageLoader())
                .start();
//        txtEndPrice.setText("¥" + result.getItem_end_price());//商品券后价格
//        txtUpgrade.setText("赚¥" + result.getUpgrade());
        txtSale.setText("已售" + result.getSold_quantity() + "件");
//        txtMoney.setText("原价：" + result.getItem_price());
//        txtTkmoney.setText("现在升级VIP最高可得佣金¥" + result.getCore_commission());
        txtTitleProduct.setText(result.getGoods_desc());
        txtCouponmoney.setText(result.getCoupon_discount() + "元优惠券");
//        txtTime.setText("使用时间:" + result.getCouponstarttime() + "-" + result.getCouponendtime());
//        txtSellerNick.setText(result.getSellernick());
        GlideUtil.intoLongImagView(mContext, result.getGoods_image_url(), imgPic);
//        txtUpgrade.setText("赚¥" + result.getCommission());
    }

    @Override
    public void setPromotionInfo(PddPromotionModel response) {
        boolean hasInstalled = IntentUtils.checkHasInstalledApp(mContext, "com.xunmeng.pinduoduo");
        if (hasInstalled) {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(response.getMobile_url()));
            startActivity(intent);
        } else {
            Bundle bundle = new Bundle();
            bundle.putString("link", response.getWe_app_web_view_url());
            IntentUtils.get().goActivity(mContext, WebViewPddDetailsActivity.class, bundle);
        }

    }


    @OnClick({R.id.img_back, R.id.txt_confirm, R.id.btn_buy, R.id.btn_share, R.id.ll_home, R.id.txt_member})
    public void onViewClicked(View view) {
//        String pic = result.getItem_pic() == null ? "" : result.getItem_pic().get(0);
        switch (view.getId()) {
            case R.id.img_back:
                finish();
                break;
            case R.id.txt_confirm:
//                type = 0;
//                openTaoBao();
                getMvpPresenter().getPddPromotion(userModel.getId(), result.getGoods_id() + "", userModel.getUser_channel_id());
                break;
            case R.id.btn_buy:
//                type = 1;
//                getMvpPresenter().orderConfirm(result.getItem_id(), pic, result.getItem_title(), result.getItem_price()
//                        , result.getItem_end_price(), result.getTkrates(), result.getTkmoney() + "", userModel.getId(), userModel.getUser_channel_id()
//                        , result.getCouponmoney());
                break;
            case R.id.btn_share:
                Bundle bundle = new Bundle();
                bundle.putString("item_id", itemId);
                IntentUtils.get().goActivity(mContext, ProductDetails2Activity.class, bundle);
                break;
            case R.id.ll_home:
                IntentUtils.get().goActivityKill(mContext, MainActivity.class);
                break;
            case R.id.txt_member:
                ActivityUtils.startActivity(MemberActivity.class);
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
