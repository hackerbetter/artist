package com.hackerbetter.artist.servlet;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

import com.hackerbetter.artist.consts.Constants;
import com.hackerbetter.artist.controller.RequestDispatcher;
import com.hackerbetter.artist.protocol.ClientInfo;
import com.hackerbetter.artist.util.JsonPathFunctionHandler;
import com.hackerbetter.artist.util.Response;
import com.hackerbetter.artist.util.common.Tools;
import com.hackerbetter.artist.util.common.ToolsAesCrypt;
import com.hackerbetter.artist.util.parse.RequestParamUtil;

public class ArtistServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	private Logger logger = LoggerFactory.getLogger(ArtistServlet.class);
	
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		super.doGet(req, resp);
	}
	
	@Override
	protected void service(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		try {
			String requestId = UUID.randomUUID().toString().replaceAll("-","");
			MDC.put("requestId", requestId);
			String returnToClient = doProcess(request, response);
			if(null==returnToClient)
				return;
			// 返回
			responseClient(response, returnToClient);
		} catch (Exception e) {
			logger.error("ArtistServerServlet发生异常", e);
		}finally{
			MDC.remove("requestId");
		}
	}
	
	private String doProcess(HttpServletRequest request,
			HttpServletResponse response) throws IOException, Exception,
			UnsupportedEncodingException {
	    String parameterString = null;
	    List<FileItem> items = null;
	    //获取参数
	    if(ServletFileUpload.isMultipartContent(request)){
	         items = UploadFileRequestHandler.getFileItems(request);
	        parameterString = UploadFileRequestHandler.getParameterString(items);
	    }else {
	        parameterString = getParameterString(request);
	    }
		//解密
		String isEncrypt = request.getParameter("isEncrypt"); // 是否加密(0:不加密)
		String deCrypt = decrypt(isEncrypt, parameterString);
		//判断参数是否合法
		if (StringUtils.isBlank(deCrypt)||!StringUtils.startsWith(deCrypt.trim(), "{")
				||!StringUtils.endsWith(deCrypt.trim(), "}")) {
			responseClient(response, encrypt(isEncrypt, Response.paramError("")));
			return null;
		}
		//是否压缩
		String isCompress = "";
		//处理多个请求
		StringBuilder builder = new StringBuilder();
		String[] commandStrings = StringUtils.splitByWholeSeparator(deCrypt, "}|");
		List<String> apiResponses = new ArrayList<String>();
		for (int i = 0; i < commandStrings.length; i++) {
			//数据里的最后一个元素不需要加"}"
			String command =  (i==commandStrings.length-1) ? commandStrings[i] : (commandStrings[i]+"}");
			if(notFirstApiRequest(i)){
			    command = JsonPathFunctionHandler.handle(command,apiResponses);
			}
			//是否压缩
			if (i==0) {
				isCompress = getIsCompress(command);
			}
			String ip= request.getHeader("X-Forwarded-For"); 
			logger.info("request json:{},request ip:{}",command,ip);
            String apiResult = processRequest(request, command, items);
			apiResponses.add(apiResult);
			builder.append(apiResult).append("|");
		}
		String returnToClient = StringUtils.stripEnd(builder.toString(), "|");
		// 压缩
		returnToClient = compress(isCompress, returnToClient);
		// 加密
		returnToClient = encrypt(isEncrypt, returnToClient);
		return returnToClient;
	}
	
    private boolean notFirstApiRequest(int i){
	    return i!=0;
	}
	
	/**
	 * 处理请求
	 * @param request
	 * @param deCrypt
	 * @return
	 */
	private String processRequest(HttpServletRequest request, String deCrypt, List<FileItem> items) {
		ClientInfo clientInfo=new ClientInfo();
		JSONObject requestObject = JSONObject.fromObject(deCrypt);
		//验证是否存在command
		if (requestObject==null||!requestObject.has(Constants.command)) {
			return Response.paramError("");
		}
		//验证command是否传了多个
		String command = requestObject.getString(Constants.command);
		if (StringUtils.isBlank(command)||StringUtils.startsWith(command.trim(), "[")
				||StringUtils.endsWith(command.trim(), "]")) {
			return Response.paramError("");
		}
		//解析参数
		RequestParamUtil.parseRequestParam(request, requestObject, clientInfo, items);
		String result=RequestDispatcher.dispatch(clientInfo);
		return result;
	}
	
	/**
	 * 获取参数
	 * @param request
	 * @return
	 * @throws Exception 
	 */
	private String getParameterString(HttpServletRequest request) throws Exception {
		StringBuilder builder = new StringBuilder();
		InputStream inStream = request.getInputStream();
		byte[] buffer = new byte[1024];
		int len = 0;
		while ((len=inStream.read(buffer)) != -1) {
			builder.append(new String(buffer, 0, len));
		}
		inStream.close();
		return builder.toString();
	}
	
	/**
	 * 获取是否压缩
	 * @param string
	 * @return
	 */
	private String getIsCompress(String string) {
		JSONObject requestObject = JSONObject.fromObject(string);
		if(requestObject.has(Constants.isCompress)){
		    return requestObject.getString(Constants.isCompress);
		}
		return "0";
	}
	
	/**
	 * 解密
	 * @param isEncrypt
	 * @param string
	 * @return
	 * @throws Exception
	 */
	private String decrypt(String isEncrypt, String string) throws Exception {
		if (StringUtils.equals(isEncrypt, "0")) { // 不加密
			return string;
		}
		return ToolsAesCrypt.Decrypt(string, Constants.key);
	}
	
	/**
	 * 压缩
	 * @param isCompress
	 * @param string
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	private String compress(String isCompress, String string) throws UnsupportedEncodingException {
		if (StringUtils.equals(isCompress, "1")) { // 压缩
			byte[] compressBytes = Tools.compress(string.getBytes("UTF-8"));
			return Tools.base64Encode(compressBytes, "UTF-8");
		}
		return string;
	}
	
	/**
	 * 加密
	 * @param isEncrypt
	 * @param string
	 * @return
	 * @throws Exception
	 */
	private String encrypt(String isEncrypt, String string) throws Exception {
		if (StringUtils.equals(isEncrypt, "0")) { // 不加密
			return string;
		}
		return ToolsAesCrypt.Encrypt(string, Constants.key);
	}
	
	/**
	 * 响应客户端
	 * @param response
	 * @param returnString
	 * @throws IOException
	 */
	private void responseClient(HttpServletResponse response, String returnString) throws IOException {
		response.setHeader("Pragma", "no-cache");
		response.setContentType("text/plain;charset=UTF-8");
		PrintWriter out = response.getWriter();
		out.print(returnString);
	}

}
