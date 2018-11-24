package com.juzhe.www.ui.adapter;

import android.support.v4.content.ContextCompat;
import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.util.SizeUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.juzhe.www.R;
import com.juzhe.www.bean.TeamOrderModel;
import com.juzhe.www.utils.GlideUtil;
import com.juzhe.www.utils.MagicTextViewUtil;

/**
 * @package: com.juzhe.www.ui.adapter
 * @user:xhkj
 * @date:2018/11/2
 * @description: 团队数据列表
 **/
public class TeamDataListAdapter extends BaseQuickAdapter<TeamOrderModel, BaseViewHolder> {

    public TeamDataListAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder helper, TeamOrderModel item) {
        TextView mTextView = helper.getView(R.id.txt_title);
        ImageView imgPic = helper.getView(R.id.img_pic);
        GlideUtil.loadRoundImage(mContext, item.getItem_pic(), SizeUtils.px2dp(mContext, 4), imgPic);
        MagicTextViewUtil.getInstance(mTextView)
                .append(R.drawable.ic_order_tag)
                .append(item.getItem_title())
                .show();
        TextView mtxt_order_status = helper.getView(R.id.txt_order_status);
        switch (item.getOrder_status()) {
            case 2:
                mtxt_order_status.setBackground(ContextCompat.getDrawable(mContext, R.drawable.bg_round_green));
                break;
            case 5:
                mtxt_order_status.setBackground(ContextCompat.getDrawable(mContext, R.drawable.bg_round_gray));
                break;
            case 4:
                mtxt_order_status.setBackground(ContextCompat.getDrawable(mContext, R.drawable.bg_round_orange));
                break;

        }
        helper.setText(R.id.txt_time, item.getCreate_at() + " 创建")
                .setText(R.id.txt_commission, item.getCommission())
                .setText(R.id.txt_payment_amount, "¥" + item.getPayment_amount())
                .setText(R.id.txt_order_status, item.getOrder_status_name());

    }
}
