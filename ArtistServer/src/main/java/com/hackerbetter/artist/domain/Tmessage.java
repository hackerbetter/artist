package com.hackerbetter.artist.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonIgnore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.roo.addon.entity.RooEntity;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.json.RooJson;
import org.springframework.roo.addon.tostring.RooToString;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by hacker on 2014/4/29.
 * 用户评论
 */
@RooJavaBean
@RooJson
@JsonIgnoreProperties(value = {"id","paintingId"})
@RooToString
@RooEntity(versionField="", table="tmessage", persistenceUnit="persistenceUnit", transactionManager="transactionManager")
public class Tmessage implements Serializable {
    private static Logger logger= LoggerFactory.getLogger(Tmessage.class);

    @NotNull
    @Column(name="author",length = 100)
    private String author;

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

    @NotNull
    @Column(name="paintingId")
    private Long paintingId;

    @JoinColumn(name = "parentId", referencedColumnName = "id")
    @OneToOne(cascade=CascadeType.ALL,optional = true)
    private Tmessage replyTo;

    public static List<Tmessage> findList(String where, String orderby, List<Object> params,Integer pageNow,Integer pageSize) {
        try {
            TypedQuery<Tmessage> q = entityManager().createQuery(
                    "SELECT o FROM Tmessage o " + where + orderby, Tmessage.class);
            if (null != params && !params.isEmpty()) {
                int index = 1;
                for (Object param : params) {
                    q.setParameter(index, param);
                    index = index + 1;
                }
            }
            q.setFirstResult(pageNow * pageSize)
                    .setMaxResults(pageSize);
            return q.getResultList();

        } catch (Exception e) {
            logger.error(e.getMessage());
            return null;
        }
    }

    public static List<Tmessage> findList(String where, String orderby, List<Object> params) {
        TypedQuery<Tmessage> q = entityManager().createQuery(
                "SELECT o FROM Tmessage o " + where + orderby, Tmessage.class);
        if (null != params && !params.isEmpty()) {
            int index = 1;
            for (Object param : params) {
                q.setParameter(index, param);
                index = index + 1;
            }
        }
        return q.getResultList();
    }

    public static List<Tmessage> findAllByPaintingId(Long paintingId,Integer pageNow,Integer pageSize){
        List<Object> param=new ArrayList<Object>();
        String where=" where o.paintingId=? and o.state=? ";
        param.add(paintingId);
        param.add("1");
        List<Tmessage> list=Tmessage.findList(where," order by o.createtime desc",param, pageNow, pageSize);
        return list;
    }

    public static Long count(Long paintingId){
        List<Object> param=new ArrayList<Object>();
        String where=" where o.paintingId=? and o.state=? ";
        param.add(paintingId);
        param.add("1");
        return count(where,param);
    }

    public static Long count(String where,List<Object> params){
        TypedQuery<Long> totalQ = entityManager().createQuery(
                "select count(o) from Tmessage o " + where, Long.class);
        if (null != params && !params.isEmpty()) {
            int index = 1;
            for (Object param : params) {
                totalQ.setParameter(index, param);
                index = index + 1;
            }
        }
        return totalQ.getSingleResult();
    }
}
