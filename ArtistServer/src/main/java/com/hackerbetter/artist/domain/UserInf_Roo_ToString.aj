// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package com.hackerbetter.artist.domain;

import java.lang.String;

privileged aspect UserInf_Roo_ToString {
    
    public String UserInf.toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Channel: ").append(getChannel()).append(", ");
        sb.append("Createtime: ").append(getCreatetime()).append(", ");
        sb.append("Id: ").append(getId()).append(", ");
        sb.append("Imei: ").append(getImei()).append(", ");
        sb.append("Imsi: ").append(getImsi()).append(", ");
        sb.append("Isemular: ").append(getIsemular()).append(", ");
        sb.append("Lastchannel: ").append(getLastchannel()).append(", ");
        sb.append("Lastnetconnecttime: ").append(getLastnetconnecttime()).append(", ");
        sb.append("Mac: ").append(getMac()).append(", ");
        sb.append("Machine: ").append(getMachine()).append(", ");
        sb.append("Mobilenum: ").append(getMobilenum()).append(", ");
        sb.append("PhoneSIM: ").append(getPhoneSIM()).append(", ");
        sb.append("Platfrom: ").append(getPlatfrom()).append(", ");
        sb.append("Regtime: ").append(getRegtime()).append(", ");
        sb.append("Softwareversion: ").append(getSoftwareversion()).append(", ");
        sb.append("Userno: ").append(getUserno());
        return sb.toString();
    }
    
}
