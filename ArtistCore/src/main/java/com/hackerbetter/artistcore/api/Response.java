package com.hackerbetter.artistcore.api;

import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.json.RooJson;

/**
 * Created by hacker on 2014/4/30.
 */
@RooJavaBean
@RooJson
public class Response {
    private String errorCode;
    private Object value;
}
