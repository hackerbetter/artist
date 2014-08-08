package com.artist.cms.quartz;

import com.artist.cms.domain.*;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Component
public class VersionDataStatService {
	
	private Logger logger = LoggerFactory.getLogger(VersionDataStatService.class);
	private Calendar calendar = Calendar.getInstance();
	
	public void process() {
		logger.info("版本数据统计开始");
		try {
			List<Tplatform> platforms = Tplatform.findAllTplatforms(); //平台
			for (Tplatform platform : platforms) {
				String plat = platform.getName();
				if (!StringUtils.isEmpty(plat)) {
					StringBuilder builder = new StringBuilder(" where");
					List<Object> params = new ArrayList<Object>();
					
					builder.append(" o.platform=? ");
					params.add(plat);
					
					builder.append(" group by o.version ");
					List<TversionInfo> list = TversionInfo.getList(builder.toString(), "", params); //版本号
					if (list!=null&&list.size()>0) {
						for (TversionInfo tversionInfo : list) {
							String version = tversionInfo.getVersion();
							if (!StringUtils.isEmpty(version)) {
								singleVersionStat(plat, version, new Date());
							}
						}
					}
				}
			}
		} catch (Exception e) {
			logger.error("版本数据统计发生异常:",e);
		}
		logger.info("版本数据统计结束");
	}
	
	/**
	 * 单个版本统计
	 * @param version
	 */
	public void singleVersionStat(String platform, String version, Date date) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		
		//今日
		calendar.setTime(date);
		String today = sdf.format(calendar.getTime());
		
		//前一天
		calendar.setTime(date);
		calendar.add(Calendar.DATE,   -1);
		String oneDay = sdf.format(calendar.getTime());
		
		//前七天
		calendar.setTime(date);
		calendar.add(Calendar.DATE,   -7);
		String weekDay = sdf.format(calendar.getTime());
		
		//前30天
		calendar.setTime(date);
		calendar.add(Calendar.DATE,   -30);
		String monthDay = sdf.format(calendar.getTime());
		
		//总用户数
		StringBuilder builder1 = new StringBuilder(" where");
		List<Object> params1 = new ArrayList<Object>();
		
		builder1.append(" o.platfrom=? and");
		params1.add(platform);
		
		builder1.append(" o.softwareversion=? and");
		params1.add(version);
		
		builder1.append(" date_format(o.createtime, '%Y-%m-%d')<=? ");
		params1.add(oneDay);
		
		BigDecimal totalUsers = UserInf.getTotalVolume(builder1.toString(), params1);
		
		//总注册用户数
		StringBuilder builder2 = new StringBuilder(" where");
		List<Object> params2 = new ArrayList<Object>();
		
		builder2.append(" o.platform=? and");
		params2.add(platform);
		
		builder2.append(" o.softwareversion=? and");
		params2.add(version);
		
		builder2.append(" date_format(o.createtime, '%Y-%m-%d')<=? and");
		params2.add(oneDay);
		
		builder2.append(" o.createtime is not null");
		BigDecimal totalRegisterUsers = TregisterInfo.getTotalVolume(builder2.toString(), params2);
		
		//平均注册率
		Integer registerRate = 0;
		//Integer registerRate = Math.round((totalRegisterUsers.floatValue()*100)/totalUsers.floatValue());

		//当日新增用户
		StringBuilder builder3 = new StringBuilder(" where");
		List<Object> params3 = new ArrayList<Object>();
		
		builder3.append(" o.platfrom=? and");
		params3.add(platform);
		
		builder3.append(" o.softwareversion=? and");
		params3.add(version);
		
		builder3.append(" date_format(o.createtime, '%Y-%m-%d')=? ");
		params3.add(oneDay);
		
		BigDecimal todayNewAddUsers = UserInf.getTotalVolume(builder3.toString(), params3);
		
		//当日注册用户数
		StringBuilder builder4 = new StringBuilder(" where");
		List<Object> params4 = new ArrayList<Object>();
		
		builder4.append(" o.platform=? and");
		params4.add(platform);
		
		builder4.append(" o.softwareversion=? and");
		params4.add(version);
		
		builder4.append(" date_format(o.createtime, '%Y-%m-%d')=? and");
		params4.add(oneDay);
		
		builder4.append(" o.createtime is not null");
		BigDecimal todayRegisterUsers = TregisterInfo.getTotalVolume(builder4.toString(), params4);

		//当日活跃用户
		StringBuilder builder5 = new StringBuilder(" where");
		List<Object> params5 = new ArrayList<Object>();
		
		builder5.append(" o.platfrom=? and");
		params5.add(platform);
		
		builder5.append(" o.softwareversion=? and");
		params5.add(version);
		
		builder5.append(" date_format(o.lastnetconnecttime, '%Y-%m-%d')=? ");
		params5.add(oneDay);
		
		BigDecimal todayConnectNetUsers = UserInf.getTotalVolume(builder5.toString(), params5);
		
		//7日活跃用户
		StringBuilder builder6 = new StringBuilder(" where");
		List<Object> params6 = new ArrayList<Object>();
		
		builder6.append(" o.platfrom=? and");
		params6.add(platform);
		
		builder6.append(" o.softwareversion=? and");
		params6.add(version);
		
		builder6.append(" date_format(o.lastnetconnecttime, '%Y-%m-%d')>=? and");
		params6.add(weekDay);
		
		builder6.append(" date_format(o.lastnetconnecttime, '%Y-%m-%d')<? ");
		params6.add(today);
		
		BigDecimal weekConnectNetUsers = UserInf.getTotalVolume(builder6.toString(), params6);
		
		//30日活跃用户
		StringBuilder builder7 = new StringBuilder(" where");
		List<Object> params7 = new ArrayList<Object>();
		
		builder7.append(" o.platfrom=? and");
		params7.add(platform);
		
		builder7.append(" o.softwareversion=? and");
		params7.add(version);
		
		builder7.append(" date_format(o.lastnetconnecttime, '%Y-%m-%d')>=? and");
		params7.add(monthDay);
		
		builder7.append(" date_format(o.lastnetconnecttime, '%Y-%m-%d')<? ");
		params7.add(today);
		
		BigDecimal monthConnectNetUsers = UserInf.getTotalVolume(builder7.toString(), params7);
		
		
		TversionStat versionStat = new TversionStat();
		calendar.setTime(date);
		calendar.add(Calendar.DATE,   -1);
		versionStat.setStatdate(calendar.getTime());
		versionStat.setPlatform(platform);
		versionStat.setVersion(version);
		versionStat.setTodaynewaddusers(todayNewAddUsers+"");
		versionStat.setTodayconnectnetusers(todayConnectNetUsers+"");
		versionStat.setWeekconnectnetusers(weekConnectNetUsers+"");
		versionStat.setMonthconnectnetusers(monthConnectNetUsers+"");
		versionStat.setTodayregisterusers(todayRegisterUsers+"");
		versionStat.setTotalregisterusers(totalRegisterUsers+"");
		versionStat.setRegisterrate(registerRate+"");
		versionStat.setTotalusers(totalUsers+"");
		versionStat.persist();
	}
	
}
