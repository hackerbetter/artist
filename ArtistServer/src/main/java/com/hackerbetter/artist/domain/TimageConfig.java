package com.hackerbetter.artist.domain;

import com.hackerbetter.artist.util.common.Page;
import org.codehaus.jackson.annotate.JsonIgnore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.roo.addon.entity.RooEntity;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.json.RooJson;
import org.springframework.roo.addon.tostring.RooToString;

import javax.persistence.Column;
import javax.persistence.TypedQuery;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@RooJavaBean
@RooToString
@RooJson
@RooEntity(versionField="", table="timageconfig", persistenceUnit="persistenceUnit", transactionManager="transactionManager")
public class TimageConfig implements Serializable {

    private static Logger logger= LoggerFactory.getLogger(TimageConfig.class);

	private static final long serialVersionUID = 1L;

	@Column(name = "platform",length = 50)
	private String platform;

    @NotNull
	@Column(name = "url")
	private String url;

    @JsonIgnore
    @NotNull
    @Column(name="realPath")
    private String realPath;

    @JsonIgnore
    @NotNull
	@Column(name = "state",length = 1)
	private String state="1"; //0 关闭 1开启

    @JsonIgnore
	@Column(name = "sort")
	private Date sort;

    @JsonIgnore
    @NotNull
    @Column(name = "type",length = 1)
	private String type; // 0 首页图片 1 轮播图片
	
	@Column(name="tpaintingId")
	private Long tpaintingId;


    public static void findList(String where, String orderby, List<Object> params, Page<TimageConfig> page) {
        try {
            TypedQuery<TimageConfig> q = entityManager().createQuery(
                    "SELECT o FROM TimageConfig o " + where + orderby, TimageConfig.class);
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
                    "select count(o) from TimageConfig o " + where, Long.class);
            if (null != params && !params.isEmpty()) {
                int index = 1;
                for (Object param : params) {
                    totalQ.setParameter(index, param);
                    index = index + 1;
                }
            }
            page.setTotalResult(totalQ.getSingleResult().intValue());
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }

	public static List<TimageConfig> findList(String where, String orderby, List<Object> params) {
		TypedQuery<TimageConfig> q = entityManager().createQuery(
				"SELECT o FROM TimageConfig o " + where + orderby, TimageConfig.class);
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
