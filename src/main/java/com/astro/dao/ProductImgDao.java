package com.astro.dao;

import com.astro.entity.Product;
import com.astro.entity.ProductImg;

import java.util.List;

/**
 * Created by astro on 2018/1/8.
 */
public interface ProductImgDao {

    //批量添加图片
    int batchInsertProductImg(List<ProductImg> productImgList);

    int deleteProductImgByProductImg(long productId);

    List<ProductImg> quertProductImgList(long productId);
}
