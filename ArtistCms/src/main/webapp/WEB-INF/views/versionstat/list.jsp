<%@ page import="org.apache.commons.lang.StringUtils" %>
<%@ page import="com.artist.cms.util.Page" %>
<%@ page import="com.artist.cms.domain.TversionStat" %>
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
<%}%>	

</script>
<body>
	<div style="margin-top: 10px;"></div>
	<table width="100%" cellspacing="0" cellpadding="0" border="0">
		<tbody>
			<tr>
				<td style="padding: 2px 10px;">
					<form action="<%=request.getContextPath()%>/versionstat/list" method="post">
						<div style="float: left;">
							<table width="100%" cellspacing="2" cellpadding="2" border="0">
								<tr>
									<td align="right">平台:</td>
									<td>
										<select id="platform" name="platform" style="width:100px">
											<option value="0" <c:if test='${"0" eq platform}'>selected</c:if>>全部</option>
											<option value="android" <c:if test='${"android" eq platform}'>selected</c:if>>android</option>
											<option value="iPhone" <c:if test='${"iPhone" eq platform}'>selected</c:if>>iPhone</option>
										</select>
									</td>
									<td align="right">显示行数:</td>
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
					<table width="100%" cellspacing="0" cellpadding="2"
						class="dataTable">
						<tbody>
							<tr class="dataTableHead">
								<td width="7%" class="thOver"><strong>平台</strong></td>
								<td width="6%" class="thOver"><strong>版本号</strong></td>
								<td width="20%" class="thOver"><strong><a href="<%=request.getContextPath()%>/versionstat/piechart?type=todaynewaddusers">当日新增用户</a></strong></td>
								<td width="20%" class="thOver"><strong><a href="<%=request.getContextPath()%>/versionstat/piechart?type=todayconnectnetusers">当日联网用户</a></strong></td>
								<td width="20%" class="thOver"><strong><a href="<%=request.getContextPath()%>/versionstat/piechart?type=weekconnectnetusers">7日联网用户</a></strong></td>
								<td width="20%" class="thOver"><strong><a href="<%=request.getContextPath()%>/versionstat/piechart?type=monthconnectnetusers">30日联网用户</a></strong></td>
								<td width="20%" class="thOver"><strong><a href="<%=request.getContextPath()%>/versionstat/piechart?type=todayregisterusers">今日注册用户</a></strong></td>
								<td width="20%" class="thOver"><strong><a href="<%=request.getContextPath()%>/versionstat/piechart?type=totalregisterusers">注册用户总数</a></strong></td>
								<!-- <td width="10%" class="thOver"><strong>注册率</strong></td> -->
								<td width="20%" class="thOver"><strong><a href="<%=request.getContextPath()%>/versionstat/piechart?type=totalusers">总用户数量</a></strong></td>
							</tr>
							<%
								Page page2 = (Page)request.getAttribute("page");
								if (null!=page2 && null!=page2.getList()) {
									for(int i=0; i<page2.getList().size(); i++) {
										TversionStat versionStat = (TversionStat)page2.getList().get(i); //前1天的统计
										String todayNewAddUsers = versionStat.getTodaynewaddusers();
										String todayConnectNetUsers = versionStat.getTodayconnectnetusers();
										String weekConnectNetUsers = versionStat.getWeekconnectnetusers();
										String monthConnectNetUsers = versionStat.getMonthconnectnetusers();
										String todayRegisterUsers = versionStat.getTodayregisterusers();
										String totalRegisterUsers = versionStat.getTotalregisterusers();
										String totalUsers = versionStat.getTotalusers();
										String platform = versionStat.getPlatform();
										String version = versionStat.getVersion();
										//升降率
										float todayNewAddUsersRate = 0;
										float todayConnectNetUsersRate = 0;
										float weekConnectNetUsersRate = 0;
										float monthConnectNetUsersRate = 0;
										float todayRegisterUsersRate = 0;
										float totalRegisterUsersRate = 0;
										float totalUsersRate = 0;
										if (null!=page2 && null!=page2.getList2()) {
											for(int j=0; j<page2.getList2().size(); j++) {
												TversionStat versionStat2 = (TversionStat)page2.getList2().get(j); //前2天的统计
												if (versionStat.getVersion().equals(versionStat2.getVersion())) {
													//当日新增用户
													String todayNewAddUsers2 = versionStat2.getTodaynewaddusers();
													if (todayNewAddUsers2!=null&&!todayNewAddUsers2.equals("0")) {
														todayNewAddUsersRate = 100*(Float.parseFloat(todayNewAddUsers)-Float.parseFloat(todayNewAddUsers2))/(Float.parseFloat(todayNewAddUsers2));
														todayNewAddUsersRate = (float)(Math.round(todayNewAddUsersRate*100))/100;
													}
													//当日联网用户
										    		String todayConnectNetUsers2 = versionStat2.getTodayconnectnetusers();
													if (todayConnectNetUsers2!=null&&!todayConnectNetUsers2.equals("0")) {
														todayConnectNetUsersRate = 100*(Float.parseFloat(todayConnectNetUsers)-Float.parseFloat(todayConnectNetUsers2))/(Float.parseFloat(todayConnectNetUsers2));
														todayConnectNetUsersRate = (float)(Math.round(todayConnectNetUsersRate*100))/100;
													}
										    		//7日联网用户
										    		String weekConnectNetUsers2 = versionStat2.getWeekconnectnetusers();
										    		if (weekConnectNetUsers2!=null&&!weekConnectNetUsers2.equals("0")) {
										    			weekConnectNetUsersRate = 100*(Float.parseFloat(weekConnectNetUsers)-Float.parseFloat(weekConnectNetUsers2))/(Float.parseFloat(weekConnectNetUsers2));
										    			weekConnectNetUsersRate = (float)(Math.round(weekConnectNetUsersRate*100))/100;
										    		}
										    		//30日联网用户
										    		String monthConnectNetUsers2 = versionStat2.getMonthconnectnetusers();
										    		if (monthConnectNetUsers2!=null&&!monthConnectNetUsers2.equals("0")) {
										    			monthConnectNetUsersRate = 100*(Float.parseFloat(monthConnectNetUsers)-Float.parseFloat(monthConnectNetUsers2))/(Float.parseFloat(monthConnectNetUsers2));
										    			monthConnectNetUsersRate = (float)(Math.round(monthConnectNetUsersRate*100))/100;
										    		}
										    		//今日注册用户
										    		String todayRegisterUsers2 = versionStat2.getTodayregisterusers();
										    		if (todayRegisterUsers2!=null&&!todayRegisterUsers2.equals("0")) {
										    			todayRegisterUsersRate = 100*(Float.parseFloat(todayRegisterUsers)-Float.parseFloat(todayRegisterUsers2))/(Float.parseFloat(todayRegisterUsers2));
										    			todayRegisterUsersRate = (float)(Math.round(todayRegisterUsersRate*100))/100;
										    		}
										    		//注册用户总数
										    		String totalRegisterUsers2 = versionStat2.getTotalregisterusers();
										    		if (totalRegisterUsers!=null&&!totalRegisterUsers.equals("0")) {
										    			totalRegisterUsersRate = 100*(Float.parseFloat(totalRegisterUsers)-Float.parseFloat(totalRegisterUsers2))/(Float.parseFloat(totalRegisterUsers2));
										    			totalRegisterUsersRate = (float)(Math.round(totalRegisterUsersRate*100))/100;
										    		}
										    		//总用户数量
										    		String totalUsers2 = versionStat2.getTotalusers();
										    		if (totalUsers2!=null&&!totalUsers2.equals("0")) {
										    			totalUsersRate = 100*(Float.parseFloat(totalUsers)-Float.parseFloat(totalUsers2))/(Float.parseFloat(totalUsers2));
										    			totalUsersRate = (float)(Math.round(totalUsersRate*100))/100;
										    		}
										    		break;
												} else {
													continue;
												}
											}
										}
							%>
								<tr>
									<td title=""><%=platform %></td>
									<td title=""><%=version %></td>
									<td title=""><a href="<%=request.getContextPath()%>/versionstat/linechart?type=todaynewaddusers&version=<%=version %>&platform=<%=platform %>"><% if(todayNewAddUsersRate>0){out.print("<font color=red>"+todayNewAddUsers+"(+"+todayNewAddUsersRate+"%)</a>");}else{out.print("<font color=darkgreen>"+todayNewAddUsers+"("+todayNewAddUsersRate+"%)");} %></a></td>
									<td title=""><a href="<%=request.getContextPath()%>/versionstat/linechart?type=todayconnectnetusers&version=<%=version %>&platform=<%=platform %>"><% if(todayConnectNetUsersRate>0){out.print("<font color=red>"+todayConnectNetUsers+"(+"+todayConnectNetUsersRate+"%)</a>");}else{out.print("<font color=darkgreen>"+todayConnectNetUsers+"("+todayConnectNetUsersRate+"%)");} %></a></td>
									<td title=""><a href="<%=request.getContextPath()%>/versionstat/linechart?type=weekconnectnetusers&version=<%=version %>&platform=<%=platform %>"><% if(weekConnectNetUsersRate>0){out.print("<font color=red>"+weekConnectNetUsers+"(+"+weekConnectNetUsersRate+"%)</a>");}else{out.print("<font color=darkgreen>"+weekConnectNetUsers+"("+weekConnectNetUsersRate+"%)");} %></a></td>
									<td title=""><a href="<%=request.getContextPath()%>/versionstat/linechart?type=monthconnectnetusers&version=<%=version %>&platform=<%=platform %>"><% if(monthConnectNetUsersRate>0){out.print("<font color=red>"+monthConnectNetUsers+"(+"+monthConnectNetUsersRate+"%)</a>");}else{out.print("<font color=darkgreen>"+monthConnectNetUsers+"("+monthConnectNetUsersRate+"%)");} %></a></td>
									<td title=""><a href="<%=request.getContextPath()%>/versionstat/linechart?type=todayregisterusers&version=<%=version %>&platform=<%=platform %>"><% if(todayRegisterUsersRate>0){out.print("<font color=red>"+todayRegisterUsers+"(+"+todayRegisterUsersRate+"%)</a>");}else{out.print("<font color=darkgreen>"+todayRegisterUsers+"("+todayRegisterUsersRate+"%)");} %></a></td>
									<td title=""><a href="<%=request.getContextPath()%>/versionstat/linechart?type=totalregisterusers&version=<%=version %>&platform=<%=platform %>"><% if(totalRegisterUsersRate>0){out.print("<font color=red>"+totalRegisterUsers+"(+"+totalRegisterUsersRate+"%)</a>");}else{out.print("<font color=darkgreen>"+totalRegisterUsers+"("+totalRegisterUsersRate+"%)");} %></a></td>
									<%-- <td title=""><%=versionStat.getRegisterrate() %>%</td> --%>
									<td title=""><a href="<%=request.getContextPath()%>/versionstat/linechart?type=totalusers&version=<%=version %>&platform=<%=platform %>"><% if(totalUsersRate>0){out.print("<font color=red>"+totalUsers+"(+"+totalUsersRate+"%)</a>");}else{out.print("<font color=darkgreen>"+totalUsers+"("+totalUsersRate+"%)");} %></a></td>
								</tr>
							<%
								} 
							%>
							<tr>
								<td align="left" id="dg1_PageBar" colspan="17"><div
										style="float: right; font-family: Tahoma">
										<c:choose>
											<c:when test="${page.pageIndex != 0}">
												<a
													href="<%=request.getContextPath()%>/versionstat/list?platform=${platform }&maxResult=${page.maxResult}&pageIndex=0">第一页</a>
											</c:when>
											<c:otherwise>
										第一页
									</c:otherwise>
										</c:choose>
										|&nbsp;
										<c:choose>
											<c:when test="${page.pageIndex > 0}">
												<a
													href="<%=request.getContextPath()%>/versionstat/list?platform=${platform }&maxResult=${page.maxResult}&pageIndex=${page.pageIndex - 1}">上一页</a>
											</c:when>
											<c:otherwise>
										上一页
									</c:otherwise>
										</c:choose>
										&nbsp;|&nbsp;
										<c:choose>
											<c:when test="${page.pageIndex + 1 < page.totalPage}">
												<a
													href="<%=request.getContextPath()%>/versionstat/list?platform=${platform }&maxResult=${page.maxResult}&pageIndex=${page.pageIndex + 1}">下一页</a>
											</c:when>
											<c:otherwise>
										下一页
									</c:otherwise>
										</c:choose>
										&nbsp;|&nbsp;
										<c:choose>
											<c:when test="${page.pageIndex + 1 != page.totalPage}">
												<a
													href="<%=request.getContextPath()%>/versionstat/list?platform=${platform }&maxResult=${page.maxResult}&pageIndex=${page.totalPage - 1}">最末页</a>
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
												window.location.href="<%=request.getContextPath()%>/versionstat/list?platform=${platform }&maxResult=${page.maxResult}&pageIndex=" + pageindex;
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