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
	
	//常量
	public final static String accessType = "C"; //接入方式
	public final static String subChannel = "00092493"; //用户系统
	
	//类型
	public final static String requestType = "requestType"; //请求类型
	public final static String type = "type"; //查询类型
	public final static String imageType = "imageType"; //图片类型
	public final static String item = "item"; //栏目id
	public final static String paintingId="paintingId";//作品id

	//自动跟单
	public final static String starterUserNo = "starterUserNo"; //发起人用户编号
	public final static String times = "times"; //跟单次数
	public final static String joinAmt = "joinAmt"; //跟单金额
	public final static String percent = "percent"; //跟单百分比
	public final static String maxAmt = "maxAmt"; //百分比跟单最大金额
	public final static String joinType = "joinType"; //跟单类型(0:金额跟单,1:百分比跟单)
	public final static String forceJoin = "forceJoin"; //是否强制跟单(1:强制跟单,0:不强制跟单)
	
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
	public final static String source = "source"; //来源(联合登录)
	public final static String openId = "openId"; //第三方用户标识(联合登录)
	public final static String alias = "alias"; //客户端别名(用于安卓推送)

	//查询
	public final static String date = "date"; //日期
	public final static String pageIndex = "pageindex"; //第几页
	public final static String maxResult = "maxresult"; //每页显示多少条
	public final static String startTime = "starttime"; //开始时间
	public final static String endTime = "endtime"; //结束时间
	public final static String sysCurrentTime = "syscurrenttime"; //系统当前时间
	public final static String timeRemaining = "time_remaining"; //当前期剩余时间
	public final static String startDate = "startdate"; //开始日期
	public final static String endDate = "enddate"; //结束日期
	public final static String startLine = "startLine"; //起始行
	public final static String stopLine = "stopLine"; //结束行
	public final static String sessionId = "sessionid"; //SessionId
	public final static String transation_id = "transation_id"; //交易ID
	public final static String stateMemo = "stateMemo"; //状态描述

	//注册
	public final static String recommender = "recommender"; //推荐人的用户名
	public final static String agencyNo = "agencyNo"; //默认的代理编号
	
	//用户中心
	public final static String score = "score"; //积分
	public final static String scoreType = "scoreType"; //积分类型
	public final static String bindPhoneNum = "bindPhoneNum"; //绑定的手机号
	public final static String securityCode = "securityCode"; //验证码
	public final static String content = "content"; //反馈内容
	public final static String contactWay = "contactWay"; //联系方式
	

	//消息设置
	public final static String token = "token"; //iPhone手机标识
	public final static String info = "info"; //信息
	public final static String keyStr = "keyStr"; //键

	//短信通知
	public final static String smstype="smstype"; //短信类型， 1为开奖 2为中奖
	public final static String needToSend="needToSend" ; //开关状态，0:关闭发送,1:打开发送

	//返回
	public final static String message = "message"; //错误信息
	
	//其他
	public final static String key = "<>hj12@#$$%^~~ff"; //加密串
	public final static String mobile_pattern = "(13[0-9]|15[0-9]|18[0-9]|147)\\d{8}"; //手机号码的正则
	
	//帮助中心
	public final static String code = "code"; //key值
}
