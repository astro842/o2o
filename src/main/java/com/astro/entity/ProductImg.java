package com.astro.entity;

import lombok.Data;

import java.util.Date;

/**
 * Created by astro on 2017/12/20.
 */
@Data
public class ProductImg {

    private Long productImgId;
    private String imgAddr;
    private String imgDesc;
    private Integer priority;
    private Date createTime;
    private Long productId;


}
