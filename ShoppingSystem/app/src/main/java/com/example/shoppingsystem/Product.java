package com.example.shoppingsystem;

import java.io.Serializable;

/**
 * Created by 79124 on 2019/4/1.
 */

public class Product implements Serializable{
    private String productId;//编号
    private String productName;//名字
    private int productImageId;//图片
    private double productPrice;//定价
    private double productSalePrice;//促销价
    private String productDetail;//简介
    private int productSalesVolume;//销量
    private String productCategory;//分类
    private double additionalCharges;//额外费用，例如运费
    private String productAttribute;//商品属性，例如尺寸大小

    public Product(String productId,String productName,int productImageId,double productPrice,double productSalePrice,
                   String productDetail,int productSalesVolume,String productCategory,double additionalCharges,String productAttribute)
    {
        this.productId = productId;
        this.productName = productName;
        this.productImageId = productImageId;
        this.productPrice = productPrice;
        this.productSalePrice = productSalePrice;
        this.productDetail = productDetail;
        this.productSalesVolume = productSalesVolume;
        this.productCategory = productCategory;
        this.additionalCharges = additionalCharges;
        this.productAttribute = productAttribute;
    }
    public Product(String productId,String productName,int productImageId){
        this.productId = productId;
        this.productName = productName;
        this.productImageId = productImageId;
    }

    public void setProductId(String productId){
        this.productId = productId;
    }
    public void setProductName(String productName){
        this.productName = productName;
    }
    public void setProductImageId(int productImageId){
        this.productImageId = productImageId;
    }
    public void setProductPrice(double productPrice){
        this.productPrice = productPrice;
    }
    public void setProductSalePrice(double productSalePrice){
        this.productSalePrice = productSalePrice;
    }
    public void setProductDetail(String productDetail){
        this.productDetail = productDetail;
    }
    public void setProductSalesVolume(int productSalesVolume){
        this.productSalesVolume = productSalesVolume;
    }
    public void setProductCategory(String productCategory){
        this.productCategory = productCategory;
    }
    public void setAdditionalCharges(double additionalCharges){
        this.additionalCharges = additionalCharges;
    }
    public void setProductAttribute(String productAttribute){
        this.productAttribute = productAttribute;
    }

    public String getProductId(){
        return productId;
    }
    public String getProductName(){
        return productName;
    }
    public int getProductImageId(){
        return productImageId;
    }
    public double getProductPrice(){
        return productPrice;
    }
    public double getProductSalePrice(){
        return productSalePrice;
    }
    public String getProductDetail(){
        return  productDetail;
    }
    public int getProductSalesVolume(){
        return  productSalesVolume;
    }
    public String getProductCategory(){
        return  productCategory;
    }
    public double getAdditionalCharges(){
        return  additionalCharges;
    }
    public String getProductAttribute(){
        return  productAttribute;
    }
}
