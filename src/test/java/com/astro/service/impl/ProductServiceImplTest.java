package com.astro.service.impl;

import com.astro.BaseTest;
import com.astro.dao.ProductDao;
import com.astro.dao.ProductImgDao;
import com.astro.dto.ImageHolder;
import com.astro.dto.ProductExcution;
import com.astro.entity.Product;
import com.astro.entity.ProductCategory;
import com.astro.entity.Shop;
import com.astro.service.ProductService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by astro on 2018/1/13.
 */
public class ProductServiceImplTest extends BaseTest{

    @Autowired
    private ProductService productService;


    @Test
    public void modifyProduct(){

    }

    @Test
    public void addProduct() throws Exception {
        Product product = new Product();
        Shop shop = new Shop();
        shop.setShopId(41l);
        ProductCategory pc = new ProductCategory();
        pc.setProductCategoryId(1l);

        product.setShop(shop);
        product.setProductCategory(pc);
        product.setProductName("addTest1");
        product.setProductDesc("addTest1");
        product.setPriority(1);

        File thumbnail = new File("E:/upload/o2o/p1.jpg");
        InputStream is = new FileInputStream(thumbnail);
        ImageHolder imageHolder = new ImageHolder(thumbnail.getName(),is);

        File thumbnail1 = new File("E:/upload/o2o/p1.jpg");
        InputStream is1 = new FileInputStream(thumbnail1);
        File thumbnail2 = new File("E:/upload/o2o/p1.jpg");
        InputStream is2 = new FileInputStream(thumbnail2);
        List<ImageHolder> imageHolderList = new ArrayList<>();
        imageHolderList.add(new ImageHolder(thumbnail1.getName(),is1));
        imageHolderList.add(new ImageHolder(thumbnail2.getName(),is2));

        productService.addProduct(product, imageHolder, imageHolderList);

    }

    @Test
    public void getProductById() throws Exception {
    }

    @Test
    public void getProductList() throws Exception {
    }

}