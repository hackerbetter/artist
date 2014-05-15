package com.hackerbetter.artist.util;

public class VersionUtil {
    public static boolean needUpdate(String originVersion,String upgradeVersion){
        return secondVersionBiggerThenFirstVersion(originVersion, upgradeVersion);
    }
    
    public static boolean isSameVersion(String originVersion,String upgradeVersion){
        if(originVersion == null || upgradeVersion == null){
            return false;
        }
        return toRealDoubleVersion(originVersion) == toRealDoubleVersion(upgradeVersion);
    }
    
    public static boolean secondVersionBiggerThenFirstVersion(String originVersion,String upgradeVersion){
        if(originVersion == null || upgradeVersion == null){
            return false;
        }
        return toRealDoubleVersion(originVersion) < toRealDoubleVersion(upgradeVersion);
    }
    
    public static boolean secondVersionBiggerOrEqualFirstVersion(String firstVersion,String secondVersion){
        if(firstVersion == null || secondVersion == null){
            return false;
        }
        return toRealDoubleVersion(firstVersion) <= toRealDoubleVersion(secondVersion);
    }
    
    public static Double toRealDoubleVersion(String version){
        return Double.parseDouble("0." + version.replace(" ", "").replace(".", ""));
    }
}
