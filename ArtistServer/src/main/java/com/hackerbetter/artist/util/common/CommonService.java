package com.hackerbetter.artist.util.common;

import com.hackerbetter.artist.protocol.ClientInfo;
import com.hackerbetter.artist.service.CoreService;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;


@Service
public class CommonService {

	private Logger logger = LoggerFactory.getLogger(CommonService.class);
	
	@Autowired
	private CoreService coreService;
    /**
     * 得到UserNo
     * @param clientInfo
     * @return
     */
    public String getUserno(ClientInfo clientInfo) {
        String userno = clientInfo.getUserno();
        if (StringUtils.isBlank(userno)) { // 客户端没传userNo,就根据用户名去取
            userno = getUserNoByUsername(clientInfo.getUsername());
        }
        return userno;
    }
	/**
	 * 查询用户信息
	 * @param clientInfo
	 * @return
	 */
	public String getUserByUsernoOrUsername(ClientInfo clientInfo) {
		String userno = clientInfo.getUserno();
        try {
            if (StringUtils.isBlank(userno)) { // 客户端没传userno,就根据username查找
                return coreService.queryUsersByUserName(clientInfo.getUsername());
            }
            return coreService.queryUsersByUserno(userno);
        } catch (UnsupportedEncodingException e) {
            logger.error("查找用户信息异常",e);
        }
        return null;
    }
	
	/**
	 * 根据用户名获取userno
	 * @param username
	 * @return
	 */
	public String getUserNoByUsername(String username) {
		if (StringUtils.isBlank(username)) {
			return "";
		}
		try {
			String result = coreService.queryUsersByUserName(username);
			if (StringUtils.isBlank(result)) {
				return "";
			}
			JSONObject fromObject = JSONObject.fromObject(result);
			if (fromObject!=null) {
				String errorCode = fromObject.getString("errorCode");
				if (StringUtils.equals(errorCode, "0000")) {
					JSONObject valueObject = fromObject.getJSONObject("value");
					return valueObject.getString("userno");
				}
			}
		} catch (Exception e) {
			logger.error("根据用户名获取userno发生异常", e);
		}
		return "";
	}

	
	
}
