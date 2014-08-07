package com.artist.cms.controller;

import com.artist.cms.domain.TversionStat;
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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@RequestMapping(value = "/versionstat")
@Controller
public class VersionStatController {

	private Calendar calendar = Calendar.getInstance();
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping("/list")
	public ModelAndView list(@RequestParam(value = "platform", required = false, defaultValue="0") String platform,
			@ModelAttribute("page") Page page, ModelAndView view) {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			//统计前1天的记录
			StringBuilder builder = new StringBuilder(" where");
			List<Object> params = new ArrayList<Object>();
			
			if (StringUtils.isNotBlank(platform)&&!platform.equals("0")) {
				builder.append(" o.platform=? and");
				params.add(platform);
			}
			
			//统计时间(statdate)
			calendar.setTime(new Date());
			calendar.add(Calendar.DATE, -1);
			builder.append(" date_format(o.statdate, '%Y-%m-%d')=? and");
			params.add(sdf.format(calendar.getTime()));
			
			if (builder.toString().endsWith("and")) {
				builder.delete(builder.length() - 3, builder.length());
			}
			if (builder.toString().endsWith("where")) {
				builder.delete(builder.length() - 5, builder.length());
			}
			TversionStat.findList(builder.toString(), "order by o.version desc", params, page, true);
			
			//查询前2天的记录
			StringBuilder builder2 = new StringBuilder(" where");
			List<Object> params2 = new ArrayList<Object>();
			//统计时间(statdate)
			calendar.setTime(new Date());
			calendar.add(Calendar.DATE, -2);
			builder2.append(" date_format(o.statdate, '%Y-%m-%d')=? and");
			params2.add(sdf.format(calendar.getTime()));
			
			if (builder2.toString().endsWith("and")) {
				builder2.delete(builder2.length() - 3, builder2.length());
			}
			if (builder2.toString().endsWith("where")) {
				builder2.delete(builder2.length() - 5, builder2.length());
			}
			TversionStat.findList(builder2.toString(), "order by o.version desc", params2, page, false);
			
			view.addObject("platform", platform);
			view.addObject("page", page);
		} catch (Exception e) {
			view.addObject("errormsg", e.getMessage());
		}
		view.setViewName("versionstat/list");
		return view;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping("/linechart")
	public ModelAndView lineChart(@RequestParam(value = "type") String type,
			@RequestParam(value = "platform") String platform,
			@RequestParam(value = "version") String version,
			@ModelAttribute("page") Page page, HttpServletRequest request, ModelAndView view) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat sdf2 = new SimpleDateFormat("MM-dd");
		
		StringBuilder builder = new StringBuilder(" where");
		List<Object> params = new ArrayList<Object>();
		//开始时间
		calendar.setTime(new Date());
		calendar.add(Calendar.DATE, -30);
		builder.append(" date_format(o.statdate, '%Y-%m-%d')>=? and");
		params.add(sdf.format(calendar.getTime()));
		//结束时间
		calendar.setTime(new Date());
		calendar.add(Calendar.DATE, -1);
		builder.append(" date_format(o.statdate, '%Y-%m-%d')<=? and");
		params.add(sdf.format(calendar.getTime()));
		//平台
		if (StringUtils.isNotBlank(platform)) {
			builder.append(" o.platform= ? and");
			params.add(platform);
		}
		//版本号
		if (StringUtils.isNotBlank(version)) {
			builder.append(" o.version like ? and");
			params.add(version+"%");
		}
		
		if (builder.toString().endsWith("and")) {
			builder.delete(builder.length() - 3, builder.length());
		}
		if (builder.toString().endsWith("where")) {
			builder.delete(builder.length() - 5, builder.length());
		}
		page.setMaxResult(1000); //设置最大记录数
		TversionStat.findList(builder.toString(), "order by o.statdate asc", params, page, true);
		
