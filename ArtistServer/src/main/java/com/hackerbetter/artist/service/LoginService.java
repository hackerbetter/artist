package com.hackerbetter.artist.service;


import com.codahale.metrics.annotation.Timed;
import com.hackerbetter.artist.consts.ErrorCode;
import static com.hackerbetter.artist.util.Response.*;

import com.hackerbetter.artist.util.JsonUtil;
import com.hackerbetter.artist.util.Response;
import net.sf.json.JSONObject;

import org.apache.camel.Produce;
import org.apache.camel.ProducerTemplate;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hackerbetter.artist.protocol.ClientInfo;
import com.hackerbetter.artist.util.common.Tools;

import java.util.HashMap;
import java.util.Map;

/**
 * 登录
 * @author Administrator
 *
 */
@Service
public class LoginService {
	
	private Logger logger = LoggerFactory.getLogger(LoginService.class);
	
	@Autowired
	private CoreService coreService;

    @Produce(uri = "jms:topic:loginSuccess", context = "camelContext")
    private ProducerTemplate loginSuccessTemplate;

	/**
	 * 登录
	 * @param clientInfo
	 * @return
	 */
    @Timed(name="用户登录")
	public String login(ClientInfo clientInfo) {
		try {
			String userno = "";// 用户编号
			String dbPassword = ""; //密码
			String state = ""; //状态
			String password = clientInfo.getPassword(); //客户端传过来的密码
			String username = clientInfo.getUsername(); //客户端传过来的用户名
			if (StringUtils.isBlank(username)) { //用户名为空,返回参数错误
				return paramError(clientInfo.getImei());
			} 
			String result = coreService.queryUsersByUserName(username.trim());
			if (StringUtils.isBlank(result)) { //返回结果为空
                return paramError(clientInfo.getImei());
			}
            Map<String,Object> resultMap= JsonUtil.transferJson2Map(result);
			if (resultMap==null) {
				return fail(ErrorCode.ERROR);
			}
			Map<String,Object> valueMap= null;
			String errorCode = (String) resultMap.get("errorCode");
			if (StringUtils.equals(errorCode, "0000")) {
                valueMap= (Map<String, Object>) resultMap.get("value");
				dbPassword = (String) valueMap.get("password");
				state = valueMap.get("state").toString();
                userno= valueMap.get("userno").toString();
			} else {
				return new Response(ErrorCode.get(errorCode)).toJson();
			}
			if (StringUtils.equals(state, "0")) { //关闭用户
				return new Response(ErrorCode.UserReg_UserExit).toJson();
			} else if (StringUtils.equals(state, "2")) { //用户暂停
				return new Response(ErrorCode.UserReg_UserPause).toJson();
			} else if (StringUtils.equals(state, "1")&&StringUtils.isNotBlank(password)
					&&StringUtils.equals(dbPassword, Tools.EncoderByMd5(password))) { //状态正常，密码正确
				//登录成功处理
				loginSuccessJms(userno,clientInfo);
				return success("登陆成功",valueMap);
			} else {
				return response(new Response(ErrorCode.UserLogin_PassWordError));
			}
		} catch (Exception e) {
			logger.error("登录发生异常", e);
			return paramError(clientInfo.getImei());
		}
	}
    /**
     * 登录成功的Jms
     * @param clientInfo
     */
    public void loginSuccessJms(String userno,ClientInfo clientInfo) {
        Map<String, Object> body = new HashMap<String, Object>();
        body.put("userno", userno);
        body.put("username", clientInfo.getUsername());
        body.put("imei", clientInfo.getImei());
        body.put("platform", clientInfo.getPlatform());
        body.put("type", clientInfo.getType());
        body.put("machineId", clientInfo.getMachineId());
        body.put("softwareversion", clientInfo.getSoftwareVersion());
        body.put("channel", clientInfo.getCoopId());
        body.put("mac", clientInfo.getMac());
        body.put("token", clientInfo.getToken());
        body.put("alias", clientInfo.getAlias());

        logger.info("loginSuccessTemplate start, bodys:{}", body);
        loginSuccessTemplate.sendBody(body);
    }
}
