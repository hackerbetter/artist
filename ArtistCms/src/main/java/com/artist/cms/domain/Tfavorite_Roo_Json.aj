// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package com.artist.cms.domain;

import com.artist.cms.domain.Tfavorite;
import flexjson.JSONDeserializer;
import flexjson.JSONSerializer;
import java.lang.String;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

privileged aspect Tfavorite_Roo_Json {
    
    public String Tfavorite.toJson() {
        return new JSONSerializer().exclude("*.class").serialize(this);
    }
    
    public static Tfavorite Tfavorite.fromJsonToTfavorite(String json) {
        return new JSONDeserializer<Tfavorite>().use(null, Tfavorite.class).deserialize(json);
    }
    
    public static String Tfavorite.toJsonArray(Collection<Tfavorite> collection) {
        return new JSONSerializer().exclude("*.class").serialize(collection);
    }
    
    public static Collection<Tfavorite> Tfavorite.fromJsonArrayToTfavorites(String json) {
        return new JSONDeserializer<List<Tfavorite>>().use(null, ArrayList.class).use("values", Tfavorite.class).deserialize(json);
    }
    
}
