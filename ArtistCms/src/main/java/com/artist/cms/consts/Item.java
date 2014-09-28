package com.artist.cms.consts;

/**
 * Created by hacker on 2014/5/15.
 */
public enum Item {

    Information("information"),
    Artists("artists"),
    Appreciation("appreciation"),
    ArtGallery("artGallery");

    private String memo;

    Item(String memo) {
        this.memo = memo;
    }

    public String getMemo(){
        return memo;
    }

}
