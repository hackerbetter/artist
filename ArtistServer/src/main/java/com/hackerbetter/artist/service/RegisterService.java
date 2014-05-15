package com.hackerbetter.artist.service;

import static com.hackerbetter.artist.util.Response.*;

import com.hackerbetter.artist.consts.ErrorCode;
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

import java.util.HashMap;
import java.util.Map;

/**
 * 注册
 * 
 * @author Administrator
 * 
 */
@Service
public class RegisterService {

	private Logger logger = LoggerFactory.getLogger(RegisterService.class);

	@Autowired
	private CoreService coreService;
	
	/**
	 * 注册
	 * 
	 * @param clientInfo
	 * @return
	 */
	public String register(ClientInfo clientInfo) {
		JSONObject responseJson = new JSONObject();
		try {
			String password = clientInfo.getPassword();// 密码
            String userno="";
			if (StringUtils.isBlank(password)) { //密码不允许为空
				return fail("密码为空");
			}
			String name = clientInfo.getName();// 姓名
			if (StringUtils.isNotBlank(name)&&name.length()>16) { //验证姓名的长度
			    return fail("姓名超过长度限制");
			}
			String username = clientInfo.getUsername().trim();// 用户名
			String coopId = clientInfo.getCoopId();// 渠道号
			String imei = clientInfo.getImei();// 手机串号
			
			String result = coreService.register(username, password, name.trim(), coopId, imei); //注册
			if (StringUtils.isBlank(result)) { // 如果返回空,参数错误
				return paramError(imei);
			}
            Map<String,Object> resultMap= JsonUtil.transferJson2Map(result);
			if (resultMap==null) {
		    	return fail("注册失败");
			}
            String errorCode = (String) resultMap.get("errorCode");
            if (StringUtils.equals(errorCode, "0000")) {
                //注册成功后的处理
                Map<String,Object> valueMap = (Map<String, Object>) resultMap.get("value");
                userno=valueMap.get("userno").toString();
                registerSuccessJms(userno,clientInfo);
                return success("注册成功", valueMap);
            } else {
                return response(new Response(ErrorCode.get(errorCode)));
            }
		} catch (Exception e) {
			logger.error("注册发生异常", e);
            return fail("系统繁忙");
		}
	}

    @Produce(uri = "jms:topic:registerSuccess", context = "camelContext")
    private ProducerTemplate registerSuccessTemplate;

    private void registerSuccessJms(String userno,ClientInfo clientInfo) {
        Map<String, Object> body = new HashMap<String, Object>();
        body.put("userno", userno);
        body.put("imei", clientInfo.getImei());
        body.put("mac", clientInfo.getMac());
        body.put("username", clientInfo.getUsername());
        body.put("platform", clientInfo.getPlatform());
        body.put("machine", clientInfo.getMachineId());
        body.put("softwareversion", clientInfo.getSoftwareVersion());
        body.put("channel", clientInfo.getCoopId());
        body.put("recommender", clientInfo.getRecommender()); //推荐人的用户名
        logger.info("registerSuccessTemplate start,bodys:{}", body);
        registerSuccessTemplate.sendBody(body);
    }
}
