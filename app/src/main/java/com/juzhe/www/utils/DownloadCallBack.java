package com.juzhe.www.utils;

/**
 * @package: com.juzhe.www.utils
 * @user:xhkj
 * @date:2018/11/16
 * @description:
 **/
public interface DownloadCallBack {

    void onProgress(int progress);

    void onCompleted();

    void onError(String msg);

}