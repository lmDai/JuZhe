package com.juzhe.www.ui.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.blankj.utilcode.util.SizeUtils;
import com.juzhe.www.R;
import com.juzhe.www.mvp.model.ShareInviteTempModel;
import com.juzhe.www.utils.GlideUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * @package: com.bestsoft.ui.adapter
 * @user:xhkj
 * @date:2018/11/7
 * @description:
 **/
public class ImgPagerAdapter extends PagerAdapter {
    private List<ShareInviteTempModel> mList = new ArrayList<>();
    private Context mContext;

    public ImgPagerAdapter(List<ShareInviteTempModel> mList, Context mContext) {
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
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_main, container, false);
        ImageView imageView = view.findViewById(R.id.iv);
        GlideUtil.loadRoundImage(mContext, mList.get(position).getUrl(), SizeUtils.px2dp(10), imageView);
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        ((ViewPager) container).removeView((ImageView) object);
    }
}
