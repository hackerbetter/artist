// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package com.artist.cms.domain;

import java.lang.String;

privileged aspect Tpainting_Roo_ToString {
    
    public String Tpainting.toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Author: ").append(getAuthor()).append(", ");
        sb.append("CategoryId: ").append(getCategoryId()).append(", ");
        sb.append("Content: ").append(getContent()).append(", ");
        sb.append("Countries: ").append(getCountries()).append(", ");
        sb.append("Createtime: ").append(getCreatetime()).append(", ");
        sb.append("Id: ").append(getId()).append(", ");
        sb.append("Item: ").append(getItem()).append(", ");
        sb.append("ShortImage: ").append(getShortImage()).append(", ");
        sb.append("Sort: ").append(getSort()).append(", ");
        sb.append("State: ").append(getState()).append(", ");
        sb.append("Title: ").append(getTitle());
        return sb.toString();
    }
    
}