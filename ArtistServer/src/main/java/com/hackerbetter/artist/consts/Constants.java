package com.hackerbetter.artist.consts;

/**
 * 常量类
 * @author Administrator
 *
 */
public class Constants {
	
	//公共请求信息
	public final static String command = "command"; //请求命令
	public final static String imei = "imei"; //手机标识
	public final static String imsi = "imsi"; //手机标识
	public final static String softwareVersion = "softwareversion"; //版本号
	public final static String coopId = "coopid"; //渠道号
	public final static String machineId = "machineid"; //机型
	public final static String platform = "platform"; //平台(android|iPhone|塞班)
	public final static String isemulator =  "isemulator"; //是否是模拟器
	public final static String isCompress = "isCompress"; //是否压缩
	public final static String phoneSIM = "phoneSIM"; //SIM卡手机号
	public final static String mac = "mac"; //网卡地址
	
	public final static String loginType="loginType";//登陆类型（alipay,qq,sina,normal）
	

	//类型
	public final static String requestType = "requestType"; //请求类型
	public final static String type = "type"; //查询类型
	public final static String imageType = "imageType"; //图片类型
	public final static String item = "item"; //栏目id
	public final static String paintingId="paintingId";//作品id

	//用户信息
	public final static String username = "username"; //用户名
	public final static String password = "password"; //密码
	public final static String name = "name"; //真实姓名
	public final static String nickname = "nickname"; //昵称
	public final static String userName = "userName"; //用户姓名
	public final static String certId = "certid"; //身份证号
	public final static String mobileId = "mobileid"; //手机号码
	public final static String userNo = "userno"; //用户编号
	public final static String email = "email"; //邮箱
	
	//修改密码
	public final static String old_pass = "old_pass"; //原密码
	public final static String new_pass = "new_pass"; //新密码
	
	//登录
	public final static String isAutoLogin = "isAutoLogin"; //是否自动登录
	public final static String randomNumber = "randomNumber"; //自动登录随机数

	//查询
	public final static String date = "date"; //日期
	public final static String pageIndex = "pageindex"; //第几页
	public final static String maxResult = "maxresult"; //每页显示多少条
	public final static String startDate = "startdate"; //开始日期
	public final static String endDate = "enddate"; //结束日期
	public final static String startLine = "startLine"; //起始行
	public final static String stopLine = "stopLine"; //结束行
	public final static String sessionId = "sessionid"; //SessionId

	//用户中心
	public final static String bindPhoneNum = "bindPhoneNum"; //绑定的手机号
	public final static String securityCode = "securityCode"; //验证码
	public final static String content = "content"; //评论内容
    public final static String replyTo = "replyTo"; //回复的评论id


    //消息设置
	public final static String info = "info"; //信息
	public final static String keyStr = "keyStr"; //键


	//其他
	public final static String key = "<>hj12@#$$%^~~ff"; //加密串
	public final static String mobile_pattern = "(13[0-9]|15[0-9]|18[0-9]|147)\\d{8}"; //手机号码的正则
	
	//帮助中心
	public final static String code = "code"; //key值
}
