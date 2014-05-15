package com.hackerbetter.artistcore.service;

import com.hackerbetter.artistcore.constants.Const;
import com.hackerbetter.artistcore.constants.ErrorCode;
import com.hackerbetter.artistcore.domain.TuserInfo;
import com.hackerbetter.artistcore.utils.PaySign;
import com.hackerbetter.artistcore.utils.VerifyUtil;
import com.hackerbetter.artistcore.exception.ArtistException;
import org.apache.camel.Produce;
import org.apache.camel.ProducerTemplate;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
@Transactional
public class TuserinfoService {

	private Logger logger = LoggerFactory.getLogger(TuserinfoService.class);

	@Produce(uri = "jms:topic:userCreated")
	private ProducerTemplate userCreatedProducer;

	@Produce(uri = "jms:topic:userOpened")
	private ProducerTemplate userOpenedProducer;

	@Produce(uri = "jms:topic:userModify")
	private ProducerTemplate userModifyProducer;

	/**
	 * 用户注册方法
	 * @param userInfo 用户对象
	 * @return
	 * @throws ArtistException
	 */
	@Transactional
	public void register(TuserInfo userInfo) throws ArtistException {

		long startMillis = System.currentTimeMillis();
		logger.info("用户注册操作开始，开始时间: " + startMillis);
		logger.info("UserInfo: " + userInfo);
		logger.info("开始校验数据合法性");
		checkUserInfo(userInfo);
		trimUserInfo(userInfo);
		logger.info("开始校验用户是否已注册过");

		TuserInfo userByUsername = getUserInfoByUserName(userInfo.getUsername());
		checkUserInfoExists(userByUsername);

		// 用户没有注册过处理
		if (null == userByUsername) {
			if (userInfo.getNickname() != null && !userInfo.getNickname().trim().isEmpty()) {
				List<TuserInfo> resultList = TuserInfo.findTuserInfosByNickname(userInfo.getNickname());
				if (resultList != null && !resultList.isEmpty()) {
					throw new ArtistException(ErrorCode.UserReg_NicknameExists);
				}
			}
			doRegister(userInfo, new Date());
		} else {
			// 关闭用户开通
			doOpen(userInfo, userByUsername, new Date());
		}
		long endMillis = System.currentTimeMillis();
		logger.info("用户注册操作结束， 结束时间：" + endMillis + "，用时: " + (endMillis - startMillis));
	}

	/**
	 * 修改用户信息 根据userno修改用户信息，忽略平台传参错误情况，平台选择性的修改用户信息 绑定手机号，绑定邮箱依此方法
	 * 此方法修改用户信息忽略用户状态
	 * 
	 * @param params
	 * @return
	 */
	@Transactional
	public TuserInfo modify(Map<String, String> params) throws ArtistException {

		long startMillis = System.currentTimeMillis();
		logger.info("用户修改信息操作开始，开始时间: " + startMillis);
		logger.info("Paramter: " + params);
		logger.info("开始校验数据合法性");
		checkParams(params);
		logger.info("根据userno获取用户");
		TuserInfo userinfo = TuserInfo.findTuserInfo(params.get(Const.Tuserinfo_Userno));
		if (null == userinfo) {
			logger.info("用户修改信息用户不存在");
			throw new ArtistException(ErrorCode.UserMod_UserNotExists);
		}
		logger.info("UserInfo: " + userinfo.toString());
		checkUserInfo(userinfo);
		if (params.get(Const.Tuserinfo_NickName) != null && !params.get(Const.Tuserinfo_NickName).trim().isEmpty()) {
			if (!params.get(Const.Tuserinfo_NickName).equals(userinfo.getNickname())) {
				List<TuserInfo> resultList =TuserInfo.findTuserInfosByNickname(params.get(Const.Tuserinfo_NickName));
				if (resultList != null && !resultList.isEmpty()) {
					throw new ArtistException(ErrorCode.UserReg_NicknameExists);
				}
			}
		}

		logger.info("开始修改用户信息");
		doModify(userinfo, params);

		long endMillis = System.currentTimeMillis();
		logger.info("用户修改信息操作结束， 结束时间：" + endMillis + "，用时: " + (endMillis - startMillis));

		return userinfo;
	}

	/**
	 * 执行用户信息修改
	 * 
	 * @param userinfo
	 * @param params
	 * @return
	 */
	private void doModify(TuserInfo userinfo, Map<String, String> params) {
		// 暂时允许修改用户名[孙扬2012-09-12 17:34:37]
		/*
		 * String userName = params.get(Const.Tuserinfo_UserName); if
		 * (!StringUtil.isEmpty(userinfo.getUserName()) && null != userName) {
		 * 
		 * logger.info("用户信息修改用户名不允许修改"); throw new
		 * ArtistException(ErrorCode.UserMod_UserNameNoAllowMod); }
		 */
		Set<String> keySet = params.keySet();
		for (String key : keySet) {

			String value = params.get(key);
			if ("password".equals(key)) { // 如果是密码则进行加密

				try {
					value = PaySign.EncoderByMd5(value);
				} catch (Exception e) {
					logger.error("用户修改信息加密密码出错: " + e.getMessage());
					throw new ArtistException(ErrorCode.UserReg_PassMD5Error);
				}
			}
		}
		userinfo.setModTime(new Date());
		logger.info("modifyUserInfo,params：" + params);
		logger.info("修改前UserInfo：" + userinfo.toString() + "password:" + userinfo.getPassword());
		userinfo.merge();
		logger.info("修改后UserInfo：" + userinfo.toString() + "password:" + userinfo.getPassword());
		try {
			userModifyProducer.sendBody(userinfo.toJson());
		} catch (Exception e) {
		}
	}

