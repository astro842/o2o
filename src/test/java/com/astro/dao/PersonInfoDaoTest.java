package com.astro.dao;

import com.astro.BaseTest;
import com.astro.entity.PersonInfo;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

import static org.junit.Assert.*;

/**
 * Created by astro on 2018/2/4.
 */
public class PersonInfoDaoTest extends BaseTest {

    @Autowired
    private PersonInfoDao personInfoDao;

    @Test
    public void queryPersonInfoById() throws Exception {
        PersonInfo personInfo = personInfoDao.queryPersonInfoById(1);
        System.out.println(personInfo);

    }

    @Test
    public void insertPersonInfo() throws Exception {
        PersonInfo p = new PersonInfo();
        p.setName("test");
        p.setCreateTime(new Date());
        p.setEmail("123");
        p.setGender("男");
        p.setLastEditTime(new Date());
        p.setEnableStatus(1);
        p.setProfileImg("123");
        p.setUserType(1);
        int i = personInfoDao.insertPersonInfo(p);
        System.out.println(i);
    }

}