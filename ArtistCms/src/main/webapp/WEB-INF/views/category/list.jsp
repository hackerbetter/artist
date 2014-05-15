<%@ page import="com.artist.cms.util.Page" %>
<%@ page import="com.artist.cms.util.StringUtil" %>
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
		url : "<%=request.getContextPath()%>/category/remove",
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
				window.location.href="<%=request.getContextPath()%>/category/list";
			} else if (data.errorCode == "500") {
							Dialog.alert("服务器错误");
			} else {
							Dialog.alert("删除失败");
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
					<form action="<%=request.getContextPath()%>/category/query" method="post">
						<div style="float: left;">
							<table width="100%" cellspacing="2" cellpadding="2" border="0">
								<tr>
									<td align="right">描述:</td>
									<td>
                                        <input type="text" name="memo" class="inputText"/>
									</td>
									<td align="right" width="50px"><input type="submit" value="查询" class="inputButton">
									</td>
									<td align="right" width="50px"><input type="button" value="新增" class="inputButton" onclick="javascript:location.href='<%=request.getContextPath()%>/category/addUI'">
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
								<td width="10%" height="30" class="thOver"></td>
								<td width="20%" class="thOver"><strong>名称</strong></td>
								<td width="30%" class="thOver"><strong>描述</strong></td>
								<td width="20%" class="thOver"><strong>开关</strong></td>
								<td width="20%" class="thOver"><strong>操作</strong></td>
							</tr>
							<c:forEach items="${categorys}" var="category" varStatus="num">
								<tr class="DataAutoWidth">
									<td title="${num.count}">${num.count}</td>
									<td>
                                         ${category.name}
									</td>
                                    <td>
                                         ${category.memo}
                                    </td>
									<td align="center">
										<c:if test="${'0' eq category.state }">关</c:if>
										<c:if test="${'1' eq category.state }">开</c:if>
									</td>
									<td align="center">
										<a href="<%=request.getContextPath()%>/category/editUI?id=${category.id}">编辑</a>
										<a href="javascript:remove('${category.id}')">删除</a>
									</td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
				</td>
			</tr>
		</tbody>
	</table>
</body>
</html>