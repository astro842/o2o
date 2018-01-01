package com.astro.service.impl;


import com.astro.dao.ShopCategoryDao;
import com.astro.entity.ShopCategory;
import com.astro.service.ShopCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by astro on 2017/12/30.
 */
@Service
public class ShopCategoryServiceImpl implements ShopCategoryService {

    @Autowired
    private ShopCategoryDao shopCategoryDao;


    @Override
    public List<ShopCategory> getShopCategoruList(ShopCategory shopCategoryCondition) {
        return shopCategoryDao.queryShopCategory(shopCategoryCondition);
    }
}