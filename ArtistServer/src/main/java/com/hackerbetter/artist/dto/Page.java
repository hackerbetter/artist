package com.hackerbetter.artist.dto;

import flexjson.JSONDeserializer;
import flexjson.JSONSerializer;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.json.RooJson;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


/**
 * Created by hacker on 2014/5/16.
 */
@RooJavaBean
public class Page<E> {
    private int pageNow;
    private int pageSize;
    private Long count;
    private Long totalPage;
    private List<E> value;

    public Page() {
    }

    public Page(int pageNow, int pageSize, Long count) {
        this.pageNow = pageNow;
        this.pageSize = pageSize;
        this.count = count;
        this.totalPage = count/pageSize+(count%pageSize>0?1:0);
    }

    public Page(int pageNow, int pageSize, Long count, Long totalPage) {
        this.pageNow = pageNow;
        this.pageSize = pageSize;
        this.count = count;
        this.totalPage = totalPage;
    }

    public Page(int pageNow, int pageSize, Long count, Long totalPage, List<E> value) {
        this.pageNow = pageNow;
        this.pageSize = pageSize;
        this.count = count;
        this.totalPage = totalPage;
        this.value = value;
    }

    public String toJson() {
        return new JSONSerializer().exclude("*.class").serialize(this);
    }
    public static Page<Object> fromJsonToPage(String json) {
        return new JSONDeserializer<Page>().use(null, Page.class).deserialize(json);
    }
    public static String toJsonArray(Collection<Page<Object>> collection) {
        return new JSONSerializer().exclude("*.class").serialize(collection);
    }
    public static Collection<Page> fromJsonArrayToPages(String json) {
        return new JSONDeserializer<List<Page>>().use(null, ArrayList.class).use("values", Page.class).deserialize(json);
    }
}
