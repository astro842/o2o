package com.astro.util.wechat;

import lombok.Data;

/**
 * Created by astro on 2018/1/31.
 */
@Data
public class WeChatUser {

    private static final long serialVersionUID = 1L;

    private int id;

    private String openId;

    private String nickName;

    private int sex;

    private String province;

    private String city;

    private String country;

    private String headimgurl;

    private String privilege;

    private String unionid;

    @Override
    public String toString() {
        return "WeChatUser{" +
                "id=" + id +
                ", openId='" + openId + '\'' +
                ", nickName='" + nickName + '\'' +
                ", sex=" + sex +
                ", province='" + province + '\'' +
                ", city='" + city + '\'' +
                ", country='" + country + '\'' +
                ", headimgurl='" + headimgurl + '\'' +
                ", privilege='" + privilege + '\'' +
                ", unionid='" + unionid + '\'' +
                '}';
    }
}
