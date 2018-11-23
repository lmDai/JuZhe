package com.juzhe.www.ui.adapter;

import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.juzhe.www.R;
import com.juzhe.www.bean.PopularProductModel;
import com.juzhe.www.utils.MagicTextViewUtil;

/**
 * @package: com.bestsoft.ui.adapter
 * @user:xhkj
 * @date:2018/11/2
 * @description: 他们都在买
 **/
public class PopularProductAdapter extends BaseQuickAdapter<PopularProductModel, BaseViewHolder> {
    public PopularProductAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder helper, PopularProductModel item) {
        TextView mTextView = helper.getView(R.id.txt_name);
        MagicTextViewUtil.getInstance(mTextView)
                .append(R.drawable.ic_order_tag)
                .append("红色123454541213")
                .show();
    }
}
