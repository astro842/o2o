package com.astro.dto;

import com.astro.entity.ProductCategory;
import com.astro.enums.ProductCategoryStateEnum;
import lombok.Data;


import java.util.List;

/**
 * Created by astro on 2018/1/6.
 */
@Data
public class ProductCagegoryExecution {

    private int state;
    private String stateInfo;
    private List<ProductCategory> productCategoryList;

    public ProductCagegoryExecution(){}

    //成功
    public ProductCagegoryExecution(ProductCategoryStateEnum stateEnum,List<ProductCategory> productCategoryList){
        this.state = stateEnum.getState();
        this.stateInfo = stateEnum.getStateInfo();
        this.productCategoryList = productCategoryList;
    }

    //失败
    public ProductCagegoryExecution(ProductCategoryStateEnum stateEnum){
        this.stateInfo = stateEnum.getStateInfo();
        this.state =stateEnum.getState();
    }
}
