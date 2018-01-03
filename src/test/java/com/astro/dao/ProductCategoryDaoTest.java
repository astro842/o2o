package com.astro.dao;

import com.astro.BaseTest;
import com.astro.entity.ProductCategory;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by astro on 2018/1/3.
 */
public class ProductCategoryDaoTest extends BaseTest{
    
    @Autowired
    private  ProductCategoryDao productCategoryDao;
    
    @Test
    public void queryProductCategoryList() throws Exception {
        List<ProductCategory> productCategories = productCategoryDao.queryProductCategoryList(5l);
        System.out.println(productCategories);

    }

}