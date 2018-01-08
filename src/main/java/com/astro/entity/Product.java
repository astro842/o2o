package com.astro.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Created by astro on 2017/12/20.
 */
@Data
public class Product implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = -349433539553804024L;
    private Long productId;
    private String productName;
    private String productDesc;
    private String imgAddr;// 简略图
    private String normalPrice;
    private String promotionPrice;
    private Integer priority;
    private Date createTime;
    private Date lastEditTime;
    //0：表示下架  1：在前端展示
    private Integer enableStatus;
    private Integer point;

    private List<ProductImg> productImgList;
    private ProductCategory productCategory;
    private Shop shop;



}
