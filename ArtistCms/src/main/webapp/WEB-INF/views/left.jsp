<%@ page import="com.artist.cms.consts.PropertiesConstant" %>
<%-- <%@page import="com.ruyicai.cmgr.domain.Tpermission"%>--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link href="<%=request.getContextPath() %>/styles/default.css" rel="stylesheet" type="text/css">
<title>Insert title here</title>
<script type="text/javascript">
var Tab = {};

Tab.onTabMouseOver = function(ele){
	if(ele.className=="divtab"){
		ele.className="divtabHover";
	}
}

Tab.onTabMouseOut = function(ele){
	if(ele.className=="divtabHover"){
		ele.className="divtab";
	}
}
function $(ele) {
  if (typeof(ele) == 'string'){
    ele = document.getElementById(ele)
    if(!ele){
  		return null;
    }
  }
  return ele;
}
function $T(tagName,ele){
	ele = $(ele);
	ele = ele || document;
	var ts = ele.getElementsByTagName(tagName);//此处返回的不是数组
	var arr = [];
	var len = ts.length;
	for(var i=0;i<len;i++){
		arr.push($(ts[i]));
	}
	return arr;
}
Tab.onTabClick = function(ele){
	var arr = $T("div",ele.parentElement);
	var len = arr.length;
	for(var i=0;i<len;i++){
		var c = arr[i];
		var cn = c.className;
		if(cn=="divtabCurrent"){
			c.className = "divtab";
			c.style.zIndex=""+(len-i)+"";
		}
	}
	ele.className="divtabCurrent";
	ele.style.zIndex="33";
}
var Application = {};
Application.onChildMenuClick = function(ele,flag){//flag仅在回退/前进时置为true
	if(!flag){
		var url = ele.getAttribute("url");
		window.parent.content.location = url;
	}
	Tab.onTabClick(ele);
}

Application.onChildMenuMouseOver = function(ele){
	Tab.onTabMouseOver(ele);
}

Application.onChildMenuMouseOut = function(ele){
	Tab.onTabMouseOut(ele);
}
</script>
</head>
<%-- <%! 
	public boolean ispermission(String inid, HttpServletRequest request) {
		List<Tpermission> tpermissions = (List<Tpermission>)request.getSession().getAttribute("permission");
		if(null != tpermissions && !tpermissions.isEmpty()) {
			for(Tpermission tpermission : tpermissions) {
				if(tpermission.getMenuinid().equals(new BigDecimal(inid))) {
					return true;
				}
			}
		}
		return false;
	}
%> --%>
<body style="border-right: 1px solid #B1B1AD">
	<table width="100%" border="0" cellpadding="0" cellspacing="0">
		<tr>
			<td style="padding:0px 0px 0px 0px;">
			<table width="100%" border="0" cellspacing="0" cellpadding="0">
				<tr valign="top">
					<td align="left" id="_VMenutable" class="verticalTabpageBar">
					<table width="100%" border="0" cellspacing="0" cellpadding="0">
						<tr>
							<td align="right" valign="bottom">
                                <div id="_ChildMenu">
                                    <div url="<%=request.getContextPath()%>/imageconfig/list" onmouseout="Application.onChildMenuMouseOut(this)" onmouseover="Application.onChildMenuMouseOver(this)" onclick="Application.onChildMenuClick(this)" class="divtab"><b><span style="margin-left: 10px">图片管理</span></b></div>
                                    <div url="<%=request.getContextPath()%>/painting/list" onmouseout="Application.onChildMenuMouseOut(this)" onmouseover="Application.onChildMenuMouseOver(this)" onclick="Application.onChildMenuClick(this)" class="divtab"><b><span style="margin-left: 10px">作品管理</span></b></div>
                                    <div url="<%=request.getContextPath()%>/category/list" onmouseout="Application.onChildMenuMouseOut(this)" onmouseover="Application.onChildMenuMouseOver(this)" onclick="Application.onChildMenuClick(this)" class="divtab"><b><span style="margin-left: 10px">栏目管理</span></b></div>
                                </div>
							</td>
						</tr>
					</table>
					</td>
				</tr>
			</table>
			</td>
		</tr>
	</table>
</body>
</html>