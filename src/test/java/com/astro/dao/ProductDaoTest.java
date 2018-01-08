package com.astro.dao;

import com.astro.BaseTest;
import com.astro.entity.Product;
import com.astro.entity.ProductCategory;
import com.astro.entity.Shop;
import com.astro.entity.ShopCategory;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

import static org.junit.Assert.*;

/**
 * Created by astro on 2018/1/8.
 */
public class ProductDaoTest extends BaseTest{

    @Autowired
    private ProductDao productDao;

    @Test
    public void insertProduct() throws Exception {

        Product product = new Product();
        product.setProductName("商品1");
        product.setCreateTime(new Date());
        product.setEnableStatus(0);
        product.setLastEditTime(new Date());
        product.setNormalPrice("10");
        product.setProductDesc("haiahi");
        product.setPriority(1);

        Shop shop =new Shop();
        shop.setShopId(41l);
        product.setShop(shop);

        ProductCategory productCategory = new ProductCategory();
        productCategory.setProductCategoryId(1l);
        product.setProductCategory(productCategory);

        int i = productDao.insertProduct(product);
        Assert.assertEquals(1,i);
    }

}