package com.artist.cms.util;

import com.artist.cms.exception.ArtistException;
import org.codehaus.jackson.map.DeserializationConfig.Feature;
import org.codehaus.jackson.map.ObjectMapper;


/**
 * JSON转换工具类
 */
public class JsonUtil {

	private static ObjectMapper objectMapper = new ObjectMapper();

	public static String toJson(Object obj) {
		try {
			return objectMapper.writeValueAsString(obj);
		} catch (Exception e) {
			throw new ArtistException("toJson error", e);
		}
	}

	public static <T> T fromJsonToObject(String body, Class<T> clazz) {
		try {
			objectMapper.configure(Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);
			return objectMapper.readValue(body, clazz);
		} catch (Exception e) {
			throw new ArtistException(" error", e);
		}
	}

	public static void main(String[] args) {
		//		String j = "[{\"imei\":\"1\",\"platform\":\"2\",\"version\":\"1\"},{\"imei\":\"qqq\",\"platform\":\"2\",\"version\":\"1\"}]";
		//		JSONDeserializer serializer = new JSONDeserializer(); 
		//		Object o=serializer.deserialize(j, Tgrayupgrade.class);
		//		String products = "[{\"product_id\": \"123\",\"name\":\"stack\"},{\"product_id\": \"456\",\"name\":\"overflow\"}]";
		//		List<Tgrayupgrade> productInfoList = new JSONDeserializer<List<Tgrayupgrade>>().use(null, ArrayList.class)
		//				.use("values", Tgrayupgrade.class).deserialize(j);
		//		for (Tgrayupgrade a : productInfoList)
		//			System.out.println(a);
		//		j=j.substring(1,j.length());
		//		String[] aa=j.split("},");
		//		for(String a:aa){
		//			System.out.println(a);
		//		Tgrayupgrade t=	JsonUtil.fromJsonToObject(a, Tgrayupgrade.class);
		//		System.out.println(t);
		//		}
	}
}
