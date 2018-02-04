package com.astro.web.wechat;

import com.astro.dto.ShopExecution;
import com.astro.dto.WeChatAuthExecution;
import com.astro.entity.PersonInfo;
import com.astro.entity.WeChatAuth;
import com.astro.entity.WeChatUser;
import com.astro.dto.UserAccessToken;
import com.astro.enums.WeChatAuthStateEnum;
import com.astro.service.PersonInfoService;
import com.astro.service.ShopService;
import com.astro.service.WeChatAuthService;
import com.astro.util.wechat.WeChatUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

/**
 * Created by astro on 2018/1/31.
 */
//https://open.weixin.qq.com/connect/oauth2/authorize?appid=wx3c64c76a13e70aaf&redirect_uri=http://o2o.astro842.cn/o2o/wechatlogin/logincheck&response_type=code&scope=snsapi_base&state=1#wechat_redirect

//https://open.weixin.qq.com/connect/oauth2/authorize?appid=wx3c64c76a13e70aaf&redirect_uri=http://o2owechat.free.ngrok.cc/o2o/wechatlogin/logincheck&role_type=1&response_type=code&scope=snsapi_userinfo&state=1#wechat_redirect
@Controller
@RequestMapping("wechatlogin")
@Slf4j
public class WeChatLoginController {

    private static final String FRONTEND = "1";
    private static final String SHOPEND = "2";
    @Autowired
    private PersonInfoService personInfoService;
    @Autowired
    private WeChatAuthService weChatAuthService;
    @Autowired
    private ShopService shopService;


    @GetMapping("/logincheck")
    public String doGet(HttpServletRequest request, HttpServletResponse response) {
        log.info("wechat get login start");
        String code = request.getParameter("code");
        String roleType = request.getParameter("state");
        log.info("wechat login code:" + code);
        WeChatUser user = null;
        String openId = null;
        WeChatAuth auth = null;
        if (null != code) {
            UserAccessToken token;
            try {
                token = WeChatUtil.getUserAccessToken(code);
                log.info("wechat login token:" + token.toString());
                String accessToken = token.getAccessToken();
                openId = token.getOpenId();

                user = WeChatUtil.getUserInfo(accessToken, openId);
                log.info("wechat login user:" + user);
                request.getSession().setAttribute("openId", openId);
                auth = weChatAuthService.getWeChatAuthByOpenid(openId);
            } catch (Exception e) {
                log.error("error -------------" + e.toString());
                e.printStackTrace();
            }

        }
        log.info("weixin login success.");
        log.info("login role_type:" + roleType);


        //获得openId后与数据库对对比是否有  无则创建
        if (FRONTEND.equals(roleType)) {
            PersonInfo personInfo = WeChatUtil.getPersonInfoFromRequest(user);
            if (auth == null) {
                auth = new WeChatAuth();
                auth.setOpenId(openId);
                auth.setPersonInfo(personInfo);
                auth.setCreateTime(new Date());
                WeChatAuthExecution register = weChatAuthService.register(auth);
                if (register.getState() != WeChatAuthStateEnum.SUCCESS.getState()) {
                    return null;
                }
            }
            log.info("----------------auth.getPersonInfo().getUserId():" + auth.getPersonInfo().getUserId());
            personInfo = personInfoService.getPersonInfoById(auth.getPersonInfo().getUserId());
            request.getSession().setAttribute("user", personInfo);
            return "frontend/index";

        }

        if (SHOPEND.equals(roleType)) {
            PersonInfo personInfo = null;
            WeChatAuthExecution we = null;
            if (auth == null) {
                auth = new WeChatAuth();
                auth.setOpenId(openId);
                personInfo = WeChatUtil.getPersonInfoFromRequest(user);

                auth.setPersonInfo(personInfo);
                we = weChatAuthService.register(auth);
                if (we.getState() != WeChatAuthStateEnum.SUCCESS.getState()) {
                    return null;
                }
                personInfo = personInfoService.getPersonInfoById(auth.getPersonInfo().getUserId());
                request.getSession().setAttribute("user", personInfo);
//                ShopExecution se = shopService.getByEmployeeId(personInfo.getUserId());
//                request.getSession().setAttribute("user", personInfo);
//                if (se.getShopList() == null || se.getShopList().size() <= 0) {
//                    return "shop/registershop";
//                } else {
//                    request.getSession().setAttribute("shopList", se.getShopList());
//                    return "shop/shoplist";
//                }
            }

            }return null;


    }
}
