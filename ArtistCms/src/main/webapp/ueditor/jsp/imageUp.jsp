    <%@ page language="java" contentType="text/html; charset=utf-8"
             pageEncoding="utf-8"%>
    <%@ page import="com.artist.cms.util.Uploader" %>
    <%@ page import="com.artist.cms.consts.PropertiesConstant" %>
<%
request.setCharacterEncoding("utf-8");
response.setCharacterEncoding("utf-8");
String dir= PropertiesConstant.uploadPath;
String baseUrl=PropertiesConstant.imageUrl;
Uploader up = new Uploader(request);
up.setSavePath( dir );
String[] fileType = {".gif" , ".png" , ".jpg" , ".jpeg" , ".bmp"};
up.setAllowFiles(fileType);
up.setMaxSize(10000); //单位KB
up.upload();
response.getWriter().print("{'original':'"+up.getOriginalName()+"','url':'"+up.getUrl().replace(dir,baseUrl)+"','title':'"+up.getTitle()+"','state':'"+up.getState()+"'}");
%>
