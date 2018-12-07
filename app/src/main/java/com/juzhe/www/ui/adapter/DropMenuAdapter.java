package com.juzhe.www.ui.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.juzhe.www.R;
import com.juzhe.www.bean.SelectModel;

/**
 * @package: com.juzhe.www.ui.adapter
 * @user:xhkj
 * @date:2018/12/5
 * @description:
 **/
public class DropMenuAdapter extends BaseQuickAdapter<SelectModel, BaseViewHolder> {
    public DropMenuAdapter(int layoutResId) {
        super(layoutResId);
    }
    @Override
    protected void convert(BaseViewHolder helper, SelectModel item) {
        helper.setText(R.id.txt_filter, item.getSelectname());
    }
}