package com.astro.dao;


import com.astro.entity.ProductCategory;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by astro on 2017/12/20.
 */
public interface ProductCategoryDao {

    //显示商店的所有商品类别
    List<ProductCategory> queryProductCategoryList (long shopId);
    //批量添加
    int batchInsertProductCategory(List<ProductCategory> productCategoryList);
    //删除
    int deleteProductCategory(@Param("productCategoryId")long productCategoryId,@Param("shopId") long shopId);

}
