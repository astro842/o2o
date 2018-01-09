package com.astro.service.impl;

import com.astro.dao.ProductDao;
import com.astro.dto.ProductExcution;
import com.astro.entity.Product;
import com.astro.service.ProductService;
import com.astro.util.PageCalculator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by astro on 2018/1/9.
 */
@Service
public class ProductServiceImpl implements ProductService{

    @Autowired
    private ProductDao productDao;

    @Override
    public ProductExcution getProductList(Product productCondition, int pageIndex, int pageSize) {

        int rowIndex = PageCalculator.caltulateRowIdex(pageIndex,pageSize);
        List<Product> productList = productDao.queryProductList(productCondition, rowIndex, pageSize);
        int count = productDao.queryProductCount(productCondition);
        ProductExcution pe = new ProductExcution();
        pe.setProductList(productList);
        pe.setCount(count);
        return pe;
    }

    @Override
    public Product getProductById(long productId) {
        return null;
    }
}
