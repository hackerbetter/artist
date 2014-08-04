package com.hackerbetter.artist.dto;

import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.json.RooJson;
import org.springframework.roo.addon.tostring.RooToString;

/**
 * Created by hacker on 2014/8/4.
 */
@RooJavaBean
@RooJson
@RooToString
public class ImgDto {
    private String ref;
    private String width;
    private String height;
    private String alt;
    private String src;

    public ImgDto(){}

    public ImgDto(String ref,String width,String height,String alt,String src){
        this.ref=ref;
        this.width=width;
        this.height=height;
        this.alt=alt;
        this.src=src;
    }
}
