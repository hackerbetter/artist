package com.artist.cms.consts;

/**
 * Created by hacker on 2014/5/15.
 */
public enum Item {

    Information("Information"),
    Artists("Artists"),
    Appreciation("Appreciation"),
    ArtGallery("ArtGallery");

    private String memo;

    Item(String memo) {
        this.memo = memo;
    }

    public String getMemo(){
        return memo;
    }

}
