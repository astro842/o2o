package com.astro.web.wechat;

import com.astro.util.wechat.WeChatUser;
import com.astro.util.wechat.message.pojo.UserAccessToken;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import sun.rmi.runtime.Log;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by astro on 2018/1/31.
 */
@Controller
@RequestMapping("wechatlogin")
@Slf4j
public class WeChatLoginController {

    @GetMapping("/logincheck")
    public String doGet(HttpServletRequest request, HttpServletResponse response) {
        log.info("wechat get login start");
        String code = request.getParameter("code");
        log.info("wechat login code:" + code);
        WeChatUser user = null;
        String openId = null;
        if (null!=code){
            UserAccessToken token;
        }
        return null;
    }
}
