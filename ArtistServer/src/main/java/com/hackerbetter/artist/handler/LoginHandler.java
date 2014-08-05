package com.hackerbetter.artist.handler;


import com.hackerbetter.artist.protocol.ArtistServerInterfaceHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hackerbetter.artist.protocol.ClientInfo;
import com.hackerbetter.artist.service.LoginService;

/**
 * 登录
 * @author Administrator
 *
 */
@Service("login")
public class LoginHandler implements ArtistServerInterfaceHandler {
	private Logger logger = LoggerFactory.getLogger(LoginHandler.class);
	
	@Autowired
    private LoginService loginService;

	public String execute(ClientInfo clientInfo) {
		String responseString = loginService.login(clientInfo);
        logger.info("登录返回:"+responseString+",Imei="+clientInfo.getImei());
		return responseString;
	}

}
