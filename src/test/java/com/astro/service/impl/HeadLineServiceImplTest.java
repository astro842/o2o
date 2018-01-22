package com.astro.service.impl;

import com.astro.BaseTest;
import com.astro.dao.HeadLineDao;
import com.astro.entity.HeadLine;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by astro on 2018/1/20.
 */

public class HeadLineServiceImplTest extends BaseTest{

    @Autowired
    private HeadLineDao headLineDao;

    @Test
    public void getHeadLineList() throws Exception {
        HeadLine h =new HeadLine();
        h.setEnableStatus(1);
        List<HeadLine> headLineList = headLineDao.queryHeadLine(h);
        System.out.println(headLineList);
    }

}