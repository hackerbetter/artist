package com.hackerbetter.artistcore.utils;

import com.hackerbetter.artistcore.exception.ArtistException;
import flexjson.JSONDeserializer;
import org.apache.commons.lang.StringUtils;
import org.codehaus.jackson.map.DeserializationConfig.Feature;
import org.codehaus.jackson.map.ObjectMapper;

import java.util.HashMap;
import java.util.Map;

/**
 * JSON转换工具类
 */
public class JsonUtil {

	private static ObjectMapper objectMapper = new ObjectMapper();

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

	public static String toJson(Object obj) {
		try {
			return objectMapper.writeValueAsString(obj);
		} catch (Exception e) {
			throw new ArtistException("OrderRequest.toJson error", e);
		}
	}

	public static <T> T fromJsonToObject(String body, Class<T> clazz) {
		try {
			objectMapper.configure(Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);
			return objectMapper.readValue(body, clazz);
		} catch (Exception e) {
			throw new ArtistException("OrderRequest.fromJsonToOrderRequest error", e);
		}
	}
	
	public static String parseWithTag(String json, String tag) {
		json = json.substring(json.indexOf(tag) + tag.length());
		StringBuilder builder = new StringBuilder();
		boolean append = false;
		int index = 0;
		for(int i = 0; i < json.length(); i ++) {
			char c = json.charAt(i);
			if('{' == c) {
				append = true;
				index ++;
			}
			if('}' == c) {
				index --;
				if(0 == index) {
					builder.append(c);
					break;
				}
			}
			if(append) {
				builder.append(c);
			}
		}
		return builder.toString();
	}
}
