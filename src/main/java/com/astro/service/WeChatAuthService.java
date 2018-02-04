package com.astro.service;

import com.astro.dto.WeChatAuthExecution;
import com.astro.entity.WeChatAuth;

/**
 * Created by astro on 2018/2/4.
 */
public interface WeChatAuthService {

    WeChatAuth getWeChatAuthByOpenid(String openId);

    WeChatAuthExecution register(WeChatAuth weChatAuth);

}
