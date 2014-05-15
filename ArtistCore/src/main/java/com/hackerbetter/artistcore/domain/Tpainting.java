package com.hackerbetter.artistcore.domain;

import org.springframework.roo.addon.entity.RooEntity;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.json.RooJson;
import org.springframework.roo.addon.tostring.RooToString;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by hacker on 2014/4/29.
 * 绘画作品
 */
@RooJavaBean
@RooJson
@RooToString
@RooEntity(versionField="", table="Tpainting", persistenceUnit="persistenceUnit", transactionManager="transactionManager")
public class Tpainting  implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="id",length = 8)
    private long id;
    @Column(name="title")
    private String title;
    @Column(name="info")
    private String info;
    @Column(name="author",length = 10)
    private String author;
    @Column(name="content")
    private String content;
    @Column(name="state")
    private int state=0;
    @Column(name="createtime")
    private Date createtime=new Date();
    @Column(name="categoryId")
    private long categoryId;
}
