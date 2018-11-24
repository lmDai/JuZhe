package com.juzhe.www.bean;

/**
 * @package: com.bestsoft.bean
 * @user:xhkj
 * @date:2018/11/21
 * @description:
 **/
public class ThirdLoginModel<T> {

    /**
     * errorcode : 0
     * msg : 登录成功！
     * data : {"id":34,"headimgurl":"http://qzapp.qlogo.cn/qzapp/101520389/444445AD79D26C7566D752E70DC5CEF4/30","nickname":"25663","level":1,"taobao_pid":"mm_116147303_36984478_29770200275","total_income":0,"balance":0,"discount":0,"invite_code":"EACAE0A1","user_channel_id":2,"settingtaobao":2,"alipay_account":"","real_name":""}
     */

    private int errorcode;
    private String msg;
    private T data;

    public int getErrorcode() {
        return errorcode;
    }

    public void setErrorcode(int errorcode) {
        this.errorcode = errorcode;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

}
