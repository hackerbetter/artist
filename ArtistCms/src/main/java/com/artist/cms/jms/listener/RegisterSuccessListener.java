package com.artist.cms.jms.listener;

import com.artist.cms.consts.Platform;
import com.artist.cms.domain.TregisterInfo;
import org.apache.camel.Body;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Map;

/**
 *  注册成功的Jms
 * @author Administrator
 *
 */
@Service
public class RegisterSuccessListener {

	private Logger logger = Logger.getLogger(RegisterSuccessListener.class);

	public void process(@Body Map<String,String> body) {
        String userNo=body.get("userno");
        String imei=body.get("imei");
        String mac=body.get("mac");
        String userName=body.get("username");
        String platform=body.get("platform");
        String machine=body.get("machine");
        String softwareversion=body.get("softwareversion");
        String channel=body.get("channel");
        logger.info("注册成功的Jms start "+"imei="+imei+";userName="+userName+";userNo="+userNo+";mac="+mac);
		try {
			//记录注册信息
			recordRegisterInfo(userNo, userName, imei, platform, softwareversion, machine, channel, mac);
		} catch (Exception e) {
			logger.error("注册成功的Jms发生异常imei="+imei+";userName="+userName+";userNo="+userNo+";mac="+mac, e);
		}
	}
	

	/**
	 * 记录注册信息
	 * @param userNo
	 * @param userName
	 * @param imei
	 * @param platform
	 * @param softwareversion
	 * @param machine
	 * @param channel
	 * @param mac
	 */
	private void recordRegisterInfo(String userNo, String userName, String imei, String platform, String softwareversion, 
			String machine, String channel, String mac) {
		try {
			if (StringUtils.equals(platform, Platform.iPhone.value())) {
				imei = mac;
			}
			TregisterInfo tregisterInfo = new TregisterInfo();
			tregisterInfo.setImei(imei);
			tregisterInfo.setMac(mac);
			tregisterInfo.setUsername(userName);
			tregisterInfo.setUserno(userNo);
			tregisterInfo.setPlatform(platform);
			tregisterInfo.setMachine(machine);
			tregisterInfo.setSoftwareversion(softwareversion);
			tregisterInfo.setCreatetime(new Date());
			tregisterInfo.setChannel(channel);
			tregisterInfo.persist();
		} catch (Exception e) {
			logger.error("记录注册信息发生异常", e);
		}
	}

}
