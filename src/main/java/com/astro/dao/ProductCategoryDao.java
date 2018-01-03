package com.astro.dao;


import com.astro.entity.ProductCategory;

import java.util.List;

/**
 * Created by astro on 2017/12/20.
 */
public interface ProductCategoryDao {

    List<ProductCategory> queryProductCategoryList (long shopId);
}
