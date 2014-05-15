package com.hackerbetter.artist.service;

import com.hackerbetter.artist.consts.ErrorCode;
import com.hackerbetter.artist.domain.Tfavorite;
import com.hackerbetter.artist.domain.Tpainting;
import com.hackerbetter.artist.util.Response;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.hackerbetter.artist.protocol.ClientInfo;

import java.util.List;

import static com.hackerbetter.artist.util.Response.fail;
import static com.hackerbetter.artist.util.Response.paramError;
import static com.hackerbetter.artist.util.Response.success;

/**
 * 用户service
 * @author Administrator
 *
 */
@Service
public class UserInfoService {

	private Logger logger = LoggerFactory.getLogger(UserInfoService.class);
	
	@Autowired
	private CoreService coreService;

	/**
	 * 绑定邮箱
	 * @param clientInfo
	 * @return
	 */
	public String bindEmail(ClientInfo clientInfo) {
    	try {
    		String userno = clientInfo.getUserno(); //用户编号
    		String email = clientInfo.getEmail(); //邮箱
    		if (StringUtils.isBlank(userno)||StringUtils.isBlank(email)) {
    			return paramError(clientInfo.getImei());
    		}
    		String result = coreService.bindEmail(userno, email);
    		if (StringUtils.equals(result, "0")) {
    			return success("绑定成功");
    		}
            return new Response(ErrorCode.get(result)).toJson();
		} catch (Exception e) {
			logger.error("绑定邮箱发生异常", e);
            return fail("系统繁忙");
		} 
    }
	
	/**
	 * 解绑邮箱
	 * @param clientInfo
	 * @return
	 */
	public String removeBindEail(ClientInfo clientInfo) {
    	JSONObject responseJson = new JSONObject();
    	try {
    		String userNo = clientInfo.getUserno(); //用户编号
    		if (StringUtils.isBlank(userNo)) {
                return paramError(clientInfo.getImei());
    		}
    		String result = coreService.bindEmail(userNo, " ");
    		if (StringUtils.equals(result, "0")) {
                return success("解绑成功");
    		}
            return new Response(ErrorCode.get(result)).toJson();
		} catch (Exception e) {
			logger.error("解绑邮箱发生异常", e);
            return fail("系统繁忙");
		} 
    }

    public String collectQuery(ClientInfo clientInfo) {
        String userno= clientInfo.getUserno();//用户编号
        if(StringUtils.isBlank(userno)){
            return fail(ErrorCode.UserMod_UserNoEmpty);
        }
        try {
            List<Long> paintingIds= Tfavorite.getCollectPaintingIdByUserno(Long.parseLong(userno));
            if(null!=paintingIds&&!paintingIds.isEmpty()){
                 List<Tpainting> list=Tpainting.findList(paintingIds);
                 return success("查询成功",list);
            }else{
                return success("无收藏记录");
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
            return fail("系统繁忙");
        }
    }
}
