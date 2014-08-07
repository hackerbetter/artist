<%@page import="java.math.BigDecimal"%>
<%@ page import="org.apache.commons.lang.StringUtils" %>
<%@ page import="com.artist.cms.util.Page" %>
<%@ page import="com.artist.cms.domain.CoopStat" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link href="<%=request.getContextPath()%>/styles/default.css" rel="stylesheet" type="text/css">
<script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/Dialog.js"></script>	
</head>
<script type="text/javascript">	
<% 
String errormsg = (String)request.getAttribute("errormsg");
if(StringUtils.isNotBlank(errormsg)) {
%>
function showerror() {
	Dialog.alert("<%=errormsg%>");
}
$(document).ready(function() {
	showerror();
});
<%	
}
%>	

</script>
<body>
	<div style="margin-top: 10px;"></div>
	<table width="100%" cellspacing="0" cellpadding="0" border="0">
		<tbody>
			<tr>
				<td style="padding: 2px 10px;">
					<form action="<%=request.getContextPath()%>/coopstat/list" method="post">
						<div style="float: left;">
							<table width="100%" cellspacing="2" cellpadding="2" border="0">
								<tr>
									<td align="right">&nbsp;渠道号:</td>
									<td>
										<input name="coopid" type="text" style="width: 120px" id="coopid" value="${coopid }" class="inputText" onfocus="this.select();" />
									</td>
									<td align="right">&nbsp;排序字段:</td>
									<td>
										<select id="orderBy" name="orderBy" style="width:130px">
											<option value="newusers" <c:if test='${"newusers" eq orderBy}'>selected</c:if>>今日新增用户</option>
											<option value="todayconnect" <c:if test='${"todayconnect" eq orderBy}'>selected</c:if>>今日联网用户</option>
											<option value="sevenday" <c:if test='${"sevenday" eq orderBy}'>selected</c:if>>7日联网用户</option>
											<option value="monthday" <c:if test='${"monthday" eq orderBy}'>selected</c:if>>30日联网用户</option>
											<option value="todayregnum" <c:if test='${"todayregnum" eq orderBy}'>selected</c:if>>今日注册用户</option>
											<option value="totalregnum" <c:if test='${"totalregnum" eq orderBy}'>selected</c:if>>注册用户总数</option>
											<option value="regrate" <c:if test='${"regrate" eq orderBy}'>selected</c:if>>注册率</option>
											<option value="totalnum" <c:if test='${"totalnum" eq orderBy}'>selected</c:if>>总用户数量</option>
											<option value="sumamt" <c:if test='${"sumamt" eq orderBy}'>selected</c:if>>购彩金额</option>
											<option value="buylotpersons" <c:if test='${"buylotpersons" eq orderBy}'>selected</c:if>>购彩人数</option>
											<option value="todayregrate" <c:if test='${"todayregrate" eq orderBy}'>selected</c:if>>今日注册率</option>
											<option value="todayregchargeusers" <c:if test='${"todayregchargeusers" eq orderBy}'>selected</c:if>>注册用户充值人数</option>
											<option value="todayregchargeamt" <c:if test='${"todayregchargeamt" eq orderBy}'>selected</c:if>>注册用户充值金额</option>
											<option value="chargerate" <c:if test='${"chargerate" eq orderBy}'>selected</c:if>>充值率</option>
											<option value="todayregbuylotusers" <c:if test='${"todayregbuylotusers" eq orderBy}'>selected</c:if>>注册用户购彩人数</option>
											<option value="todayregbuylotamt" <c:if test='${"todayregbuylotamt" eq orderBy}'>selected</c:if>>注册用户购彩金额</option>
											<option value="totalbuylotvalidorder" <c:if test='${"totalbuylotvalidorder" eq orderBy}'>selected</c:if>>购彩成功订单数</option>
											<option value="totalbuylotorder" <c:if test='${"totalbuylotorder" eq orderBy}'>selected</c:if>>购彩当天全部订单数</option>
											<option value="rechargeusernumber" <c:if test='${"rechargeusernumber" eq orderBy}'>selected</c:if>>充值人数</option>
											<option value="rechargeuseramount" <c:if test='${"rechargeuseramount" eq orderBy}'>selected</c:if>>充值金额</option>
											<option value="totalcashsuccess" <c:if test='${"totalcashsuccess" eq orderBy}'>selected</c:if>>提现订单成功数</option>
											<option value="totalcashamount" <c:if test='${"totalcashamount" eq orderBy}'>selected</c:if>>提现订单成功金额</option>
											<option value="totalcash" <c:if test='${"totalcash" eq orderBy}'>selected</c:if>>当天提现订单总数</option>
											
										</select>
									</td>
									<td align="right">&nbsp;排序方式:</td>
									<td>
										<select id="orderDir" name="orderDir" style="width:60px">
											<option value="asc" <c:if test='${"asc" eq orderDir}'>selected</c:if>>正序</option>
											<option value="desc" <c:if test='${"desc" eq orderDir}'>selected</c:if>>倒序</option>
										</select>
									</td>
									<td align="right">&nbsp;显示行数:</td>
									<td>
										<select id="maxResult" name="maxResult" style="width:60px">
											<option value="15" <c:if test='${"15" eq page.maxResult}'>selected</c:if>>15</option>
											<option value="30" <c:if test='${"30" eq page.maxResult}'>selected</c:if>>30</option>
											<option value="50" <c:if test='${"50" eq page.maxResult}'>selected</c:if>>50</option>
											<option value="100" <c:if test='${"100" eq page.maxResult}'>selected</c:if>>100</option>
										</select>
									</td>
									<td align="center"><input type="submit" value="查询" class="inputButton">&nbsp;&nbsp;&nbsp;
									</td>
								</tr>
							</table>
						</div>
					</form>
				</td>
			</tr>
			<tr>
				<td
					style="padding-top: 2px; padding-left: 6px; padding-right: 6px; padding-bottom: 2px;">
					<table width="200%" cellspacing="0" cellpadding="2"
						class="dataTable">
						<tbody>
							<tr class="dataTableHead">
								<td width="15%" class="thOver"><strong>渠道名称</strong></td>
								<td width="15%" class="thOver"><strong><a href="<%=request.getContextPath()%>/coopstat/piechart?type=newusers">今日新增用户</a></strong></td>
								<td width="15%" class="thOver"><strong><a href="<%=request.getContextPath()%>/coopstat/piechart?type=todayconnect">今日联网用户</a></strong></td>
								<td width="15%" class="thOver"><strong><a href="<%=request.getContextPath()%>/coopstat/piechart?type=sevenday">7日联网用户</a></strong></td>
								<td width="15%" class="thOver"><strong><a href="<%=request.getContextPath()%>/coopstat/piechart?type=monthday">30日联网用户</a></strong></td>
								<td width="15%" class="thOver"><strong><a href="<%=request.getContextPath()%>/coopstat/piechart?type=todayregnum">今日注册用户</a></strong></td>
								<td width="15%" class="thOver"><strong><a href="<%=request.getContextPath()%>/coopstat/piechart?type=totalregnum">注册用户总数</a></strong></td>
								<td width="8%" class="thOver"><strong><a href="<%=request.getContextPath()%>/coopstat/piechart?type=regrate">注册率</a></strong></td>
								<td width="15%" class="thOver"><strong><a href="<%=request.getContextPath()%>/coopstat/piechart?type=totalnum">总用户数量</a></strong></td>
							<%
								Page page2 = (Page)request.getAttribute("page");
								if (null!=page2 && null!=page2.getList()) {
									for(int i=0; i<page2.getList().size(); i++) {
										CoopStat coopstat = (CoopStat)page2.getList().get(i); //前1天的统计
										String coopid = coopstat.getCoopid();
										BigDecimal todaynew = coopstat.getNewusers();
										BigDecimal todayconnect = coopstat.getTodayconnect();
										BigDecimal sevendayconnect = coopstat.getSevenday();
										BigDecimal monthconnect = coopstat.getMonthday();
										BigDecimal totalusernum = coopstat.getTotalnum();
										BigDecimal totalregnum = coopstat.getTotalregnum();
										BigDecimal todayregnum = coopstat.getTodayregnum();

										//升降率
										float todaynewrate = 0;
										float todayconnectrate = 0;
										float sevendayconnectrate = 0;
										float monthconnectrate = 0;
										float totaluserrate = 0;
										float todayregnumcrate = 0;
										float totalregnumcrate = 0;
										if (null!=page2 && null!=page2.getList2()) {
											for(int j=0; j<page2.getList2().size(); j++) {
												CoopStat coopstat2 = (CoopStat)page2.getList2().get(j); //前2天的统计
												if (coopstat.getCoopid().equals(coopstat2.getCoopid())) {
													//今日新增用户
													BigDecimal todaynewusers2 = coopstat2.getNewusers();
													if (todaynewusers2.intValue()>0) {
														todaynewrate = 100*(todaynew.floatValue()-todaynewusers2.floatValue())/(todaynewusers2.floatValue());
											    		todaynewrate = (float)(Math.round(todaynewrate*100))/100;
													}
										    		//今日联网用户
										    		BigDecimal todayConnect2 = coopstat2.getTodayconnect();
										    		if (todayConnect2.intValue()>0) {
										    			todayconnectrate = 100*(todayconnect.floatValue()-todayConnect2.floatValue())/(todayConnect2.floatValue());
											    		todayconnectrate = (float)(Math.round(todayconnectrate*100))/100;
										    		}
										    		//7日联网用户
										    		BigDecimal severday2 = coopstat2.getSevenday();
										    		if (severday2.intValue()>0) {
										    			sevendayconnectrate = 100*(sevendayconnect.floatValue()-severday2.floatValue())/(severday2.floatValue());
											    		sevendayconnectrate = (float)(Math.round(sevendayconnectrate*100))/100;
										    		}
										    		//30日联网用户
										    		BigDecimal monthday2 = coopstat2.getMonthday();
										    		if (monthday2.intValue()>0) {
										    			monthconnectrate = 100*(monthconnect.floatValue()-monthday2.floatValue())/(monthday2.floatValue());
												    	monthconnectrate = (float)(Math.round(monthconnectrate*100))/100;
										    		}
											    	//今日注册用户
											    	BigDecimal todayregnum2 = coopstat2.getTodayregnum();
											    	if (todayregnum2.intValue()>0) {
											    		todayregnumcrate = 100*(todayregnum.floatValue()-todayregnum2.floatValue())/(todayregnum2.floatValue());
											    		todayregnumcrate = (float)(Math.round(todayregnumcrate*100))/100;
											    	}
										    		//注册用户总数
										    		BigDecimal totalregnum2 = coopstat2.getTotalregnum();
											    	if (totalregnum2.intValue()>0) {
											    		totalregnumcrate = 100*(totalregnum.floatValue()-totalregnum2.floatValue())/(totalregnum2.floatValue());
											    		totalregnumcrate = (float)(Math.round(totalregnumcrate*100))/100;
											    	}
										    		//总用户数量
										    		BigDecimal totalnum2 = coopstat2.getTotalnum();
											    	if (totalnum2.intValue()>0) {
											    		totaluserrate = 100*(totalusernum.floatValue()-totalnum2.floatValue())/(totalnum2.floatValue());
											    		totaluserrate = (float)(Math.round(totaluserrate*100))/100;
											    	}
										    		break;
												} else {
													continue;
												}
											}
										}
							%>
								<tr>
									<td title=""><%=coopstat.getCoopname() %></td>
									<td title=""><a href="<%=request.getContextPath()%>/coopstat/linechart?type=newusers&coopid=<%=coopid %>"><% if(todaynewrate>0){out.print("<font color=red>"+todaynew+"(+"+todaynewrate+"%)</a>");}else{out.print("<font color=darkgreen>"+todaynew+"("+todaynewrate+"%)");} %></a></td>
									<td title=""><a href="<%=request.getContextPath()%>/coopstat/linechart?type=todayconnect&coopid=<%=coopid %>"><% if(todayconnectrate>0){out.print("<font color=red>"+todayconnect+"(+"+todayconnectrate+"%)</a>");}else{out.print("<font color=darkgreen>"+todayconnect+"("+todayconnectrate+"%)");} %></td>
									<td title=""><a href="<%=request.getContextPath()%>/coopstat/linechart?type=sevenday&coopid=<%=coopid %>"><% if(sevendayconnectrate>0){out.print("<font color=red>"+sevendayconnect+"(+"+sevendayconnectrate+"%)</a>");}else{out.print("<font color=darkgreen>"+sevendayconnect+"("+sevendayconnectrate+"%)");} %></td>
									<td title=""><a href="<%=request.getContextPath()%>/coopstat/linechart?type=monthday&coopid=<%=coopid %>"><% if(monthconnectrate>0){out.print("<font color=red>"+monthconnect+"(+"+monthconnectrate+"%)</a>");}else{out.print("<font color=darkgreen>"+monthconnect+"("+monthconnectrate+"%)");} %></td>
									<td title=""><a href="<%=request.getContextPath()%>/coopstat/linechart?type=todayregnum&coopid=<%=coopid %>"><% if(todayregnumcrate>0){out.print("<font color=red>"+todayregnum+"(+"+todayregnumcrate+"%)</a>");}else{out.print("<font color=darkgreen>"+todayregnum+"("+todayregnumcrate+"%)");} %></td>
									<td title=""><a href="<%=request.getContextPath()%>/coopstat/linechart?type=totalregnum&coopid=<%=coopid %>"><% if(totalregnumcrate>0){out.print("<font color=red>"+totalregnum+"(+"+totalregnumcrate+"%)</a>");}else{out.print("<font color=darkgreen>"+totalregnum+"("+totalregnumcrate+"%)");} %></td>
									<td title=""><%=coopstat.getRegrate() %>%</td>
									<td title=""><a href="<%=request.getContextPath()%>/coopstat/linechart?type=totalnum&coopid=<%=coopid %>"><% if(totaluserrate>0){out.print("<font color=red>"+totalusernum+"(+"+totaluserrate+"%)</a>");}else{out.print("<font color=darkgreen>"+totalusernum+"("+totaluserrate+"%)");} %></td>
								</tr>
							<%} %>
							<tr>
								<td align="left" id="dg1_PageBar" colspan="11"><div
										style="float: right; font-family: Tahoma">
										<c:choose>
											<c:when test="${page.pageIndex != 0}">
												<a
													href="<%=request.getContextPath()%>/coopstat/list?orderBy=${orderBy}&orderDir=${orderDir}&maxResult=${page.maxResult}&pageIndex=0">第一页</a>
											</c:when>
											<c:otherwise>
										第一页
									</c:otherwise>
										</c:choose>
										|&nbsp;
										<c:choose>
											<c:when test="${page.pageIndex > 0}">
												<a
													href="<%=request.getContextPath()%>/coopstat/list?orderBy=${orderBy}&orderDir=${orderDir}&maxResult=${page.maxResult}&pageIndex=${page.pageIndex - 1}">上一页</a>
											</c:when>
											<c:otherwise>
										上一页
									</c:otherwise>
										</c:choose>
										&nbsp;|&nbsp;
										<c:choose>
											<c:when test="${page.pageIndex + 1 < page.totalPage}">
												<a
													href="<%=request.getContextPath()%>/coopstat/list?orderBy=${orderBy}&orderDir=${orderDir}&maxResult=${page.maxResult}&pageIndex=${page.pageIndex + 1}">下一页</a>
											</c:when>
											<c:otherwise>
										下一页
									</c:otherwise>
										</c:choose>
										&nbsp;|&nbsp;
										<c:choose>
											<c:when test="${page.pageIndex + 1 != page.totalPage}">
												<a
													href="<%=request.getContextPath()%>/coopstat/list?orderBy=${orderBy}&orderDir=${orderDir}&maxResult=${page.maxResult}&pageIndex=${page.totalPage - 1}">最末页</a>
											</c:when>
											<c:otherwise>
										最末页
									</c:otherwise>
										</c:choose>
										&nbsp;|&nbsp; &nbsp;&nbsp;转到第&nbsp;<input type="text"
											onkeyup="value=value.replace(/\D/g,'')" style="width: 40px"
											class="inputText" id="_PageBar_Index">&nbsp;页&nbsp;&nbsp;<input
											type="button" value="跳转" class="inputButton" onclick="go()">
										<script type="text/javascript">
											function go() {
												var pageindex = parseInt($("#_PageBar_Index").val()) - 1;
												window.location.href="<%=request.getContextPath()%>/coopstat/list?orderBy=${orderBy}&orderDir=${orderDir}&maxResult=${page.maxResult}&pageIndex=" + pageindex;
											}
										</script>
									</div>
									<div style="float: left; font-family: Tahoma">共
										${page.totalResult} 条记录，每页 ${page.maxResult} 条，当前第
										${page.pageIndex + 1} / ${page.totalPage} 页</div></td>
							</tr>
						</tbody>
						<%
							} 
						%>
					</table>
				</td>
			</tr>
		</tbody>
	</table>
</body>
</html>