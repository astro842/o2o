package com.astro.entity;

import lombok.Data;

import java.util.Date;

/**
 * Created by astro on 2017/12/19.
 */
@Data
public class Area {

    private Integer areaId;
    private String areaName;
    //权重
    private Integer priority;
    private Date createTime;
    private Date lastEditTime;


}
