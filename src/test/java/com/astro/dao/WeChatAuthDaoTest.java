package com.astro.dao;

import com.astro.BaseTest;
import com.astro.entity.PersonInfo;
import com.astro.entity.WeChatAuth;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

import static org.junit.Assert.*;

/**
 * Created by astro on 2018/2/4.
 */
public class WeChatAuthDaoTest extends BaseTest {

    @Autowired
    private WeChatAuthDao weChatAuthDao;

    @Test
    public void queryWeChatInfoByOpenId() throws Exception {
        WeChatAuth weChatAuth = weChatAuthDao.queryWeChatInfoByOpenId("add");
        System.out.println(weChatAuth);
    }

    @Test
    public void insertWeChatAuth() throws Exception {

        WeChatAuth w = new WeChatAuth();
        PersonInfo p = new PersonInfo();
        p.setUserId(1l);

        w.setCreateTime(new Date());
        w.setOpenId("add");
        w.setPersonInfo(p);
        int i = weChatAuthDao.insertWeChatAuth(w);
        System.out.println(i);
    }

}