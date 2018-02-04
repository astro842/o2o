package com.astro.service.impl;

import com.astro.BaseTest;
import com.astro.dto.WeChatAuthExecution;
import com.astro.entity.PersonInfo;
import com.astro.entity.WeChatAuth;
import com.astro.enums.WeChatAuthStateEnum;
import com.astro.service.WeChatAuthService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

import static org.junit.Assert.*;

/**
 * Created by astro on 2018/2/4.
 */
public class WeChatAuthServiceImplTest extends BaseTest{

    @Autowired
    private WeChatAuthService weChatAuthService;

    @Test
    public void getWeChatAuthByOpenid() throws Exception {
        WeChatAuth adss = weChatAuthService.getWeChatAuthByOpenid("adsss");
        System.out.println(adss);
    }

    @Test
    public void register() throws Exception {
        WeChatAuth weChatAuth=new WeChatAuth();
        PersonInfo personInfo=new PersonInfo();
        personInfo.setEnableStatus(1);
        personInfo.setCreateTime(new Date());
        personInfo.setName("花湖");
        personInfo.setUserType(1);
        weChatAuth.setPersonInfo(personInfo);
        weChatAuth.setOpenId("adsss");
        weChatAuth.setCreateTime(new Date());

        WeChatAuthExecution register = weChatAuthService.register(weChatAuth);

        assertEquals(WeChatAuthStateEnum.SUCCESS.getState(),register.getState());


    }

}