	/**
	 * 根据平台参数校验用户信息
	 * 
	 * @param userinfo
	 * @param params
	 * @return
	 */
	public void checkUserInfo(TuserInfo userinfo, Map<String, String> params) {

		String username = params.get(Const.Tuserinfo_UserName);
		if (null == username) {
			return;
		}
		if (!userinfo.getUsername().equals(username)) {
			logger.info("用户修改信息用户名不允许修改");
			throw new ArtistException(ErrorCode.UserMod_UsernameNotallowMod);
		}
	}

	/**
	 * 校验平台参数
	 * 
	 * @param params
	 * @return
	 */
	private void checkParams(Map<String, String> params) {

		if (StringUtils.isEmpty(params.get(Const.Tuserinfo_Userno))) {

			logger.info("用户修改信息userno为空");
			throw new ArtistException(ErrorCode.UserMod_UserNoEmpty);
		}
		String mobileid = params.get(Const.Tuserinfo_Mobileid);
		if (!StringUtils.isEmpty(mobileid)) {
			if (!VerifyUtil.isPhone(mobileid)) {
				logger.info("用户修改信息手机号不合法");
				throw new ArtistException(ErrorCode.UserReg_MobileIdError);
			}
		}
		String email = params.get(Const.Tuserinfo_Email);
		if (StringUtils.isNotBlank(email)) {
			if (!VerifyUtil.isEmail(email)) {
				logger.info("用户修改信息邮箱不合法");
				throw new ArtistException(ErrorCode.UserReg_EmailError);
			}
		}
	}

	/**
	 * 开通关闭用户
	 * 
	 * @param userInfo
	 * @param user
	 * @param now
	 * @return
	 * @throws ArtistException
	 */
	private void doOpen(TuserInfo userInfo, TuserInfo user, Date now) {
		userInfo.setUserno(user.getUserno());
		userInfo.setRegTime(now);
		userInfo.setModTime(now);
		userInfo.setState(1);
		try {
			userInfo.setPassword(PaySign.EncoderByMd5(userInfo.getPassword()));
		} catch (Exception e) {

			logger.error("用户注册加密密码出错: " + e.getMessage());
			throw new ArtistException(ErrorCode.UserReg_PassMD5Error);
		}
        userInfo.merge();
		userOpenedProducer.sendBody(userInfo.toJson());
	}

	/**
	 * 验证用户是否存在
	 * 
	 * @param user
	 * @return
	 */
	private void checkUserInfoExists(TuserInfo user) {
		if (user == null) {
			return;
		}
		if (user.getState()==1) {
			throw new ArtistException(ErrorCode.UserReg_UserExists);
		}
		if (user.getState()==2) {
			throw new ArtistException(ErrorCode.UserReg_UserPause);
		}
	}

	/**
	 * 为用户注册方法
	 * 
	 * @param userInfo
	 * @param now
	 * @return
	 * @throws ArtistException
	 */
	private void doRegister(TuserInfo userInfo, Date now) {

		userInfo.setRegTime(now);
		userInfo.setModTime(now);
		try {
			logger.info("mobile_phome:" + userInfo.getMobile() + ",password:" + userInfo.getPassword() + ",aa");
			userInfo.setPassword(PaySign.EncoderByMd5(userInfo.getPassword()));
		} catch (Exception e) {
			logger.error("用户注册加密密码出错: ", e);
			throw new ArtistException(ErrorCode.UserReg_PassMD5Error);
		}
		userInfo.persist();
		logger.info("添加用户 UserInfo: " + userInfo.toString());
		try {
			userCreatedProducer.sendBody(userInfo.toJson());
		} catch (Exception e) {
		}
	}

	/**
	 * 根据用户名查找用户
	 * @param userName
	 * @return
	 */
	private TuserInfo getUserInfoByUserName(String userName) {

		logger.info("根据用户名查找用户");
		if (!StringUtils.isEmpty(userName)) {
			TuserInfo tuserinfo = TuserInfo.findTuserInfoByUsername(userName);
			if (null != tuserinfo) {
				logger.info("UserInfoByUserName: " + tuserinfo);
				return tuserinfo;
			}
		}
		return null;
	}

	/**
	 * 去除用户信息两边空格
	 * 
	 * @param userInfo
	 */
	private void trimUserInfo(TuserInfo userInfo) {
		if (StringUtils.isNotBlank(userInfo.getUsername())) {
			// 去除用户名左右空格
			userInfo.setUsername(StringUtils.trim(userInfo.getUsername()));
		}
		if (StringUtils.isNotBlank(userInfo.getNickname())) {
			// 去除昵称左右空格
			userInfo.setNickname(StringUtils.trim(userInfo.getNickname()));
		}
	}

	/**
	 * 验证用户信息
	 * 
	 * @param userInfo
	 * @return
	 */
	private void checkUserInfo(TuserInfo userInfo) {

		if (StringUtils.isBlank(userInfo.getUsername())) {
			logger.error("用户名不能为空");
			throw new ArtistException(ErrorCode.UserReg_UsernameError);
		}
		if (!StringUtils.isEmpty(userInfo.getMobile())) {
			if (!VerifyUtil.isPhone(userInfo.getMobile())) {
				logger.error("用户注册手机号不合法");
				throw new ArtistException(ErrorCode.UserReg_MobileIdError);
			}
		}
		if (!StringUtils.isEmpty(userInfo.getEmail())) {
			if (!VerifyUtil.isEmail(userInfo.getEmail())) {
				logger.error("用户注册邮件不合法");
				throw new ArtistException(ErrorCode.UserReg_EmailError);
			}
		}
		if (StringUtils.isEmpty(userInfo.getPassword())) {
			userInfo.setPassword("000000");
		}
	}

}
