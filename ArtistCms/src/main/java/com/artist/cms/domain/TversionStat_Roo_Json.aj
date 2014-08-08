// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package com.artist.cms.domain;

import com.artist.cms.domain.TversionStat;
import flexjson.JSONDeserializer;
import flexjson.JSONSerializer;
import java.lang.String;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

privileged aspect TversionStat_Roo_Json {
    
    public String TversionStat.toJson() {
        return new JSONSerializer().exclude("*.class").serialize(this);
    }
    
    public static TversionStat TversionStat.fromJsonToTversionStat(String json) {
        return new JSONDeserializer<TversionStat>().use(null, TversionStat.class).deserialize(json);
    }
    
    public static String TversionStat.toJsonArray(Collection<TversionStat> collection) {
        return new JSONSerializer().exclude("*.class").serialize(collection);
    }
    
    public static Collection<TversionStat> TversionStat.fromJsonArrayToTversionStats(String json) {
        return new JSONDeserializer<List<TversionStat>>().use(null, ArrayList.class).use("values", TversionStat.class).deserialize(json);
    }
    
}
