package com.hackerbetter.artist.consts;

/**
 * Created by hacker on 2014/4/29.
 */
public enum FavoriteType {
    STAR(1,"赞"),
    COLLECT(2,"收藏");

    private String memo;
    private int value;

    public int value(){return value;}
    public String memo(){return memo;}
    private FavoriteType(int value,String memo){
        this.value=value;
        this.memo=memo;
    }
}
