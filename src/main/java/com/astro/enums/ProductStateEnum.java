package com.astro.enums;

import lombok.Getter;

/**
 * Created by astro on 2018/1/9.
 */
@Getter
public enum ProductStateEnum {

    OFFLINE(-1, "非法商品"), SUCCESS(0, "操作成功"), PASS(2, "通过认证"), INNER_ERROR(
            -1001, "操作失败"),EMPTY(-1002, "商品为空");

    private int state;
    private String stateInfo;


    private ProductStateEnum(int state, String stateInfo){
        this.state=state;
        this.stateInfo=stateInfo;
    }

    public static ProductStateEnum stateOf(int state){
        for (ProductStateEnum stateEnum: values()){
            if (stateEnum.getState() == state){
                return stateEnum;
            }
        }
        return null;
    }

}
