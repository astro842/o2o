package com.astro.dao;

import com.astro.BaseTest;
import com.astro.entity.HeadLine;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by astro on 2018/1/15.
 */
public class HeadLineDaoTest extends BaseTest{

    @Autowired
    private HeadLineDao headLineDao;

    @Test
    public void queryHeadLine() throws Exception {
        List<HeadLine> headLines = headLineDao.queryHeadLine(new HeadLine());
        System.out.println(headLines);
    }

}