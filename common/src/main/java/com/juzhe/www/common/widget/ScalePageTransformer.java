package com.juzhe.www.common.widget;

import android.os.Build;
import android.support.v4.view.ViewPager;
import android.view.View;

/**
 * @package: com.juzhe.www.common.widget
 * @user:xhkj
 * @date:2018/12/6
 * @description:ViewPager 翻页效果
 **/
public class ScalePageTransformer implements ViewPager.PageTransformer {
    private static final float min_scale = 1.0f;

    @Override
    public void transformPage(View page, float position) {
        float scaleFactor = Math.max(min_scale, 1 - Math.abs(position));
        float rotate = 20 * Math.abs(position);
        if (position < -1) {

        } else if (position < 0) {
            page.setScaleX(scaleFactor);
            page.setScaleY(scaleFactor);
            page.setRotationY(rotate);
        } else if (position >= 0 && position < 1) {
            page.setScaleX(scaleFactor);
            page.setScaleY(scaleFactor);
            page.setRotationY(-rotate);
        } else if (position >= 1) {
            page.setScaleX(scaleFactor);
            page.setScaleY(scaleFactor);
            page.setRotationY(-rotate);
        }
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
            page.getParent().requestLayout();
        }
    }
}
