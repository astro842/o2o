package com.astro.service.impl;

import com.astro.BaseTest;
import com.astro.dto.ShopExecution;
import com.astro.entity.Area;
import com.astro.entity.PersonInfo;
import com.astro.entity.Shop;
import com.astro.entity.ShopCategory;
import com.astro.enums.ShopStateEnum;
import com.astro.service.ShopService;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by astro on 2017/12/24.
 */
public class ShopServiceImplTest extends BaseTest{

    @Autowired
    private ShopService shopService;


    @Test
    public void queryShopList(){
        Shop shop = new Shop();
        Area area = new Area();
        area.setAreaId(1);
        shop.setArea(area);
        ShopExecution shopExecution= shopService.getShopList(shop,2,2);
        System.out.println(shopExecution.getShopList());
        System.out.println(shopExecution.getCount());

    }

    @Test
    public void modifyShop()throws Exception{
        Long id = 41L;
        Shop shop = shopService.queryShopById(id);
        shop.setShopName("修改了的shopName");
        File shopImg = new File("E:/upload/o2o/p2.jpg");
        InputStream is = new FileInputStream(shopImg);

        ShopExecution shopExecution = shopService.modifyShop(shop, is, shopImg.getName());
        System.out.println(shopExecution.getShop().toString());
    }

    @Test
    public void addShop() throws FileNotFoundException {

        Shop shop = new Shop();
        Area area = new Area();
        PersonInfo owner = new PersonInfo();
        ShopCategory shopCategory = new ShopCategory();

        owner.setUserId(1L);
        area.setAreaId(2);
        shopCategory.setShopCategoryId(2L);

        shop.setOwner(owner);
        shop.setArea(area);
        shop.setShopCategory(shopCategory);

        shop.setShopName("测试店铺3");
        shop.setShopDesc("test1");
        shop.setShopAddr("test1");

        shop.setPhone("1212121");
        //shop.setShopImg("234231");
        shop.setCreateTime(new Date());
        shop.setEnableStatus(ShopStateEnum.CHECK.getState());
        shop.setPriority(0);
        shop.setAdvice("xixihah");

        File shopImg = new File("E:/upload/o2o/p1.jpg");
        InputStream is = new FileInputStream(shopImg);
        ShopExecution se = shopService.addShop(shop, is ,shopImg.getName());
        Assert.assertEquals(ShopStateEnum.CHECK.getState(),se.getState());


    }

}