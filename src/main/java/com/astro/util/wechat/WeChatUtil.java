package com.astro.util.wechat;

import com.astro.dto.UserAccessToken;
import com.astro.entity.PersonInfo;
import com.astro.entity.WeChatUser;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.io.JsonEOFException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.experimental.Tolerate;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONObject;

import javax.net.ssl.*;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;

/**
 * Created by astro on 2018/2/3.
 */
@Slf4j
public class WeChatUtil {

    // String codeUrl =
    // "https://open.weixin.qq.com/connect/oauth2/authorize?appid=wxf0e81c3bee622d60&redirect_uri=http://o2o.astro842/o2o/wechatlogin/logincheck"
    // "&role_type=1&response_type=code&scope=snsapi_userinfo&state=1#wechat_redirect";

    public static UserAccessToken getUserAccessToken(String code) {
        JSONObject jsonObject = null;
        //测试号的appID
        String appId = "wx3c64c76a13e70aaf";
        log.info("appId:" + appId);
        //测试号的appsecret
        String appsecret = "c5e7f39c0f35826f030dff6ad35c5499";
        log.info("secret:" + appsecret);
        String url = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=" + appId + "&secret=" + appsecret + "&code=" + code + "&grant_type=authorization_code";
        //向url发送请求 获取token json字符串
        jsonObject = httpsRequest(url, "GET", null);
        String tokenStr = jsonObject.toString();
        log.info("userAccessToken:" + tokenStr);
        UserAccessToken token = new UserAccessToken();
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            token = objectMapper.readValue(tokenStr, UserAccessToken.class);
        } catch (JsonParseException e) {
            log.error("获取用户AccessToken失败1：" + e.toString());
            e.printStackTrace();
        } catch (IOException e) {
            log.error("获取用户AccessToken失败2：" + e.toString());
            e.printStackTrace();
        }
        if (token == null) {
            log.error("error 获取用户AccessToken失败3：");
            return null;
        }
        return token;
    }

    private static JSONObject httpsRequest(String requestUrl, String requestMethod, String outputStr) {
        JSONObject jsonObject = null;
        StringBuffer buffer = new StringBuffer();
        try {
            //创建SSLContext对象 并使用指定的信任管理器初始化
            TrustManager[] tm = {new MyX509TrustManager()};
            SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");
            sslContext.init(null, tm, new java.security.SecureRandom());
            //从上述sslcontext对象中得到 sslsocketFactory 对象
            SSLSocketFactory ssf = sslContext.getSocketFactory();

            URL url = new URL(requestUrl);
            HttpsURLConnection httpsURLConnection = (HttpsURLConnection) url.openConnection();
            httpsURLConnection.setSSLSocketFactory(ssf);
            httpsURLConnection.setDoInput(true);
            httpsURLConnection.setDoOutput(true);
            httpsURLConnection.setUseCaches(false);
            //设置请求方式
            httpsURLConnection.setRequestMethod(requestMethod);

            if ("GET".equalsIgnoreCase(requestMethod))
                httpsURLConnection.connect();

            //当有数据需要提交时(写)
            if (null != outputStr) {
                OutputStream outputStream = httpsURLConnection.getOutputStream();
                outputStream.write(outputStr.getBytes("UTF-8"));
                outputStream.close();
            }
            //（读）
            InputStream inputStream = httpsURLConnection.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

            String str = null;
            while ((str = bufferedReader.readLine()) != null) {
                buffer.append(str);
            }
            bufferedReader.close();
            inputStreamReader.close();
            //释放资源
            inputStream.close();
            ;
            inputStream = null;
            httpsURLConnection.disconnect();
            log.debug("https buffer:" + buffer.toString());
            jsonObject = JSONObject.fromObject(buffer.toString());

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchProviderException e) {
            e.printStackTrace();
        } catch (KeyManagementException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }

    public static WeChatUser getUserInfo(String accessToken, String openId) {
        String url = "https://api.weixin.qq.com/sns/userinfo?access_token="
                + accessToken + "&openid=" + openId + "&lang=zh_CN";
        JSONObject jsonObject = WeChatUtil.httpsRequest(url, "GET", null);
        WeChatUser user = new WeChatUser();
        String openid = jsonObject.getString("openid");
        if (openid == null) {
            log.info("获取用户信息失败。");
            return null;
        }

        user.setOpenId(openid);
        user.setNickName(jsonObject.getString("nickname"));
        user.setSex(jsonObject.getInt("sex"));
        user.setProvince(jsonObject.getString("province"));
        user.setCity(jsonObject.getString("city"));
        user.setCountry(jsonObject.getString("country"));
        user.setHeadimgurl(jsonObject.getString("headimgurl"));
        user.setPrivilege(null);
        // user.setUnionid(jsonObject.getString("unionid"));
        log.info("user-------------------:" + user.toString());
        return user;

    }

    public static PersonInfo getPersonInfoFromRequest(WeChatUser user) {
        PersonInfo personInfo = new PersonInfo();
        personInfo.setName(user.getNickName());
        personInfo.setGender(user.getSex()+"");
        personInfo.setProfileImg(user.getHeadimgurl());
        personInfo.setEnableStatus(1);
        return personInfo;

    }
}
