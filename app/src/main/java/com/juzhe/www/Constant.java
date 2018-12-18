package com.juzhe.www;

import android.os.Environment;

/**
 * @package: com.juzhe.www
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
        int search_1 = 0;
        int search_2 = 1;
        int banner_1 = 2;
        int menu_1 = 3;
        int menu_2 = 4;
        int menu_3 = 5;
        int advert_1 = 6;
        int advert_2 = 7;
        int advert_3 = 8;
        int advert_4 = 9;
        int fast_entrance = 10;
        int fast_entrance_title = 11;
        int advert_5 = 12;
    }

    public interface mode {
        String H5 = "h5";
        String CATE_GORY = "category";
    }

    public interface components {
        String search_1 = "search_1";
        String search_2 = "search_2";
        String banner_1 = "banner_1";
        String menu_1 = "menu_1";
        String menu_2 = "menu_2";
        String menu_3 = "menu_3";
        String advert_1 = "advert_1";
        String advert_2 = "advert_2";
        String advert_3 = "advert_3";
        String advert_4 = "advert_4";
        String advert_5 = "advert_5";
    }
}
