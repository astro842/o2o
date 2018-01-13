package com.astro.dao;

import com.astro.BaseTest;
import com.astro.dto.ProductExcution;
import com.astro.entity.Product;
import com.astro.entity.ProductCategory;
import com.astro.entity.Shop;
import com.astro.entity.ShopCategory;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by astro on 2018/1/8.
 */
public class ProductDaoTest extends BaseTest {

    @Autowired
    private ProductDao productDao;

    @Test
    public void queryProductById() {
        Product product = productDao.queryProductById(4l);
        System.out.println(product);
    }

    @Test
    public void updateProduct() {
        Product product = new Product();
        product.setPriority(111111111);
        product.setProductId(4l);
        Shop shop =new Shop();
        shop.setShopId(41l);
        product.setShop(shop);
        productDao.updateProduct(product);
    }

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

        Shop shop = new Shop();
        shop.setShopId(41l);
        product.setShop(shop);

        ProductCategory productCategory = new ProductCategory();
        productCategory.setProductCategoryId(1l);
        product.setProductCategory(productCategory);

        int i = productDao.insertProduct(product);
        Assert.assertEquals(1, i);
    }

    @Test
    public void queryProductList() throws Exception {
        Product productCondition = new Product();
        ProductCategory pc = new ProductCategory();
        productCondition.setProductCategory(pc);
        List<Product> products = productDao.queryProductList(productCondition, 0, 2);
        int i = productDao.queryProductCount(productCondition);
        System.out.println(products);
        System.out.println("i=" + i);
    }

    @Test
    public void updateProductCategoryToNull() throws Exception {

        int i = productDao.updateProductCategoryToNull(1);
        assertEquals(2, i);
    }

}