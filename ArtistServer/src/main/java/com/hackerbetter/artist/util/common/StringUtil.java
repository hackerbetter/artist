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
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isEmpty(String string) {
		if (StringUtils.equals(StringUtils.trimToEmpty(string), "")) {
			return true;
		}
		
		/*if (StringUtils.isEmpty(string)) {
			return true;
		}
		if (string.trim().equals("")) {
			return true;
		}*/
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
		
		/*if (!isEmpty(string)&&string.endsWith(endCharacter)) {
			string = string.substring(0, string.length()-endCharacter.length());
		}
		return string;*/
	}
	
	/**
	 * 将字符串数组用符合连接
	 * @param list
	 * @param character
	 * @return
	 */
	/*public static String joinStringArrayWithCharacter(List<String> list, String character) {
		StringBuilder builder = new StringBuilder();
		if (list!=null&&list.size()>0) {
			for (String string : list) {
				if (!isEmpty(string)) {
					builder.append(string).append(character);
				}
			}
		}
		return removeEndCharacter(builder.toString(), character);
	}*/
	
	/**
	 * 去掉开始的0
	 * @param multiple
	 * @return
	 */
	public static String removeStartZero(String string) {
		return StringUtils.removeStart(string, "0");
		
		/*if (string!=null && string.length()==2 && string.startsWith("0")) {
			return string.substring(1);
		}
		return string;*/
	}
	
	public static String join(String split, String... values) {
		StringBuilder builder = new StringBuilder();
		for(String s : values) {
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
	
}
