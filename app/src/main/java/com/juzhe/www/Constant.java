package com.juzhe.www;

import android.os.Environment;

/**
 * @package: com.bestsoft
 * @user:xhkj 常量
 * @date:2018/10/31
 * @description:
 **/
public class Constant {
    public static final int PAGE_SIZE = 10;//每页条数
    public static final int MY_WALLET = 0;
    public static final int ZHI_FU_BAO = 1;
    public static final String channel_id = "2";
    public final static String USER = "user";//用户信息
    public final static String isLOGIN = "is_login";//是否登录
    public static final int HOME_REFRESH = 0x001;//首页刷新
    public static final int HOME_END_REFRESH = 0x002;//首页结束
    public static final String UPDATE_USER = "update_user";
    public final static String APP_ROOT_PATH = Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + MyApplication.getInstance().getPackageName();
    public final static String DOWNLOAD_DIR = "/downlaod/";
    public static final int WITH_DRAW = 2;

    public interface viewType {
        int typeBanner = 1;         //轮播图
        int typeGv = 2;             //九宫格
        int typeTitle = 3;          //标题
        int typeList = 4;           //list
        int typeNews = 5;           //新闻
        int typeMarquee = 6;        //跑马灯
        int typePlus = 7;          //不规则视图
        int typeSticky = 8;         //指示器
        int typeFooter = 9;         //底部
        int typeGvSecond = 10;      //九宫格
    }
}
