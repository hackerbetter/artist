package com.artist.cms.quartz;

import com.artist.cms.domain.Coop;
import com.artist.cms.domain.CoopStat;
import com.artist.cms.domain.TuserInfo;
import com.artist.cms.domain.UserInf;
import com.artist.cms.util.CommonUtil;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

@Component
public class CoopDataStatService {
	
	private Logger logger = Logger.getLogger(CoopDataStatService.class);
	private Calendar calendar = Calendar.getInstance();

	public void process() {
		logger.info("渠道数据统计开始");
		try {
			List<Coop> coops = Coop.findAllCoops();
			for (int i = 0; i < coops.size(); i++) {
				Coop coop = coops.get(i);
				singleCoopStat(coop.getCoopid()+"", coop.getCoopname(), coop.getRate(), new Date());
			}
			//统计总量
			totalCoopStat(coops, new Date());
		} catch (Exception e) {
			logger.error("渠道数据统计发生异常:"+e.toString());
		}
		logger.info("渠道数据统计结束");
	}
	
	/**
	 * 手动统计接口
	 * @param strDate
	 */
	public void coopDataStat(String strDate) {
		logger.info("渠道数据统计开始");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		try {
			Date date = sdf.parse(strDate);
			List<Coop> coops = Coop.findAllCoops();
			for (int i = 0; i < coops.size(); i++) {
				Coop coop = coops.get(i);
				singleCoopStat(coop.getCoopid()+"", coop.getCoopname(), coop.getRate(), date);
			}
			//统计总量
			totalCoopStat(coops, date);
		} catch (Exception e) {
			logger.error("渠道数据统计发生异常:"+e.toString());
		}
		logger.info("渠道数据统计结束");
	}
	
	/**
	 * 单个渠道统计
	 * @param coopid
	 * @param coopname
	 */
	public void singleCoopStat(String coopid, String coopname, Integer rate, Date date) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		logger.info("coopId="+coopid+",date="+sdf.format(date)+",统计开始");
		if (hasStat(coopid, date)) {
			return ;
		}
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
		//总用户数(激活数)
		StringBuilder builder1 = new StringBuilder(" where");
		List<Object> params1 = new ArrayList<Object>();
		builder1.append(" date_format(o.createtime, '%Y-%m-%d')<=? and");
		params1.add(oneDay);
		builder1.append(" o.channel=? ");
		params1.add(coopid);
		BigDecimal totalUsers = UserInf.getTotalVolume(builder1.toString(), params1);
		//总注册用户数
		StringBuilder builder2 = new StringBuilder(" where");
		List<Object> params2 = new ArrayList<Object>();
		builder2.append(" to_char(o.regtime, 'yyyy-MM-dd')<=? and");
		params2.add(oneDay);
		builder2.append(" o.channel=? ");
		params2.add(coopid);
		BigDecimal totalRegUsers = TuserInfo.getTotalVolume(builder2.toString(), params2);
		//渠道平均注册率(注册用户总数/激活用户总数)
	    BigDecimal regrate = BigDecimal.ZERO;
	    if (totalUsers.intValue()>0) {
	    	regrate = new BigDecimal(Math.round((totalRegUsers.floatValue() * 100) / totalUsers.floatValue()));
	    }
		//当日新增用户数
		StringBuilder builder3 = new StringBuilder(" where");
		List<Object> params3 = new ArrayList<Object>();
		builder3.append(" date_format(o.createtime, '%Y-%m-%d')=? and");
		params3.add(oneDay);
		builder3.append(" o.channel=? ");
		params3.add(coopid);
		BigDecimal todayNewUsers = UserInf.getTotalVolume(builder3.toString(), params3);
		//当日新增扣量后
		BigDecimal newfix = new BigDecimal(Math.round((todayNewUsers.floatValue() * rate.floatValue()) / 100));
		//当日注册用户数
		StringBuilder builder4 = new StringBuilder(" where");
		List<Object> params4 = new ArrayList<Object>();
		builder4.append(" to_char(o.regtime, 'yyyy-MM-dd')=? and");
		params4.add(oneDay);
		builder4.append(" o.channel=? ");
		params4.add(coopid);
		BigDecimal todayRegUsers = TuserInfo.getTotalVolume(builder4.toString(), params4);
		//新注册的用户
		List<TuserInfo> tuserInfos = TuserInfo.getList(builder4.toString(), " order by o.regtime asc", params4);
		//解决IN列表过长的问题
		List<String> registerUserNoList = CommonUtil.getInRegisterUserNoListFromTuserInfos(tuserInfos);
		//当日注册率(当日注册用户数/当日新增用户数)
	    BigDecimal todayRegrate = BigDecimal.ZERO;
	    if (todayNewUsers.intValue()>0) {
	    	todayRegrate = new BigDecimal(Math.round((todayRegUsers.floatValue() * 100) / todayNewUsers.floatValue()));
	    }
		//当日活跃用户数
		StringBuilder builder5 = new StringBuilder(" where");
		List<Object> params5 = new ArrayList<Object>();
		builder5.append(" date_format(o.lastnetconnecttime, '%Y-%m-%d')=? and");
		params5.add(oneDay);
		builder5.append(" o.channel=? ");
		params5.add(coopid);
		BigDecimal todayActiveUsers = UserInf.getTotalVolume(builder5.toString(), params5);
		//7日活跃用户数
		StringBuilder builder6 = new StringBuilder(" where");
		List<Object> params6 = new ArrayList<Object>();
		builder6.append(" date_format(o.lastnetconnecttime, '%Y-%m-%d')>=? and");
		params6.add(weekDay);
		builder6.append(" date_format(o.lastnetconnecttime, '%Y-%m-%d')<? and");
		params6.add(today);
		builder6.append(" o.channel=? ");
		params6.add(coopid);
		BigDecimal weekActiveUsers = UserInf.getTotalVolume(builder6.toString(), params6);
		//30日活跃用户数
		StringBuilder builder7 = new StringBuilder(" where");
		List<Object> params7 = new ArrayList<Object>();
		builder7.append(" date_format(o.lastnetconnecttime, '%Y-%m-%d')>=? and");
		params7.add(monthDay);
		builder7.append(" date_format(o.lastnetconnecttime, '%Y-%m-%d')<? and");
		params7.add(today);
		builder7.append(" o.channel=? ");
		params7.add(coopid);
		BigDecimal monthActiveUsers = UserInf.getTotalVolume(builder7.toString(), params7);

