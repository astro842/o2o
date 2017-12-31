package com.astro.service.impl;

import com.astro.BaseTest;
import com.astro.entity.Area;
import com.astro.service.AreaService;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * Created by astro on 2017/12/20.
 */
public class AreaServiceImplTest extends BaseTest{

    @Autowired
    private AreaService areaService;

    @Test
    public void getAreaList() throws Exception {
        List<Area> list = areaService.getAreaList();
        Assert.assertEquals(3,list.size());

    }

}