package com.astro.dao;


import com.astro.entity.ShopCategory;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by astro on 2017/12/28.
 */
public interface ShopCategoryDao {
    List<ShopCategory> queryShopCategory(@Param("shopCategoryCondition") ShopCategory shopCategoryCondition);
}
