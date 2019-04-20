package com.example.shoppingsystem.Entity;

public class Goods {
    private int product_id;//商品id
    private int store_id;//商店id
    private String pro_name;//商品名
    private String pro_detail;//商品描述
    private double pro_price;//定价
    private double pro_favl;//促销价
    private int pro_num;//库存
    private String type;//种类
    private int pro_sale;//销量
    private String pro_image;//图片
    private double extra_money;//运费
    private int product_num;
    private boolean isSelect_product;//商品是否在购物车中被选中

    public int getProduct_id() {
        return product_id;
    }

    public void setProduct_id(int product_id) {
        this.product_id = product_id;
    }

    public int getStore_id() {
        return store_id;
    }

    public void setStore_id(int store_id) {
        this.store_id = store_id;
    }

    public String getPro_name() {
        return pro_name;
    }

    public void setPro_name(String pro_name) {
        this.pro_name = pro_name;
    }

    public String getPro_detail() {
        return pro_detail;
    }

    public void setPro_detail(String pro_detail) {
        this.pro_detail = pro_detail;
    }

    public double getPro_price() {
        return pro_price;
    }

    public void setPro_price(double pro_price) {
        this.pro_price = pro_price;
    }

    public double getPro_favl() {
        return pro_favl;
    }

    public void setPro_favl(double pro_favl) {
        this.pro_favl = pro_favl;
    }

    public int getPro_num() {
        return pro_num;
    }

    public void setPro_num(int pro_num) {
        this.pro_num = pro_num;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getPro_sale() {
        return pro_sale;
    }

    public void setPro_sale(int pro_sale) {
        this.pro_sale = pro_sale;
    }

    public String getPro_image() {
        return pro_image;
    }

    public void setPro_image(String pro_image) {
        this.pro_image = pro_image;
    }

    public double getExtra_money() {
        return extra_money;
    }

    public void setExtra_money(double extra_money) {
        this.extra_money = extra_money;
    }

    public int getProduct_num() {
        return product_num;
    }

    public void setProduct_num(int product_num) {
        this.product_num = product_num;
    }

    public boolean isSelect_product() {
        return isSelect_product;
    }

    public void setSelect_product(boolean select_product) {
        isSelect_product = select_product;
    }

    @Override
    public String toString() {
        return "Goods{" +
                "product_id=" + product_id +
                ", store_id=" + store_id +
                ", pro_name='" + pro_name + '\'' +
                ", pro_detail='" + pro_detail + '\'' +
                ", pro_price=" + pro_price +
                ", pro_favl=" + pro_favl +
                ", pro_num=" + pro_num +
                ", type='" + type + '\'' +
                ", pro_sale=" + pro_sale +
                ", pro_image='" + pro_image + '\'' +
                ", extra_money=" + extra_money +
                ", product_num=" + product_num +
                ", isSelect_product=" + isSelect_product +
                '}';
    }
}
