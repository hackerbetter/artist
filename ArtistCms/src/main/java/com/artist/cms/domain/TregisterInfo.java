package com.artist.cms.domain;

import org.springframework.roo.addon.entity.RooEntity;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.json.RooJson;
import org.springframework.roo.addon.tostring.RooToString;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@RooJavaBean
@RooToString
@RooJson
@RooEntity(versionField="", table="tregisterinfo", persistenceUnit="persistenceUnit", transactionManager="transactionManager")
public class TregisterInfo {

	@Column(name = "imei")
	private String imei;
	
	@Column(name = "mac")
	private String mac;
	
	@Column(name = "username")
	private String username;
	
	@Column(name = "userno")
	private String userno;
	
	@Column(name = "platform")
	private String platform;
	
	@Column(name = "machine")
	private String machine;
	
	@Column(name = "softwareversion")
	private String softwareversion;
	
	@Column(name = "createtime")
	@Temporal(TemporalType.TIMESTAMP)
	private Date createtime;
	
	@Column(name = "logintime")
	@Temporal(TemporalType.TIMESTAMP)
	private Date logintime;
	
	@Column(name = "channel")
	private String channel;
	
	@Column(name = "loginchannel")
	private String loginchannel;
	
	
	public static List<TregisterInfo> getList(String where, String orderby, List<Object> params) {
		TypedQuery<TregisterInfo> q = entityManager().createQuery(
				"SELECT o FROM TregisterInfo o " + where + orderby, TregisterInfo.class);
		if (null != params && !params.isEmpty()) {
			int index = 1;
			for (Object param : params) {
				q.setParameter(index, param);
				index = index + 1;
			}
		}
		return q.getResultList();
	}

    public static BigDecimal getTotalVolume(String where, List<Object> params) {
        TypedQuery<Long> totalQ = entityManager().createQuery(
                "select count(o) from TregisterInfo o " + where, Long.class);
        if (null != params && !params.isEmpty()) {
            int index = 1;
            for (Object param : params) {
                totalQ.setParameter(index, param);
                index = index + 1;
            }
        }
        Long ret = totalQ.getSingleResult();
        return ret == null? BigDecimal.ZERO : new BigDecimal(ret);
    }
	
}
