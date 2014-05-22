<%@ page import="com.artist.cms.consts.Item" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link href="<%=request.getContextPath()%>/styles/default.css" rel="stylesheet" type="text/css">
<link type="text/css" href="<%=request.getContextPath()%>/ueditor/themes/default/css/ueditor.css"/>
<!-- 配置文件 -->
<script type="text/javascript" src="<%=request.getContextPath()%>/ueditor/ueditor.config.js"></script>
<!-- 编辑器源码文件 -->
<script type="text/javascript" src="<%=request.getContextPath()%>/ueditor/ueditor.all.min.js"></script>
<!-- 语言包文件(建议手动加载语言包，避免在ie下，因为加载语言失败导致编辑器加载失败) -->
<script type="text/javascript" src="<%=request.getContextPath()%>/ueditor/lang/zh-cn/zh-cn.js"></script>
</head>
<body>
	<div style="margin-top: 10px;" align="center"><h3>添加作品</h3></div>
	<form action="<%=request.getContextPath()%>/painting/add" method="post">
		<table width="100%" cellspacing="0" cellpadding="0" border="0" style="table-layout: fixed;" >
			<tbody>
				<tr>
					<td style="padding-top: 2px; padding-left: 6px; padding-right: 6px; padding-bottom: 2px;">
						<table width="100%" cellspacing="0" cellpadding="2" class="dataTable">
							<tbody>
                                <tr class="thOver">
                                    <td width="6%" class="thOver"><strong>标题:</strong></td>
                                    <td width="94%">
                                         <input type="text" name="title" class="inputText" />
                                         <input type="hidden" name="state" value="1"/>
                                    </td>
                                </tr>
                                <tr class="thOver">
                                    <td width="6%" class="thOver"><strong>作者:</strong></td>
                                    <td width="94%">
                                        <input type="text" name="author" class="inputText" />
                                    </td>
                                </tr>
                                <tr class="thOver">
                                    <td width="6%" class="thOver"><strong>国家:</strong></td>
                                    <td width="94%">
                                        <input type="text" name="countries" class="inputText" />
                                    </td>
                                </tr>
                                <tr class="thOver">
                                    <td width="6%" class="thOver"><strong>类型:</strong></td>
                                    <td width="94%">
                                        <select id="categoryId" name="categoryId" class="zSelect">
                                            <option value="">-选择类型-</option>
                                            <c:forEach items="${tcategories }" var="tcategory">
                                                <option value="${tcategory.id }" >${tcategory.name }</option>
                                            </c:forEach>
                                        </select>
                                    </td>
                                </tr>
                                <tr class="thOver">
                                    <td width="6%" class="thOver"><strong>栏目:</strong></td>
                                    <td width="94%">
                                        <select id="item" name="item" class="zSelect">
                                            <option value="">-选择栏目-</option>
                                            <c:forEach items="<%=Item.values()%>" var="item">
                                                <option value="${item.memo }" >${item.memo }</option>
                                            </c:forEach>
                                        </select>
                                    </td>
                                </tr>
                                <tr>
                                    <td width="6%" class="thOver"><strong>简图:</strong></td>
                                    <td width="94%">
                                        <script id="shortImage" name="shortImage" type="text/plain" style="width:99%;height:100px;white-space:normal">
                                        </script>
                                        <script type="text/javascript">
                                            var editor = new baidu.editor.ui.Editor({
                                                toolbars:[[ 'source', 'undo', 'redo','|','insertimage']]
                                            });
                                            editor.render("shortImage");
                                            editor.setDisabled();
                                        </script>
                                    </td>
                                </tr>
                                <tr>
                                    <td width="6%" class="thOver"><strong>内容:</strong></td>
                                    <td width="94%">
                                            <script id="container" name="content" type="text/plain" style="width:99%;height:300px;white-space:normal">
                                            </script>
                                            <script type="text/javascript">
                                                var editor = UE.getEditor('container');
                                            </script>
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