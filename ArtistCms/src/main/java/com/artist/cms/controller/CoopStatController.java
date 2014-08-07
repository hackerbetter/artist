package com.artist.cms.controller;

import com.artist.cms.domain.CoopStat;
import com.artist.cms.util.ChartUtil;
import com.artist.cms.util.Page;
import org.apache.commons.lang.StringUtils;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@RequestMapping(value = "/coopstat")
@Controller
public class CoopStatController {

	private Calendar calendar = Calendar.getInstance();
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping("/list")
	public ModelAndView list(
			@RequestParam(value = "coopid", required = false, defaultValue="") String coopid,
			@RequestParam(value = "orderBy", required = false, defaultValue="") String orderBy,
			@RequestParam(value = "orderDir", required = false, defaultValue="") String orderDir,
			@ModelAttribute("page") Page page,HttpServletRequest request, ModelAndView view) {
		try {
			ArrayList<String> cooplist=new ArrayList<String>();
			//查询前1天的记录
			StringBuilder builder = new StringBuilder(" where");
			List<Object> params = new ArrayList<Object>();
			//查询前2天的记录
			StringBuilder builder2 = new StringBuilder(" where");
			List<Object> params2 = new ArrayList<Object>();
		
			if(StringUtils.isNotBlank(coopid)){
				builder.append(" o.coopid=? and ");
				params.add(coopid);
				builder2.append(" o.coopid=? and ");
				params2.add(coopid);
			}
			

			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			//前一天
			calendar.setTime(new Date());
			calendar.add(Calendar.DATE, -1);
			String preOneDay = sdf.format(calendar.getTime());
			
			//前二天
			calendar.setTime(new Date());
			calendar.add(Calendar.DATE, -2);
			String preTwoDay = sdf.format(calendar.getTime());
			
			//排序
			StringBuilder orderBuilder = new StringBuilder();
			if (StringUtils.isEmpty(orderBy)||StringUtils.isEmpty(orderDir)) {
				orderBy = "totalnum";
				orderDir = "desc";
			}
			orderBuilder.append("order by o."+orderBy+" "+orderDir);
			
			//统计时间
			builder.append(" date_format(o.statdate, '%Y-%m-%d')=? and");
			params.add(preOneDay);

			if (builder.toString().endsWith("and")) {
				builder.delete(builder.length() - 3, builder.length());
			}
			CoopStat.findList(builder.toString(), orderBuilder.toString(), params, page, true);
			
			//统计时间
			builder2.append(" date_format(o.statdate, '%Y-%m-%d')=? and");
			params2.add(preTwoDay);

			if (builder2.toString().endsWith("and")) {
				builder2.delete(builder2.length() - 3, builder2.length());
			}
			CoopStat.findList(builder2.toString(), orderBuilder.toString(), params2, page, false);
			view.addObject("page", page);
			view.addObject("orderBy", orderBy);
			view.addObject("orderDir", orderDir);
			view.addObject("coopid", coopid);
		} catch (Exception e) {
			view.addObject("errormsg", e.getMessage());
		}
		view.setViewName("coopstat/list");
		return view;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping("/linechart")
	public ModelAndView lineChart(@RequestParam(value = "type", required = false) String type,
			@RequestParam(value = "coopid", required = false) String coopid,
			@RequestParam(value = "starttime", required = false) String starttime,
			@RequestParam(value = "endtime", required = false) String endtime,
			@ModelAttribute("page") Page page, HttpServletRequest request, ModelAndView view) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat sdf2 = new SimpleDateFormat("MM-dd");
		
		StringBuilder builder = new StringBuilder(" where");
		List<Object> params = new ArrayList<Object>();
		
		if (StringUtils.isNotBlank(coopid)) { //渠道号
			builder.append(" o.coopid=? and");
			params.add(coopid);
		}
		
		if (StringUtils.isEmpty(starttime)) { //开始时间
			calendar.setTime(new Date());
			calendar.add(Calendar.DATE, -30);
			starttime = sdf.format(calendar.getTime());
		}
		builder.append(" date_format(o.statdate, '%Y-%m-%d')>=? and");
		params.add(starttime);
		
		if (StringUtils.isEmpty(endtime)) { //结束时间
			calendar.setTime(new Date());
			calendar.add(Calendar.DATE, -1);
			endtime = sdf.format(calendar.getTime());
		}
		builder.append(" date_format(o.statdate, '%Y-%m-%d')<=? ");
		params.add(endtime);
		
		page.setMaxResult(1000); //设置最大记录数
		CoopStat.findList(builder.toString(), "order by o.statdate asc", params, page, true);
		
		DefaultCategoryDataset linedataset = new DefaultCategoryDataset(); //数据
		List<CoopStat> list = (List<CoopStat>)page.getList();
		String headStr = ""; //图片上方显示文字
		if (type!=null && type.trim().equals("newusers")) {
			headStr = "新增用户";
			if (list!=null && list.size()>0) {
				for (CoopStat coopStat : list) {
					BigDecimal newusers = coopStat.getNewusers();
					if (newusers==null) {
						newusers = BigDecimal.ZERO;
					}
					linedataset.addValue(newusers.doubleValue(), "变化曲线", sdf2.format(coopStat.getStatdate()));
				}
			}
		} else if (type!=null && type.trim().equals("todayconnect")) {
			headStr = "当日联网用户";
			if (list!=null && list.size()>0) {
				for (CoopStat coopStat : list) {
					BigDecimal todayconnect = coopStat.getTodayconnect();
					if (todayconnect==null) {
						todayconnect = BigDecimal.ZERO;
					}
					linedataset.addValue(todayconnect.doubleValue(), "变化曲线", sdf2.format(coopStat.getStatdate()));
				}
			}
		} else if (type!=null && type.trim().equals("sevenday")) {
			headStr = "7日联网用户";
			if (list!=null && list.size()>0) {
				for (CoopStat coopStat : list) {
					BigDecimal sevenday = coopStat.getSevenday();
					if (sevenday==null) {
						sevenday = BigDecimal.ZERO;
					}
					linedataset.addValue(sevenday.doubleValue(), "变化曲线", sdf2.format(coopStat.getStatdate()));
				}
			}
		} else if (type!=null && type.trim().equals("monthday")) {
			headStr = "30日联网用户";
			if (list!=null && list.size()>0) {
				for (CoopStat coopStat : list) {
					BigDecimal monthday = coopStat.getMonthday();
					if (monthday==null) {
						monthday = BigDecimal.ZERO;
					}
					linedataset.addValue(monthday.doubleValue(), "变化曲线", sdf2.format(coopStat.getStatdate()));
				}
			}
		} else if (type!=null && type.trim().equals("todayregnum")) {
			headStr = "当日注册用户";
			if (list!=null && list.size()>0) {
				for (CoopStat coopStat : list) {
					BigDecimal todayregnum = coopStat.getTodayregnum();
					if (todayregnum==null) {
						todayregnum = BigDecimal.ZERO;
					}
					linedataset.addValue(todayregnum.doubleValue(), "变化曲线", sdf2.format(coopStat.getStatdate()));
				}
			}
		} else if (type!=null && type.trim().equals("totalregnum")) {
			headStr = "注册用户总数";
			if (list!=null && list.size()>0) {
				for (CoopStat coopStat : list) {
					BigDecimal totalregnum = coopStat.getTotalregnum();
					if (totalregnum==null) {
						totalregnum = BigDecimal.ZERO;
					}
					linedataset.addValue(totalregnum.doubleValue(), "变化曲线", sdf2.format(coopStat.getStatdate()));
				}
			}
		} else if (type!=null && type.trim().equals("totalnum")) {
			headStr = "总用户数";
			if (list!=null && list.size()>0) {
				for (CoopStat coopStat : list) {
					BigDecimal totalnum = coopStat.getTotalnum();
					if (totalnum==null) {
						totalnum = BigDecimal.ZERO;
					}
					linedataset.addValue(totalnum.doubleValue(), "变化曲线", sdf2.format(coopStat.getStatdate()));
				}
			}
		}
		String graphURL = ChartUtil.generateLineChart(request, linedataset, headStr, "日期", "数量");
		view.addObject("graphURL", graphURL);
		view.addObject("page", page);
		view.addObject("type", type);
		view.addObject("starttime", starttime);
		view.addObject("endtime", endtime);
		view.addObject("coopid", coopid);
		view.setViewName("coopstat/linechart");
		return view;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping("/piechart")
	public ModelAndView pieChart(@RequestParam(value = "type", required = false) String type,
			@ModelAttribute("page") Page page, HttpServletRequest request, ModelAndView view) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		
		//前一天
		calendar.setTime(new Date());
		calendar.add(Calendar.DATE, -1);
		String preOneDay = sdf.format(calendar.getTime());
		
		StringBuilder builder = new StringBuilder(" where");
		List<Object> params = new ArrayList<Object>();
		
		builder.append(" date_format(o.statdate, '%Y-%m-%d')=? and");
		params.add(preOneDay);
		
		builder.append(" o.coopid<>0 ");
		
		page.setMaxResult(1000); //设置最大记录数
		CoopStat.findList(builder.toString(), "order by o.statdate asc", params, page, true);
		
		DefaultPieDataset dataSet = new DefaultPieDataset(); //数据集
		List<CoopStat> list = (List<CoopStat>)page.getList();
		String titleStr = "";
		if (type!=null && type.trim().equals("newusers")) {
			titleStr = "新增用户";
			BigDecimal others = BigDecimal.ZERO;
			BigDecimal totalVolume = CoopStat.getTotalVolume("newusers", builder.toString(), params);
			for (CoopStat coopStat : list) {
				if (totalVolume.intValue()>0&&(coopStat.getNewusers().doubleValue()/totalVolume.doubleValue())>0.1) {
					dataSet.setValue(coopStat.getCoopname(), coopStat.getNewusers()); 
				} else {
					others = others.add(coopStat.getNewusers());
				}
			}
			dataSet.setValue("Others", others); 
		} else if (type!=null && type.trim().equals("todayconnect")) {
			titleStr = "当日联网用户";
			BigDecimal others = BigDecimal.ZERO;
			BigDecimal totalVolume = CoopStat.getTotalVolume("todayconnect", builder.toString(), params);
			for (CoopStat coopStat : list) {
				if (totalVolume.intValue()>0&&(coopStat.getTodayconnect().doubleValue()/totalVolume.doubleValue())>0.1) {
					dataSet.setValue(coopStat.getCoopname(), coopStat.getTodayconnect()); 
				} else {
					others = others.add(coopStat.getTodayconnect());
				}
			}
			dataSet.setValue("Others", others); 
		} else if (type!=null && type.trim().equals("sevenday")) {
			titleStr = "7日联网用户";
			BigDecimal others = BigDecimal.ZERO;
			BigDecimal totalVolume = CoopStat.getTotalVolume("sevenday", builder.toString(), params);
			for (CoopStat coopStat : list) {
				if (totalVolume.intValue()>0&&(coopStat.getSevenday().doubleValue()/totalVolume.doubleValue())>0.1) {
					dataSet.setValue(coopStat.getCoopname(), coopStat.getSevenday()); 
				} else {
					others = others.add(coopStat.getSevenday());
				}
			}
			dataSet.setValue("Others", others); 
		} else if (type!=null && type.trim().equals("monthday")) {
			titleStr = "30日联网用户";
			BigDecimal others = BigDecimal.ZERO;
			BigDecimal totalVolume = CoopStat.getTotalVolume("monthday", builder.toString(), params);
			for (CoopStat coopStat : list) {
				if (totalVolume.intValue()>0&&(coopStat.getMonthday().doubleValue()/totalVolume.doubleValue())>0.1) {
					dataSet.setValue(coopStat.getCoopname(), coopStat.getMonthday()); 
				} else {
					others = others.add(coopStat.getMonthday());
				}
			}
			dataSet.setValue("Others", others); 
		} else if (type!=null && type.trim().equals("todayregnum")) {
			titleStr = "当日注册用户";
			BigDecimal others = BigDecimal.ZERO;
			BigDecimal totalVolume = CoopStat.getTotalVolume("todayregnum", builder.toString(), params);
			for (CoopStat coopStat : list) {
				if (totalVolume.intValue()>0&&(coopStat.getTodayregnum().doubleValue()/totalVolume.doubleValue())>0.1) {
					dataSet.setValue(coopStat.getCoopname(), coopStat.getTodayregnum()); 
				} else {
					others = others.add(coopStat.getTodayregnum());
				}
			}
			dataSet.setValue("Others", others); 
		} else if (type!=null && type.trim().equals("totalregnum")) {
			titleStr = "注册用户总数";
			BigDecimal others = BigDecimal.ZERO;
			BigDecimal totalVolume = CoopStat.getTotalVolume("totalregnum", builder.toString(), params);
			for (CoopStat coopStat : list) {
				if (totalVolume.intValue()>0&&(coopStat.getTotalregnum().doubleValue()/totalVolume.doubleValue())>0.1) {
					dataSet.setValue(coopStat.getCoopname(), coopStat.getTotalregnum()); 
				} else {
					others = others.add(coopStat.getTotalregnum());
				}
			}
			dataSet.setValue("Others", others); 
		} else if (type!=null && type.trim().equals("regrate")) {
			titleStr = "平均注册率";
			BigDecimal others = BigDecimal.ZERO;
			BigDecimal totalVolume = CoopStat.getTotalVolume("regrate", builder.toString(), params);
			for (CoopStat coopStat : list) {
				if (totalVolume.intValue()>0&&(coopStat.getRegrate().doubleValue()/totalVolume.doubleValue())>0.1) {
					dataSet.setValue(coopStat.getCoopname(), coopStat.getRegrate()); 
				} else {
					others = others.add(coopStat.getRegrate());
				}
			}
			dataSet.setValue("Others", others); 
		} else if (type!=null && type.trim().equals("totalnum")) {
			titleStr = "总用户数";
			BigDecimal others = BigDecimal.ZERO;
			BigDecimal totalVolume = CoopStat.getTotalVolume("totalnum", builder.toString(), params);
			for (CoopStat coopStat : list) {
				if (totalVolume.intValue()>0&&(coopStat.getTotalnum().doubleValue()/totalVolume.doubleValue())>0.1) {
					dataSet.setValue(coopStat.getCoopname(), coopStat.getTotalnum()); 
				} else {
					others = others.add(coopStat.getTotalnum());
				}
			}
			dataSet.setValue("Others", others); 
		}
		String graphURL = ChartUtil.generatePieChart(request, dataSet, titleStr);
		view.addObject("graphURL", graphURL);
		view.setViewName("coopstat/piechart");
		return view;
	}
	
}
