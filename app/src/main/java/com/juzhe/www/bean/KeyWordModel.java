package com.juzhe.www.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

/**
 * @package: com.juzhe.www.bean
 * @user:xhkj
 * @date:2018/11/14
 * @description:
 **/

@Entity
public class KeyWordModel {
    //主键自增
    @Id(autoincrement = true)
    private Long id;
    private String keyword;

    @Generated(hash = 1188290633)
    public KeyWordModel() {
    }

    @Generated(hash = 160108242)
    public KeyWordModel(Long id, String keyword) {
        this.id = id;
        this.keyword = keyword;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    @Override
    public boolean equals(Object o) {
        KeyWordModel inItem = (KeyWordModel) o;
        return keyword.equals(inItem.getKeyword());
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

}
