package com.artist.cms.dto;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.json.RooJson;

import java.io.IOException;

@RooJavaBean
public class ResponseData {

	private String errorCode;
	private Object value;

    public String toJson(){
        try {
            ObjectMapper om = new ObjectMapper();
            return om.writeValueAsString(this);
        } catch (IOException e) {
        }
        return "";
    }
}
