package com.astro.dao;

import com.astro.entity.WeChatAuth;

/**
 * Created by astro on 2018/2/4.
 */

public interface WeChatAuthDao {

  WeChatAuth queryWeChatInfoByOpenId(String openId);

  int insertWeChatAuth(WeChatAuth weChatAuth);
}

