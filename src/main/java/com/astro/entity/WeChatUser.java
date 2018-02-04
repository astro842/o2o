package com.astro.entity;

import lombok.Data;

import java.util.Arrays;

/**
 * Created by astro on 2018/2/3.
 */
@Data
public class WeChatUser {

    private String openId;
    private String nickName;
    private int sex;
    private String province;
    private String city;
    private String country;
    private String headimgurl;
    private String language;
    private String[] privilege;

    @Override
    public String toString() {
        return "WeChatUser{" +
                "openId='" + openId + '\'' +
                ", nickName='" + nickName + '\'' +
                ", sex=" + sex +
                ", province='" + province + '\'' +
                ", city='" + city + '\'' +
                ", country='" + country + '\'' +
                ", headimgurl='" + headimgurl + '\'' +
                ", language='" + language + '\'' +
                ", privilege=" + Arrays.toString(privilege) +
                '}';
    }
}
