package com.hackerbetter.artist.protocol;

import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.json.RooJson;

@RooJson
@RooJavaBean
public class ClientInfo {

	// 基本字段
	private String command = ""; // 请求命令
	private String imei = ""; // 手机标识
	private String imsi = ""; // 手机标识
	private String softwareVersion = ""; // 版本号
	private String coopId = ""; // 渠道号
	private String machineId = ""; // 机型
	private String username = ""; // 用户名
	private String platform = ""; // 平台(android|iPhone|塞班)
	private String isCompress = ""; // 是否压缩,1为压缩
	private String isemulator = ""; // 是否是模拟器
	private String phoneSIM = ""; // SIM卡手机号
	private String mac = ""; // 网卡地址
	private String loginType="";//登陆类型（alipay,qq,sina,normal）
	private String mobileId = ""; //手机号码

	//类型
	private String requestType = ""; // 请求类型
	private String type = "";// 查询类型
	private String imageType = "";// 图片类型 0 首页图片 1 轮播图片
	private String item = "";//栏目
    private String paintingId="";//作品id

	// 查询
	private String startDate = ""; // 账户信息查询开始时间
	private String endDate = ""; // 账户信息查询结束时间
	private String startLine = "";// 开始条数
	private String stopLine = "";// 结束条数
	private String maxresult = "";// 每页显示条数
	private String pageindex = "";// 页数，第几页
	private String sessionid = ""; // sessionid
	private String sysSessionid = ""; // sysSessionid


	//用户信息
	private String userno = ""; // 用户编号
	private String password = ""; // 密码
	private String certid = "";// 身份证号
	private String name = "";// 真实姓名
	private String nickname = ""; // 昵称
	private String email = ""; // 邮箱
	
	// 用户中心
	private String bindPhoneNum = ""; // 绑定的手机号码
	private String securityCode = ""; // 验证码

	//修改密码
	private String oldPass = ""; // 旧密码
	private String newPass = ""; // 新密码
	
	//登录
	private String isAutoLogin = ""; // 是否自动登录
	private String randomNumber = ""; // 自动登录随机数

	// 新闻资讯
	private String newsType = ""; // 资讯查询的类型
	private String id = ""; //编号
	private String keyStr = ""; //关键字
	
	//消息设置
	public String info = ""; //信息
	public String state = ""; //状态
    public String content="";//评论内容
    public String replyTo="";//要回复的评论id

	//短信通知
	private String needToSend="" ; //开关状态，0:关闭发送,1:打开发送
	
	//帮助中心
	private String code = ""; //key值

}
