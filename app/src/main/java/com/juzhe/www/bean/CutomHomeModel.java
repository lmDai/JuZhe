package com.juzhe.www.bean;

import java.util.List;

/**
 * @package: com.juzhe.www.bean
 * @user:xhkj
 * @date:2018/12/12
 * @description:
 **/
public class CutomHomeModel {
    private boolean result;
    private List<Data> data;
    public void setResult(boolean result) {
        this.result = result;
    }
    public boolean getResult() {
        return result;
    }

    public void setData(List<Data> data) {
        this.data = data;
    }
    public List<Data> getData() {
        return data;
    }
}
