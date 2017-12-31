package com.astro.dao;

import com.astro.BaseTest;
import com.astro.entity.Area;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * Created by astro on 2017/12/20.
 */
public class AreaDaoTest extends BaseTest {
   @Autowired
   private AreaDao areaDao;

   @Test
    public void testQueryArea(){
       List<Area> list = areaDao.queryArea();
       Assert.assertEquals(3,list.size());
   }

}
