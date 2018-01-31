package com.astro.util.wechat.message.pojo;

import lombok.Data;

/**
 * Created by astro on 2018/1/31.
 */
@Data
public class UserAccessToken {

    private String accessToken;

    private String expiresIn;

    private String refreshToken;

    private String openId;

    private String scope;

    @Override
    public String toString() {
        return "UserAccessToken{" +
                "accessToken='" + accessToken + '\'' +
                ", expiresIn='" + expiresIn + '\'' +
                ", refreshToken='" + refreshToken + '\'' +
                ", openId='" + openId + '\'' +
                ", scope='" + scope + '\'' +
                '}';
    }
}
