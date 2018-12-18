package com.juzhe.www.utils;

import android.graphics.Rect;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * @package: com.juzhe.www.utils
 * @user:xhkj
 * @date:2018/11/2
 * @description:
 **/
public class Advert5ItemDecoration extends RecyclerView.ItemDecoration {
    private int space;

    public Advert5ItemDecoration(int space) {
        this.space = space;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        LinearLayoutManager layoutManager = (LinearLayoutManager) parent.getLayoutManager();
        //竖直方向的
        if (layoutManager.getOrientation() == LinearLayoutManager.VERTICAL) {
            //最后一项需要 bottom
            if (parent.getChildAdapterPosition(view) == layoutManager.getItemCount() - 1) {
                outRect.bottom = space;
            }
            outRect.top = space;
            outRect.left = space;
            outRect.right = space;
        } else {
            //最后一项需要right
            if (parent.getChildAdapterPosition(view) == layoutManager.getItemCount() - 1) {
                outRect.right = space;
            }
            outRect.top = space;
            if (parent.getChildAdapterPosition(view) != 0)
                outRect.left = space;
            outRect.bottom = space;
        }
    }
}
