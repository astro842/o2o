package com.astro.service.impl;

import com.astro.dao.ProductCategoryDao;
import com.astro.dao.ProductDao;
import com.astro.dto.ProductCagegoryExecution;
import com.astro.entity.Product;
import com.astro.entity.ProductCategory;
import com.astro.enums.ProductCategoryStateEnum;
import com.astro.exceptions.ProductCategoryOperationException;
import com.astro.service.ProductCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by astro on 2018/1/4.
 */
@Service
public class ProductCategoryServiceImpl implements ProductCategoryService {

    @Autowired
    private ProductCategoryDao productCategoryDao;

    @Autowired
    private ProductDao productDao;

    @Override
    public List<ProductCategory> getProductCategoryList(long shopId) {
        return productCategoryDao.queryProductCategoryList(shopId);
    }

    @Override
    public ProductCagegoryExecution batchAddProductCategory(List<ProductCategory> productCategoryList) throws ProductCategoryOperationException {
        if (productCategoryList != null && productCategoryList.size() > 0) {
            try {

                int effNum = productCategoryDao.batchInsertProductCategory(productCategoryList);
                if (effNum <= 0) {
                    throw new ProductCategoryOperationException("商店类别创建失败");
                } else {
                    return new ProductCagegoryExecution(ProductCategoryStateEnum.SUCCESS, productCategoryList);
                }
            } catch (Exception e) {
                throw new ProductCategoryOperationException("batchAddProductCategory-err");
            }

        } else {
            return new ProductCagegoryExecution(ProductCategoryStateEnum.EMPTY_LIST);
        }
    }

    @Override
    @Transactional
    public ProductCagegoryExecution deleteProductCategory(long productCategoryId, long shopId) throws ProductCategoryOperationException {
        //解除product中与category的关联
        try {
            int i = productDao.updateProductCategoryToNull(productCategoryId);
            if (i <0){
                throw new ProductCategoryOperationException("商品类别删除-类别更新失败");
            }
        }catch (ProductCategoryOperationException e){
            throw new ProductCategoryOperationException("deletePC err1:" + e.getMessage());
        }

        try {
            int effNum = productCategoryDao.deleteProductCategory(productCategoryId, shopId);
            if (effNum <= 0) {
                throw new ProductCategoryOperationException("商品类别删除失败");
            } else {
                return new ProductCagegoryExecution(ProductCategoryStateEnum.SUCCESS);
            }
        } catch (ProductCategoryOperationException e) {
            throw new ProductCategoryOperationException("deletePC err2:" + e.getMessage());
        }

    }
}