<%@page import="java.util.Map"%>
<%@page import="java.math.BigDecimal"%>
<%@ page import="com.artist.cms.util.StringUtil" %>
<%@ page import="com.artist.cms.util.Page" %>
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
    if(!StringUtil.isEmpty(errormsg)) {
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

function remove(id) {
	$.ajax({
		url : "<%=request.getContextPath()%>/imageconfig/remove",
		type:"POST",
		async:false,
		data:"id=" + id,
		success:function(data){
			if(data.errorCode == "1"||data.errorCode == "0") {
				Dialog.alert(data.value);
				 var now = new Date();
				 var exitTime = now.getTime() + 1000;
				  while (true) {
				        now = new Date();
				        if (now.getTime() > exitTime)
				           break;
				 }
				window.location.href="<%=request.getContextPath()%>/imageconfig/list";
			} else if (data.errorCode == "500") {
                Dialog.alert("服务器错误");
			}
		}
	});
}

function doTop(id) {
    $.ajax({
        url : "<%=request.getContextPath()%>/imageconfig/top",
        type:"POST",
        async:false,
        data:"id=" + id,
        success:function(data){
            if(data.errorCode == "1"||data.errorCode == "0") {
                Dialog.alert(data.value);
                var now = new Date();
                var exitTime = now.getTime() + 1000;
                while (true) {
                    now = new Date();
                    if (now.getTime() > exitTime)
                        break;
                }
                window.location.href="<%=request.getContextPath()%>/imageconfig/list";
            } else if (data.errorCode == "500") {
                Dialog.alert("服务器错误");
            } else {
                Dialog.alert("置顶失败");
            }
        }
    });
}
</script>
<body>
	<div style="margin-top: 10px;"></div>
	<table width="100%" cellspacing="0" cellpadding="0" border="0">
		<tbody>
			<tr>
				<td style="padding: 2px 10px;">
					<form action="<%=request.getContextPath()%>/imageconfig/list" method="post">
						<div style="float: left;">
							<table width="100%" cellspacing="2" cellpadding="2" border="0">
								<tr>
									<td align="right">显示行数:</td>
									<td><select id="maxResult" name="maxResult" style="width:60px">
											<option value="15" <c:if test='${"15" eq page.maxResult}'>selected</c:if>>15</option>
											<option value="30" <c:if test='${"30" eq page.maxResult}'>selected</c:if>>30</option>
											<option value="50" <c:if test='${"50" eq page.maxResult}'>selected</c:if>>50</option>
											<option value="100" <c:if test='${"100" eq page.maxResult}'>selected</c:if>>100</option>
									</select></td>
									<td align="right" width="50px"><input type="submit" value="查询" class="inputButton">
									</td>
									<td align="right" width="50px"><input type="button" value="新增" onclick="javascript:location.href='<%=request.getContextPath()%>/imageconfig/addUI'" class="inputButton">
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
							<tr class="dataTableHead" align="center">
								<td width="4%" height="30" class="thOver"></td>
								<td width="10%" class="thOver"><strong>类型</strong></td>
								<td width="20%" class="thOver"><strong>作品</strong></td>
								<td width="30%" class="thOver"><strong>地址</strong></td>
								<td width="8%" class="thOver"><strong>开关</strong></td>
								<td width="28%" class="thOver"><strong>操作</strong></td>
							</tr>
							<% 
								Page page2 = (Page)request.getAttribute("page");
								if(null != page2 && null != page2.getList()) {
							%>
							<c:forEach items="${page.list}" var="imageconfig" varStatus="num">
								<tr class="DataAutoWidth">
									<td title="${num.count}">${num.count}</td>
									<td>
                                        <c:if test="${imageconfig.type eq '0'}">首页图片</c:if>
                                        <c:if test="${imageconfig.type eq '1'}">轮播图片</c:if>
									</td>
									<td align="center">
										<c:forEach items="${tpaintings }" var="tpainting">
											<c:if test="${tpainting.id eq imageconfig.tpaintingId}">${tpainting.title }</c:if>
										</c:forEach>
									</td>
									<td>
										${imageconfig.url }
									</td>
									<td align="center">
										<c:if test="${'0' eq imageconfig.state }">关</c:if>
										<c:if test="${'1' eq imageconfig.state }">开</c:if>
									</td>
									<td align="center">
										<a href="<%=request.getContextPath()%>/imageconfig/editUI?id=${imageconfig.id}">编辑</a>
										<a href="javascript:remove('${imageconfig.id}')">删除</a>
										<a href="javascript:doTop('${imageconfig.id}')">置顶</a>
									</td>
								</tr>
							</c:forEach>
							<tr>
								<td align="left" id="dg1_PageBar" colspan="9"><div
										style="float: right; font-family: Tahoma">
										<c:choose>
											<c:when test="${page.pageIndex != 0}">
												<a
													href="<%=request.getContextPath()%>/imageconfig/list?maxResult=${page.maxResult}&pageIndex=0">第一页</a>
											</c:when>
											<c:otherwise>
										第一页
									</c:otherwise>
										</c:choose>
										|&nbsp;
										<c:choose>
											<c:when test="${page.pageIndex > 0}">
												<a
													href="<%=request.getContextPath()%>/imageconfig/list?maxResult=${page.maxResult}&pageIndex=${page.pageIndex - 1}">上一页</a>
											</c:when>
											<c:otherwise>
										上一页
									</c:otherwise>
										</c:choose>
										&nbsp;|&nbsp;
										<c:choose>
											<c:when test="${page.pageIndex + 1 < page.totalPage}">
												<a
													href="<%=request.getContextPath()%>/imageconfig/list?maxResult=${page.maxResult}&pageIndex=${page.pageIndex + 1}">下一页</a>
											</c:when>
											<c:otherwise>
										下一页
									</c:otherwise>
										</c:choose>
										&nbsp;|&nbsp;
										<c:choose>
											<c:when test="${page.pageIndex+1 != page.totalPage && page.totalPage != 0}">
												<a
													href="<%=request.getContextPath()%>/imageconfig/list?maxResult=${page.maxResult}&pageIndex=${page.totalPage - 1}">最末页</a>
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
												window.location.href="<%=request.getContextPath()%>/imageconfig/list?maxResult=${page.maxResult}&pageIndex=" + pageindex;
											}
										</script>
									</div>
									<div style="float: left; font-family: Tahoma">共
										${page.totalResult} 条记录，每页 ${page.maxResult} 条，当前第
										${page.pageIndex + 1} / ${page.totalPage} 页</div></td>
							</tr>
							<%} %>
						</tbody>
					</table>
				</td>
			</tr>
		</tbody>
	</table>
</body>
</html>