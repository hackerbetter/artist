// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package com.hackerbetter.artist.protocol;

import com.hackerbetter.artist.protocol.ClientInfo;
import flexjson.JSONDeserializer;
import flexjson.JSONSerializer;
import java.lang.String;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

privileged aspect ClientInfo_Roo_Json {
    
    public String ClientInfo.toJson() {
        return new JSONSerializer().exclude("*.class").serialize(this);
    }
    
    public static ClientInfo ClientInfo.fromJsonToClientInfo(String json) {
        return new JSONDeserializer<ClientInfo>().use(null, ClientInfo.class).deserialize(json);
    }
    
    public static String ClientInfo.toJsonArray(Collection<ClientInfo> collection) {
        return new JSONSerializer().exclude("*.class").serialize(collection);
    }
    
    public static Collection<ClientInfo> ClientInfo.fromJsonArrayToClientInfoes(String json) {
        return new JSONDeserializer<List<ClientInfo>>().use(null, ArrayList.class).use("values", ClientInfo.class).deserialize(json);
    }
    
}