package com.artist.cms.util;

public enum enum_Position {

	POSITION_dingbuxuanchuantu("dingbuxuanchuantu", "首页顶部宣传图"), 
	POSITION_head("head", "首页轮播图"), 
	POSITION_shouyeguanggaowei("shouyeguanggaowei", "首页广告位"), 
	POSITION_zixunguanggaowei("zixunguanggaowei", "彩票咨询页广告位"), 
	POSITION_zixunyelunbo("zixunyelunbo", "彩票咨询页轮播");

	private String name;
	private String value;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	enum_Position(String value, String name) {
		this.name = name;
		this.value = value;
	}

	public static String getName(String value) {
		for (enum_Position values : enum_Position.values()) {
			if (values.value.equals(value)) {
				return values.name;
			}
		}
		return "";
	}

}
