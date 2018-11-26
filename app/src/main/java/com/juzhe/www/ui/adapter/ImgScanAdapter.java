package com.juzhe.www.ui.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.github.chrisbanes.photoview.PhotoView;
import com.juzhe.www.bean.PhotoInfo;
import com.juzhe.www.ui.activity.ImageScanActivity;

import java.util.ArrayList;
import java.util.List;


/**
 * @package: com.juzhe.www.ui.adapter
 * @user:xhkj
 * @date:2018/11/7
 * @description:
 **/
public class ImgScanAdapter extends PagerAdapter {
    private List<PhotoInfo> mList = new ArrayList<>();
    private Context mContext;

    public ImgScanAdapter(List<PhotoInfo> mList, Context mContext) {
        this.mList = mList;
        this.mContext = mContext;
    }

    @Override
    public int getCount() {
        return mList.size();
    }


    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
        return (view == o);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        PhotoView photoView = new PhotoView(container.getContext());
        Glide.with(mContext).load(mList.get(position).getUrl())
                .into(photoView);
        photoView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mContext instanceof ImageScanActivity) {
                    ((ImageScanActivity) mContext).finish();
                }
            }
        });
        container.addView(photoView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        return photoView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }
}
