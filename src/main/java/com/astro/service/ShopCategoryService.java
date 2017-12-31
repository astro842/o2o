package com.astro.service;

import com.astro.entity.ShopCategory;

import java.util.List;

/**
 * Created by astro on 2017/12/30.
 */
public interface ShopCategoryService {

    List<ShopCategory> getShopCategoruList(ShopCategory shopCategory);
}
