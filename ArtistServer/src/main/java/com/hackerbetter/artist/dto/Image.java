package com.hackerbetter.artist.dto;

import org.springframework.roo.addon.javabean.RooJavaBean;

import java.util.Date;

/**
 * Created by hacker on 2014/5/15.
 */
@RooJavaBean
public class Image {
    private Long id;
    private String url;
    private Long tpaintingId;
    private String title;
    private String author;
    private String countries;
    private Date createtime;
    private String info;
}
