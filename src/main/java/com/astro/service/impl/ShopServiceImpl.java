package com.astro.service.impl;

import com.astro.dao.ShopDao;
import com.astro.dto.ImageHolder;
import com.astro.dto.ShopExecution;
import com.astro.entity.Shop;
import com.astro.enums.ShopStateEnum;
import com.astro.exceptions.ShopOperationException;
import com.astro.service.ShopService;
import com.astro.util.ImageUtil;
import com.astro.util.PageCalculator;
import com.astro.util.PathUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.List;

/**
 * Created by astro on 2017/12/24.
 */
@Service
public class ShopServiceImpl implements ShopService {

    @Autowired
    private ShopDao shopDao;

    @Override
    public ShopExecution getShopList(Shop shopCondition, int pageIndex, int pageSize) {
        int rowIndex = PageCalculator.caltulateRowIdex(pageIndex,pageSize);
        List<Shop> shopList = shopDao.queryShopList(shopCondition,rowIndex,pageSize);
        int shopCount = shopDao.queryShopCount(shopCondition);

        ShopExecution se = new ShopExecution();
        if (shopList != null){
            se.setShopList(shopList);
            se.setCount(shopCount);
        }else {
            se.setState(ShopStateEnum.INNER_ERROR.getState());
        }
        return se;
    }

    @Override
    public Shop queryShopById(Long shopId) {
        return shopDao.queryByShopId(shopId);
    }

    @Override
    public ShopExecution modifyShop(Shop shop, ImageHolder thumbnail) throws IOException {
        //1.判断是否要改图片
        if (shop == null || shop.getShopId() == null){
            return new ShopExecution(ShopStateEnum.NULL_SHOP);
        }else{
            if (thumbnail.getImage()!= null && thumbnail.getImageName() != null && !"".equals(thumbnail.getImageName())){
                Shop tempShop = shopDao.queryByShopId(shop.getShopId());
                if (tempShop.getShopImg() != null){
                    ImageUtil.deleteFilePath(tempShop.getShopImg());
                }
                addShopImg(shop,thumbnail);
            }
        }
        //2.更新shop
        shop.setLastEditTime(new Date());
        int effNum = shopDao.updateShop(shop);
        if (effNum <= 0){
            return new ShopExecution(ShopStateEnum.INNER_ERROR);
        }else {
          shop = shopDao.queryByShopId(shop.getShopId());
          return new ShopExecution(ShopStateEnum.SUCCESS,shop);
        }

    }

    @Override
    @Transactional
    public ShopExecution addShop(Shop shop, ImageHolder thumbnail) {
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
                if (thumbnail.getImage() != null){
                    try {
                        addShopImg(shop, thumbnail);
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

    private void addShopImg(Shop shop,ImageHolder thumbnail) throws IOException {
        String dest = PathUtil.getShopImagePath(shop.getShopId());
        System.out.println(dest);
        String shopImgAddr = ImageUtil.generateThumBnail(thumbnail,dest);
        shop.setShopImg(shopImgAddr);
    }
}
