package com.juzhe.www.bean;

/**
 * @package: com.juzhe.www.bean
 * @user:xhkj
 * @date:2018/12/5
 * @description:
 **/
public class PddListModel {

    /**
     * goods_id : 4616323767
     * goods_name : 红豆男装羽绒服冬季新款休闲修身多彩印花男士羽绒服外套156V
     * thumb_url : http://t00img.yangkeduo.com/goods/images/2018-11-23/dad2410152e9514293fbdde19ad48cd2.jpeg
     * price : 14.01
     * opt_id : 743
     * opt_name : 男装
     */

    private String goods_id;
    private String goods_name;
    private String thumb_url;
    private String price;
    private int opt_id;
    private String opt_name;
    /**
     * goods_id : 4616323767
     * goods_desc : null
     * sold_quantity : 20
     * category_name : 520.00
     * has_coupon : true
     * coupon_discount : 520.00
     * coupon_total_quantity : 2000
     * coupon_remain_quantity : 1000
     * coupon_start_time : 1543766400
     * coupon_end_time : 1544284799
     * promotion_rate : 150
     */
    private String item_end_price;
    private String goods_desc;
    private int sold_quantity;
    private String category_name;
    private boolean has_coupon;
    private String coupon_discount;
    private double coupon_total_quantity;
    private double coupon_remain_quantity;
    private double coupon_start_time;
    private double coupon_end_time;
    private double promotion_rate;

    private String estimate;
    private String upgrade;

    public String getItem_end_price() {
        return item_end_price;
    }

    public void setItem_end_price(String item_end_price) {
        this.item_end_price = item_end_price;
    }

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

    public String getThumb_url() {
        return thumb_url;
    }

    public void setThumb_url(String thumb_url) {
        this.thumb_url = thumb_url;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public int getOpt_id() {
        return opt_id;
    }

    public void setOpt_id(int opt_id) {
        this.opt_id = opt_id;
    }

    public String getOpt_name() {
        return opt_name;
    }

    public void setOpt_name(String opt_name) {
        this.opt_name = opt_name;
    }

    public String getGoods_desc() {
        return goods_desc;
    }

    public void setGoods_desc(String goods_desc) {
        this.goods_desc = goods_desc;
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

    public double getCoupon_start_time() {
        return coupon_start_time;
    }

    public void setCoupon_start_time(double coupon_start_time) {
        this.coupon_start_time = coupon_start_time;
    }

    public double getCoupon_end_time() {
        return coupon_end_time;
    }

    public void setCoupon_end_time(double coupon_end_time) {
        this.coupon_end_time = coupon_end_time;
    }

    public double getPromotion_rate() {
        return promotion_rate;
    }

    public void setPromotion_rate(double promotion_rate) {
        this.promotion_rate = promotion_rate;
    }

    public String getEstimate() {
        return estimate;
    }

    public void setEstimate(String estimate) {
        this.estimate = estimate;
    }

    public String getUpgrade() {
        return upgrade;
    }

    public void setUpgrade(String upgrade) {
        this.upgrade = upgrade;
    }
}
