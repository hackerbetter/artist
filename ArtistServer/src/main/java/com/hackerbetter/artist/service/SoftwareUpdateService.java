package com.hackerbetter.artist.service;

import com.codahale.metrics.annotation.Timed;
import com.hackerbetter.artist.protocol.ClientInfo;
import net.sf.json.JSONObject;
import org.apache.camel.Produce;
import org.apache.camel.ProducerTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * 软件升级
 * @author Administrator
 *
 */
@Service
public class SoftwareUpdateService {
	
	private Logger logger = LoggerFactory.getLogger(SoftwareUpdateService.class);

	@Produce(uri = "jms:topic:recordUserInfo", context = "camelContext")
	private ProducerTemplate recordUserInfoTemplate;
	
	/**
	 * 开机联网
	 * @param clientInfo
	 * @return
	 */
	@Timed(name="getSoftwareUpdateInfo")
	public String getSoftwareUpdateInfo(ClientInfo clientInfo) {
		JSONObject responseJson = new JSONObject();
		try {
			//记录用户信息和广告JMS
			recordUserInfoJMS(clientInfo);
			responseJson.put("title", "开机联网");
		} catch (Exception e) {
			logger.error("软件升级发生异常", e);
		}
		return responseJson.toString();
	}



	/**
	 * 记录用户信息和广告的JMS
	 * @param clientInfo
	 */
	private void recordUserInfoJMS(ClientInfo clientInfo) {
		try {
			String imei = clientInfo.getImei(); //手机标识
			String imsi = clientInfo.getImsi(); //SIM卡标识
			String platform = clientInfo.getPlatform(); //平台
			String machineId = clientInfo.getMachineId(); //机型
			String isEmulator = clientInfo.getIsemulator(); //是否使用模拟器
			String softwareVersion = clientInfo.getSoftwareVersion(); //版本号
			String phoneSIM = clientInfo.getPhoneSIM(); //SIM卡手机号
			String mac = clientInfo.getMac(); //网卡地址
			
			Map<String, Object> body = new HashMap<String, Object>();
			body.put("imei", imei);
			body.put("imsi", imsi);
			body.put("platform", platform);
			body.put("machineId", machineId);
			body.put("isEmulator", isEmulator);
			body.put("softwareVersion", softwareVersion);
			body.put("phoneSIM", phoneSIM);
			body.put("mac", mac);
			
			//记录用户信息的JMS
			logger.info("recordUserInfoTemplate start, bodys:{}" , body);
			recordUserInfoTemplate.sendBody(body);
		} catch (Exception e) {
			logger.error("记录用户信息和广告的JMS发生异常", e);
		}
	}


}
