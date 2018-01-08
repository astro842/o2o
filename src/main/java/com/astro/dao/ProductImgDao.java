package com.astro.dao;

import com.astro.entity.Product;
import com.astro.entity.ProductImg;

import java.util.List;

/**
 * Created by astro on 2018/1/8.
 */
public interface ProductImgDao {

    int batchInsertProductImg(List<ProductImg> productImgList);
}
