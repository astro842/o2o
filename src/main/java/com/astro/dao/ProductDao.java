package com.astro.dao;

import com.astro.entity.Product;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by astro on 2018/1/8.
 */
public interface ProductDao {

    int insertProduct(Product product);

    //分页查询  根据条件查
    List<Product> queryProductList(@Param("productCondition") Product productCondition,@Param("rowIndex")int rowIndex,
                                   @Param("pageSize") int pageSize);
    //查询总数
    int queryProductCount(@Param("productCondition") Product productCondition);
    //删除productCategory时， 把关联的product的 category更新为null
    int updateProductCategoryToNull(long productCategoryId);
}
