package com.hackerbetter.artist.service;

import com.hackerbetter.artist.consts.ErrorCode;
import com.hackerbetter.artist.protocol.ClientInfo;
import com.hackerbetter.artist.util.JsonUtil;
import com.hackerbetter.artist.util.Response;
import com.hackerbetter.artist.util.common.HttpUtil;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;

import static com.hackerbetter.artist.util.Response.fail;
import static com.hackerbetter.artist.util.Response.paramError;

/**
 * Created by hacker on 2014/4/30.
 */
@Service
public class CoreService {

    private Logger logger = LoggerFactory.getLogger(CoreService.class);
    @Value("${coreUrl}")
    private String coreUrl;
    /**
     * 根据用户名查询用户
     *
     * @param username
     * @return
     * @throws java.io.UnsupportedEncodingException
     */
    public String queryUsersByUserName(String username) throws UnsupportedEncodingException {
        String url = coreUrl + "user/findOne";
        String param="username=" + URLEncoder.encode(username, "UTF-8");
        String result = HttpUtil.sendRequestByPost(url, param, true);
        logger.info("根据用户名:{}查询用户返回:{}",param,result);
        return result;
    }

    /**
     * 根据userno查询用户
     *
     * @param userno
     * @return
     * @throws java.io.UnsupportedEncodingException
     */
    public String queryUsersByUserno(String userno) throws UnsupportedEncodingException {
        String url = coreUrl + "user/findOne";
        String param ="userno="+userno;
        String result = HttpUtil.sendRequestByPost(url,param, true);
        logger.info("根据用户编号:{}查询用户返回:{}",param,result);
        return result;
    }
    /**
     * 通过用户名进行注册
     *
     * @param username
     * @param password
     * @param channel
     * @return
     * @throws UnsupportedEncodingException
     */
    public String register(String username, String password,String name,
                           String channel, String imei) throws UnsupportedEncodingException {
        StringBuffer paramStr = new StringBuffer();
        paramStr.append("username=").append(username);
        paramStr.append("&password=").append(URLEncoder.encode(password, "UTF-8")); // 编码防止密码里有+等字符
        if (StringUtils.isNotBlank(name)) { // 是否填写真实姓名
            paramStr.append("&name=").append(URLEncoder.encode(name, "UTF-8"));
        }
        if (StringUtils.isNotBlank(channel)) { //渠道号
            paramStr.append("&channel=").append(channel);
        }
        paramStr.append("&imei=").append(imei); //手机标识

        String url = coreUrl + "user/register";
        String result = HttpUtil.sendRequestByPost(url, paramStr.toString(), true);
        logger.info("注册返回:{},paramStr:{}", result, paramStr.toString());
        return result;
    }

    /**
     * 修改密码
     *
     * @param userno
     * @param password
     * @return
     * @throws UnsupportedEncodingException
     */
    public String resetPassword(String userno, String password) throws UnsupportedEncodingException {
        StringBuffer paramStr = new StringBuffer();
        paramStr.append("userno=" + userno);
        paramStr.append("&password=" + URLEncoder.encode(password, "UTF-8"));

        String url = coreUrl + "user/resetPassword";
        String result = HttpUtil.sendRequestByPost(url, paramStr.toString(), true);
        logger.info("修改密码的返回:{},paramStr:{}", result, paramStr.toString());
        if (StringUtils.isNotBlank(result)) {
            JSONObject fromObject = JSONObject.fromObject(result);
            if (fromObject!=null&&fromObject.has("errorCode")) {
                return fromObject.getString("errorCode");
            }
        }
        return "";
    }

    /**
     * 修改昵称
     *
     * @param userno
     * @param nickname
     * @return
     * @throws UnsupportedEncodingException
     */
    public String updateNickName(String userno, String nickname) throws UnsupportedEncodingException {
        StringBuffer paramStr = new StringBuffer();
        paramStr.append("userno=" + userno);
        paramStr.append("&nickname=" + URLEncoder.encode(nickname, "UTF-8"));

        String url = coreUrl + "user/modify";
        String result = HttpUtil.sendRequestByPost(url, paramStr.toString(), true);
        logger.info("修改昵称的返回:{},paramStr:{}", result, paramStr.toString());
        if (StringUtils.isNotBlank(result)) {
            JSONObject fromObject = JSONObject.fromObject(result);
            if (fromObject!=null&&fromObject.has("errorCode")) {
                return fromObject.getString("errorCode");
            }
        }
        return "";
    }

    /**
     * 绑定邮箱
     * @param userno
     * @param email
     * @return
     */
    public String bindEmail(String userno, String email) {
        StringBuffer paramStr = new StringBuffer();
        paramStr.append("userno=").append(userno);
        paramStr.append("&email=").append(email);

        String url = coreUrl + "user/modify";
        String result = HttpUtil.sendRequestByPost(url, paramStr.toString(), true);
        logger.info("绑定邮箱返回:{},paramStr:{}", result, paramStr.toString());
        if (StringUtils.isNotBlank(result)) {
            JSONObject fromObject = JSONObject.fromObject(result);
            if (fromObject!=null&&fromObject.has("errorCode")) {
                return fromObject.getString("errorCode");
            }
        }
        return "";
    }

    /**
     * 验证查询用户信息结果
     * 错误则返回错误信息，正确则返回null
     * @param jsonStr
     * @return
     */
    public String validateUserInfo(String jsonStr){
        if (StringUtils.isBlank(jsonStr)) { //返回结果为空
            return paramError("");
        }
        Map<String,Object> resultMap= JsonUtil.transferJson2Map(jsonStr);
        if (resultMap==null) {
            return fail(ErrorCode.ERROR);
        }
        String errorCode = (String) resultMap.get("errorCode");
        if (StringUtils.equals(errorCode, "0000")) {
            Map<String,Object> valueMap= (Map<String, Object>) resultMap.get("value");
            String state = valueMap.get("state").toString();
            if (StringUtils.equals(state, "0")) { //关闭用户
                return new Response(ErrorCode.UserReg_UserExit).toJson();
            } else if (StringUtils.equals(state, "2")) { //用户暂停
                return new Response(ErrorCode.UserReg_UserPause).toJson();
            }
            return null;
        } else {
            return new Response(ErrorCode.get(errorCode)).toJson();
        }
    }

    public String validateUserByUserno(String userno) throws UnsupportedEncodingException {
        String userinfo=queryUsersByUserno(userno);
        return validateUserInfo(userinfo);
    }
}
