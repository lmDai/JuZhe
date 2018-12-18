package com.juzhe.www.bean;

import java.util.List;

/**
 * @package: com.juzhe.www.bean
 * @user:xhkj
 * @date:2018/12/17
 * @description:
 **/
public class NavModel {

    /**
     * normal_icon : http://test.res.cqjjsms.com/20181115/5bed3b8f7e292-83645226864725.png
     * select_icon : http://test.res.cqjjsms.com/20181217/5c17902a881d2-44928453874374.png
     * select_color :
     * selected : 2
     * title : 实时跑单
     * mode : []
     */

    private String normal_icon;
    private String select_icon;
    private String select_color;
    private int selected;
    private String title;
    private List<?> mode;

    public String getNormal_icon() {
        return normal_icon;
    }

    public void setNormal_icon(String normal_icon) {
        this.normal_icon = normal_icon;
    }

    public String getSelect_icon() {
        return select_icon;
    }

    public void setSelect_icon(String select_icon) {
        this.select_icon = select_icon;
    }

    public String getSelect_color() {
        return select_color;
    }

    public void setSelect_color(String select_color) {
        this.select_color = select_color;
    }

    public int getSelected() {
        return selected;
    }

    public void setSelected(int selected) {
        this.selected = selected;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<?> getMode() {
        return mode;
    }

    public void setMode(List<?> mode) {
        this.mode = mode;
    }
}
