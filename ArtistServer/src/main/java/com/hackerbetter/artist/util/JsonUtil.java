package com.hackerbetter.artist.util;

import flexjson.JSONDeserializer;
import flexjson.JSONSerializer;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * JSON转换工具类
 */
public class JsonUtil {

    private static Logger logger= LoggerFactory.getLogger(JsonUtil.class);
	/**
	 * json字符串转换为Map<String,Object>格式
	 * 
	 * @param json
	 * @return
	 */
	public static Map<String, Object> transferJson2Map(String json) {
		if (StringUtils.isBlank(json)) {
			return new HashMap<String, Object>();
		}
		Map<String, Object> result = new JSONDeserializer<Map<String, Object>>().use(null,
				new HashMap<String, Object>().getClass()).deserialize(json);
		return result;
	}

	public static String toJson(Map map) {
		try {
			return new JSONSerializer().serialize(map);
		} catch (Exception e) {
			logger.error("toJson error", e);
		}
        return "";
    }

    public static String add(String json,String key,Object value) {
        JSONObject responseJson = JSONObject.fromObject(json);
        responseJson.put(key, value);
        return responseJson.toString();
    }
}
