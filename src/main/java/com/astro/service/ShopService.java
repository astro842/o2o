package com.astro.service;

import com.astro.dto.ImageHolder;
import com.astro.dto.ShopExecution;
import com.astro.entity.Product;
import com.astro.entity.Shop;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * Created by astro on 2017/12/24.
 */
public interface ShopService {

    ShopExecution getShopList(Shop shopCondition,int pageIndex,int pageSize);

    Shop queryShopById(Long shopId);

    ShopExecution modifyShop(Shop shop, ImageHolder imageHolder) throws IOException;

    ShopExecution addShop(Shop shop, ImageHolder imageHolder);


}
