// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package com.hackerbetter.artist.domain;

import java.lang.String;

privileged aspect Tfavorite_Roo_ToString {
    
    public String Tfavorite.toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("FavoriteType: ").append(getFavoriteType()).append(", ");
        sb.append("Id: ").append(getId()).append(", ");
        sb.append("PaintingId: ").append(getPaintingId()).append(", ");
        sb.append("State: ").append(getState()).append(", ");
        sb.append("Userno: ").append(getUserno());
        return sb.toString();
    }
    
}
