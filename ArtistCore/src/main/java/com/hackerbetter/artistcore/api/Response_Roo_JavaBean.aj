// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package com.hackerbetter.artistcore.api;

import java.lang.Object;
import java.lang.String;

privileged aspect Response_Roo_JavaBean {
    
    public String Response.getErrorCode() {
        return this.errorCode;
    }
    
    public void Response.setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }
    
    public Object Response.getValue() {
        return this.value;
    }
    
    public void Response.setValue(Object value) {
        this.value = value;
    }
    
}
