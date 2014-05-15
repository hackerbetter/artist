package com.artist.cms.domain;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.roo.addon.entity.RooEntity;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.json.RooJson;
import org.springframework.roo.addon.tostring.RooToString;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.List;

/**
 * Created by hacker on 2014/4/29.
 */
@RooJavaBean
@RooJson
@RooToString
@RooEntity(versionField="", table="Tfavorite", persistenceUnit="persistenceUnit", transactionManager="transactionManager")
public class Tfavorite  implements Serializable {
    @Column
    private Long userno;
    @Column
    private Long paintingId;
    @Column
    private int favoriteType;
    @Column
    private int state=1;//0停用 1启用

    public static List<Tfavorite> findByPaintingId(Long paintingId){
        return entityManager().createQuery("From Tfavorite o where o.paintingId=?").setParameter(1,paintingId).getResultList();
    }
}
