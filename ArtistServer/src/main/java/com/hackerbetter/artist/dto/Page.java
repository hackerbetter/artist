package com.hackerbetter.artist.dto;

import org.springframework.roo.addon.javabean.RooJavaBean;

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
}
