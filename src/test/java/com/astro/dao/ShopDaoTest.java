package com.astro.dao;

import com.astro.BaseTest;
import com.astro.entity.Area;
import com.astro.entity.PersonInfo;
import com.astro.entity.Shop;
import com.astro.entity.ShopCategory;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.net.SocketPermission;
import java.util.Date;
import java.util.Set;

import static org.junit.Assert.*;

/**
 * Created by astro on 2017/12/23.
 */
public class ShopDaoTest extends BaseTest{
    @Autowired
    private ShopDao shopDao;

    @Test
    public void insertShop() throws Exception {

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

        shop.setShopName("test");
        shop.setShopDesc("test");
        shop.setShopAddr("test");

        shop.setPhone("121212");
        shop.setShopImg("23423");
        shop.setCreateTime(new Date());
        shop.setEnableStatus(1);
        shop.setPriority(1);
        shop.setAdvice("xixihah");

        Integer integer = shopDao.insertShop(shop);
        int a=integer;
        Assert.assertEquals(1,a);
    }

    @Test
    public void updateShop() throws Exception {
          Shop shop = new Shop();
          shop.setShopId(5L);
          shop.setShopName("ttetetet");

        Integer integer = shopDao.updateShop(shop);
        int a=integer;
        Assert.assertEquals(1,a);
    }

}