package com.astro.entity;

import lombok.Data;

import java.util.Date;

/**
 * Created by astro on 2017/12/19.
 */
@Data
public class WeChatAuth {

     private Long weChatAuthId;
     private String openId;
     private Date createTime;
     private PersonInfo personInfo;


}
