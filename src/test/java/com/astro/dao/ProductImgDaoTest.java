package com.astro.dao;

import com.astro.BaseTest;
import com.astro.entity.ProductImg;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by astro on 2018/1/8.
 */
public class ProductImgDaoTest extends BaseTest{

    @Autowired
    private ProductImgDao productImgDao;

    @Test
    public void batchInsertProductImg() throws Exception {

        ProductImg productImg1 = new ProductImg();
        productImg1.setCreateTime(new Date());
        productImg1.setImgAddr("111");
        productImg1.setImgDesc("desc");
        productImg1.setPriority(1);
        productImg1.setProductId(1l);


        ProductImg productImg2 = new ProductImg();
        productImg2.setCreateTime(new Date());
        productImg2.setImgAddr("111");
        productImg2.setImgDesc("desc");
        productImg2.setPriority(1);
        productImg2.setProductId(1l);

        List<ProductImg> productImgList =new ArrayList<>();
        productImgList.add(productImg1);
        productImgList.add(productImg2);

        int i = productImgDao.batchInsertProductImg(productImgList);
        assertEquals(2,i);
    }

}