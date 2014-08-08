// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package com.artist.cms.domain;

import com.artist.cms.domain.UserInf;
import flexjson.JSONDeserializer;
import flexjson.JSONSerializer;
import java.lang.String;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

privileged aspect UserInf_Roo_Json {
    
    public String UserInf.toJson() {
        return new JSONSerializer().exclude("*.class").serialize(this);
    }
    
    public static UserInf UserInf.fromJsonToUserInf(String json) {
        return new JSONDeserializer<UserInf>().use(null, UserInf.class).deserialize(json);
    }
    
    public static String UserInf.toJsonArray(Collection<UserInf> collection) {
        return new JSONSerializer().exclude("*.class").serialize(collection);
    }
    
    public static Collection<UserInf> UserInf.fromJsonArrayToUserInfs(String json) {
        return new JSONDeserializer<List<UserInf>>().use(null, ArrayList.class).use("values", UserInf.class).deserialize(json);
    }
    
}
