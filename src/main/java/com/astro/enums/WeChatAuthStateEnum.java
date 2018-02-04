package com.astro.enums;

/**
 * Created by astro on 2018/2/4.
 */
public enum WeChatAuthStateEnum {

    LOGINFAIL(-1, "openId输入有误"), SUCCESS(0, "操作成功"), NULL_AUTH_INFO(-1006,
            "注册信息为空");

    private int state;

    private String stateInfo;

    private WeChatAuthStateEnum(int state, String stateInfo) {
        this.state = state;
        this.stateInfo = stateInfo;
    }

    public int getState() {
        return state;
    }

    public String getStateInfo() {
        return stateInfo;
    }

    public static WeChatAuthStateEnum stateOf(int index) {
        for (WeChatAuthStateEnum state : values()) {
            if (state.getState() == index) {
                return state;
            }
        }
        return null;
    }

}
