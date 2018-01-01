package com.astro.dao;


import com.astro.entity.Shop;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by astro on 2017/12/23.
 */
public interface ShopDao {

    //分页   商店类别  区域 商店名 商店状态 商店owner
    List<Shop> queryShopList(@Param("shopCondition") Shop shopCondition,@Param("rowIndex") int rowIndex,
                             @Param("pageSize") int pageSize);
    int queryShopCount(@Param("shopCondition") Shop shopCondition);

    int insertShop(Shop shop);

    int updateShop(Shop shop);

    Shop queryByShopId(Long shopId);

}
