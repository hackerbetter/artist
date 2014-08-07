package com.artist.cms.domain;

import com.artist.cms.util.Page;
import org.apache.log4j.Logger;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.roo.addon.entity.RooEntity;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.json.RooJson;
import org.springframework.roo.addon.tostring.RooToString;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

@RooJavaBean
@RooToString
@RooJson
@RooEntity(versionField="", table="tversionstat", persistenceUnit="persistenceUnit", transactionManager="transactionManager")
public class TversionStat {
	private static final Logger logger = Logger.getLogger(TversionStat.class);

	@Column(name = "statdate", columnDefinition = "TIMESTAMP(6)")
	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(style = "S-")
	private Date statdate;
	
	@Column(name = "platform")
	private String platform;
	
	@Column(name = "version")
	private String version;
	
	@Column(name = "todaynewaddusers")
	private String todaynewaddusers;
	
	@Column(name = "todayconnectnetusers")
	private String todayconnectnetusers;
	
	@Column(name = "weekconnectnetusers")
	private String weekconnectnetusers;
	
	@Column(name = "monthconnectnetusers")
	private String monthconnectnetusers;
	
	@Column(name = "todayregisterusers")
	private String todayregisterusers;
	
	@Column(name = "totalregisterusers")
	private String totalregisterusers;
	
	@Column(name = "registerrate")
	private String registerrate;
	
	@Column(name = "totalusers")
	private String totalusers;
	
	public static void findList(String where, String orderby,
			List<Object> params, Page<TversionStat> page, boolean isList) {
		try {
			if (isList) {
				TypedQuery<TversionStat> q = entityManager().createQuery(
						"SELECT o FROM TversionStat o " + where + orderby, TversionStat.class);
				if (null != params && !params.isEmpty()) {
					int index = 1;
					for (Object param : params) {
						q.setParameter(index, param);
						index = index + 1;
					}
				}
				q.setFirstResult(page.getPageIndex() * page.getMaxResult())
						.setMaxResults(page.getMaxResult());
				page.setList(q.getResultList());
				TypedQuery<Long> totalQ = entityManager().createQuery(
						"select count(o) from TversionStat o " + where, Long.class);
				if (null != params && !params.isEmpty()) {
					int index = 1;
					for (Object param : params) {
						totalQ.setParameter(index, param);
						index = index + 1;
					}
				}
				page.setTotalResult(totalQ.getSingleResult().intValue());
			} else {
				TypedQuery<TversionStat> q = entityManager().createQuery(
						"SELECT o FROM TversionStat o " + where + orderby, TversionStat.class);
				if (null != params && !params.isEmpty()) {
					int index = 1;
					for (Object param : params) {
						q.setParameter(index, param);
						index = index + 1;
					}
				}
				q.setFirstResult(page.getPageIndex() * page.getMaxResult())
						.setMaxResults(page.getMaxResult());
				page.setList2(q.getResultList());
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
	}
	
}
