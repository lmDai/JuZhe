package com.juzhe.www.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Parcelable;

import java.util.List;

/**
 * @package: com.juzhe.www.utils
 * @user:xhkj
 * @date:2018/10/30
 * @description: 意图跳转
 **/
public class IntentUtils {
    public static final String OPEN_ACTIVITY_KEY = "open_activity";//intent跳转传递传输key
    private static IntentUtils manager;
    private static Intent intent;

    private IntentUtils() {

    }

    public static IntentUtils get() {
        if (manager == null) {
            synchronized (IntentUtils.class) {
                if (manager == null) {
                    manager = new IntentUtils();
                }
            }
        }
        intent = new Intent();
        return manager;
    }

    /**
     * 获取上一个界面传递过来的参数
     *
     * @param activity this
     * @param <T>      泛型
     * @return
     */
    public <T> T getParcelableExtra(Activity activity) {
        Parcelable parcelable = activity.getIntent().getParcelableExtra(OPEN_ACTIVITY_KEY);
        activity = null;
        return (T) parcelable;
    }

    /**
     * 启动一个Activity
     *
     * @param _this
     * @param _class
     */
    public void goActivity(Context _this, Class<? extends Activity> _class) {
        intent.setClass(_this, _class);
        _this.startActivity(intent);
        _this = null;
    }

    public void goActivity(Context _this, Class<? extends Activity> _class, Bundle bundle) {
        intent.putExtra("bundle", bundle);
        intent.setClass(_this, _class);
        _this.startActivity(intent);
        _this = null;
    }

    /**
     * 启动activity后kill前一个
     *
     * @param _this
     * @param _class
     */
    public void goActivityKill(Context _this, Class<? extends Activity> _class) {
        intent.setClass(_this, _class);
        _this.startActivity(intent);
        ((Activity) _this).finish();
        _this = null;
    }

    /**
     * 回调跳转
     *
     * @param _this
     * @param _class
     * @param requestCode
     */
    public void goActivityResult(Activity _this, Class<? extends Activity> _class, int requestCode) {
        intent.setClass(_this, _class);
        _this.startActivityForResult(intent, requestCode);
        _this = null;
    }

    /**
     * 回调跳转并finish当前页面
     *
     * @param _this
     * @param _class
     * @param requestCode
     */
    public void goActivityResultKill(Activity _this, Class<? extends Activity> _class, int requestCode) {
        intent.setClass(_this, _class);
        _this.startActivityForResult(intent, requestCode);
        ((Activity) _this).finish();
        _this = null;
    }

    /**
     * 传递一个序列化实体类
     *
     * @param _this
     * @param _class
     * @param parcelable
     */
    public void goActivity(Context _this, Class<? extends Activity> _class, Parcelable parcelable) {
        intent.setClass(_this, _class);
        putParcelable(parcelable);
        _this.startActivity(intent);
        _this = null;
    }

    /**
     * 启动一个Activity
     *
     * @param _this
     * @param _class
     * @param flags
     * @param parcelable 传递的实体类
     */
    public void goActivity(Context _this, Class<? extends Activity> _class, int flags, Parcelable parcelable) {
        intent.setClass(_this, _class);
        setFlags(flags);
        putParcelable(parcelable);
        _this.startActivity(intent);
        _this = null;
    }

    public void goActivityKill(Context _this, Class<? extends Activity> _class, Parcelable parcelable) {
        intent.setClass(_this, _class);
        putParcelable(parcelable);
        _this.startActivity(intent);
        ((Activity) _this).finish();
        _this = null;
    }

    public void goActivityKill(Context _this, Class<? extends Activity> _class, int flags, Parcelable parcelable) {
        intent.setClass(_this, _class);
        setFlags(flags);
        putParcelable(parcelable);
        _this.startActivity(intent);
        ((Activity) _this).finish();
        _this = null;
    }

    public void goActivityResult(Activity _this, Class<? extends Activity> _class, Parcelable parcelable, int requestCode) {
        intent.setClass(_this, _class);
        putParcelable(parcelable);
        _this.startActivityForResult(intent, requestCode);
        _this = null;
    }

    public void goActivityResult(Activity _this, Class<? extends Activity> _class, int flags, Parcelable parcelable, int requestCode) {
        intent.setClass(_this, _class);
        putParcelable(parcelable);
        setFlags(flags);
        _this.startActivityForResult(intent, requestCode);
        _this = null;
    }

    public void goActivityResultKill(Activity _this, Class<? extends Activity> _class, Parcelable parcelable, int requestCode) {
        intent.setClass(_this, _class);
        putParcelable(parcelable);
        _this.startActivityForResult(intent, requestCode);
        _this.finish();
        _this = null;
    }

    public void goActivityResultKill(Activity _this, Class<? extends Activity> _class, int flags, Parcelable parcelable, int requestCode) {
        intent.setClass(_this, _class);
        putParcelable(parcelable);
        setFlags(flags);
        _this.startActivityForResult(intent, requestCode);
        _this.finish();
        _this = null;
    }

    private void setFlags(int flags) {
        if (flags < 0) return;
        intent.setFlags(flags);
    }

    private void putParcelable(Parcelable parcelable) {
        if (parcelable == null) return;
        intent.putExtra(OPEN_ACTIVITY_KEY, parcelable);
    }

    public static boolean isWeixinAvilible(Context context) {
        final PackageManager packageManager = context.getPackageManager();// 获取packagemanager
        List<PackageInfo> pinfo = packageManager.getInstalledPackages(0);// 获取所有已安装程序的包信息
        if (pinfo != null) {
            for (int i = 0; i < pinfo.size(); i++) {
                String pn = pinfo.get(i).packageName;
                if (pn.equals("com.tencent.mm")) {
                    return true;
                }
            }
        }
        return false;
    }
}
