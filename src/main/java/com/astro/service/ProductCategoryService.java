package com.astro.service;

import com.astro.dto.ProductCagegoryExecution;
import com.astro.entity.ProductCategory;
import com.astro.exceptions.ProductCategoryOperationException;

import java.util.List;

/**
 * Created by astro on 2018/1/4.
 */
public interface ProductCategoryService {

    //获取店铺的商品类别
    List<ProductCategory> getProductCategoryList(long shopId);

    //批量添加
    ProductCagegoryExecution batchAddProductCategory(List<ProductCategory> productCategoryList)
            throws ProductCategoryOperationException;
    //删除
    ProductCagegoryExecution deleteProductCategory(long productCategoryId,long shopId)
            throws  ProductCategoryOperationException;

}
