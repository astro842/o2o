package com.astro.dao;

import com.astro.BaseTest;
import com.astro.entity.ProductCategory;
import com.astro.entity.Shop;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Date;
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

    @Test
    public void batchInsertProductCategory() throws  Exception{

        ProductCategory pc1 = new ProductCategory();
        pc1.setProductCategoryName("testCategory1");
        pc1.setPriority(2);
        pc1.setCreateTime(new Date());
        pc1.setShopId(41l);

        ProductCategory pc2 =new ProductCategory();
        pc2.setProductCategoryName("testCategory2");
        pc2.setPriority(2);
        pc2.setCreateTime(new Date());
        pc2.setShopId(41l);

        List<ProductCategory> productCategoryList = new ArrayList<>();
        productCategoryList.add(pc1);
        productCategoryList.add(pc2);

        int i = productCategoryDao.batchInsertProductCategory(productCategoryList);
        System.out.println("i="+i);
    }

    @Test
    public void deleteProductCategory() throws  Exception{
        int i = productCategoryDao.deleteProductCategory(9, 5);
        System.out.println("i:"+i);
    }

}