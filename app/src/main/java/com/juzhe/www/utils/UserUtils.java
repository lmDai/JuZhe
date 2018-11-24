package com.juzhe.www.utils;

import android.content.Context;
import android.text.TextUtils;

import com.alibaba.fastjson.JSON;
import com.juzhe.www.Constant;
import com.juzhe.www.bean.UserModel;

/**
 * @package: com.juzhe.www.utils
 * @user:xhkj
 * @date:2018/11/24
 * @description:
 **/
public class UserUtils {
    public static UserModel getUser(Context mContext) {
        String user = (String) SpUtils.getParam(mContext, Constant.USER, "");
        if (TextUtils.isEmpty(user)) {
            return null;
        }
        return JSON.parseObject(user, UserModel.class);
    }

    public static void saveUserInfo(Context mContext, UserModel userModel) {
        String userResult = JSON.toJSONString(userModel);
        SpUtils.setParam(mContext, Constant.USER, userResult);
    }
}
