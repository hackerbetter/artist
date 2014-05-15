package com.hackerbetter.artistcore.domain;

import org.apache.commons.lang.StringUtils;
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
 * 用户表
 */
@RooJavaBean
@RooJson
@RooToString
@RooEntity(versionField="", table="tuserinfo", persistenceUnit="persistenceUnit", transactionManager="transactionManager",identifierField = "userno",identifierColumn = "userno")
public class TuserInfo implements Serializable{
    @Column(name="username",length = 60)
    @NotNull
    private String username;//用户名
    @Column(name="password")
    @NotNull
    private String password;//密码
    @Column(name="name",length = 60)
    private String name;//真实姓名
    @Column(name="nickname",length = 60)
    private String nickname;//昵称
    @Column(name="info")
    private String info;//介绍
    @Column(name="email")
    private String email;
    @Column(name="mobile",length = 60)
    private String mobile;
    @Column(name="qq",length = 60)
    private String qq;
    @Column(name="address")
    private String address;//画廊地址
    @Column(name="state",length = 2)
    @NotNull
    private int state=0;//状态 0注销 1在用 2停用
    @NotNull
    @Column(name="regtime")
    private Date regTime=new Date();//注册时间
    @NotNull
    @Column(name="modtime")
    private Date modTime=new Date();//修改时间

    @Column(name = "channel", length = 44)
    @NotNull
    private String channel ="1";
    @Column(name = "imei", length = 32)
    private String imei;

    public static TuserInfo findTuserInfo(String userno){
        Long usernoLong=Long.parseLong(userno);
        return findTuserInfo(usernoLong);
    }
    public static TuserInfo findTuserInfoByUsername(String username) {
        List<TuserInfo> list = entityManager().createQuery("from TuserInfo o where o.username=?", TuserInfo.class).setParameter(1, username).getResultList();
        if (list == null || list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }

    public static List<TuserInfo> findTuserInfosByNickname(String nickname) {
        return entityManager().createQuery("from TuserInfo o where o.nickname=?", TuserInfo.class).setParameter(1, nickname).getResultList();
    }

    public static List<TuserInfo> findTuserInfos(TuserInfo userinfo){
        StringBuilder builder = new StringBuilder(" where");
        List<Object> params = new ArrayList<Object>();

        /*if(StringUtils.isNotBlank(userinfo.getInfo())){
            builder.append(" o.info like ?");
            params.add('%'+userinfo.getInfo()+'%');
        }
        if(StringUtils.isNotBlank(userinfo.getAddress())){
            builder.append(" o.info like ?");
            params.add('%'+userinfo.getAddress()+'%');
        }*/
        if(userinfo.getUserno()!=null){
            builder.append(" o.userno=?");
            params.add(userinfo.getUserno());
        }
        addParam(builder,params," o.username=?",userinfo.getUsername());
        addParam(builder,params," o.nickname=?",userinfo.getNickname());
        addParam(builder,params," o.email=?",userinfo.getEmail());
        addParam(builder,params," o.mobile=?",userinfo.getMobile());
        addParam(builder,params," o.name=?",userinfo.getName());
        addParam(builder,params," o.qq=?",userinfo.getQq());
        return TuserInfo.getList(builder.toString(), " order by o.regTime asc", params);
    }

    private static void addParam(StringBuilder builder,List<Object> params,String param,String value){
        if(StringUtils.isNotBlank(value)){
            builder.append(param);
            params.add(value);
        }
    }
    public static List<TuserInfo> getList(String where,String orderby,List<Object> params){
        TypedQuery<TuserInfo> q = entityManager().createQuery(
                "from TuserInfo o " + where + orderby, TuserInfo.class);
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
