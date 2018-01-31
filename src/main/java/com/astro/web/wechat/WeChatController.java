package com.astro.web.wechat;

import com.astro.util.wechat.SignUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by astro on 2018/1/31.
 */
@Controller
@RequestMapping("wechat")
@Slf4j
public class WeChatController {

    public void doGet(HttpServletRequest request, HttpServletResponse response) {

        String signature = request.getParameter("signature");
        String timestamp = request.getParameter("timestamp");
        String nonce = request.getParameter("nonce");
        String echostr = request.getParameter("echostr");
        PrintWriter out = null;

        try {
            out=response.getWriter();
            if (SignUtil.checkSignature(signature,timestamp,nonce)){
                log.info("wechat get success");
                out.print(echostr);
            }
        }catch (IOException e){
            e.printStackTrace();
        }finally {
            if (out!=null)
                out.close();
        }
    }
}
