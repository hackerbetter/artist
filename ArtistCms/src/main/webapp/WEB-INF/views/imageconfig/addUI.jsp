<%@page import="java.math.BigDecimal, java.util.Date"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link href="<%=request.getContextPath()%>/styles/default.css" rel="stylesheet" type="text/css">
<script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery.js"></script>
<link type="text/css" href="<%=request.getContextPath()%>/ueditor/themes/default/css/ueditor.css"/>
<!-- 配置文件 -->
<script type="text/javascript" src="<%=request.getContextPath()%>/ueditor/ueditor.config.js"></script>
<!-- 编辑器源码文件 -->
<script type="text/javascript" src="<%=request.getContextPath()%>/ueditor/ueditor.all.min.js"></script>
<!-- 语言包文件(建议手动加载语言包，避免在ie下，因为加载语言失败导致编辑器加载失败) -->
<script type="text/javascript" src="<%=request.getContextPath()%>/ueditor/lang/zh-cn/zh-cn.js"></script>
</head>
<script language="JavaScript">
    $(function(){

        $("input[name='type']").bind("click",function(){
            var item = $(":radio:checked");
            if(item.val()=="0"){
                $("#info").show();
            }else{
                $("#info").hide();
            }
        })

    })
function selectAct(){
	var productno=document.getElementById("productno").value;
	var URL="<%=request.getContextPath()%>/imageconfig/addUI?productno="+productno;
    location.href=URL;
}
</script>
<body>
	<div style="margin-top: 10px;" align="center"><h3>添加图片配置</h3></div>
	<form action="<%=request.getContextPath()%>/imageconfig/add" method="post" enctype="multipart/form-data">
		<table width="100%" cellspacing="0" cellpadding="0" border="0" style="table-layout: fixed;" >
			<tbody>
				<tr>
					<td style="padding-top: 2px; padding-left: 6px; padding-right: 6px; padding-bottom: 2px;">
						<table width="100%" cellspacing="0" cellpadding="2" class="dataTable">
							<tbody>
                                <tr class="thOver">
                                    <td width="6%" class="thOver"><strong>类型:</strong></td>
                                    <td width="94%">
                                         <input type="radio" value="0" name ="type" checked="true">首页图片
                                         <input type="radio" value="1" name ="type" >轮播图片
                                    </td>
                                </tr>
                                <tr class="thOver">
                                    <td width="6%" class="thOver"><strong>图片:</strong></td>
                                    <td width="94%">
                                        <script id="url" name="url" type="text/plain" style="width:99%;height:100px;white-space:normal">
                                            ${imageconfig.url }
                                        </script>
                                        <script type="text/javascript">
                                            var editor = new baidu.editor.ui.Editor({
                                                toolbars:[[ 'source', 'undo', 'redo','|','insertimage']]
                                            });
                                            editor.render("url");
                                            editor.setDisabled();
                                        </script>
                                    </td>
                                </tr>
								<tr class="thOver">
									<td width="6%" class="thOver"><strong>作品:</strong></td>
									<td width="93%">
										<select id="tpaintingId" name="tpaintingId">
											<option value="">-关联作品-</option>
											<c:forEach items="${tpaintings }" var="tpainting">
												<option value="${tpainting.id }" >${tpainting.title }</option>
											</c:forEach>
										</select>
									</td>
								</tr>
                                <tr class="thOver" id="info">
                                    <td width="6%" class="thOver"><strong>介绍:</strong></td>
                                    <td width="94%">
                                        <textarea  name="info" style="width: 400px;height: 150px"></textarea>
                                    </td>
                                </tr>
								<tr>
									<td colspan="2" width="100%">
										<input name="submit" type="submit" style="width: 40" class="button" value="提交" />
									</td>
								</tr>
							</tbody>
						</table>
					</td>
				</tr>
			</tbody>
		</table>
	</form>
</body>
</html>