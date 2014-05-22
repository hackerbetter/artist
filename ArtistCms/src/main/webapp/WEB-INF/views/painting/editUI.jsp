<%@ page import="com.artist.cms.consts.Item" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link href="<%=request.getContextPath()%>/styles/default.css" rel="stylesheet" type="text/css">
    <!-- 配置文件 -->
    <script type="text/javascript" src="<%=request.getContextPath()%>/ueditor/ueditor.config.js"></script>
    <!-- 编辑器源码文件 -->
    <script type="text/javascript" src="<%=request.getContextPath()%>/ueditor/ueditor.all.js"></script>
    <!-- 语言包文件(建议手动加载语言包，避免在ie下，因为加载语言失败导致编辑器加载失败) -->
    <script type="text/javascript" src="<%=request.getContextPath()%>/ueditor/lang/zh-cn/zh-cn.js"></script>
</head>
<body>
<div style="margin-top: 10px;" align="center"><h3>作品修改</h3></div>
<form action="<%=request.getContextPath()%>/painting/edit" method="post">
    <input type="hidden" name="id" value="${tpainting.id }"/>
    <table width="100%" cellspacing="0" cellpadding="0" border="0" style="table-layout: fixed;" >
        <tbody>
        <tr>
            <td style="padding-top: 2px; padding-left: 6px; padding-right: 6px; padding-bottom: 2px;">
                <table width="100%" cellspacing="0" cellpadding="2" class="dataTable">
                    <tbody>
                    <tr class="thOver">
                        <td width="6%" class="thOver"><strong>标题:</strong></td>
                        <td width="94%">
                            <input type="text" name="title" class="inputText" value="${tpainting.title }"/>
                        </td>
                    </tr>
                    <tr class="thOver">
                        <td width="6%" class="thOver"><strong>作者:</strong></td>
                        <td width="94%">
                            <input type="text" name="author" class="inputText" value="${tpainting.author }"/>
                        </td>
                    </tr>
                    <tr class="thOver">
                        <td width="6%" class="thOver"><strong>国家:</strong></td>
                        <td width="94%">
                            <input type="text" name="countries" class="inputText" value="${tpainting.countries }"/>
                        </td>
                    </tr>
                    <tr class="thOver">
                        <td width="6%" class="thOver"><strong>类型:</strong></td>
                        <td width="94%">
                            <select id="categoryId" name="categoryId" class="zSelect">
                                <option value="">-选择类型-</option>
                                <c:forEach items="${tcategories }" var="tcategory">
                                    <c:choose>
                                        <c:when test="${tcategory.id==tpainting.categoryId }">
                                            <option value="${tcategory.id }" selected="selected">${tcategory.name }</option>
                                        </c:when>
                                        <c:otherwise>
                                            <option value="${tcategory.id }" >${tcategory.name }</option>
                                        </c:otherwise>
                                    </c:choose>
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
                                    <c:choose>
                                        <c:when test="${item.memo==tpainting.item }">
                                            <option value="${item.memo }" selected="selected">${item.memo }</option>
                                        </c:when>
                                        <c:otherwise>
                                            <option value="${item.memo }" >${item.memo }</option>
                                        </c:otherwise>
                                    </c:choose>
                                </c:forEach>
                            </select>
                        </td>
                    </tr>
                    <tr class="thOver">
                        <td width="6%" class="thOver"><strong>开关:</strong></td>
                        <td width="94%">
                            <input type="radio" value="0" name ="state" <c:if test='${"0" eq tpainting.state}'>checked</c:if>>关
                            <input type="radio" value="1" name ="state" <c:if test='${"1" eq tpainting.state}'>checked</c:if>>开
                        </td>
                    </tr>
                    <tr>
                        <td width="6%" class="thOver"><strong>简图:</strong></td>
                        <td width="94%">
                            <script id="shortImage" name="shortImage" type="text/plain" style="width:99%;height:100px;white-space:normal">
                                ${tpainting.shortImage }
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
                                ${tpainting.content }
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