package com.juzhe.www.ui.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.juzhe.www.bean.MessageModel;

/**
 * @package: com.bestsoft.ui.adapter
 * @user:xhkj
 * @date:2018/11/2
 * @description: 消息列表
 **/
public class MessageAdapter extends BaseQuickAdapter<MessageModel, BaseViewHolder> {
    public MessageAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder helper, MessageModel item) {

    }
}
