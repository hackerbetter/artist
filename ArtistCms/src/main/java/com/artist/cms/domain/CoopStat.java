package com.artist.cms.domain;

import com.artist.cms.util.Page;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.roo.addon.entity.RooEntity;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.json.RooJson;
import org.springframework.roo.addon.tostring.RooToString;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@RooJavaBean
@RooToString
@RooJson
@RooEntity(versionField="", table="coopstat", persistenceUnit="persistenceUnit", transactionManager="transactionManager")
public class CoopStat {
	private static final Logger logger= LoggerFactory.getLogger(CoopStat.class);

	@Column(name = "statdate", columnDefinition = "TIMESTAMP(6)")
	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(style = "S-")
	private Date statdate;
	
	@Column(name = "coopid")
	private String coopid;
	
	@Column(name = "newusers")
	private BigDecimal newusers;
	
	@Column(name = "todayconnect")
	private BigDecimal todayconnect;
	
	@Column(name = "totalnum")
	private BigDecimal totalnum;
	
	@Column(name = "sevenday")
	private BigDecimal sevenday;
	
	@Column(name = "monthday")
	private BigDecimal monthday;
	
	@Column(name = "coopname")
	private String coopname;
	
	@Column(name = "totalregnum")
	private BigDecimal totalregnum;
	
	@Column(name = "todayregnum")
	private BigDecimal todayregnum;
	
	@Column(name = "new_fix")
	private BigDecimal new_fix;
	
	@Column(name = "regrate")
	private BigDecimal regrate;

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static void findList(String where, String orderby,
			List<Object> params, Page<CoopStat> page, boolean isList) {
		try {
			if (isList) {
				Query q =  entityManager().createNativeQuery(
						"SELECT * FROM CoopStat o " + where + orderby,CoopStat.class);
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
				Query totalQ = entityManager().createNativeQuery(
						"SELECT * FROM CoopStat o " + where + orderby);
				if (null != params && !params.isEmpty()) {
					int index = 1;
					for (Object param : params) {
							totalQ.setParameter(index, param);
						index = index + 1;
					}
				}
				page.setTotalResult(totalQ.getResultList().size());
			} else {
				Query q = entityManager().createNativeQuery(
						"SELECT * FROM CoopStat o " + where + orderby,CoopStat.class);
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
	
	public static BigDecimal getTotalVolume(String colume, String where, List<Object> params) {
		TypedQuery<BigDecimal> totalQ = entityManager().createQuery(
				"select sum("+colume+") from CoopStat o " + where, BigDecimal.class);
		if (null != params && !params.isEmpty()) {
			int index = 1;
			for (Object param : params) {
				totalQ.setParameter(index, param);
				index = index + 1;
			}
		}
		BigDecimal ret = totalQ.getSingleResult();
		return ret == null? BigDecimal.ZERO : ret;
	}
	
	public static List<CoopStat> getList(String where, String orderby, List<Object> params) {
		TypedQuery<CoopStat> q = entityManager().createQuery("SELECT o FROM CoopStat o " + where + orderby, CoopStat.class);
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
