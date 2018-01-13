package com.astro.service;

import com.astro.dto.ImageHolder;
import com.astro.dto.ProductExcution;
import com.astro.entity.Product;
import com.astro.exceptions.ProductOperationException;

import java.io.IOException;
import java.util.List;

/**
 * Created by astro on 2018/1/9.
 */
public interface ProductService {

    // 1.thumbnail   2.productImgNameList 多张详情图
    ProductExcution addProduct(Product product, ImageHolder thumbnail, List<ImageHolder> productImgNameList) throws ProductOperationException, IOException;

    ProductExcution getProductList(Product productCondition,int pageIndex,int pageSize);

    Product getProductById(long productId);

    ProductExcution modifyProduct(Product product, ImageHolder thumbnail, List<ImageHolder> productImgNameList) throws ProductOperationException, IOException;



}
