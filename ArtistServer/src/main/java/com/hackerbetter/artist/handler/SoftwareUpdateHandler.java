package com.hackerbetter.artist.handler;

import com.hackerbetter.artist.protocol.ArtistServerInterfaceHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hackerbetter.artist.protocol.ClientInfo;
import com.hackerbetter.artist.service.SoftwareUpdateService;

/**
 * 软件升级
 * @author Administrator
 *
 */
@Service("softwareupdate")
public class SoftwareUpdateHandler implements ArtistServerInterfaceHandler {
	
	@Autowired
	private SoftwareUpdateService softwareUpdateService;

	public String execute(ClientInfo clientInfo) {
		String responseString = softwareUpdateService.getSoftwareUpdateInfo(clientInfo);
		return responseString;
	}
	
}
