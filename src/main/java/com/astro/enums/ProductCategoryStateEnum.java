package com.astro.enums;

import lombok.Getter;

/**
 * Created by astro on 2018/1/4.
 */
@Getter
public enum  ProductCategoryStateEnum {

    SUCCESS(1, "创建成功"),
    INNER_ERROR(-1001, "操作失败"),
    EMPTY_LIST(-1002, "添加数少于1");

    private int state;
    private String stateInfo;

    ProductCategoryStateEnum(int state, String stateInfo) {
        this.state = state;
        this.stateInfo = stateInfo;
    }

    public static ProductCategoryStateEnum stateof(int index){
        for (ProductCategoryStateEnum state : values()){
            if (state.getState() == index){
                return state;
            }
        }
        return null;

    }

}
