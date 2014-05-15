package com.artist.cms.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class DateUtils {
  /**
   * 
   * @param strDate
   * @param rd
   * @return
   */
	public static java.util.Date addDay(Date strDate, int rd) {
		if (strDate == null)
			return null;
		GregorianCalendar calendar = new GregorianCalendar();
		calendar.setTime(strDate);
		calendar.add(GregorianCalendar.DATE, rd);
		strDate = calendar.getTime();
		return strDate;
	}

	
	public static Date getthisHalfMonth() {
		GregorianCalendar d = new GregorianCalendar();
		if (d.get(Calendar.DAY_OF_MONTH) >= 15) {
			d.set(Calendar.DAY_OF_MONTH, 15);
			d.set(Calendar.HOUR_OF_DAY, 0);
			d.set(Calendar.MINUTE, 0);
			d.set(Calendar.SECOND, 0);
			d.set(Calendar.MILLISECOND, 0);
		} else {
			d.set(Calendar.DAY_OF_MONTH, 1);
			d.set(Calendar.HOUR_OF_DAY, 0);
			d.set(Calendar.MINUTE, 0);
			d.set(Calendar.SECOND, 0);
			d.set(Calendar.MILLISECOND, 0);
		}
		return d.getTime();
	}

	public static Date getthisOneWeek() {
		GregorianCalendar d = new GregorianCalendar();
		if (d.get(Calendar.DAY_OF_WEEK) == 1) {
			d.add(Calendar.DATE, -6);
		}
		if (d.get(Calendar.DAY_OF_WEEK) == 2) {
			d.add(Calendar.DATE, -0);
		} else
			d.add(Calendar.DATE, -(d.get(Calendar.DAY_OF_WEEK) - 2));
		d.set(Calendar.HOUR_OF_DAY, 0);
		d.set(Calendar.MINUTE, 0);
		d.set(Calendar.SECOND, 0);
		d.set(Calendar.MILLISECOND, 0);
		return d.getTime();
	}
	public static Date getFirstDayOfMonth() {
		GregorianCalendar d = new GregorianCalendar();
		d.set(Calendar.DAY_OF_MONTH, 1);
		d.set(Calendar.HOUR_OF_DAY, 0);
		d.set(Calendar.MINUTE, 0);
		d.set(Calendar.SECOND, 0);
		return d.getTime();
	}

	public static Date getOneWeekAgo() {
		GregorianCalendar d = new GregorianCalendar();
		d.add(Calendar.DATE, -7);
		d.set(Calendar.HOUR_OF_DAY, 0);
		d.set(Calendar.MINUTE, 0);
		d.set(Calendar.SECOND, 0);
		d.set(Calendar.MILLISECOND, 0);
		return d.getTime();
	}

	public static Date getDateOneMonthAgo() {
		GregorianCalendar d = new GregorianCalendar();
		d.add(Calendar.MONTH, -1);
		d.set(Calendar.HOUR_OF_DAY, 0);
		d.set(Calendar.MINUTE, 0);
		d.set(Calendar.SECOND, 0);
		return d.getTime();
	}

	public static Date getYesterday() {
		GregorianCalendar d = new GregorianCalendar();
		d.add(Calendar.DATE, -1);
		d.set(Calendar.HOUR_OF_DAY, 0);
		d.set(Calendar.MINUTE, 0);
		d.set(Calendar.SECOND, 0);
		d.set(Calendar.MILLISECOND, 0);
		return d.getTime();
	}
	public static Date getBeforeYesterday() {
		GregorianCalendar d = new GregorianCalendar();
		d.add(Calendar.DATE, -2);
		d.set(Calendar.HOUR_OF_DAY, 0);
		d.set(Calendar.MINUTE, 0);
		d.set(Calendar.SECOND, 0);
		d.set(Calendar.MILLISECOND, 0);
		return d.getTime();
	}
	public static Date getToday() {
		GregorianCalendar d = new GregorianCalendar();
		d.set(Calendar.HOUR_OF_DAY, 0);
		d.set(Calendar.MINUTE, 0);
		d.set(Calendar.MILLISECOND, 0);
		d.set(Calendar.SECOND, 0);
		return d.getTime();
	}

	public static Date getTomorrow() {
		GregorianCalendar d = new GregorianCalendar();
		d.add(Calendar.DATE, 1);
		d.set(Calendar.HOUR_OF_DAY, 0);
		d.set(Calendar.MINUTE, 0);
		d.set(Calendar.SECOND, 0);
		d.set(Calendar.MILLISECOND, 0);
		return d.getTime();
	}
	public static Date getFiveMinituesAgo() {
		GregorianCalendar d = new GregorianCalendar();
		d.add(Calendar.MINUTE, -3);
		d.set(Calendar.SECOND, 0);
		d.set(Calendar.MILLISECOND, 0);
		return d.getTime();
	}

	public static Date getHalfAHourAgo() {
		GregorianCalendar d = new GregorianCalendar();
		d.add(Calendar.MINUTE, -30);
		d.set(Calendar.SECOND, 0);
		d.set(Calendar.MILLISECOND, 0);
		return d.getTime();
	}

	public static Calendar processCalendar(Calendar d) {
		d.set(Calendar.HOUR_OF_DAY, 0);
		d.set(Calendar.MINUTE, 0);
		d.set(Calendar.SECOND, 0);
		d.set(Calendar.MILLISECOND, 0);
		return d;
	}

	public static Date getNow() {
		GregorianCalendar d = new GregorianCalendar();
		d.set(Calendar.MILLISECOND, 0);
		return d.getTime();
	}

	public static Date getFormatNow() {
		GregorianCalendar d = new GregorianCalendar();
		d.set(Calendar.SECOND, 0);
		d.set(Calendar.MILLISECOND, 0);
		return d.getTime();
	}
	
	/**
	 * long转Date
	 * @param time
	 * @return
	 */
	public static Date getLongToDate(long time) {
		GregorianCalendar d = new GregorianCalendar();
		d.setTimeInMillis(time);
		return d.getTime();
	}
	
	public static Date getThereHoursBefore() {
		GregorianCalendar d = new GregorianCalendar();
		d.add(Calendar.MINUTE, -30);
		d.set(Calendar.SECOND, 0);
		d.set(Calendar.MILLISECOND, 0);
		return d.getTime();
	}

	public static Date getMinutesAgo(int minutes) {
		GregorianCalendar d = new GregorianCalendar();
		d.add(Calendar.MINUTE, -minutes);
		d.set(Calendar.SECOND, 0);
		d.set(Calendar.MILLISECOND, 0);
		return d.getTime();
	}

	public static Date getDaysAgo(int days) {
		GregorianCalendar d = new GregorianCalendar();
		d.add(Calendar.DATE, -days);
		d.set(Calendar.HOUR_OF_DAY, 0);
		d.set(Calendar.MINUTE, 0);
		d.set(Calendar.SECOND, 0);
		d.set(Calendar.MILLISECOND, 0);
		return d.getTime();
	}

	public static Date getHoursAgoOfOneDay(Date d, int hours) {
		GregorianCalendar c = new GregorianCalendar();
		c.setTime(d);
		c.add(Calendar.HOUR_OF_DAY, -hours);
		return c.getTime();
	}

	public static Date getMinutesBeforeOneDay(Date d, Integer minutes) {
		GregorianCalendar c = new GregorianCalendar();
		c.setTime(d);
		c.add(Calendar.MINUTE, -minutes);
		return c.getTime();
	}

	/**
	 * 将时分秒清零
	 * 
	 * @param date
	 * @return
	 */
	public static Date processDate(Date date) {
		GregorianCalendar d = new GregorianCalendar();
		d.setTime(date);
		d.set(Calendar.HOUR_OF_DAY, 0);
		d.set(Calendar.SECOND, 0);
		d.set(Calendar.MILLISECOND, 0);
		return d.getTime();
	}

	public static Date minutesAfterOneDay(Date d, int minutes) {
		GregorianCalendar g = new GregorianCalendar();
		g.setTime(d);
		g.add(Calendar.MINUTE, minutes);
		return g.getTime();
	}
	public static Date getTodayEndTime(Date d) {
		GregorianCalendar g = new GregorianCalendar();
		g.setTime(d);
		g.set(Calendar.HOUR_OF_DAY, 23);
		g.set(Calendar.MINUTE, 59);
		g.set(Calendar.SECOND, 59);
		g.set(Calendar.MILLISECOND, 999);
		return g.getTime();
	}
	
	public static Date changStringToDate(String s){
		SimpleDateFormat sdf=new SimpleDateFormat(Contants.DATE_FORMAT_ZH);
		Date d=null;
		if(s==null||"".equals(s.trim())){
			return null;
		}
		try {
			d = sdf.parse(s);
		} catch (ParseException e) {
			sdf=new SimpleDateFormat(Contants.DATE_FORMAT_DAY);
			try {
				d=sdf.parse(s);
			} catch (ParseException e1) {
				return null;
			}
			return d;
		}
		return d;
	}
	
	
	public static Date formatDate(String value,String s){
		SimpleDateFormat sdf=new SimpleDateFormat(s);
		Date d=null;
		if(value==null||"".equals(value.trim())){
			return null;
		}
		try {
			d = sdf.parse(value);
		} catch (ParseException e) {
			sdf=new SimpleDateFormat(s);
			try {
				d=sdf.parse(value);
			} catch (ParseException e1) {
				return null;
			}
			return d;
		}
		return d;
	}
	
//	public static void main(String[] args) {
//		
//		System.out.println(defaultDateFormat(formatDate(DateUtils.defaultDayDateFormat(new Date()), "yyyy-MM-dd")));
//		
//	}
	
	
//	public static void main(String[] args) {
//		System.out.println(formatDate("2012-01-01 12:12:12", Contants.DATE_FORMAT_ZH));
//	}
	
	public static Date StringToDate(String s){
		SimpleDateFormat sdf=new SimpleDateFormat(Contants.DATE_FORMAT_YYYYMMDDHHMMSS);
		Date d=null;
		if(s==null||"".equals(s.trim())){
			return null;
		}
		try {
			d = sdf.parse(s);
		} catch (ParseException e) {
			sdf=new SimpleDateFormat(Contants.DATE_FORMAT_YYYYMMDDHHMMSS);
			try {
				d=sdf.parse(s);
			} catch (ParseException e1) {
				return null;
			}
			return d;
		}
		return d;
	}
	
	 /**
	  * 将日期转换成yyyy-MM-dd HH:mm:ss 字符串
	  * @param d
	  * @return yyyy-MM-dd HH:mm:ss 
	  */
	 public static String defaultDateFormat(Date d){
		 SimpleDateFormat format= new SimpleDateFormat(Contants.DATE_FORMAT_ZH);
		 String s = format.format(d);
		 return s;
	 }
	 /**
	  * 将日期转换成yyyy-MM-dd 字符串
	  * @param d
	  * @return yyyy-MM-dd
	  */
	 public static String defaultDayDateFormat(Date d){
		 SimpleDateFormat format= new SimpleDateFormat(Contants.DATE_FORMAT_DAY);
		 String s = format.format(d);
		 return s;
	 }
	 
	 /**
	  * 将日期转换成yyyyMMdd 字符串
	  * @param d
	  * @return yyyy-MM-dd
	  */
	 public static String dateFormatyyyyMMdd(Date d){
		 SimpleDateFormat format= new SimpleDateFormat("yyyyMMdd");
		 String s = format.format(d);
		 return s;
	 }
	 
	 /**
	  * 将Date转换为yyyyMMddHHmmss的格式
	  * @param date
	  * @return
	  */
	 public static String getDateYYYYMMDDHHMMSS(Date date){
		 SimpleDateFormat sdf= new SimpleDateFormat(Contants.DATE_FORMAT_YYYYMMDDHHMMSS);
		 return sdf.format(date);
	 }
	 /**
	  * 返回两个日期的时间差，年月日时分秒的形式
	  * @param fDate
	  * @param oDate
	  * @return
	  */
	public static String getTimedifference(Date fDate, Date oDate) {
		if (null == fDate || null == oDate) {
			return "-";
		}
		String oDateString=getDateYYYYMMDDHHMMSS(oDate);
		StringBuilder sb = new StringBuilder();
		long seconds = (oDate.getTime() - fDate.getTime()) / 1000;
		if(seconds<=0)return "已过期("+oDateString+")";
		long second = seconds % 60;
		long minute = (seconds % (60 * 60)) / 60;
		long hour = (seconds % (24 * 60 * 60)) / (60 * 60);
		long day = seconds / (24 * 60 * 60);

		if (day > 0)
			sb.append("<span>" + day + "</span>天&nbsp;");
		if (hour > 0)
			sb.append("<span>" + hour + "</span>小时&nbsp;");
		if (minute > 0)
			sb.append("<span>" + minute + "</span>分&nbsp;");
		if (second > 0)
			sb.append("<span>" + second + "</span>秒&nbsp;");
		sb.append("("+oDateString+")");
		return sb.toString();
	}
	 public static void main(String[] args) {
//				System.out.println(defaultDateFormat(getToday()));
		 StringBuilder sb=new StringBuilder();
		 long seconds =24000*60*60+60*60+64;
		 long second=seconds%60;
		  long minute=(seconds%(60*60))/60;
	       long hour=(seconds%(24*60*60))/(60*60);
	       long day=seconds/(24 * 60 * 60);
	       
	       if(day>0)
	    	   sb.append("<span>"+day+"</span>天&nbsp;");
	       if(hour>0)
	    	   sb.append("<span>"+hour+"</span>小时&nbsp;");
	       if(minute>0)
	    	   sb.append("<span>"+minute+"</span>分&nbsp;");
	       if(second>0)
	    	   sb.append("<span>"+second+"</span>秒&nbsp;");
	       System.out.println(sb.toString());
	}
	 /**
	  * 返回两个日期的准确的天数,包含计算时分秒
	  * @param fDate
	  * @param oDate
	  * @return
	  */
	 public static int getIntervalDays(Date fDate, Date oDate) {
	       if (null == fDate || null == oDate) {
	           return -1;
	       }
	       long intervalMilli = oDate.getTime() - fDate.getTime();
	       return (int) (intervalMilli / (24 * 60 * 60 * 1000));
	    }
	 

	  /**
	   * 返回两个日期的天数,只计算天，再做差值
	   * @param fDate
	   * @param oDate
	   * @return
	   */
	public static int daysOfTwo(Date fDate, Date oDate) {
	       Calendar aCalendar = Calendar.getInstance();
	       aCalendar.setTime(fDate);
	       int day1 = aCalendar.get(Calendar.DAY_OF_YEAR);
	       aCalendar.setTime(oDate);
	       int day2 = aCalendar.get(Calendar.DAY_OF_YEAR);
	       return day2 - day1;
	}

    /**
     * 判断一个字符串是否是日期
     * @param param
     * @return
     */
    public static boolean isDate(String param){
        try {
            SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMdd");
            Date date=sdf.parse(param);
            return true;
        } catch (ParseException e) {
            return false;
        }
    }
}
