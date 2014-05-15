package com.hackerbetter.artist.util.parse;

import com.hackerbetter.artist.consts.Constants;
import com.hackerbetter.artist.protocol.ClientInfo;
import com.hackerbetter.artist.servlet.UploadFileRequestHandler;
import net.sf.json.JSONObject;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.lang.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 解析请求参数
 * @author Administrator
 *
 */
public class RequestParamUtil {

	/**
	 * 解析参数
	 * @param request
	 * @param requestObject
	 * @param clientInfo
	 */
	public static void parseRequestParam(HttpServletRequest request, JSONObject requestObject, ClientInfo clientInfo, List<FileItem> items) {
		//基本参数
		clientInfo.setCommand(requestObject.getString(Constants.command)); //请求命令
		clientInfo.setSysSessionid(request.getSession().getId()); //SessionId
		
		if (requestObject.has(Constants.imei)) { //手机标识
			clientInfo.setImei(requestObject.getString(Constants.imei));
		}
		if (requestObject.has(Constants.imsi)) { //SIM卡标识
			clientInfo.setImsi(requestObject.getString(Constants.imsi));
		}
		if (requestObject.has(Constants.softwareVersion)) {
			clientInfo.setSoftwareVersion(requestObject.getString(Constants.softwareVersion)); //版本号
		}
		if (requestObject.has(Constants.machineId)) {
			clientInfo.setMachineId(requestObject.getString(Constants.machineId)); //机型
		}
		if (requestObject.has(Constants.coopId)) {
			clientInfo.setCoopId(requestObject.getString(Constants.coopId)); //渠道号
		}
		if (requestObject.has(Constants.platform)) {
			clientInfo.setPlatform(requestObject.getString(Constants.platform)); //平台
		}
		if (requestObject.has(Constants.isCompress)) { //是否压缩
			clientInfo.setIsCompress(requestObject.getString(Constants.isCompress));
		}
		if (requestObject.has(Constants.isemulator)) { //是否是模拟器联网
			clientInfo.setIsemulator(requestObject.getString(Constants.isemulator));
		}
		if (requestObject.has(Constants.phoneSIM)) { //SIM卡手机号
			clientInfo.setPhoneSIM(requestObject.getString(Constants.phoneSIM));
		}
		if (requestObject.has(Constants.mac)) { //网卡地址
			clientInfo.setMac(requestObject.getString(Constants.mac));
		}
		if (requestObject.has(Constants.loginType)) { //登陆类型（alipay,qq,sina,normal）
			clientInfo.setLoginType(requestObject.getString(Constants.loginType));
		}
		
		//类型
		if (requestObject.has(Constants.requestType)) { //请求类型
			clientInfo.setRequestType(requestObject.getString(Constants.requestType));
		}
		if (requestObject.has(Constants.type)) { //查询类型
			clientInfo.setType(requestObject.getString(Constants.type));
		}
        if (requestObject.has(Constants.imageType)) { //图片类型
            clientInfo.setImageType(requestObject.getString(Constants.imageType));
        }
        if (requestObject.has(Constants.item)) { //栏目
            clientInfo.setItem(requestObject.getString(Constants.item));
        }
        if (requestObject.has(Constants.paintingId)) { //作品id
            clientInfo.setPaintingId(requestObject.getString(Constants.paintingId));
        }
		//查询
		if (requestObject.has(Constants.startDate)) { //开始时间
			clientInfo.setStartDate(requestObject.getString(Constants.startDate));
		}
		if (requestObject.has(Constants.endDate)) { //结束时间
			clientInfo.setEndDate(requestObject.getString(Constants.endDate));
		}
		if (requestObject.has(Constants.maxResult)) { //每页显示多少条
			clientInfo.setMaxresult(requestObject.getString(Constants.maxResult));
		}
		if (requestObject.has(Constants.startLine)) { //起始行
			clientInfo.setStartLine(requestObject.getString(Constants.startLine));
		}
		if (requestObject.has(Constants.stopLine)) { //结束行
			clientInfo.setStopLine(requestObject.getString(Constants.stopLine));
		}
		if (requestObject.has(Constants.pageIndex)) { //第几页
			clientInfo.setPageindex(requestObject.getString(Constants.pageIndex));
		}
		if (requestObject.has(Constants.sessionId)) { //sessionID
			clientInfo.setSessionid(requestObject.getString(Constants.sessionId));
		}




		if (requestObject.has(Constants.username)) { //DNA 手机号码
			clientInfo.setUsername(requestObject.getString(Constants.username));
		}
		if (requestObject.has(Constants.certId)) { //DNA 身份证号
			clientInfo.setCertid(requestObject.getString(Constants.certId));
		}



		if (requestObject.has(Constants.mobileId)) { //手机号码
			clientInfo.setMobileId(requestObject.getString(Constants.mobileId));
		}


		if (requestObject.has(Constants.keyStr)) { //键
			clientInfo.setKeyStr(requestObject.getString(Constants.keyStr));
		}
		
		//用户信息
		if (requestObject.has(Constants.userNo)) { //用户编号
			clientInfo.setUserno(requestObject.getString(Constants.userNo));
		}
		if (requestObject.has(Constants.nickname)) { //昵称
			clientInfo.setNickname(requestObject.getString(Constants.nickname));
		}
		if (requestObject.has(Constants.password)) { //密码
			clientInfo.setPassword(requestObject.getString(Constants.password));
		}
		if (requestObject.has(Constants.name)) { //名字
			clientInfo.setName(requestObject.getString(Constants.name));
		}
		if (requestObject.has(Constants.email)) { //邮箱
			clientInfo.setEmail(requestObject.getString(Constants.email));
		}
		
		//用户中心
		if (requestObject.has(Constants.bindPhoneNum)) { //绑定的手机号码
			clientInfo.setBindPhoneNum(requestObject.getString(Constants.bindPhoneNum));
		}
		if (requestObject.has(Constants.securityCode)) { //验证码
			clientInfo.setSecurityCode(requestObject.getString(Constants.securityCode));
		}

		
		//修改密码
		if (requestObject.has(Constants.old_pass)) { //旧密码
			clientInfo.setOldPass(requestObject.getString(Constants.old_pass));
		}
		if (requestObject.has(Constants.new_pass)) { //新密码
			clientInfo.setNewPass(requestObject.getString(Constants.new_pass));
		}
		
		//登录
		if (requestObject.has(Constants.isAutoLogin)) { //是否自动登录
			clientInfo.setIsAutoLogin(requestObject.getString(Constants.isAutoLogin));
		}
		if (requestObject.has(Constants.randomNumber)) { //自动登录的随机数
			clientInfo.setRandomNumber(requestObject.getString(Constants.randomNumber));
		}
		if (requestObject.has(Constants.source)) { //来源(联合登录)
			clientInfo.setSource(requestObject.getString(Constants.source));
		}
		if (requestObject.has(Constants.openId)) { //第三方用户标识(联合登录)
			clientInfo.setOpenId(requestObject.getString(Constants.openId));
		}
		if (requestObject.has(Constants.alias)) { //客户端别名(用于安卓推送)
			clientInfo.setAlias(requestObject.getString(Constants.alias));
		}
		
		//注册
		if (requestObject.has(Constants.recommender)) { //推荐人的用户名
			clientInfo.setRecommender(requestObject.getString(Constants.recommender));
		}
		if (requestObject.has(Constants.agencyNo)) { //默认的代理编号
			clientInfo.setAgencyNo(requestObject.getString(Constants.agencyNo));
		}
		
		//消息设置
		if (requestObject.has(Constants.token)) { //iPhone手机标识
			clientInfo.setToken(requestObject.getString(Constants.token));
		}
		if (requestObject.has(Constants.info)) { //信息
			clientInfo.setInfo(requestObject.getString(Constants.info));
		}

		//短信通知
		if(requestObject.has(Constants.smstype)){//短信类型， 1为开奖 2为中奖
			clientInfo.setSmstype(requestObject.getString(Constants.smstype));
		}
		if(requestObject.has(Constants.needToSend)){//开关状态，0:关闭发送,1:打开发送
			clientInfo.setNeedToSend(requestObject.getString(Constants.needToSend));
		}


		//帮助中心
		if (requestObject.has(Constants.code)) { //key值
			clientInfo.setCode(requestObject.getString(Constants.code));
		}

	}
	
}
