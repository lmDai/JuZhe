package com.juzhe.www.bean;

import java.util.List;

/**
 * @package: com.juzhe.www.bean
 * @user:xhkj
 * @date:2018/12/14
 * @description:
 **/
public class ComponentsBean {
    private String image;
    private String title;
    private Mode mode;
    private List<SubItemBean> subItem;

    public List<SubItemBean> getSubItem() {
        return subItem;
    }

    public void setSubItem(List<SubItemBean> subItem) {
        this.subItem = subItem;
    }

    public Mode getMode() {
        return mode;
    }

    public void setMode(Mode mode) {
        this.mode = mode;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
