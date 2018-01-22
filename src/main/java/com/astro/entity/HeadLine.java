package com.astro.entity;

import lombok.Data;

import java.util.Date;

/**
 * Created by astro on 2017/12/20.
 */
@Data
public class HeadLine {

    private Long LineId;
    private String lineName;
    private String lineLink;
    private String lineImg;
    private Integer priority;
    //0：不可用 1：可用
    private Integer enableStatus;
    private Date createTime;
    private Date lastEditTime;

}
