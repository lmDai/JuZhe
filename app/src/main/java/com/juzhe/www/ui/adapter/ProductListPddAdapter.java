package com.juzhe.www.ui.adapter;

import android.graphics.Paint;
import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.util.SizeUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.juzhe.www.R;
import com.juzhe.www.bean.PddListModel;
import com.juzhe.www.utils.GlideUtil;

/**
 * @package: com.juzhe.www.ui.adapter
 * @user:xhkj
 * @date:2018/11/2
 * @description: 商品列表
 **/
public class ProductListPddAdapter extends BaseQuickAdapter<PddListModel, BaseViewHolder> {

    public ProductListPddAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder helper, PddListModel item) {
        ImageView imgProduct = helper.getView(R.id.img_pic);
        GlideUtil.loadRoundImage(mContext, item.getThumb_url(), SizeUtils.px2dp(mContext, 4), imgProduct);
        TextView txtPrice = helper.getView(R.id.txt_price);
        TextView txtEstimate = helper.getView(R.id.txt_estimate);
        TextView txtUpgrade = helper.getView(R.id.txt_upgrade);
        txtPrice.setText("¥" + item.getPrice());
        txtPrice.setPaintFlags(txtPrice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        helper.setText(R.id.txt_resource, item.getSource())
                .setText(R.id.txt_title, item.getGoods_name())
                .setText(R.id.txt_end_price, item.getItem_end_price() + "")
                .setText(R.id.txt_sale, item.getSold_quantity() + "人已买")
                .setText(R.id.txt_couponmoney, item.getCoupon_discount() + "元优惠券");
        txtEstimate.setText("预计赚¥" + item.getEstimate());
        txtUpgrade.setText("升级赚¥" + item.getUpgrade());
    }
}
