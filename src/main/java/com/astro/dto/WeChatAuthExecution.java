package com.astro.dto;

import com.astro.entity.WeChatAuth;
import com.astro.enums.WeChatAuthStateEnum;
import lombok.Data;

import java.util.List;

/**
 * Created by astro on 2018/2/4.
 */
@Data
public class WeChatAuthExecution {
    private int state;
    private String stateInfo;
    private int count;
    private WeChatAuth weChatAuth;
    private List<WeChatAuth> weChatAuthList;
    public WeChatAuthExecution(){}

    public WeChatAuthExecution(WeChatAuthStateEnum stateEnum) {
        this.state = stateEnum.getState();
        this.stateInfo = stateEnum.getStateInfo();
    }

    // 成功的构造器
    public WeChatAuthExecution(WeChatAuthStateEnum stateEnum, WeChatAuth wechatAuth) {
        this.state = stateEnum.getState();
        this.stateInfo = stateEnum.getStateInfo();
        this.weChatAuth = wechatAuth;
    }

    // 成功的构造器
    public WeChatAuthExecution(WeChatAuthStateEnum stateEnum,
                               List<WeChatAuth> wechatAuthList) {
        this.state = stateEnum.getState();
        this.stateInfo = stateEnum.getStateInfo();
        this.weChatAuthList = wechatAuthList;
    }

}
