package com.hackerbetter.artist.controller;

import com.hackerbetter.artist.protocol.ArtistServerInterfaceHandler;
import com.hackerbetter.artist.protocol.ClientInfo;
import com.hackerbetter.artist.util.common.SpringUtils;

/**
 * 请求转发
 * @author Administrator
 *
 */
public class RequestDispatcher {

	public static String dispatch(ClientInfo clientInfo) {
		ArtistServerInterfaceHandler handler = SpringUtils.getBean(clientInfo.getCommand());
		return handler.execute(clientInfo);
	}

}
