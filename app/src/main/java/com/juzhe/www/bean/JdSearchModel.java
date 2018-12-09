package com.juzhe.www.bean;

/**
 * @package: com.juzhe.www.bean
 * @user:xhkj
 * @date:2018/12/7
 * @description:
 **/
public class JdSearchModel {

    /**
     * link : https://so.m.jd.com/ware/search.action?keyword=毛衣
     * jingdong_pid : 1576471081
     */

    private String link;
    private String jingdong_pid;

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getJingdong_pid() {
        return jingdong_pid;
    }

    public void setJingdong_pid(String jingdong_pid) {
        this.jingdong_pid = jingdong_pid;
    }
}
