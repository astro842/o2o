package com.astro.dao;

import com.astro.BaseTest;
import com.astro.entity.ShopCategory;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by astro on 2017/12/28.
 */
public class shopCategoryDaoTest extends BaseTest{

    @Autowired
    private ShopCategoryDao shopCategoryDao;

    @Test
    public void queryShopCategory() throws Exception {


        ShopCategory p1 = new ShopCategory();
        ShopCategory s2 = new ShopCategory();
        p1.setShopCategoryId(2L);
        s2.setParent(p1);
        List<ShopCategory> shopCategories = shopCategoryDao.queryShopCategory(s2);
        System.out.println(shopCategories.get(0).getShopCategoryName());
        System.out.println(shopCategories.get(1).getShopCategoryName());
        assertEquals(2,shopCategories.size());
    }

}