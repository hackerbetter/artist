// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package com.artist.cms.dto;

import com.artist.cms.dto.UeditorResponse;
import flexjson.JSONDeserializer;
import flexjson.JSONSerializer;
import java.lang.String;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

privileged aspect UeditorResponse_Roo_Json {
    
    public String UeditorResponse.toJson() {
        return new JSONSerializer().exclude("*.class").serialize(this);
    }
    
    public static UeditorResponse UeditorResponse.fromJsonToUeditorResponse(String json) {
        return new JSONDeserializer<UeditorResponse>().use(null, UeditorResponse.class).deserialize(json);
    }
    
    public static String UeditorResponse.toJsonArray(Collection<UeditorResponse> collection) {
        return new JSONSerializer().exclude("*.class").serialize(collection);
    }
    
    public static Collection<UeditorResponse> UeditorResponse.fromJsonArrayToUeditorResponses(String json) {
        return new JSONDeserializer<List<UeditorResponse>>().use(null, ArrayList.class).use("values", UeditorResponse.class).deserialize(json);
    }
    
}