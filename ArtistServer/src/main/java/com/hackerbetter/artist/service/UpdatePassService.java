package com.hackerbetter.artist.service;

import static com.hackerbetter.artist.util.Response.*;
import com.hackerbetter.artist.util.common.CommonService;
import com.hackerbetter.artist.util.common.Tools;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.hackerbetter.artist.protocol.ClientInfo;

/**
 * 修改密码
 * @author Administrator
 *
 */
@Service
public class UpdatePassService {
	
	private Logger logger = LoggerFactory.getLogger(UpdatePassService.class);
    
	@Autowired
	private CoreService coreService;

    @Autowired
    private CommonService commonService;

	/**
     * 重置密码
     * @param clientInfo
     * @return
     */
    public String updatePassword(ClientInfo clientInfo){
    	try {
    		String result = commonService.getUserByUsernoOrUsername(clientInfo);
    		if (StringUtils.isBlank(result)) { // 如果返回空,参数错误
    			return paramError(clientInfo.getImei());
    		}
			JSONObject fromObject = JSONObject.fromObject(result);
			if (fromObject==null) {
    			return paramError(clientInfo.getImei());
			}
			String errorCode = fromObject.getString("errorCode");
			if (!StringUtils.equals(errorCode, "0000")) {
                return fail("修改失败");
            }
            JSONObject valueObject = fromObject.getJSONObject("value");
            if (valueObject!=null) {
                String userno=valueObject.getString("userno");
                String dbPassword = valueObject.getString("password");
                String pwd = Tools.EncoderByMd5(clientInfo.getOldPass());
                if (StringUtils.isNotBlank(dbPassword)&&StringUtils.equals(dbPassword, pwd)) { //原密码正确
                    String resetPwdResult = coreService.resetPassword(userno, clientInfo.getNewPass());
                    if (StringUtils.equals(resetPwdResult, "0000")) {
                       return success("修改成功");
                    }
                } else {
                      return fail("原密码错误");
                }
            }
		} catch (Exception e) {
			logger.error("重置密码发生异常", e);
            return fail("系统繁忙");
		}
        return paramError(clientInfo.getImei());
    }

}
