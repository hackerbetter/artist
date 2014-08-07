package com.artist.cms.domain;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Query;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.TypedQuery;
import org.springframework.roo.addon.entity.RooEntity;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.json.RooJson;
import org.springframework.roo.addon.tostring.RooToString;

@RooJavaBean
@RooToString
@RooJson
@RooEntity(versionField="", table="TUSERINFO", persistenceUnit="lotteryPersistenceUnit", transactionManager="lotteryTransactionManager")
public class TuserInfo {

    @Id
    @Column(name="USERNO")
    private String userno;

    @Column(name="NAME")
    private String name;

    @Column(name="USERNAME")
    private String username;

    @Column(name="REGTIME")
    @Temporal(TemporalType.TIMESTAMP)
    private Date regtime;

    @Column(name = "channel")
    private String channel;


    public static BigDecimal getTotalVolume(String where, List<Object> params) {
        TypedQuery<Long> totalQ = entityManager().createQuery(
                "select count(o) from TuserInfo o " + where, Long.class);
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

    public static List<TuserInfo> getList(String where, String orderby, List<Object> params) {
        TypedQuery<TuserInfo> q = entityManager().createQuery(
                "SELECT o FROM TuserInfo o " + where + orderby, TuserInfo.class);
        if (null != params && !params.isEmpty()) {
            int index = 1;
            for (Object param : params) {
                q.setParameter(index, param);
                index = index + 1;
            }
        }
        return q.getResultList();
    }

    @SuppressWarnings("unchecked")
    public static List<Object[]> findList(String where) {
        Query  q =  entityManager().createNativeQuery(where );
        return q.getResultList();
    }

}