		DefaultCategoryDataset linedataset = new DefaultCategoryDataset(); //数据
		List<TversionStat> list = (List<TversionStat>)page.getList();
		String headStr = ""; //图片上方显示文字
		if (type!=null && type.trim().equals("todaynewaddusers")) {
			headStr = "当日新增用户";
			if (list!=null && list.size()>0) {
				for (TversionStat versionStat : list) {
					String todayNewAddUsers = versionStat.getTodaynewaddusers();
					if (todayNewAddUsers==null) {
						todayNewAddUsers = "0";
					}
					linedataset.addValue(Double.parseDouble(todayNewAddUsers), "变化曲线", sdf2.format(versionStat.getStatdate()));
				}
			}
		} else if (type!=null && type.trim().equals("todayconnectnetusers")) {
			headStr = "当日联网用户";
			if (list!=null && list.size()>0) {
				for (TversionStat versionStat : list) {
					String todayConnectNetUsers = versionStat.getTodayconnectnetusers();
					if (todayConnectNetUsers==null) {
						todayConnectNetUsers = "0";
					}
					linedataset.addValue(Double.parseDouble(todayConnectNetUsers), "变化曲线", sdf2.format(versionStat.getStatdate()));
				}
			}
		} else if (type!=null && type.trim().equals("weekconnectnetusers")) {
			headStr = "7日联网用户";
			if (list!=null && list.size()>0) {
				for (TversionStat versionStat : list) {
					String weekConnectNetUsers = versionStat.getWeekconnectnetusers();
					if (weekConnectNetUsers==null) {
						weekConnectNetUsers = "0";
					}
					linedataset.addValue(Double.parseDouble(weekConnectNetUsers), "变化曲线", sdf2.format(versionStat.getStatdate()));
				}
			}
		} else if (type!=null && type.trim().equals("monthconnectnetusers")) {
			headStr = "30日联网用户";
			if (list!=null && list.size()>0) {
				for (TversionStat versionStat : list) {
					String monthConnectNetUsers = versionStat.getMonthconnectnetusers();
					if (monthConnectNetUsers==null) {
						monthConnectNetUsers = "0";
					}
					linedataset.addValue(Double.parseDouble(monthConnectNetUsers), "变化曲线", sdf2.format(versionStat.getStatdate()));
				}
			}
		} else if (type!=null && type.trim().equals("todayregisterusers")) {
			headStr = "今日注册用户";
			if (list!=null && list.size()>0) {
				for (TversionStat versionStat : list) {
					String todayRegisterUsers = versionStat.getTodayregisterusers();
					if (todayRegisterUsers==null) {
						todayRegisterUsers = "0";
					}
					linedataset.addValue(Double.parseDouble(todayRegisterUsers), "变化曲线", sdf2.format(versionStat.getStatdate()));
				}
			}
		} else if (type!=null && type.trim().equals("totalregisterusers")) {
			headStr = "注册用户总数";
			if (list!=null && list.size()>0) {
				for (TversionStat versionStat : list) {
					String totalRegisterUsers = versionStat.getTotalregisterusers();
					if (totalRegisterUsers==null) {
						totalRegisterUsers = "0";
					}
					linedataset.addValue(Double.parseDouble(totalRegisterUsers), "变化曲线", sdf2.format(versionStat.getStatdate()));
				}
			}
		} else if (type!=null && type.trim().equals("totalusers")) {
			headStr = "总用户数量";
			if (list!=null && list.size()>0) {
				for (TversionStat versionStat : list) {
					String totalUsers = versionStat.getTotalusers();
					if (totalUsers==null) {
						totalUsers = "0";
					}
					linedataset.addValue(Double.parseDouble(totalUsers), "变化曲线", sdf2.format(versionStat.getStatdate()));
				}
			}
		}
		String graphURL = ChartUtil.generateLineChart(request, linedataset, headStr, "日期", "数量");
		view.addObject("graphURL", graphURL);
		view.addObject("type", type);
		view.setViewName("versionstat/linechart");
		return view;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping("/piechart")
	public ModelAndView pieChart(@RequestParam(value = "type", required = false) String type,
			@ModelAttribute("page") Page page, HttpServletRequest request, ModelAndView view) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		
		StringBuilder builder = new StringBuilder(" where");
		List<Object> params = new ArrayList<Object>();
		
		calendar.setTime(new Date());
		calendar.add(Calendar.DATE, -1);
		builder.append(" date_format(o.statdate, '%Y-%m-%d')=? and");
		params.add(sdf.format(calendar.getTime()));
		
		if (builder.toString().endsWith("and")) {
			builder.delete(builder.length() - 3, builder.length());
		}
		if (builder.toString().endsWith("where")) {
			builder.delete(builder.length() - 5, builder.length());
		}
		page.setMaxResult(1000); //设置最大记录数
		TversionStat.findList(builder.toString(), "order by o.version asc", params, page, true);
		
		DefaultPieDataset dataSet = new DefaultPieDataset(); //数据集
		List<TversionStat> list = (List<TversionStat>)page.getList();
		String titleStr = "";
		if (type!=null && type.trim().equals("todaynewaddusers")) {
			titleStr = "当日新增用户";
			if (list!=null && list.size()>0) {
				for (TversionStat versionStat : list) {
					dataSet.setValue(versionStat.getVersion(), Long.parseLong(versionStat.getTodaynewaddusers()));
				}
			}
		} else if (type!=null && type.trim().equals("todayconnectnetusers")) {
			titleStr = "当日联网用户";
			if (list!=null && list.size()>0) {
				for (TversionStat versionStat : list) {
					dataSet.setValue(versionStat.getVersion(), Long.parseLong(versionStat.getTodayconnectnetusers()));
				}
			}
		} else if (type!=null && type.trim().equals("weekconnectnetusers")) {
			titleStr = "7日联网用户";
			if (list!=null && list.size()>0) {
				for (TversionStat versionStat : list) {
					dataSet.setValue(versionStat.getVersion(), Long.parseLong(versionStat.getWeekconnectnetusers()));
				}
			}
		} else if (type!=null && type.trim().equals("monthconnectnetusers")) {
			titleStr = "30日联网用户";
			if (list!=null && list.size()>0) {
				for (TversionStat versionStat : list) {
					dataSet.setValue(versionStat.getVersion(), Long.parseLong(versionStat.getMonthconnectnetusers()));
				}
			}
		} else if (type!=null && type.trim().equals("todayregisterusers")) {
			titleStr = "今日注册用户";
			if (list!=null && list.size()>0) {
				for (TversionStat versionStat : list) {
					dataSet.setValue(versionStat.getVersion(), Long.parseLong(versionStat.getTodayregisterusers()));
				}
			}
		} else if (type!=null && type.trim().equals("totalregisterusers")) {
			titleStr = "注册用户总数";
			if (list!=null && list.size()>0) {
				for (TversionStat versionStat : list) {
					dataSet.setValue(versionStat.getVersion(), Long.parseLong(versionStat.getTotalregisterusers()));
				}
			}
		} else if (type!=null && type.trim().equals("totalusers")) {
			titleStr = "总用户数量";
			if (list!=null && list.size()>0) {
				for (TversionStat versionStat : list) {
					dataSet.setValue(versionStat.getVersion(), Long.parseLong(versionStat.getTotalusers()));
				}
			}
		}
		String graphURL = ChartUtil.generatePieChart(request, dataSet, titleStr);
		view.addObject("graphURL", graphURL);
		view.setViewName("versionstat/piechart");
		return view;
	}
	
}
