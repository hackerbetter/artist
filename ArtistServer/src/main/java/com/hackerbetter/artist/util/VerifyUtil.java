package com.hackerbetter.artist.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;

import com.hackerbetter.artist.consts.Constants;
import com.hackerbetter.artist.util.common.Tools;

/**
 * 验证公共类
 * @author Administrator
 *
 */
public class VerifyUtil {

	/**
	 * 验证是否是手机号码
	 * @param mobileId
	 * @return
	 */
	public static boolean isMobile(String mobileId) {
		boolean isMobile = false;
		Pattern pattern = Pattern.compile(Constants.mobile_pattern);
		Matcher matcher = pattern.matcher(mobileId);
		if (matcher.matches()) {
			isMobile = true;
		}
		return isMobile;
	}
	
	/**
	 * 验证身份证号
	 * @param certId
	 * @return
	 */
	public static boolean isCertId(String certId) {
		boolean isCertId = false;
		String pattern_str = "\\d{15}||\\d{14}[x,X]||\\d{18}||\\d{17}[x,X]" //大陆身份证
			+"||[A-Za-z]\\d{10}"//回乡证
			+"||[A-Za-z]\\d{6}\\([A-Za-z0-9]\\)" //香港身份证
			+"||\\d{7}\\([0-9]\\)" //澳门身份证
			+"||[A-Za-z]\\d{9}"; //台湾身份证
		Pattern pattern = Pattern.compile(pattern_str);
		Matcher matcher = pattern.matcher(certId);
		if (matcher.matches()) {
			isCertId = true;
		}
		return isCertId;
	}
	
	/**
	 * 验证是否是中文
	 * @param string
	 * @return
	 */
	public static boolean isChinese(String string) {
		String regex = "[\\u4E00-\\u9FA5]+";
		if (!Tools.isEmpty(string)&&string.matches(regex)) {
			return true;
		}
		return false;
	}
	
	/**
	 * 验证昵称的合法性
	 * @param nickName
	 * @return
	 */
	public static boolean verifyNickName(String nickName) {
		if (!Tools.isEmpty(nickName)) {
			int length = 0;
			for (int i = 0; i < nickName.length(); i++) {
				String temp = nickName.substring(i, i + 1);
				if (isChinese(temp)) { //是中文
					length += 2;
				} else {
					length += 1;
				}
			}
			if (length>=4&&length<=16) { //4-16个字符(1个汉字=2个字符)
				return true;
			}
		}
		return false;
	}
	
	/**
	 * 验证被赠送手机号码的合法性
	 * @param mobile
	 * @return
	 */
	public static boolean verifyGiftedMobile(String mobile) {
		Pattern pattern = Pattern.compile("((14[0-9]|13[0-9]|15[0-9]|18[0-9])\\d{8})(,(14[0-9]|13[0-9]|15[0-9]|18[0-9])\\d{8})*");
		Matcher matcher = pattern.matcher(mobile);
		if (StringUtils.isBlank(mobile)||!matcher.matches()) {
			return false;
		}
		return true;
	}
	
}
