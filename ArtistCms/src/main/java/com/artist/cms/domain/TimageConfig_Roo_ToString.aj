// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package com.artist.cms.domain;

import java.lang.String;

privileged aspect TimageConfig_Roo_ToString {
    
    public String TimageConfig.toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Id: ").append(getId()).append(", ");
        sb.append("Info: ").append(getInfo()).append(", ");
        sb.append("Platform: ").append(getPlatform()).append(", ");
        sb.append("RealPath: ").append(getRealPath()).append(", ");
        sb.append("Sort: ").append(getSort()).append(", ");
        sb.append("State: ").append(getState()).append(", ");
        sb.append("TpaintingId: ").append(getTpaintingId()).append(", ");
        sb.append("Type: ").append(getType()).append(", ");
        sb.append("Url: ").append(getUrl());
        return sb.toString();
    }
    
}
