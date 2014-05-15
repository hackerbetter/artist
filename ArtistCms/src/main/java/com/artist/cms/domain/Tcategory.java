package com.artist.cms.domain;

import org.springframework.roo.addon.entity.RooEntity;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.json.RooJson;
import org.springframework.roo.addon.tostring.RooToString;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by hacker on 2014/4/29.
 * 栏目
 */
@RooJavaBean
@RooJson
@RooToString
@RooEntity(versionField="", table="Tcategory", persistenceUnit="persistenceUnit", transactionManager="transactionManager")
public class Tcategory  implements Serializable {
    @Column(name="name",length = 60)
    private String name;//栏目名称
    @Column(name="memo")
    private String memo;//描述
    @Column(name="state")
    private int state=1; //状态 0 关闭 1开启

    /**
     * 根据栏目描述模糊查询，支持多个模糊词查询
     * @param memos
     * @return
     */
    public static List<Tcategory> findTcategorysByMemos(String ... memos){
        StringBuilder where=new StringBuilder(" where o.memo like ?");
        List<Object> params=new ArrayList<Object>();
        params.add("%"+memos[0]+"%");
        for(int i=1;i<memos.length;i++){
            where.append(" or o.memo like ?");
            params.add("%"+memos[i]+"%");
        }
        return findList(where.toString(),"",params);
    }

    public static List<Tcategory> findList(String where, String orderby, List<Object> params) {
        TypedQuery<Tcategory> q = entityManager().createQuery(
                "SELECT o FROM Tcategory o " + where + orderby, Tcategory.class);
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
