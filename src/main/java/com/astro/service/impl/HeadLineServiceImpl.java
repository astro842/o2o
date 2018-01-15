package com.astro.service.impl;

import com.astro.dao.HeadLineDao;
import com.astro.entity.HeadLine;
import com.astro.service.HeadLineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by astro on 2018/1/15.
 */
@Service
public class HeadLineServiceImpl implements HeadLineService{

    @Autowired
    private HeadLineDao headLineDao;

    @Override
    public List<HeadLine> getHeadLineList(HeadLine headLineCondition) {
        return headLineDao.queryHeadLine(headLineCondition);
    }
}
