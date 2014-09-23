package com.hackerbetter.artist.util.common;

import org.apache.commons.lang.StringUtils;

/**
 * 字符串工具类
 * @author Administrator
 *
 */
public class StringUtil {

	/**
	 * 验证参数是否为空
	 * @param string
	 * @return
	 */
	public static boolean isEmpty(String string) {
		if (StringUtils.equals(StringUtils.trimToEmpty(string), "")) {
			return true;
		}
		return false;
	}

    /**
     * 验证参数是否为空
     * @param strs
     * @return
     */
    public static boolean isEmpty(String ...strs) {
        for(String str:strs){
            if(StringUtils.isBlank(str)){
                return true;
            }
        }
        return false;
    }

	/**
	 * 去掉字符串结尾的字符
	 * @param string
	 * @param endCharacter
	 * @return
	 */
	public static String removeEndCharacter(String string, String endCharacter) {
		return StringUtils.stripEnd(string, endCharacter);
	}
	

	
	/**
	 * 去掉开始的0
	 * @param string
	 * @return
	 */
	public static String removeStartZero(String string) {
		return StringUtils.removeStart(string, "0");
	}
	
	public static String join(String split, Object... values) {
		StringBuilder builder = new StringBuilder();
		for(Object s : values) {
			builder.append(s).append(split);
		}
		return removeEndCharacter(builder.toString().trim(), split);
	}
	
	/**
	 * null means "null" || null || ""
	 * @param first
	 * @param second
	 * @return
	 */
	public static String getSecondWhenFirstIsNull(String first,String second){
	    if(StringUtil.isEmpty(first) || "null".equals(first)){
	        return second;
	    }
	    return first;
	}


    /**
     * css样式转json  如"width: 304px; height: 411px;font:宋体"
     * @return
     */
    public static  String cssToJson(String cssStr){
        StringBuilder sb=new StringBuilder("{");
        sb.append(cssStr.replaceAll(";",","));
        sb.append("}");
        return sb.toString();
    }
}
