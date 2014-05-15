package com.artist.cms.domain;

import com.artist.cms.util.Page;
import org.codehaus.jackson.annotate.JsonIgnore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.roo.addon.entity.RooEntity;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.json.RooJson;
import org.springframework.roo.addon.solr.RooSolrSearchable;
import org.springframework.roo.addon.tostring.RooToString;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by hacker on 2014/4/29.
 * 绘画作品
 */
@RooJavaBean
@RooJson
@RooToString
@RooSolrSearchable
@RooEntity(versionField="", table="Tpainting", persistenceUnit="persistenceUnit", transactionManager="transactionManager")
public class Tpainting  implements Serializable {
    private static Logger logger= LoggerFactory.getLogger(Tpainting.class);

    @NotNull
    @Column(name="title")
    private String title;

    @Column(name="author",length = 10)
    private String author;

    @Column(name="countries",length = 10)
    private String countries;

    @Column(name="item",length = 20)
    private String item;//栏目

    @Column(name="shortImage",length = 255)
    private String shortImage;

    @Lob
    @NotNull
    @Column(name="content")
    private String content;

    @JsonIgnore
    @NotNull
    @Column(name="state",length = 1)
    private String state="1"; //0 停用 1启用

    @NotNull
    @Column(name="createtime")
    private Date createtime=new Date();

    @Column(name="categoryId")
    private long categoryId;

    @JsonIgnore
    @NotNull
    @Column(name="sort")
    private Date sort=new Date();

    public static void findList(String where, String orderby, List<Object> params, Page<Tpainting> page) {
        try {
            TypedQuery<Tpainting> q = entityManager().createQuery(
                    "SELECT o FROM Tpainting o " + where + orderby, Tpainting.class);
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
                    "select count(o) from Tpainting o " + where, Long.class);
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

    public static List<Tpainting> findList(String where, String orderby, List<Object> params) {
        TypedQuery<Tpainting> q = entityManager().createQuery(
                "SELECT o FROM Tpainting o " + where + orderby, Tpainting.class);
        if (null != params && !params.isEmpty()) {
            int index = 1;
            for (Object param : params) {
                q.setParameter(index, param);
                index = index + 1;
            }
        }
        return q.getResultList();
    }

    /**
     * 查询上一个作品
     * @return
     */
    public Tpainting findAbovePainting(){
        List<Object> params=new ArrayList<Object>();
        params.add(categoryId);
        params.add(sort);
        List <Tpainting>list=findList(" where o.categoryId=? and o.sort>? "," order by o.sort asc",params);
        if(list==null||list.isEmpty()){
            return null;
        }
        return list.get(0);
    }

}
