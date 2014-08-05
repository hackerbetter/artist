package com.hackerbetter.artist.handler;

import com.hackerbetter.artist.protocol.ArtistServerInterfaceHandler;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.hackerbetter.artist.protocol.ClientInfo;
import com.hackerbetter.artist.service.UserInfoService;

import static com.hackerbetter.artist.util.Response.paramError;

/**
 * 用户相关请求
 * @author Administrator
 *
 */
@Service("userInfo")
public class UserInfoHandler implements ArtistServerInterfaceHandler {
	
	@Autowired
    private  UserInfoService userInfoService;
	
	public String execute(ClientInfo clientInfo) {
		String responseString = "";
		
		String requestType = clientInfo.getRequestType(); //请求类型
		if (StringUtils.equals(requestType, "bindEmail")) { //绑定邮箱
			responseString = userInfoService.bindEmail(clientInfo);
		} else if (StringUtils.equals(requestType, "removeBindEmail")) { //解绑邮箱
			responseString = userInfoService.removeBindEail(clientInfo);
        } else if (StringUtils.equals(requestType, "collectQuery")) { //查询我的收藏
            responseString = userInfoService.collectQuery(clientInfo);
		} else { //参数错误
			responseString =paramError(clientInfo.getImei());
		}
		return responseString;
	}

}
