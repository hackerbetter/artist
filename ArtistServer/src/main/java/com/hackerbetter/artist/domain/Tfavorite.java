package com.hackerbetter.artist.domain;

import com.hackerbetter.artist.consts.FavoriteType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.roo.addon.entity.RooEntity;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.json.RooJson;
import org.springframework.roo.addon.tostring.RooToString;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

/**
 * Created by hacker on 2014/4/29.
 */
@RooJavaBean
@RooJson
@RooToString
@RooEntity(versionField="", table="tfavorite", persistenceUnit="persistenceUnit", transactionManager="transactionManager")
public class Tfavorite  implements Serializable {
    private static Logger logger= LoggerFactory.getLogger(Tfavorite.class);
    @Column
    private Long userno;
    @Column
    private Long paintingId;
    @Column
    private int favoriteType;
    @Column
    private int state=1;//0停用 1启用

    /**
     * 赞和收藏
     * @param paintingId
     * @param userno
     * @param favoriteType
     */
    @Transactional
    public static void operate(String paintingId, String userno,FavoriteType favoriteType) {
        try {
            Long usernoLong=Long.parseLong(userno);
            Long paintingIdLong=Long.parseLong(paintingId);
            Tfavorite favorite=findByUsernoAndPaintingId(usernoLong,paintingIdLong,favoriteType);
            if(favorite==null){
                favorite=new Tfavorite();
                favorite.setFavoriteType(favoriteType.value());
                favorite.setPaintingId(paintingIdLong);
                favorite.setUserno(usernoLong);
                favorite.persist();
            }else{
                if(favorite.getState()==1){
                    favorite.setState(0);//取消赞或收藏
                }else if(favorite.getState()==0){
                    favorite.setState(1);//重新赞或收藏
                }
            }
        } catch (NumberFormatException e) {
            logger.error(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public static Long querySupportNumByTypeAndPaintingId(Long paintingId){
       TypedQuery<Long> query= entityManager().createQuery(
               "select count(*) from Tfavorite o where o.paintingId =? and o.favoriteType=? and o.state=?", Long.class);
       query.setParameter(1,paintingId);
       query.setParameter(2,FavoriteType.STAR.value());
       query.setParameter(3,1);
       return query.getSingleResult();
    }

    public static List<Long> getCollectPaintingIdByUserno(Long userno){
        TypedQuery<Long> query=entityManager().createQuery("select o.paintingId from Tfavorite o where o.userno=? and o.favoriteType=? and o.state=?",Long.class);
        query.setParameter(1,userno);
        query.setParameter(2, FavoriteType.COLLECT.value());
        query.setParameter(3, 1);
        return query.getResultList();
    }

    public static Tfavorite findByUsernoAndPaintingId(Long userno,Long paintingId,FavoriteType favoriteType){
        List<Tfavorite> list=entityManager().createQuery
                ("select o From Tfavorite o where o.userno = ? and paintingId = ? and favoriteType=?",Tfavorite.class)
        .setParameter(1,userno)
        .setParameter(2,paintingId)
        .setParameter(3,favoriteType.value())
        .getResultList();
        if(list!=null&&!list.isEmpty()){
            return list.get(0);
        }
        return null;
    }
}
