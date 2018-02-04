package com.astro.service.impl;

import com.astro.dao.PersonInfoDao;
import com.astro.dao.WeChatAuthDao;
import com.astro.dto.WeChatAuthExecution;
import com.astro.entity.PersonInfo;
import com.astro.entity.WeChatAuth;
import com.astro.enums.WeChatAuthStateEnum;
import com.astro.service.WeChatAuthService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sun.rmi.runtime.Log;

import javax.swing.text.EditorKit;
import java.util.Date;

/**
 * Created by astro on 2018/2/4.
 */
@Service
@Slf4j
public class WeChatAuthServiceImpl implements WeChatAuthService {

    @Autowired
    private WeChatAuthDao weChatAuthDao;
    @Autowired
    private PersonInfoDao personInfoDao;


    @Override
    public WeChatAuth getWeChatAuthByOpenid(String openId) {
        return weChatAuthDao.queryWeChatInfoByOpenId(openId);
    }

    @Override
    @Transactional
    public WeChatAuthExecution register(WeChatAuth weChatAuth) {
        if (weChatAuth == null || weChatAuth.getOpenId() == null) {
            return new WeChatAuthExecution(WeChatAuthStateEnum.NULL_AUTH_INFO);
        }
        weChatAuth.setCreateTime(new Date());
        //有用户信息兵器 用户ID为空  则为第一次登录(创建personInfo)
        if (weChatAuth.getPersonInfo() != null && weChatAuth.getPersonInfo().getUserId() == null) {
            try {
                weChatAuth.getPersonInfo().setCreateTime(new Date());
                weChatAuth.getPersonInfo().setEnableStatus(1);
                PersonInfo personInfo = weChatAuth.getPersonInfo();
                int effNum = personInfoDao.insertPersonInfo(personInfo);
                if (effNum <= 0) {
                    throw new RuntimeException("添加用户信息失败1");
                }
            } catch (Exception e) {
                log.error("添加用户personInfo失败：" + e.toString());
                throw new RuntimeException("添加用户personInfo失败：" + e.toString());
            }

        }
        //创建wechatAuth
        int effnum2 = weChatAuthDao.insertWeChatAuth(weChatAuth);
        if (effnum2 <= 0) {
            throw new RuntimeException("创建账号失败2");
        }else {
            return new WeChatAuthExecution(WeChatAuthStateEnum.SUCCESS,weChatAuth);
        }
    }

}
