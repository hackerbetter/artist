package com.hackerbetter.artistcore.api;

import com.hackerbetter.artistcore.constants.ErrorCode;
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


    public Response(){}
    public Response(String errorCode,Object value){
        this.errorCode=errorCode;
        this.value=value;
    }
    public static Response success(Object value){
        return new Response(ErrorCode.OK.value,value);
    }
    public static Response success(){
        return success("");
    }
    public static Response fail(){
        return new Response(ErrorCode.ERROR.value,"参数错误");
    }
}
