package com.juzhe.www.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * @package: com.juzhe.www.bean
 * @user:xhkj
 * @date:2018/12/3
 * @description:
 **/
public class JDProductModel implements Parcelable {

    /**
     * item_id : 36766879521
     * item_title : 【张嘴零食官方旗舰店】牛肉味兰花豆120g*3袋
     * item_short_title : 张嘴零食精选，味道棒极了！120g*3袋仅需15！牛肉味，好吃不停嘴~~领券数量有限，赶紧抢！
     * item_pic : http://img14.360buyimg.com/imgzone/jfs/t26362/251/1642820608/338326/540c02fe/5be92bffN133fb88d.jpg
     * fqcat : 食品保健
     * item_price : 35
     * item_end_price : 15
     * couponmoney : 20
     * discount_link : http://coupon.m.jd.com/coupons/show.action?key=fdafb34a57204a1faad629b1cb5ad80b&roleId=16272112&to=zhangzui.jd.com
     * tkrates : 20
     * tkmoney : 7
     * estimate : 2.94
     * upgrade : 2.94
     */

    private String item_id;
    private String item_title;
    private String item_short_title;
    private List<String> item_pic;
    private String fqcat;
    private double item_price;
    private double item_end_price;
    private double couponmoney;
    private String discount_link;
    private double tkrates;
    private double tkmoney;
    private double estimate;
    private double upgrade;
    private String item_pic_copy;//	消息图
    private String link;//	链接

    private String source;

    protected JDProductModel(Parcel in) {
        item_id = in.readString();
        item_title = in.readString();
        item_short_title = in.readString();
        item_pic = in.createStringArrayList();
        fqcat = in.readString();
        item_price = in.readDouble();
        item_end_price = in.readDouble();
        couponmoney = in.readDouble();
        discount_link = in.readString();
        tkrates = in.readDouble();
        tkmoney = in.readDouble();
        estimate = in.readDouble();
        upgrade = in.readDouble();
        item_pic_copy = in.readString();
        link = in.readString();
        source = in.readString();
    }

    public static final Creator<JDProductModel> CREATOR = new Creator<JDProductModel>() {
        @Override
        public JDProductModel createFromParcel(Parcel in) {
            return new JDProductModel(in);
        }

        @Override
        public JDProductModel[] newArray(int size) {
            return new JDProductModel[size];
        }
    };

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }


    public String getItem_id() {
        return item_id;
    }

    public void setItem_id(String item_id) {
        this.item_id = item_id;
    }

    public String getItem_title() {
        return item_title;
    }

    public void setItem_title(String item_title) {
        this.item_title = item_title;
    }

    public String getItem_short_title() {
        return item_short_title;
    }

    public void setItem_short_title(String item_short_title) {
        this.item_short_title = item_short_title;
    }

    public List<String> getItem_pic() {
        return item_pic;
    }

    public void setItem_pic(List<String> item_pic) {
        this.item_pic = item_pic;
    }

    public String getFqcat() {
        return fqcat;
    }

    public void setFqcat(String fqcat) {
        this.fqcat = fqcat;
    }

    public double getItem_price() {
        return item_price;
    }

    public void setItem_price(double item_price) {
        this.item_price = item_price;
    }

    public double getItem_end_price() {
        return item_end_price;
    }

    public void setItem_end_price(double item_end_price) {
        this.item_end_price = item_end_price;
    }

    public double getCouponmoney() {
        return couponmoney;
    }

    public void setCouponmoney(double couponmoney) {
        this.couponmoney = couponmoney;
    }

    public String getDiscount_link() {
        return discount_link;
    }

    public void setDiscount_link(String discount_link) {
        this.discount_link = discount_link;
    }

    public double getTkrates() {
        return tkrates;
    }

    public void setTkrates(double tkrates) {
        this.tkrates = tkrates;
    }

    public double getTkmoney() {
        return tkmoney;
    }

    public void setTkmoney(double tkmoney) {
        this.tkmoney = tkmoney;
    }

    public double getEstimate() {
        return estimate;
    }

    public void setEstimate(double estimate) {
        this.estimate = estimate;
    }

    public double getUpgrade() {
        return upgrade;
    }

    public void setUpgrade(double upgrade) {
        this.upgrade = upgrade;
    }

    public String getItem_pic_copy() {
        return item_pic_copy;
    }

    public void setItem_pic_copy(String item_pic_copy) {
        this.item_pic_copy = item_pic_copy;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(item_id);
        dest.writeString(item_title);
        dest.writeString(item_short_title);
        dest.writeStringList(item_pic);
        dest.writeString(fqcat);
        dest.writeDouble(item_price);
        dest.writeDouble(item_end_price);
        dest.writeDouble(couponmoney);
        dest.writeString(discount_link);
        dest.writeDouble(tkrates);
        dest.writeDouble(tkmoney);
        dest.writeDouble(estimate);
        dest.writeDouble(upgrade);
        dest.writeString(item_pic_copy);
        dest.writeString(link);
        dest.writeString(source);
    }
}
