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

<body>
	<div align="center">
		<p>&nbsp;</p>
		<form action="<%=request.getContextPath()%>/coopstat/linechart" method="post">
			<table>
				<tr>
					<td>开始时间:</td>
					<td>
						<input id="starttime" name="starttime" value="${starttime}"
						class="inputText" type="text" style="width: 100px;"
						 onclick="DateTime.onImageMouseDown(event,'Calendar','starttime');">
						<img vspace="1" align="absmiddle" onmousedown="DateTime.onImageMouseDown(event,'Calendar','starttime');"
						style="position: relative; left: -25px; margin-right: -20px; cursor: pointer;"
						src="<%=request.getContextPath()%>/images/Calendar.gif">
					</td>
					<td>&nbsp;结束时间:</td>
					<td>
						<input id="endtime" name="endtime" value="${endtime}"
						class="inputText" type="text" ztype="date"
						style="width: 100px;"
						onclick="DateTime.onImageMouseDown(event,'Calendar','endtime');">
						<img vspace="1" align="absmiddle" onmousedown="DateTime.onImageMouseDown(event,'Calendar','endtime');"
						style="position: relative; left: -25px; margin-right: -20px; cursor: pointer;"
						src="<%=request.getContextPath()%>/images/Calendar.gif">
					</td>
					<td>
						&nbsp;<input name="type" type="hidden" value="${type}" />
						<input name="coopid" type="hidden" value="${coopid}" />
						<input type="submit" value="查询" />
					</td>
				</tr>
			</table>
		</form>
	</div>
	<div style="margin-top: 10px;">
		<img src="${graphURL}" width="900" height="600" border="0" >
	</div>
	<div>
		<h3 align="center">渠道统计</h3>
		<div align="right">
			<input type="button" value="返回" onclick="javascript:history.go(-1)" align="right" />
		</div>
		<table width="100%" cellspacing="0" cellpadding="2" class="dataTable">
			<tbody>
				<tr class="dataTableHead">
					<td width="12%" class="thOver"><strong>日期</strong></td>
					<td width="7%" class="thOver"><strong>数量</strong></td>
				</tr>
				<c:forEach items="${page.list}" var="coopstat" varStatus="num">
					<tr>
						<td><fmt:formatDate value="${coopstat.statdate }" pattern="yyyy-MM-dd" /></td>
						<c:if test='${"newusers" eq type}'><td>${coopstat.newusers }</td></c:if>
						<c:if test='${"todayconnect" eq type}'><td>${coopstat.todayconnect }</td></c:if>
						<c:if test='${"sevenday" eq type}'><td>${coopstat.sevenday }</td></c:if>
						<c:if test='${"monthday" eq type}'><td>${coopstat.monthday }</td></c:if>
						<c:if test='${"todayregnum" eq type}'><td>${coopstat.todayregnum }</td></c:if>
						<c:if test='${"totalregnum" eq type}'><td>${coopstat.totalregnum }</td></c:if>
						<c:if test='${"totalnum" eq type}'><td>${coopstat.totalnum }</td></c:if>
						<c:if test='${"sumamt" eq type}'><td>${coopstat.sumamt }</td></c:if>
						<c:if test='${"totalbuylotvalidorder" eq type}'><td>${coopstat.totalbuylotvalidorder }</td></c:if>
						<c:if test='${"totalbuylotorder" eq type}'><td>${coopstat.totalbuylotorder }</td></c:if>
						<c:if test='${"rechargeusernumber" eq type}'><td>${coopstat.rechargeusernumber }</td></c:if>
						<c:if test='${"rechargeuseramount" eq type}'><td>${coopstat.rechargeuseramount }</td></c:if>
						<c:if test='${"totalcashsuccess" eq type}'><td>${coopstat.totalcashsuccess }</td></c:if>
						<c:if test='${"totalcashamount" eq type}'><td>${coopstat.totalcashamount }</td></c:if>
						<c:if test='${"totalcash" eq type}'><td>${coopstat.totalcash }</td></c:if>
						<c:if test='${"buylotpersons" eq type}'><td>${coopstat.buylotpersons }</td></c:if>
						<c:if test='${"todayregchargeusers" eq type}'><td>${coopstat.todayregchargeusers }</td></c:if>
						<c:if test='${"todayregchargeamt" eq type}'><td>${coopstat.todayregchargeamt }</td></c:if>
						<c:if test='${"todayregbuylotusers" eq type}'><td>${coopstat.todayregbuylotusers }</td></c:if>
						<c:if test='${"todayregbuylotamt" eq type}'><td>${coopstat.todayregbuylotamt }</td></c:if>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</div>
</body>
</html>