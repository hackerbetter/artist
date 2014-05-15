package com.hackerbetter.artistcore.constants;

public enum ErrorCode {
    OK("0000", "成功"),
	ERROR("9999", "失败"),
    UserReg_UsernameError("0001","用户名不合法"),
    UserReg_MobileIdError("0002","手机号不合法"),
    UserReg_EmailError("0003","邮箱不合法"),
    UserReg_PassMD5Error("0004","密码加密出错"),
    UserReg_UserExists("0005","用户已存在"),
    UserReg_UserExit("0006", "用户已关闭"),
    UserReg_UserPause("0007", "用户已暂停"),
    UserReg_NicknameExists("0008","昵称已存在"),
    UserMod_UserNotExists("0009","用户不存在"),
    UserMod_UsernameNotallowMod("0010","用户名不允许修改"),
    UserMod_UserNoEmpty("0011","用户编号为空"),
    ParametersIsNull("0012","参数错误" );
	public String value;
	
	public String memo;
	
	private ErrorCode(String value, String memo) {
		this.value = value;
		this.memo = memo;
	}
	
}
