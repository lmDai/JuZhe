package com.juzhe.www.ui.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.alibaba.android.vlayout.DelegateAdapter;
import com.alibaba.android.vlayout.LayoutHelper;
import com.bumptech.glide.Glide;
import com.juzhe.www.R;
import com.juzhe.www.bean.CutomHomeModel;

import java.util.ArrayList;
import java.util.List;

/**
 * @package: com.juzhe.www.ui.adapter
 * @user:xhkj
 * @date:2018/11/8
 * @description:
 **/
public class OnePlusAdapter extends DelegateAdapter.Adapter<OnePlusAdapter.MainViewHolder> {


    private Context mContext;

    private LayoutHelper mLayoutHelper;
    private int type;
    private List<CutomHomeModel.DataBean.AdvertBean> mList = new ArrayList<>();


    private int mCount = 0;

    public OnePlusAdapter(Context context, LayoutHelper layoutHelper, int count,
                          int type, List<CutomHomeModel.DataBean.AdvertBean> mList) {
        this.mContext = context;
        this.mLayoutHelper = layoutHelper;
        this.mCount = count;
        this.type = type;
        this.mList = mList;
    }

    @Override
    public LayoutHelper onCreateLayoutHelper() {
        return mLayoutHelper;
    }


    @Override
    public MainViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = null;
        if (type == 1) {
            view = LayoutInflater.from(mContext).inflate(R.layout.item, parent, false);
        } else if (type == 2) {
            view = LayoutInflater.from(mContext).inflate(R.layout.item_puzzle_two, parent, false);
        } else {
            view = LayoutInflater.from(mContext).inflate(R.layout.item_puzzle_threee, parent, false);
        }
        return new MainViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MainViewHolder mainViewHolder, int i) {
        if (type == 1) {
            Glide.with(mContext).load(mList.get(i).getImage()).into(mainViewHolder.imageView);
        } else if (type == 2) {
            Glide.with(mContext).load(mList.get(i).getImage()).into(mainViewHolder.imageView);
            Glide.with(mContext).load(mList.get(i).getImage()).into(mainViewHolder.imageView1);
        } else {
            Glide.with(mContext).load(mList.get(i).getImage()).into(mainViewHolder.imageView);
            Glide.with(mContext).load(mList.get(i).getImage()).into(mainViewHolder.imageView1);
            Glide.with(mContext).load(mList.get(i).getImage()).into(mainViewHolder.imageView2);
        }

    }


    @Override
    public int getItemCount() {
        return mCount;
    }

    class MainViewHolder extends RecyclerView.ViewHolder {
        private ImageView imageView, imageView1, imageView2;

        public MainViewHolder(@NonNull View itemView) {
            super(itemView);
            if (type == 1) {
                imageView = itemView.findViewById(R.id.img);
            } else if (type == 2) {
                imageView = itemView.findViewById(R.id.img);
                imageView1 = itemView.findViewById(R.id.img_two);
            } else {
                imageView = itemView.findViewById(R.id.img);
                imageView1 = itemView.findViewById(R.id.img_two);
                imageView2 = itemView.findViewById(R.id.img_three);
            }

        }
    }
}

