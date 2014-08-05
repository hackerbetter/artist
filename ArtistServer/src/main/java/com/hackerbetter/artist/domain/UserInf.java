package com.hackerbetter.artist.domain;

import org.springframework.roo.addon.entity.RooEntity;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.json.RooJson;
import org.springframework.roo.addon.tostring.RooToString;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@RooJavaBean
@RooToString
@RooJson
@RooEntity(versionField="", table="tbl_userinf", persistenceUnit="persistenceUnit", transactionManager="transactionManager")
public class UserInf {

	@Column(name = "imei")
	private String imei;
	
	@Column(name = "imsi")
	private String imsi;
	
	@Column(name = "mobilenum")
	private String mobilenum;
	
	@Column(name = "createtime")
	@Temporal(TemporalType.TIMESTAMP)
	private Date createtime;
	
	@Column(name = "lastnetconnecttime")
	@Temporal(TemporalType.TIMESTAMP)
	private Date lastnetconnecttime;
	
	@Column(name = "softwareversion")
	private String softwareversion;
	
	@Column(name = "platfrom")
	private String platfrom;
	
	@Column(name = "channel")
	private String channel;
	
	@Column(name = "lastchannel")
	private String lastchannel;
	
	@Column(name = "machine")
	private String machine;
	
	@Column(name = "isemular")
	private String isemular;
	
	@Column(name = "regtime")
	@Temporal(TemporalType.TIMESTAMP)
	private Date regtime;
	
	@Column(name = "userno")
	private String userno;
	
	@Column(name = "phoneSIM")
	private String phoneSIM;
	
	@Column(name = "mac")
	private String mac;
	
	public static List<UserInf> getList(String where, String orderby, List<Object> params) {
		TypedQuery<UserInf> q = entityManager().createQuery(
				"SELECT o FROM UserInf o " + where + orderby, UserInf.class);
		if (null != params && !params.isEmpty()) {
			int index = 1;
			for (Object param : params) {
				q.setParameter(index, param);
				index = index + 1;
			}
		}
		return q.getResultList();
	}
	
}
