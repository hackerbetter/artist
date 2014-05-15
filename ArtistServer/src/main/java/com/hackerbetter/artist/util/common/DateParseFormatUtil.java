package com.hackerbetter.artist.util.common;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 日期公共类
 * @author Administrator
 *
 */
public class DateParseFormatUtil {
	
	private static Calendar calendar = Calendar.getInstance();
	private static Logger logger=LoggerFactory.getLogger(DateParseFormatUtil.class);
	/**
	 * 获得今日日期
	 * @return
	 */
	public static String getTodayDate() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		return sdf.format(new Date());
	}
	
	/**
	 * 获得前1个月的日期
	 * @return
	 */
	public static String getPreOneMonthDate() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		calendar.setTime(new Date());
		calendar.add(Calendar.DATE, -30);
		return sdf.format(calendar.getTime());
	}

	/**
	 * 解析日期字符串(yyyy-MM-dd)
	 * @param dateString
	 * @return
	 */
	public static Date parseY_M_d(String dateString) {
		try {
			if (StringUtils.isNotBlank(dateString)) {
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				return sdf.parse(dateString);
			}
		} catch (ParseException e) {
			logger.error("日期格式化异常",e);
		}
		return null;
	} 
	
	/**
	 * 格式化日期时间(yyyy-MM-dd HH:mm:ss)
	 * @param timeString
	 * @return
	 */
	public static String formatDateTime(String timeString) {
		if (StringUtils.isNotBlank(timeString)) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			return sdf.format(Long.parseLong(timeString));
		}
		return "";
	}
	
	/**
	 * 格式化日期(yyyy-MM-dd)
	 * @param dateString
	 * @return
	 */
	public static String formatY_M_d(String dateString) {
		if (StringUtils.isNotBlank(dateString)) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			return sdf.format(Long.parseLong(dateString));
		}
		return "";
	}
	
	/**
	 * 格式化日期(yyyyMMdd)
	 * @param dateString
	 * @return
	 */
	public static String formatYMd(String dateString) {
		if (StringUtils.isNotBlank(dateString)) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
			return sdf.format(Long.parseLong(dateString));
		}
		return "";
	}
	
	/**
	 * 格式化时间(HH:mm:ss)
	 * @param timeString
	 * @return
	 */
	public static String formatTime(String timeString) {
		if (StringUtils.isNotBlank(timeString)) {
			SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
			return sdf.format(Long.parseLong(timeString));
		}
		return "";
	}
	
	/**
	 * 格式化日期时间(yyyy年MM月dd日 HH时mm分ss秒)
	 * @param timeString
	 * @return
	 */
	public static String formatYMd_Hms(String timeString) {
		if (StringUtils.isNotBlank(timeString)) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日 HH时mm分ss秒");
			return sdf.format(Long.parseLong(timeString));
		}
		return "";
	}
	
	/**
	 * 格式化日期时间(yy-MM-dd HH:mm)
	 * @param timeString
	 * @return
	 */
	public static String formatYY_M_d_H_m(String timeString) {
		if (StringUtils.isNotBlank(timeString)) {
			SimpleDateFormat sdf = new SimpleDateFormat("yy-MM-dd HH:mm");
			return sdf.format(Long.parseLong(timeString));
		}
		return "";
	}
	
	/**
	 * 格式化日期时间(MM-dd HH:mm)
	 * @param timeString
	 * @return
	 */
	public static String formatM_d_H_m(String timeString) {
		if (StringUtils.isNotBlank(timeString)) {
			SimpleDateFormat sdf = new SimpleDateFormat("MM-dd HH:mm");
			return sdf.format(Long.parseLong(timeString));
		}
		return "";
	}
	
	/**
	 * 根据指定格式格式化时间
	 * @param time
	 * @param format 时间格式
	 * @return
	 */
	public static String formatDate(String time,String format) {
		if (StringUtils.isNotBlank(time)) {
			SimpleDateFormat sdf = new SimpleDateFormat(format);
			return sdf.format(Long.parseLong(time));
		}
		return "";
	}
	
}
