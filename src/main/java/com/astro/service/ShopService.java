package com.astro.service;

import com.astro.dto.ShopExecution;
import com.astro.entity.Shop;

import java.io.File;
import java.io.InputStream;

/**
 * Created by astro on 2017/12/24.
 */
public interface ShopService {

    ShopExecution addShop(Shop shop, InputStream shopImgInputStream,String fileName);
}
