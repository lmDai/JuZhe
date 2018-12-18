package com.juzhe.www.ui.adapter;

import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.blankj.utilcode.util.ScreenUtils;
import com.blankj.utilcode.util.SizeUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.juzhe.www.R;
import com.juzhe.www.bean.ComponentsBean;
import com.juzhe.www.utils.GlideUtil;

import java.util.List;

/**
 * @package: com.juzhe.www.ui.adapter
 * @user:xhkj
 * @date:2018/10/31
 * @description:
 **/
public class Advert5Adapter extends BaseQuickAdapter<ComponentsBean, BaseViewHolder> {


    public Advert5Adapter(@Nullable int resourceId) {
        super(resourceId);
    }

    @Override
    protected void convert(BaseViewHolder helper, ComponentsBean item) {
        LinearLayout linearLayout = helper.getView(R.id.ll_new_seed_item);
        ViewGroup.LayoutParams layoutParams = linearLayout.getLayoutParams();
        layoutParams.width = (ScreenUtils.getScreenWidth() - SizeUtils.dp2px(mContext, 20)) / 3;//
        layoutParams.height = layoutParams.width;
        linearLayout.setLayoutParams(layoutParams);
        GlideUtil.showImageViewLinearLayout(mContext, item.getImage(), linearLayout);
    }
}
