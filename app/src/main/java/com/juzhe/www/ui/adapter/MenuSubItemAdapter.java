package com.juzhe.www.ui.adapter;

import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.juzhe.www.R;
import com.juzhe.www.bean.CutomHomeModel;
import com.juzhe.www.bean.IconModel;
import com.juzhe.www.bean.SubItemBean;
import com.juzhe.www.utils.GlideUtil;

/**
 * @package: com.juzhe.www.ui.adapter
 * @user:xhkj
 * @date:2018/11/2
 * @description: 介绍
 **/
public class MenuSubItemAdapter extends BaseQuickAdapter<SubItemBean, BaseViewHolder> {
    public MenuSubItemAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder helper, SubItemBean item) {
        ImageView imgIcon = helper.getView(R.id.img_icon);
        GlideUtil.loadCirclePic(mContext, item.getImage(), imgIcon);
        helper.setText(R.id.txt_icon_title, item.getTitle());
    }
}
