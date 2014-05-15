package com.hackerbetter.artist.domain;

import org.springframework.roo.addon.entity.RooEntity;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.json.RooJson;
import org.springframework.roo.addon.tostring.RooToString;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

/**
 * Created by hacker on 2014/4/29.
 * 用户表
 */
@RooJavaBean
@RooJson
@RooToString
@RooEntity(versionField="", table="tuserinfo", persistenceUnit="persistenceUnit", transactionManager="transactionManager")
public class TuserInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="userno",length = 8)
    private Long userno;//用户编号
    @Column(name="username",length = 60)
    private String username;//用户名
    @Column(name="password")
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
    private int state=0;//状态 0注销 1在用
    @Column(name="regtime")
    private Date regTime=new Date();//注册时间
}
