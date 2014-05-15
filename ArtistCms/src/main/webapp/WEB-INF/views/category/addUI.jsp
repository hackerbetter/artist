<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link href="<%=request.getContextPath()%>/styles/default.css" rel="stylesheet" type="text/css">
<link href="<%=request.getContextPath()%>/styles/style.css" rel="stylesheet" type="text/css">
<script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/Dialog.js"></script>	
</head>
<body>
	<div style="margin-top: 10px;" align="center"><h3>添加栏目</h3></div>
	<form action="<%=request.getContextPath()%>/category/add" method="post">
		<table width="100%" cellspacing="0" cellpadding="0" border="0" style="table-layout: fixed;" >
			<tbody>
				<tr>
					<td style="padding-top: 2px; padding-left: 6px; padding-right: 6px; padding-bottom: 2px;">
						<table width="100%" cellspacing="0" cellpadding="2" class="dataTable">
							<tbody>
                                <tr class="thOver">
                                    <td width="6%" class="thOver"><strong>栏目名称:</strong></td>
                                    <td width="94%">
                                         <input type="text" name="name" class="inputText" />
                                         <input type="hidden" name="state" value="1"/>
                                    </td>
                                </tr>
                                <tr class="thOver">
                                    <td width="6%" class="thOver"><strong>栏目描述:</strong></td>
                                    <td width="94%">
                                         <textarea name="memo" rows="5" cols="100"></textarea>
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