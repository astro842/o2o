package com.astro.service.impl;

import com.astro.dao.ShopDao;
import com.astro.dto.ShopExecution;
import com.astro.entity.Shop;
import com.astro.enums.ShopStateEnum;
import com.astro.exceptions.ShopOperationException;
import com.astro.service.ShopService;
import com.astro.util.ImageUtil;
import com.astro.util.PathUtil;
import com.sun.javafx.scene.shape.PathUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;

/**
 * Created by astro on 2017/12/24.
 */
@Service
public class ShopServiceImpl implements ShopService {

    @Autowired
    private ShopDao shopDao;

    @Override
    @Transactional
    public ShopExecution addShop(Shop shop, InputStream shopImgInputStream, String fileName) {
        if (shop == null){
            return new ShopExecution(ShopStateEnum.NULL_SHOP);
        }
        try {
            shop.setEnableStatus(0);
            shop.setCreateTime(new Date());
            shop.setLastEditTime(new Date());

            int effectNum = shopDao.insertShop(shop);
            if (effectNum <= 0){
                throw new ShopOperationException("店铺创建失败");
            }else {
                if (shopImgInputStream != null){
                    try {
                        addShopImg(shop, shopImgInputStream,fileName);
                    }catch (Exception e){
                        throw new ShopOperationException("addShopImg error:"+e.getMessage());
                    }
                    //更新图片地址
                    effectNum = shopDao.updateShop(shop);
                    if (effectNum <= 0){
                        throw new ShopOperationException("更新图片地址");
                    }
                }
            }

        }catch (Exception e){
            throw new ShopOperationException("addShop error:"+e.getMessage());
        }
        return new ShopExecution(ShopStateEnum.CHECK,shop);
    }

    private void addShopImg(Shop shop, InputStream shopImgInputStream ,String fileName) throws IOException {
        String dest = PathUtil.getShopImagePath(shop.getShopId());
        System.out.println(dest);
        String shopImgAddr = ImageUtil.generateThumBnail(shopImgInputStream,fileName,dest);
        shop.setShopImg(shopImgAddr);
    }
}
