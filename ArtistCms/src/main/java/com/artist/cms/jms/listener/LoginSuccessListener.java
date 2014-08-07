package com.artist.cms.jms.listener;

import com.artist.cms.consts.Platform;
import com.artist.cms.domain.TregisterInfo;
import org.apache.camel.Body;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 *  登录成功的jms
 * @author Administrator
 *
 */
@Service
public class LoginSuccessListener {

	private Logger logger = Logger.getLogger(LoginSuccessListener.class);
	
	public void process(@Body Map<String,String> body) {
		try {
            String userNo=body.get("userno");
            String userName=body.get("username");
            String imei=body.get("imei");
            String platform=body.get("platform");
            String machineId=body.get("machineId");
            String softwareversion=body.get("softwareversion");
            String channel=body.get("channel");
            String mac=body.get("mac");
            logger.info("登录成功的jms start "+"userNo="+userNo+";imei="+imei+";mac="+mac);
			//记录用户信息
			recordLoginInfo(userNo, userName, imei, platform, machineId, softwareversion, channel, mac);
		} catch (Exception e) {
			logger.error("登录成功的jms发生异常", e);
		}
	}
	
	
	
	/**
	 * 记录用户登录信息
	 * @param userNo
	 * @param userName
	 * @param imei
	 * @param platform
	 * @param machine
	 * @param softwareversion
	 * @param coopId
	 * @param mac
	 */
	private void recordLoginInfo(String userNo, String userName, String imei, String platform, String machine,
			String softwareversion, String coopId, String mac) {
		try {
			if (StringUtils.equals(platform, Platform.iPhone.value())) {
				imei = mac;
			}
			StringBuilder builder = new StringBuilder(" where");
			List<Object> params = new ArrayList<Object>();
			
			builder.append(" o.imei=? and");
			params.add(imei);
			
			builder.append(" o.platform=? and");
			params.add(platform);
			
			builder.append(" o.userno=?");
			params.add(userNo);
			List<TregisterInfo> list = TregisterInfo.getList(builder.toString(), "", params);
			if (list==null||list.size()==0) {
				TregisterInfo tregisterInfo = new TregisterInfo();
				tregisterInfo.setImei(imei);
				tregisterInfo.setMac(mac);
				tregisterInfo.setUsername(userName);
				tregisterInfo.setUserno(userNo);
				tregisterInfo.setPlatform(platform);
				tregisterInfo.setMachine(machine);
				tregisterInfo.setSoftwareversion(softwareversion);
				tregisterInfo.setLogintime(new Date());
				tregisterInfo.setLoginchannel(coopId);
				tregisterInfo.persist();
			} else if (list!=null&&list.size()==1) {
				TregisterInfo tregisterInfo = list.get(0);
				tregisterInfo.setLoginchannel(coopId);
				tregisterInfo.setLogintime(new Date());
				tregisterInfo.merge();
			}
		} catch (Exception e) {
			logger.error("记录用户登录信息发生异常", e);
		}
	}
	
}
