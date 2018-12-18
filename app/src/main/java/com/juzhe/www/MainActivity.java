package com.juzhe.www;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.SizeUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.juzhe.www.base.BaseMvpActivity;
import com.juzhe.www.bean.NavModel;
import com.juzhe.www.bean.UserModel;
import com.juzhe.www.common.mvp_senior.annotaions.CreatePresenterAnnotation;
import com.juzhe.www.mvp.contract.MainContract;
import com.juzhe.www.mvp.presenter.MainPresenter;
import com.juzhe.www.ui.activity.HairCircleCenterActivity;
import com.juzhe.www.ui.activity.MemberActivity;
import com.juzhe.www.ui.activity.person.PersonalActivity;
import com.juzhe.www.ui.fragment.CircleCenterFragment;
import com.juzhe.www.ui.fragment.Home1Fragment;
import com.juzhe.www.ui.fragment.OrderFragment;
import com.juzhe.www.ui.fragment.SkillFragment;
import com.juzhe.www.utils.AppManager;
import com.juzhe.www.utils.GlideUtil;
import com.juzhe.www.utils.IntentUtils;
import com.juzhe.www.utils.UserUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 主activity
 */
@CreatePresenterAnnotation(MainPresenter.class)
public class MainActivity extends BaseMvpActivity<MainContract.View, MainPresenter> implements MainContract.View, View.OnClickListener {


    @BindView(R.id.frame_container)
    FrameLayout frameContainer;
    @BindView(R.id.ll_tab_container)
    LinearLayout llTabContainer;
    private long mExitTime;
    //最后一次按钮位置
    private int lastSelectedPosition = 0;
    private ArrayList<Fragment> tabFragment;
    private UserModel userModel;
    private List<NavModel> navModel;

    @Override
    protected int getLayout() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        tabFragment = new ArrayList<>();
        userModel = UserUtils.getUser(mContext);
        getMvpPresenter().getViewNav();
    }

    /**
     * 退出App
     *
     * @param keyCode
     * @param event
     * @return
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == event.KEYCODE_BACK) {
            if ((System.currentTimeMillis() - mExitTime) > 2000) {
                ToastUtils.showShort("再按一次退出");
                mExitTime = System.currentTimeMillis();
            } else {
                AppManager.getAppManager().appExit(false);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    public void onPageSelected(int position) {
        if (position == lastSelectedPosition) {
            return;
        }
        if (position > tabFragment.size() - 1) {
            if (position == 4) {
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.hide(tabFragment.get(lastSelectedPosition));
                transaction.show(tabFragment.get(0));
                transaction.commitAllowingStateLoss();
            }
            if (position == 3) {
                ActivityUtils.startActivity(MemberActivity.class);//发圈中心
            }
            return;
        }
        if (position == 1)
            if (UserUtils.getUser(mContext).getLevel() == 1) {
                ActivityUtils.startActivity(MemberActivity.class);
                return;
            }
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.hide(tabFragment.get(lastSelectedPosition));
        transaction.show(tabFragment.get(position));
        transaction.commitAllowingStateLoss();
        if (lastSelectedPosition != position) {
            lastSelectedPosition = position;
        }
    }

    @Override
    public void setViewNav(List<NavModel> navModel) {
        this.navModel = navModel;
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        tabFragment.add(new SkillFragment());//个人中心
        tabFragment.add(new OrderFragment());//礼包中心
        tabFragment.add(new Home1Fragment());//首页
        transaction.add(R.id.frame_container, tabFragment.get(1));
        transaction.hide(tabFragment.get(1));
        transaction.add(R.id.frame_container, tabFragment.get(2));
        transaction.hide(tabFragment.get(2));
        transaction.add(R.id.frame_container, tabFragment.get(0));
        transaction.commit();
        llTabContainer.removeAllViews();
        for (int i = 0; i < navModel.size(); i++) {
            // 创建textView
            LinearLayout linearLayout = new LinearLayout(mContext);
            linearLayout.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, 1.0f));
            linearLayout.setGravity(Gravity.CENTER);
            linearLayout.setPadding(0, SizeUtils.px2dp(mContext, 50), 0, SizeUtils.px2dp(mContext, 12));
            // 设置布局参数,注意倒包
            TextView textView = new TextView(mContext);
            ImageView imageView = new ImageView(mContext);
            textView.setTextSize(SizeUtils.px2sp(mContext, 26));
            textView.setPadding(0, SizeUtils.px2dp(mContext, 10), 0, 0);
            imageView.setLayoutParams(new LinearLayout.LayoutParams(SizeUtils.dp2px(mContext, 24), SizeUtils.dp2px(mContext, 27)));
            imageView.setAdjustViewBounds(true);
            textView.setText(navModel.get(i).getTitle());
            if (i == 2) {
                onPageSelected(i);
                GlideUtil.loadIntoUseFitWidth(mContext, navModel.get(i).getSelect_icon(), imageView);
            } else {
                GlideUtil.loadIntoUseFitWidth(mContext, navModel.get(i).getNormal_icon(), imageView);
            }
            linearLayout.addView(imageView);
            linearLayout.addView(textView);
            linearLayout.setOrientation(LinearLayout.VERTICAL);
            linearLayout.setOnClickListener(this);
            llTabContainer.addView(linearLayout);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @Override
    public void onClick(View v) {
        int position = 0;
        if (llTabContainer.getChildCount() == navModel.size())
            for (int i = 0; i < llTabContainer.getChildCount(); i++) {
                LinearLayout linearLayout = (LinearLayout) llTabContainer.getChildAt(i);
                TextView txtView = (TextView) linearLayout.getChildAt(1);
                ImageView imageView = (ImageView) linearLayout.getChildAt(0);
                if (v == linearLayout) {
                    // 让对应的这个textView变红
                    position = i;
                    onPageSelected(position);
                    if (!TextUtils.isEmpty(navModel.get(i).getSelect_color()) && navModel.get(i).getSelect_color().length() == 9) {
                        txtView.setTextColor(Color.parseColor(navModel.get(i).getSelect_color()));
                    }
                    GlideUtil.loadIntoUseFitWidth(mContext, navModel.get(i).getSelect_icon(), imageView);
                } else {
                    if (!TextUtils.isEmpty(navModel.get(i).getSelect_color()))
                        txtView.setTextColor(ContextCompat.getColor(mContext, R.color.tab_color));
                    GlideUtil.loadIntoUseFitWidth(mContext, navModel.get(i).getNormal_icon(), imageView);
                }
            }


    }
}
