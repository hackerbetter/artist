package com.hackerbetter.artist.util;

import com.hackerbetter.artist.consts.ErrorCode;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.json.RooJson;

import java.io.IOException;

/**
 * Created by hacker on 2014/4/30.
 */
@RooJavaBean
public class Response {
    private static Logger logger= LoggerFactory.getLogger(Response.class);

    private String errorCode;
    private String message;
    private Object value;

    public Response(){}

    public Response(String errorCode,String msg){
        this.errorCode=errorCode;
        this.message=msg;
    }
    public Response(ErrorCode errorCode){
        this.errorCode=errorCode.value;
        this.message=errorCode.memo;
    }
    public Response(String errorCode,String msg,Object value){
        this.errorCode=errorCode;
        this.message=msg;
        this.value=value;
    }
    public static String fail(ErrorCode errorCode){
        logger.info(errorCode.memo);
        return new Response(errorCode).toJson();
    }

    public static String paramError(String imei){
        logger.info("参数错误imei:" + imei);
        return fail(ErrorCode.ParamError);
    }

    public static String success(String msg,Object value){
        return new Response("0000",msg,value).toJson();
    }

    public static String success(String msg){
        return new Response("0000",msg).toJson();
    }

    public static String fail(String msg){
        logger.info(msg);
        return new Response("9999",msg).toJson();
    }
    public static String response(Response response){
        return response.toJson();
    }

    public String toJson(){
        try {
            ObjectMapper om = new ObjectMapper();
            return om.writeValueAsString(this);
        } catch (IOException e) {
        }
        return "";
    }
}
