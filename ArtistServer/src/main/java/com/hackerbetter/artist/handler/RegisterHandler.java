package com.hackerbetter.artist.handler;

import com.hackerbetter.artist.protocol.ArtistServerInterfaceHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.hackerbetter.artist.protocol.ClientInfo;
import com.hackerbetter.artist.service.RegisterService;

/**
 * 注册接口
 * @author Administrator
 *
 */
@Service("register")
public class RegisterHandler implements ArtistServerInterfaceHandler {
	
	@Autowired
	private RegisterService registerService;
	
	public String execute(ClientInfo clientInfo) {
		String responseString = registerService.register(clientInfo);
		return responseString;
	}
	
}
