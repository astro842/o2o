package com.astro.service;

import com.astro.entity.HeadLine;

import java.util.List;

/**
 * Created by astro on 2018/1/15.
 */
public interface HeadLineService {

    List<HeadLine> getHeadLineList(HeadLine headLineCondition);
}
