package com.artist.cms.dto;

import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.json.RooJson;

/**
 * Created by hacker on 2014/5/4.
 */
@RooJson
@RooJavaBean
public class UeditorResponse {
    private String original;
    private String url;
    private String title;
    private String state;

    public UeditorResponse() {
    }

    public UeditorResponse(String original, String url, String title, String state) {
        this.original = original;
        this.url = url;
        this.title = title;
        this.state = state;
    }
}