	    CoopStat coopStat = new CoopStat();
	    calendar.setTime(date);
		calendar.add(Calendar.DATE, -1);
	    coopStat.setStatdate(calendar.getTime());
	    coopStat.setCoopid(coopid);
	    coopStat.setNewusers(todayNewUsers);
	    coopStat.setTodayconnect(todayActiveUsers);
	    coopStat.setSevenday(weekActiveUsers);
	    coopStat.setMonthday(monthActiveUsers);
	    coopStat.setTotalnum(totalUsers);
	    coopStat.setTotalregnum(totalRegUsers);
	    coopStat.setTodayregnum(todayRegUsers);
	    coopStat.setCoopname(coopname);
	    coopStat.setNew_fix(newfix);
	    coopStat.setRegrate(regrate);
	    coopStat.persist();
	    logger.info("coopId="+coopid+",date="+sdf.format(date)+",统计结束");
	}
	
	/**
	 * 统计所有渠道的总量
	 * @param coops
	 */
	public void totalCoopStat(List<Coop> coops, Date date) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		logger.info("coopId=0"+",date="+sdf.format(date)+",统计开始");
		if (hasStat("0", date)) {
			return ;
		}
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
		//解决IN列表过长的问题
		List<String> coopIdList = CommonUtil.getInCoopIdList(coops);
		//总用户数
		StringBuilder builder1 = new StringBuilder(" where");
		List<Object> params1 = new ArrayList<Object>();
		builder1.append(" date_format(o.createtime, '%Y-%m-%d')<=? ");
		params1.add(oneDay);
		BigDecimal totalUsers = UserInf.getTotalVolume(builder1.toString(), params1);
		//总注册用户数
		StringBuilder builder2 = new StringBuilder(" where");
		List<Object> params2 = new ArrayList<Object>();
		builder2.append(" to_char(o.regtime, 'yyyy-MM-dd')<=? and");
		params2.add(oneDay);
		builder2.append(" (");
		for (String coopIds : coopIdList) {
			builder2.append(" o.channel in ("+coopIds+") or");
		}
		if (builder2.toString().endsWith("or")) {
			builder2.delete(builder2.length() - 2, builder2.length());
		}
		builder2.append(" )");
		BigDecimal totalRegUsers = TuserInfo.getTotalVolume(builder2.toString(), params2);
		//渠道平均注册率(注册用户总数/激活用户总数)
		BigDecimal regrate = BigDecimal.ZERO;
		if (totalUsers.intValue()>0) {
			regrate = new BigDecimal(Math.round((totalRegUsers.floatValue() * 100) / totalUsers.floatValue()));
		}
		//当日新增用户数
		StringBuilder builder3 = new StringBuilder(" where");
		List<Object> params3 = new ArrayList<Object>();
		builder3.append(" date_format(o.createtime, '%Y-%m-%d')=? ");
		params3.add(oneDay);
		BigDecimal todayNewUsers = UserInf.getTotalVolume(builder3.toString(), params3);
		//当日注册用户数
		StringBuilder builder4 = new StringBuilder(" where");
		List<Object> params4 = new ArrayList<Object>();
		builder4.append(" to_char(o.regtime, 'yyyy-MM-dd')=? and");
		params4.add(oneDay);
		builder4.append(" (");
		for (String coopIds : coopIdList) {
			builder4.append(" o.channel in ("+coopIds+") or");
		}
		if (builder4.toString().endsWith("or")) {
			builder4.delete(builder4.length() - 2, builder4.length());
		}
		builder4.append(" )");
		BigDecimal todayRegUsers = TuserInfo.getTotalVolume(builder4.toString(), params4);
		//新注册的用户
		List<TuserInfo> tuserInfos = TuserInfo.getList(builder4.toString(), " order by o.regtime asc", params4);
		//解决IN列表过长的问题
		List<String> registerUserNoList = CommonUtil.getInRegisterUserNoListFromTuserInfos(tuserInfos);
		//当日注册率(当日注册用户数/当日新增用户数)
		BigDecimal todayRegrate = BigDecimal.ZERO;
	    if (todayNewUsers.intValue()>0) {
	    	todayRegrate = new BigDecimal(Math.round((todayRegUsers.floatValue() * 100) / todayNewUsers.floatValue()));
	    }
		//当日活跃用户数
		StringBuilder builder5 = new StringBuilder(" where");
		List<Object> params5 = new ArrayList<Object>();
		builder5.append(" date_format(o.lastnetconnecttime, '%Y-%m-%d')=? ");
		params5.add(oneDay);
		BigDecimal todayActiveUsers = UserInf.getTotalVolume(builder5.toString(), params5);
		//7日活跃用户数
		StringBuilder builder6 = new StringBuilder(" where");
		List<Object> params6 = new ArrayList<Object>();
		builder6.append(" date_format(o.lastnetconnecttime, '%Y-%m-%d')>=? and");
		params6.add(weekDay);
		builder6.append(" date_format(o.lastnetconnecttime, '%Y-%m-%d')<? ");
		params6.add(today);
		BigDecimal weekActiveUsers = UserInf.getTotalVolume(builder6.toString(), params6);
		//30日活跃用户数
		StringBuilder builder7 = new StringBuilder(" where");
		List<Object> params7 = new ArrayList<Object>();
		builder7.append(" date_format(o.lastnetconnecttime, '%Y-%m-%d')>=? and");
		params7.add(monthDay);
		builder7.append(" date_format(o.lastnetconnecttime, '%Y-%m-%d')<? ");
		params7.add(today);
		BigDecimal monthActiveUsers = UserInf.getTotalVolume(builder7.toString(), params7);

		CoopStat coopStat = new CoopStat();
	    calendar.setTime(date);
		calendar.add(Calendar.DATE,   -1);
	    coopStat.setStatdate(calendar.getTime());
	    coopStat.setCoopid("0");
	    coopStat.setNewusers(todayNewUsers);
	    coopStat.setTodayconnect(todayActiveUsers);
	    coopStat.setSevenday(weekActiveUsers);
	    coopStat.setMonthday(monthActiveUsers);
	    coopStat.setTotalnum(totalUsers);
	    coopStat.setTotalregnum(totalRegUsers);
	    coopStat.setTodayregnum(todayRegUsers);
	    coopStat.setCoopname("总量");
	    coopStat.setRegrate(regrate);
	    coopStat.persist();
	    logger.info("coopId=0"+",date="+sdf.format(date)+",统计结束");
	}
	
	/**
	 * 判断该渠道是否已经统计过
	 * @param coopId
	 * @param date
	 * @return
	 */
	private boolean hasStat(String coopId, Date date) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		calendar.setTime(date);
		calendar.add(Calendar.DATE,   -1);
		String oneDay = sdf.format(calendar.getTime());
		
		StringBuilder builder = new StringBuilder(" where");
		List<Object> params = new ArrayList<Object>();
		
		builder.append(" date_format(o.statdate, '%Y-%m-%d')=? and");
		params.add(oneDay);
		
		builder.append(" o.coopid=? ");
		params.add(coopId);
		
		List<CoopStat> list = CoopStat.getList(builder.toString(), "", params);
		if (list!=null&&list.size()>0) {
			logger.info("coopId="+coopId+",statdate="+oneDay+",已经统计过");
			return true;
		}
		return false;
	}
	
}
