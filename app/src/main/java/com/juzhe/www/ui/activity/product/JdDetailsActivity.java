package com.juzhe.www.ui.activity.product;

import android.os.Bundle;
import android.os.Handler;
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
import com.juzhe.www.bean.JDProductModel;
import com.juzhe.www.bean.OrderConfirmModel;
import com.juzhe.www.bean.UserModel;
import com.juzhe.www.common.mvp_senior.annotaions.CreatePresenterAnnotation;
import com.juzhe.www.mvp.contract.JdDetailsContract;
import com.juzhe.www.mvp.presenter.JdDetailsPresenter;
import com.juzhe.www.ui.activity.MemberActivity;
import com.juzhe.www.utils.GlideUtil;
import com.juzhe.www.utils.IntentUtils;
import com.juzhe.www.utils.UserUtils;
import com.kepler.jd.Listener.OpenAppAction;
import com.kepler.jd.login.KeplerApiManager;
import com.kepler.jd.sdk.bean.KelperTask;
import com.kepler.jd.sdk.bean.KeplerAttachParameter;
import com.kepler.jd.sdk.exception.KeplerBufferOverflowException;
import com.youth.banner.Banner;

import org.json.JSONException;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 商品详情
 */
@CreatePresenterAnnotation(JdDetailsPresenter.class)
public class JdDetailsActivity extends BaseMvpActivity<JdDetailsContract.View, JdDetailsPresenter> implements JdDetailsContract.View {
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
    private String itemId, discount_link;
    private JDProductModel result;
    private int type = 0;//0立即领券，1 立即购买
    private OrderConfirmModel orderConfirmModel;
    private UserModel userModel;
    private JDProductModel jdProductModel;
    Handler mHandler;
    KelperTask mKelperTask;
    public static final int timeOut = 15;

    @Override
    protected int getLayout() {
        return R.layout.activity_jd_details;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        txtTitle.setText("商品详情");
        mHandler = new Handler();
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
            jdProductModel = bundle.getParcelable("item");
        }
        if (jdProductModel != null) {
            itemId = jdProductModel.getItem_id();
            discount_link = jdProductModel.getDiscount_link();
        }
        userModel = UserUtils.getUser(mContext);
        getMvpPresenter().getJdDetails(itemId, discount_link, userModel.getId(), userModel.getUser_channel_id());
        if (userModel.getLevel() == 1) {
            txtUpgrade.setVisibility(View.GONE);
        } else {
            txtUpgrade.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void setResult(JDProductModel result) {
        this.result = result;
//        banner.setBannerStyle(BannerConfig.NUM_INDICATOR);
//        banner.setImages(result.getGoods_gallery_urls())
//                .setImageLoader(new GlideImageLoader())
//                .start();
//        txtEndPrice.setText("¥" + result.getItem_end_price());//商品券后价格
//        txtUpgrade.setText("赚¥" + result.getUpgrade());
//        txtSale.setText("已售" + result.getItem_sale() + "件");
//        txtMoney.setText("原价：" + result.getItem_price());
//        txtTkmoney.setText("现在升级VIP最高可得佣金¥" + result.getCore_commission());
        txtTitleProduct.setText(result.getItem_title());
//        txtCouponmoney.setText(result.getCouponmoney() + "元优惠券");
//        txtTime.setText("使用时间:" + result.getCouponstarttime() + "-" + result.getCouponendtime());
//        txtSellerNick.setText(result.getSellernick());
        GlideUtil.intoLongImagView(mContext, result.getItem_pic_copy(), imgPic);
//        txtUpgrade.setText("赚¥" + result.getCommission());
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
                openJd();
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

    KeplerAttachParameter mKeplerAttachParameter = new KeplerAttachParameter();//这个是即时性参数  可以设置


    OpenAppAction mOpenAppAction = new OpenAppAction() {
        @Override
        public void onStatus(final int status) {
            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    if (status == OpenAppAction.OpenAppAction_start) {//开始状态未必一定执行，

                    } else {
                        mKelperTask = null;

                    }
                }
            });
        }
    };

    /**
     * 打开京东领取优惠券
     */
    private void openJd() {
        try {
            mKelperTask = KeplerApiManager.getWebViewService().openJDUrlPage(result.getLink(), mKeplerAttachParameter, this, mOpenAppAction, timeOut);
        } catch (KeplerBufferOverflowException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
