package com.juzhe.www.ui.widget;

import android.content.Context;
import android.widget.ImageView;
import android.widget.TextView;

import com.juzhe.www.R;
import com.juzhe.www.bean.ClassfyModel;
import com.juzhe.www.utils.GlideUtil;

import java.util.List;

/**
 * @package: com.juzhe.www.ui.widget
 * @user:xhkj
 * @date:2018/11/13
 * @description:
 **/
public class ClassifyPopu extends SpinerPopWindow<ClassfyModel> {
    private List<ClassfyModel> list;
    private Context context;

    public ClassifyPopu(Context context, List<ClassfyModel> list, int resId, int type) {
        super(context, list, resId,type);
        this.context = context;
        this.list = list;
    }

    @Override
    public void setData(SpinerAdapter.SpinerHolder holder, int position) {
        ImageView imgIcon = holder.getView(R.id.img_icon);
        GlideUtil.loadCirclePic(context, list.get(position).getIcon(), imgIcon);
        TextView txtTiltle = holder.getView(R.id.txt_icon_title);
        txtTiltle.setText(list.get(position).getName());
    }
}
