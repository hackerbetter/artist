package com.artist.cms.domain;

import com.artist.cms.util.Page;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.roo.addon.entity.RooEntity;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.json.RooJson;
import org.springframework.roo.addon.tostring.RooToString;

import javax.persistence.*;
import java.util.List;

@RooJavaBean
@RooToString
@RooJson
@RooEntity(versionField="", table="tbl_coop", persistenceUnit="persistenceUnit", transactionManager="transactionManager")
public class Coop {
	private static final Logger logger= LoggerFactory.getLogger(Coop.class);

	@Column(name = "coopname")
	private String coopname;
	
	@Column(name = "coopid")
	private Integer coopid;
	
	@Column(name = "rate")
	private Integer rate;

	public static List<Coop> getList(String where, String orderby, List<Object> params) {
		TypedQuery<Coop> q = entityManager().createQuery(
				"SELECT o FROM Coop o " + where + orderby, Coop.class);
		if (null != params && !params.isEmpty()) {
			int index = 1;
			for (Object param : params) {
				q.setParameter(index, param);
				index = index + 1;
			}
		}
		return q.getResultList();
	}

	public static void findAllCoops(Page<Coop> page) {
        try{
		TypedQuery<Coop> q = entityManager()
				.createQuery(
						"SELECT o FROM Coop o ", Coop.class);
		q.setFirstResult(page.getPageIndex() * page.getMaxResult()).setMaxResults(page.getMaxResult());
		page.setList(q.getResultList());
		TypedQuery<Long> totalQ = entityManager().createQuery(
				"select count(o) from Coop o ", Long.class);
		page.setTotalResult(totalQ.getSingleResult().intValue());
        }catch(Exception e) {
        	logger.error(e.getMessage());
        }
	}
}
