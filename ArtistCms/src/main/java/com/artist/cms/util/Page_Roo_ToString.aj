// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package com.artist.cms.util;

import java.lang.String;

privileged aspect Page_Roo_ToString {
    
    public String Page.toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("CurrentPageNo: ").append(getCurrentPageNo()).append(", ");
        sb.append("List: ").append(getList() == null ? "null" : getList().size()).append(", ");
        sb.append("List2: ").append(getList2() == null ? "null" : getList2().size()).append(", ");
        sb.append("MaxResult: ").append(getMaxResult()).append(", ");
        sb.append("OrderBy: ").append(getOrderBy()).append(", ");
        sb.append("OrderDir: ").append(getOrderDir()).append(", ");
        sb.append("PageIndex: ").append(getPageIndex()).append(", ");
        sb.append("TotalPage: ").append(getTotalPage()).append(", ");
        sb.append("TotalResult: ").append(getTotalResult()).append(", ");
        sb.append("Value: ").append(getValue()).append(", ");
        sb.append("OrderBySetted: ").append(isOrderBySetted());
        return sb.toString();
    }
    
}
