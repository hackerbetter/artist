package com.artist.cms.util;

import org.apache.commons.lang.StringUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtil {

	public static boolean isEmpty(String str) {
		if (StringUtils.isEmpty(str))
			return true;
		if ("".equals(str.trim()))
			return true;
		return false;
	}

	public static boolean isEmpty(Character c) {
		if (null == c)
			return true;
		if ("".equals(c))
			return true;
		return false;
	}

	public static boolean isInt(String str) {
		return str.matches("^[0-9]*$");
	}

	// 嗖付支付传过来的金额是"分"
	public static boolean isFen(String str) {
		return str.matches("^[0-9]+$");
	}
	
	public static String join(String split, String... values) {
		StringBuilder builder = new StringBuilder();
		for(String s : values) {
			builder.append(s).append(split);
		}
		return StringUtils.stripEnd(builder.toString().trim(), split);
	}
    public static String buildImgTag(String url){
        if(StringUtils.isBlank(url)){
            return "";
        }
        return new StringBuilder("<img src=\"").append(url).append("\"/>").toString();
    }

    public static String getImageSrc(String html){
        String IMGSRC_REG = "http:\"?(.*?)(\"|>|\\s+)";//匹配图片链接
        Matcher matcher = Pattern.compile(IMGSRC_REG).matcher(html);
        if (matcher.find()) {
            return matcher.group().substring(0, matcher.group().length() - 1);
        }
        return "";
    }
}
