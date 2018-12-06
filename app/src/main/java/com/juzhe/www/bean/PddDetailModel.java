package com.juzhe.www.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * @package: com.juzhe.www.bean
 * @user:xhkj
 * @date:2018/12/5
 * @description:
 **/
public class PddDetailModel implements Parcelable {

    /**
     * goods_id : 4330702130
     * goods_name : 加绒解放鞋棉鞋加厚迷彩胶鞋冬季防滑防寒男作训鞋军鞋高帮鞋
     * goods_image_url : http://t00img.yangkeduo.com/goods/images/2018-11-20/883da7a7546908194ba57dba550224ef.jpeg
     * goods_gallery_urls : ["http://t00img.yangkeduo.com/goods/images/2018-11-20/1952e139da1c212e4a5076a2a773bdec.jpeg","http://t00img.yangkeduo.com/goods/images/2018-11-20/753b1939517154777e1714a0114aec7a.jpeg","http://t00img.yangkeduo.com/goods/images/2018-11-20/95a0186bf5977d5fd574b0e2065cac3c.jpeg","http://t00img.yangkeduo.com/goods/images/2018-11-20/7fffcfb485948b0bc1703b794473d27d.jpeg","http://t00img.yangkeduo.com/goods/images/2018-11-20/2d3605718e626b400131282634312025.jpeg"]
     * goods_desc : 【新店亏钱冲销量】加绒解放鞋棉鞋加厚迷彩胶鞋冬季防滑防寒男作训鞋军鞋高帮鞋
     * price : 29.90
     * opt_id : 1281
     * opt_name : 鞋包
     * sold_quantity : 1308
     * category_name : 3.00
     * has_coupon : true
     * coupon_discount : 3.00
     * coupon_total_quantity : 10000
     * coupon_remain_quantity : 8000
     * coupon_start_time : 1543852800
     * coupon_end_time : 1546185599
     * promotion_rate : 250
     * goods_eval_score : 4.64
     */

    private String goods_id;
    private String goods_name;
    private String goods_image_url;
    private String goods_desc;
    private String price;
    private String opt_id;
    private String opt_name;
    private int sold_quantity;
    private String category_name;
    private boolean has_coupon;
    private String coupon_discount;
    private double coupon_total_quantity;
    private double coupon_remain_quantity;
    private int coupon_start_time;
    private int coupon_end_time;
    private double promotion_rate;
    private double goods_eval_score;
    private List<String> goods_gallery_urls;

    protected PddDetailModel(Parcel in) {
        goods_id = in.readString();
        goods_name = in.readString();
        goods_image_url = in.readString();
        goods_desc = in.readString();
        price = in.readString();
        opt_id = in.readString();
        opt_name = in.readString();
        sold_quantity = in.readInt();
        category_name = in.readString();
        has_coupon = in.readByte() != 0;
        coupon_discount = in.readString();
        coupon_total_quantity = in.readDouble();
        coupon_remain_quantity = in.readDouble();
        coupon_start_time = in.readInt();
        coupon_end_time = in.readInt();
        promotion_rate = in.readDouble();
        goods_eval_score = in.readDouble();
        goods_gallery_urls = in.createStringArrayList();
    }

    public static final Creator<PddDetailModel> CREATOR = new Creator<PddDetailModel>() {
        @Override
        public PddDetailModel createFromParcel(Parcel in) {
            return new PddDetailModel(in);
        }

        @Override
        public PddDetailModel[] newArray(int size) {
            return new PddDetailModel[size];
        }
    };

    public String getGoods_id() {
        return goods_id;
    }

    public void setGoods_id(String goods_id) {
        this.goods_id = goods_id;
    }

    public String getGoods_name() {
        return goods_name;
    }

    public void setGoods_name(String goods_name) {
        this.goods_name = goods_name;
    }

    public String getGoods_image_url() {
        return goods_image_url;
    }

    public void setGoods_image_url(String goods_image_url) {
        this.goods_image_url = goods_image_url;
    }

    public String getGoods_desc() {
        return goods_desc;
    }

    public void setGoods_desc(String goods_desc) {
        this.goods_desc = goods_desc;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getOpt_id() {
        return opt_id;
    }

    public void setOpt_id(String opt_id) {
        this.opt_id = opt_id;
    }

    public String getOpt_name() {
        return opt_name;
    }

    public void setOpt_name(String opt_name) {
        this.opt_name = opt_name;
    }

    public int getSold_quantity() {
        return sold_quantity;
    }

    public void setSold_quantity(int sold_quantity) {
        this.sold_quantity = sold_quantity;
    }

    public String getCategory_name() {
        return category_name;
    }

    public void setCategory_name(String category_name) {
        this.category_name = category_name;
    }

    public boolean isHas_coupon() {
        return has_coupon;
    }

    public void setHas_coupon(boolean has_coupon) {
        this.has_coupon = has_coupon;
    }

    public String getCoupon_discount() {
        return coupon_discount;
    }

    public void setCoupon_discount(String coupon_discount) {
        this.coupon_discount = coupon_discount;
    }

    public double getCoupon_total_quantity() {
        return coupon_total_quantity;
    }

    public void setCoupon_total_quantity(double coupon_total_quantity) {
        this.coupon_total_quantity = coupon_total_quantity;
    }

    public double getCoupon_remain_quantity() {
        return coupon_remain_quantity;
    }

    public void setCoupon_remain_quantity(double coupon_remain_quantity) {
        this.coupon_remain_quantity = coupon_remain_quantity;
    }

    public int getCoupon_start_time() {
        return coupon_start_time;
    }

    public void setCoupon_start_time(int coupon_start_time) {
        this.coupon_start_time = coupon_start_time;
    }

    public int getCoupon_end_time() {
        return coupon_end_time;
    }

    public void setCoupon_end_time(int coupon_end_time) {
        this.coupon_end_time = coupon_end_time;
    }

    public double getPromotion_rate() {
        return promotion_rate;
    }

    public void setPromotion_rate(double promotion_rate) {
        this.promotion_rate = promotion_rate;
    }

    public double getGoods_eval_score() {
        return goods_eval_score;
    }

    public void setGoods_eval_score(double goods_eval_score) {
        this.goods_eval_score = goods_eval_score;
    }

    public List<String> getGoods_gallery_urls() {
        return goods_gallery_urls;
    }

    public void setGoods_gallery_urls(List<String> goods_gallery_urls) {
        this.goods_gallery_urls = goods_gallery_urls;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(goods_id);
        dest.writeString(goods_name);
        dest.writeString(goods_image_url);
        dest.writeString(goods_desc);
        dest.writeString(price);
        dest.writeString(opt_id);
        dest.writeString(opt_name);
        dest.writeInt(sold_quantity);
        dest.writeString(category_name);
        dest.writeByte((byte) (has_coupon ? 1 : 0));
        dest.writeString(coupon_discount);
        dest.writeDouble(coupon_total_quantity);
        dest.writeDouble(coupon_remain_quantity);
        dest.writeInt(coupon_start_time);
        dest.writeInt(coupon_end_time);
        dest.writeDouble(promotion_rate);
        dest.writeDouble(goods_eval_score);
        dest.writeStringList(goods_gallery_urls);
    }
}
