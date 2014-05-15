package com.hackerbetter.artist.jmx;

import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.jmx.export.annotation.ManagedAttribute;
import org.springframework.jmx.export.annotation.ManagedResource;

import java.sql.SQLException;

@ManagedResource(objectName="bean:name=DBCPDataSource")
public class ManagedBasicDataSource extends BasicDataSource {
    @ManagedAttribute
    public int getNumIdle(){
        return super.getNumIdle();
    }
    
    @ManagedAttribute
    public int getNumActive(){
        return super.getNumActive();
    }
    
    @ManagedAttribute
    public int getMaxActive(){
        return super.getMaxActive();
    }
}
