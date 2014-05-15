package com.artist.cms.consts;


import com.artist.cms.util.PropertiesUtils;

import java.util.Properties;

public class PropertiesConstant {
	private static Properties cmgrProperties = PropertiesUtils.loadProperties("META-INF/spring/cms.properties");
    public static String uploadPath=cmgrProperties.getProperty("uploadPath");
    public static String imageUrl=cmgrProperties.getProperty("imageUrl");
    public static String solrUrl=cmgrProperties.getProperty("solrUrl");
}
