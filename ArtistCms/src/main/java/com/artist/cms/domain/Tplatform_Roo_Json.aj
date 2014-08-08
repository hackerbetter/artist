// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package com.artist.cms.domain;

import com.artist.cms.domain.Tplatform;
import flexjson.JSONDeserializer;
import flexjson.JSONSerializer;
import java.lang.String;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

privileged aspect Tplatform_Roo_Json {
    
    public String Tplatform.toJson() {
        return new JSONSerializer().exclude("*.class").serialize(this);
    }
    
    public static Tplatform Tplatform.fromJsonToTplatform(String json) {
        return new JSONDeserializer<Tplatform>().use(null, Tplatform.class).deserialize(json);
    }
    
    public static String Tplatform.toJsonArray(Collection<Tplatform> collection) {
        return new JSONSerializer().exclude("*.class").serialize(collection);
    }
    
    public static Collection<Tplatform> Tplatform.fromJsonArrayToTplatforms(String json) {
        return new JSONDeserializer<List<Tplatform>>().use(null, ArrayList.class).use("values", Tplatform.class).deserialize(json);
    }
    
}
