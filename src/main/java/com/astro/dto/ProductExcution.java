package com.astro.dto;

import com.astro.entity.Product;
import com.astro.enums.ProductStateEnum;
import lombok.Data;

import java.util.List;

/**
 * Created by astro on 2018/1/9.
 */
@Data
public class ProductExcution {

    private int state;
    private String stateInfo;

    private int count;
    //操作的时候用 +-
    private Product product;

    private List<Product> productList;

    public ProductExcution() {}

    //失败
    public ProductExcution(ProductStateEnum stateEnum) {
        this.state = stateEnum.getState();
        this.stateInfo = stateEnum.getStateInfo();
    }
    //成功一个
    public ProductExcution(Product product,ProductStateEnum stateEnum){
        this.product = product;
        this.state = stateEnum.getState();
        this.stateInfo = stateEnum.getStateInfo();
    }
    //成功list
    public ProductExcution(List<Product> productList,ProductStateEnum stateEnum){
        this.productList = productList;
        this.state = stateEnum.getState();
        this.stateInfo = stateEnum.getStateInfo();
    }

}
