package com.juzhe.www;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.view.KeyEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.juzhe.www.base.BaseActivity;
import com.juzhe.www.bean.UserModel;
import com.juzhe.www.ui.activity.MemberActivity;
import com.juzhe.www.ui.fragment.Home1Fragment;
import com.juzhe.www.ui.fragment.HomeFragment;
import com.juzhe.www.ui.fragment.OrderFragment;
import com.juzhe.www.ui.fragment.SkillFragment;
import com.juzhe.www.utils.AppManager;
import com.juzhe.www.utils.UserUtils;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 主activity
 */

public class MainActivity extends BaseActivity {


    @BindView(R.id.frame_container)
    FrameLayout frameContainer;
    @BindView(R.id.txt_home)
    TextView txtHome;
    @BindView(R.id.txt_order)
    TextView txtOrder;
    @BindView(R.id.txt_skill)
    TextView txtSkill;
    private long mExitTime;
    //最后一次按钮位置
    private int lastSelectedPosition = 0;
    private TextView[] tabs;
    private Drawable[] tabN;
    private Drawable[] tabH;
    private ArrayList<Fragment> tabFragment;
    private UserModel userModel;

    @Override
    protected int getLayout() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        tabFragment = new ArrayList<>();
        userModel = UserUtils.getUser(mContext);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        tabFragment.add(new HomeFragment());//首页
        tabFragment.add(new OrderFragment());//礼包中心
        tabFragment.add(new SkillFragment());//个人中心
        transaction.add(R.id.frame_container, tabFragment.get(1));
        transaction.hide(tabFragment.get(1));
        transaction.add(R.id.frame_container, tabFragment.get(2));
        transaction.hide(tabFragment.get(2));
        transaction.add(R.id.frame_container, tabFragment.get(0));
        transaction.commit();
        tabs = new TextView[]{txtHome, txtOrder, txtSkill};
        tabN = new Drawable[]{
                ContextCompat.getDrawable(this, R.drawable.tab_home_normal),
                ContextCompat.getDrawable(this, R.drawable.tab_order_normal),
                ContextCompat.getDrawable(this, R.drawable.tab_skill_normal)};
        tabH = new Drawable[]{
                ContextCompat.getDrawable(this, R.drawable.tab_home),
                ContextCompat.getDrawable(this, R.drawable.tab_order),
                ContextCompat.getDrawable(this, R.drawable.tab_skill)};

    }

    @OnClick({R.id.txt_home, R.id.txt_order, R.id.txt_skill})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.txt_home:
                onPageSelected(0);
                break;
            case R.id.txt_order:
                if (UserUtils.getUser(mContext).getLevel() == 1) {
                    ActivityUtils.startActivity(MemberActivity.class);
                    return;
                }
                onPageSelected(1);
                break;
            case R.id.txt_skill:
                onPageSelected(2);
                break;
        }
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
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.hide(tabFragment.get(lastSelectedPosition));
        transaction.show(tabFragment.get(position));
        transaction.commitAllowingStateLoss();
        if (lastSelectedPosition != position) {
            tabs[lastSelectedPosition].setCompoundDrawablesWithIntrinsicBounds(null, tabN[lastSelectedPosition], null, null);
            tabs[position].setCompoundDrawablesWithIntrinsicBounds(null, tabH[position], null, null);
            lastSelectedPosition = position;
        }
    }
}
