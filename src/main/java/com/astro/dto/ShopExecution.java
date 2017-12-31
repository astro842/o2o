package com.astro.dto;

import com.astro.entity.Shop;
import com.astro.enums.ShopStateEnum;

import java.util.List;

/**
 * Created by astro on 2017/12/24.
 */

public class ShopExecution {

    private int state;
    private String stateInfo;

    //店铺的数量
    private int count;
    //操作shop 增删改查
    private Shop shop;
    //查询shop列表
    private List<Shop> shopList;

    public ShopExecution(){};

    //店铺操作失败
    public ShopExecution(ShopStateEnum stateEnum){
        this.state=stateEnum.getState();
        this.stateInfo=stateEnum.getStateInfo();

    };
    //成功（一个）
    public ShopExecution(ShopStateEnum stateEnum,Shop shop){
        this.state=stateEnum.getState();
        this.stateInfo=stateEnum.getStateInfo();
        this.shop=shop;

    };
    //成功（List）
    public ShopExecution(ShopStateEnum stateEnum,List<Shop> shopList){
        this.state=stateEnum.getState();
        this.stateInfo=stateEnum.getStateInfo();
        this.shopList=shopList;

    };


    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getStateInfo() {
        return stateInfo;
    }

    public void setStateInfo(String stateInfo) {
        this.stateInfo = stateInfo;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public Shop getShop() {
        return shop;
    }

    public void setShop(Shop shop) {
        this.shop = shop;
    }

    public List<Shop> getShopList() {
        return shopList;
    }

    public void setShopList(List<Shop> shopList) {
        this.shopList = shopList;
    }
}
