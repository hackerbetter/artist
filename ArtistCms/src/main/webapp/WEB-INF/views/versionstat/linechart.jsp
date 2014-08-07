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
	<div style="margin-top: 10px;">
		<img src="${graphURL}" width="900" height="600" border="0" >
	</div>
	<div>
		<h3 align="center">Ruyicai Client 版本统计</h3>
		<div align="right">
			<input type="button" value="返回" onclick="javascript:history.go(-1)" align="right" />
		</div>
		<table width="100%" cellspacing="0" cellpadding="2" class="dataTable">
			<tbody>
				<tr class="dataTableHead">
					<td width="12%" class="thOver"><strong>日期</strong></td>
					<td width="7%" class="thOver"><strong>数量</strong></td>
				</tr>
				<c:forEach items="${page.list}" var="versionStat" varStatus="num">
					<tr>
						<td><fmt:formatDate value="${versionStat.statdate }" pattern="yyyy-MM-dd" /></td>
						<c:if test='${"todaynewaddusers" eq type}'><td>${versionStat.todaynewaddusers }</td></c:if>
						<c:if test='${"todayconnectnetusers" eq type}'><td>${versionStat.todayconnectnetusers }</td></c:if>
						<c:if test='${"weekconnectnetusers" eq type}'><td>${weekconnectnetusers }</td></c:if>
						<c:if test='${"monthconnectnetusers" eq type}'><td>${versionStat.monthconnectnetusers }</td></c:if>
						<c:if test='${"todayregisterusers" eq type}'><td>${versionStat.todayregisterusers }</td></c:if>
						<c:if test='${"totalregisterusers" eq type}'><td>${versionStat.totalregisterusers }</td></c:if>
						<c:if test='${"totalusers" eq type}'><td>${versionStat.totalusers }</td></c:if>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</div>
</body>
</html>