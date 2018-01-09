package com.astro.service;

import com.astro.dto.ProductExcution;
import com.astro.entity.Product;

/**
 * Created by astro on 2018/1/9.
 */
public interface ProductService {


    ProductExcution getProductList(Product productCondition,int pageIndex,int pageSize);

    Product getProductById(long productId);

}
