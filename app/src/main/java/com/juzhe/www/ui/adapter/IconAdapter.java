package com.juzhe.www.ui.adapter;

import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.juzhe.www.R;
import com.juzhe.www.bean.IconModel;
import com.juzhe.www.utils.GlideUtil;

/**
 * @package: com.juzhe.www.ui.adapter
 * @user:xhkj
 * @date:2018/11/2
 * @description: 介绍
 **/
public class IconAdapter extends BaseQuickAdapter<IconModel, BaseViewHolder> {
    public IconAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder helper, IconModel item) {
        ImageView imgIcon = helper.getView(R.id.img_icon);
        GlideUtil.loadCirclePic(mContext, item.getIcon(), imgIcon);
        helper.setText(R.id.txt_icon_title, item.getName());
    }
}
