package com.astro.entity;

import lombok.Data;

import java.util.Date;

/**
 * Created by astro on 2017/12/19.
 */
@Data
public class PersonInfo {

    private Long userId;
    private String name;
    private String profileImg;
    private String email;
    private String gender;
    private Integer enableStatus;
    private Integer userType;
    private Date createTime;
    private Date lastEditTime;


}
