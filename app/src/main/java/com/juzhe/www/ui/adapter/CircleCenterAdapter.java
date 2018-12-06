package com.juzhe.www.ui.adapter;

import android.os.Bundle;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.juzhe.www.R;
import com.juzhe.www.bean.ArticleModel;
import com.juzhe.www.bean.PhotoInfo;
import com.juzhe.www.ui.activity.ImageScanActivity;
import com.juzhe.www.ui.widget.MultiImageView;
import com.juzhe.www.utils.IntentUtils;

import java.util.ArrayList;

/**
 * @package: com.juzhe.www.ui.adapter
 * @user:xhkj
 * @date:2018/11/2
 * @description: 发圈中心
 **/
public class CircleCenterAdapter extends BaseQuickAdapter<ArticleModel, BaseViewHolder> {
    public CircleCenterAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder helper, ArticleModel item) {
        MultiImageView multiImageView = helper.getView(R.id.multiply);
        ArrayList<PhotoInfo> imageInfo = new ArrayList<>();
        PhotoInfo info = new PhotoInfo();
        info.setUrl(item.getShare_img());
        imageInfo.add(info);
        multiImageView.setList(imageInfo);
        helper.setText(R.id.txt_name, item.getName())
                .setText(R.id.txt_create_at, item.getCreate_at())
                .setText(R.id.txt_content, item.getContent())
                .addOnClickListener(R.id.ll_share);
        multiImageView.setOnItemClickListener(new MultiImageView.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Bundle bundle = new Bundle();
                bundle.putInt("position", position);
                bundle.putParcelableArrayList("imageview", imageInfo);
                IntentUtils.get().goActivity(mContext, ImageScanActivity.class, bundle);
            }
        });
    }
}
