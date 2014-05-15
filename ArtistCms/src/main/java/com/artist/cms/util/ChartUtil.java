package com.artist.cms.util;

import java.awt.Color;
import java.awt.Font;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.NumberFormat;

import javax.servlet.http.HttpServletRequest;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartRenderingInfo;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.entity.StandardEntityCollection;
import org.jfree.chart.labels.StandardCategoryItemLabelGenerator;
import org.jfree.chart.labels.StandardPieSectionLabelGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PiePlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer3D;
import org.jfree.chart.renderer.category.LineAndShapeRenderer;
import org.jfree.chart.servlet.ServletUtilities;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;

public class ChartUtil {

	/**
	 * 生成折线图
	 * @param request
	 * @param linedataset 数据集
	 * @param headStr 图片上方显示文字
	 * @param xdateStr X轴
	 * @param ynumStr Y轴
	 * @return
	 */
	public static String generateLineChart(HttpServletRequest request, DefaultCategoryDataset linedataset, String headStr, String xdateStr, String ynumStr) {
		// 定义图表对象
		JFreeChart chart = ChartFactory.createLineChart(headStr, // chart title
				xdateStr, // domain axis label
				ynumStr, // range axis label
				linedataset, // data
				PlotOrientation.VERTICAL, // orientation
				true, // include legend
				true, // tooltips
				false // urls
				);
		CategoryPlot plot = chart.getCategoryPlot();
		NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
		rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
		rangeAxis.setAutoRangeIncludesZero(true);
		rangeAxis.setUpperMargin(0.20);
		rangeAxis.setLabelAngle(Math.PI / 2.0);
		//显示节点处的值
		LineAndShapeRenderer renderer = (LineAndShapeRenderer) plot.getRenderer();
		renderer.setBaseItemLabelGenerator(new StandardCategoryItemLabelGenerator());
		//renderer.setItemLabelFont(new Font("黑体", Font.PLAIN, 12));
		renderer.setBaseItemLabelPaint(new Color(102, 102, 102));
		//renderer.setItemLabelsVisible(true);
		renderer.setBaseItemLabelsVisible(true);  
		plot.setNoDataMessage("无数据");
		
		StandardEntityCollection sec = new StandardEntityCollection();
		ChartRenderingInfo info = new ChartRenderingInfo(sec);
		String filename = null;
		try {
			filename = ServletUtilities.saveChartAsPNG(chart, 900, 600, info, request.getSession());
		} catch (IOException e) {
			e.printStackTrace();
		}
		String graphURL = request.getContextPath()+"/servlet/DisplayChart?filename="+filename;
		return graphURL;
	}
	
	/**
	 * 生成饼状图
	 * @param request
	 * @param data 数据集
	 * @param titleStr 标题
	 * @return
	 */
	public static String generatePieChart(HttpServletRequest request, DefaultPieDataset dataSet, String titleStr) {
		PiePlot plot = new PiePlot(dataSet);
		JFreeChart chart = new JFreeChart("",JFreeChart.DEFAULT_TITLE_FONT, plot, true);
		chart.setBackgroundPaint(java.awt.Color.white);//可选，设置图片背景色
		//chart.getTitle().setFont(new Font("宋体", Font.BOLD, 16));
		chart.setTitle(titleStr);//可选，设置图片标题
		// 图片中显示百分比:自定义方式，{0} 表示选项， {1} 表示数值， {2} 表示所占比例 ,小数点后两位
		plot.setLabelGenerator(new StandardPieSectionLabelGenerator(
		    "{0}={1}({2})", NumberFormat.getNumberInstance(),
		    new DecimalFormat("0.00%")));
		// 图例显示百分比:自定义方式， {0} 表示选项， {1} 表示数值， {2} 表示所占比例
		plot.setLegendLabelGenerator(new StandardPieSectionLabelGenerator(
		    "{0}={1}({2})"));
		plot.setNoDataMessage("无数据");
		ChartRenderingInfo info = new ChartRenderingInfo(new StandardEntityCollection());
		//500是图片长度，300是图片高度
		String filename = null;
		try {
			filename = ServletUtilities.saveChartAsPNG(chart, 800, 500, info, request.getSession());
		} catch (IOException e) {
			e.printStackTrace();
		}
		String graphURL = request.getContextPath() + "/servlet/DisplayChart?filename=" + filename; 
		return graphURL;
	}
	
	/**
	 * 生成柱状图
	 * @param request
	 * @param dataSet 数据集
	 * @param titleStr 标题
	 * @return
	 */
	@SuppressWarnings("deprecation")
	public static String generateBarChart(HttpServletRequest request, DefaultCategoryDataset dataSet, String titleStr) {
		JFreeChart chart = ChartFactory.createBarChart3D(titleStr, "", "数量", dataSet, PlotOrientation.VERTICAL, false, false, false);
	    chart.setBackgroundPaint(new Color(0xE1E1E1));
	    //chart.set
	    CategoryPlot plot = chart.getCategoryPlot();
	    // 设置Y轴显示整数
	    NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
	    rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
	    CategoryAxis domainAxis = plot.getDomainAxis();
	    //设置距离图片左端距离
	    domainAxis.setLowerMargin(0.05);
	    BarRenderer3D customBarRenderer = (BarRenderer3D) plot.getRenderer(); 
	    //设置柱的颜色
	    customBarRenderer.setSeriesPaint(0, Color.RED); 
	    //配置字体      
	    Font xtickfont = new Font("宋体",Font.PLAIN,15) ;// x轴刻度字体   
	    Font ytickfont = new Font("宋体",Font.PLAIN,15) ;// Y轴刻度字体 
	    plot.getDomainAxis().setTickLabelFont(xtickfont); //x轴刻度字体 
	    plot.getRangeAxis().setTickLabelFont(ytickfont); //y轴标题字体   
	    //设置顶端数据值
	    customBarRenderer.setItemLabelGenerator(new StandardCategoryItemLabelGenerator());
	    customBarRenderer.setItemLabelFont(new Font("黑体",Font.PLAIN,20));     
	    customBarRenderer.setItemLabelsVisible(true);
	    String filename = null;
		try {
			filename = ServletUtilities.saveChartAsPNG(chart, 950, 500, null, request.getSession());
		} catch (IOException e) {
			e.printStackTrace();
		}
	    String graphURL = request.getContextPath() + "/servlet/DisplayChart?filename=" + filename;
		return graphURL;
	}
	
}
