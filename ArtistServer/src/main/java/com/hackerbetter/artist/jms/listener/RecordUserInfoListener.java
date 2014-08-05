package com.hackerbetter.artist.jms.listener;


import com.hackerbetter.artist.consts.Platform;
import com.hackerbetter.artist.domain.UserInf;
import org.apache.camel.Body;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 *  记录用户信息的jms
 * @author Administrator
 *
 */
@Service
public class RecordUserInfoListener {

	private Logger logger = LoggerFactory.getLogger(RecordUserInfoListener.class);
	
	@Transactional
	public void process(@Body Map<String,String> body) {
        String imei= body.get("imei");
        String imsi= body.get("imsi");
        String platform= body.get("platform");
        String mac=body.get("mac");
        String machineId= body.get("machineId");
        String isEmulator=body.get("isEmulator");
        String softwareVersion= body.get("softwareVersion");
        String coopId=body.get("coopId");
        String phoneSIM=body.get("phoneSIM");
        logger.info("记录用户信息jms start "+"imei="+imei+";platform="+platform+";mac="+mac);
		try {
			//安卓、塞班(用imei作手机标识)
			if (StringUtils.equals(platform, Platform.android.value())) {
				if (StringUtils.isBlank(imei)) {
					return ;
				}
				androidDispose(imei, imsi, platform, machineId, isEmulator, softwareVersion, coopId, phoneSIM, mac);
			} else if (StringUtils.equals(platform, Platform.iPhone.value())) { //苹果(用mac做标识)
				if (StringUtils.isBlank(mac)) {
					return ;
				}
				iphoneDispose(imsi, platform, machineId, isEmulator, softwareVersion, coopId, phoneSIM, mac);
			} else {
				logger.info("记录用户信息jms处理时平台未知,platform="+platform);
			}
		} catch (Exception e) {
			logger.error("记录用户信息的jms发生异常imei="+imei, e);
		}
	}

    /**
     * 安卓处理
     * @param imei
     * @param imsi
     * @param platform
     * @param machineId
     * @param isEmulator
     * @param softwareVersion
     * @param coopId
     * @param phoneSIM
     * @param mac
     */
    private void androidDispose(String imei, String imsi, String platform, String machineId, String isEmulator,
                                String softwareVersion, String coopId, String phoneSIM, String mac) {
        StringBuilder builder = new StringBuilder(" where");
        List<Object> params = new ArrayList<Object>();

        builder.append(" o.imei=?");
        params.add(imei);
        List<UserInf> list = UserInf.getList(builder.toString(), "", params);
        if (list==null||list.size()==0) { //无此用户数据记录,是个新来的用户,创建新用户
            //插入记录
            addUserInf(imei, imsi, machineId, platform, softwareVersion, coopId, isEmulator, phoneSIM, mac);
        } else if (list!=null&&list.size()==1) { //老用户
            UserInf userInf = list.get(0);
            //更新用户信息
            updateUserInf(userInf, imsi, softwareVersion, isEmulator, phoneSIM, coopId);
        }
    }

	/**
	 * 苹果处理
	 * @param imsi
	 * @param platform
	 * @param machineId
	 * @param isEmulator
	 * @param softwareVersion
	 * @param coopId
	 * @param phoneSIM
	 * @param mac
	 */
	private void iphoneDispose(String imsi, String platform, String machineId, String isEmulator, 
			String softwareVersion, String coopId, String phoneSIM, String mac) {
		StringBuilder builder = new StringBuilder(" where");
		List<Object> params = new ArrayList<Object>();
		
		builder.append(" o.mac=?");
		params.add(mac);
		List<UserInf> list = UserInf.getList(builder.toString(), "", params);
		if (list==null||list.size()==0) { //无此用户数据记录,是个新来的用户,创建新用户
			//插入记录
			addUserInf("", imsi, machineId, platform, softwareVersion, coopId, isEmulator, phoneSIM, mac);
		} else if (list!=null&&list.size()==1) { //老用户
			UserInf userInf = list.get(0);
			//更新用户信息
			updateUserInf(userInf, imsi, softwareVersion, isEmulator, phoneSIM, coopId);
		}
	}
	
	/**
	 * 记录用户信息
	 * @param imei
	 * @param imsi
	 * @param machineId
	 * @param platform
	 * @param softwareVersion
	 * @param coopId
	 * @param isEmulator
	 * @param phoneSIM
	 * @param mac
	 */
	private void addUserInf(String imei, String imsi, String machineId, String platform, String softwareVersion, 
			String coopId, String isEmulator, String phoneSIM, String mac) {
		if (StringUtils.equals(platform, Platform.iPhone.value())) {
			imei = mac;
		}
		UserInf userInf = new UserInf();
		userInf.setImei(imei);
		userInf.setImsi(imsi);
		userInf.setCreatetime(new Date());
		userInf.setMachine(machineId);
		userInf.setLastnetconnecttime(new Date());
		userInf.setPlatfrom(platform);
		userInf.setSoftwareversion(softwareVersion);
		userInf.setChannel(coopId);
		userInf.setLastchannel(coopId);
		userInf.setIsemular(isEmulator);
		userInf.setPhoneSIM(phoneSIM);
		userInf.setMac(mac);
		userInf.persist();
	}
	
	/**
	 * 更新用户信息
	 * @param userInf
	 * @param imsi
	 * @param softwareVersion
	 * @param isEmulator
	 * @param phoneSIM
	 */
	private void updateUserInf(UserInf userInf, String imsi, String softwareVersion, String isEmulator, 
			String phoneSIM, String coopId) {
		//原用户的imsi为空,但是这次imsi不是空了,说明用户用上手机卡了
		if(StringUtils.isBlank(userInf.getImsi())&&StringUtils.isNotBlank(imsi)) {
			userInf.setImsi(imsi);
		}
		//版本号变化了需要更新版本
		if(!StringUtils.equals(userInf.getSoftwareversion(), softwareVersion)) { 
			userInf.setSoftwareversion(softwareVersion);
		}
		//是否使用模拟器
		if(StringUtils.isNotBlank(isEmulator)) { 
			userInf.setIsemular(isEmulator);
		}
		//如果数据库中的SIM手机号为空或者有变化则更新
		String dbPhoneSIM = userInf.getPhoneSIM(); //数据库中的SIM手机号
		if (StringUtils.isNotBlank(phoneSIM)&&(StringUtils.isBlank(dbPhoneSIM)||!StringUtils.equals(dbPhoneSIM, phoneSIM))) { 
			userInf.setPhoneSIM(phoneSIM);
		}
		//最新渠道号
		if (StringUtils.isNotBlank(coopId)) {
			userInf.setLastchannel(coopId);
		}
		userInf.setLastnetconnecttime(new Date()); //更新最后联网日期
		userInf.merge();
	}
	
}
