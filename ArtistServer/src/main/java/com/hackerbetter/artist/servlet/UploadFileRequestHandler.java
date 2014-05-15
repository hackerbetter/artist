package com.hackerbetter.artist.servlet;

import java.io.File;
import java.util.List;

import javax.servlet.http.HttpServletRequest;


import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

public class UploadFileRequestHandler {
    /**
     * 获取参数
     * @param request
     * @return
     * @throws Exception 
     */
    public static String getParameterString(List<FileItem> items) throws Exception {
        for(FileItem item:items){
            if(item.isFormField()){
                return item.getString();
            }
        }
        return "";
    }
    
    public static FileItem getUploadFileItem(List<FileItem> items) {
        for(FileItem item:items){
            if(!item.isFormField()){
                return item;
            }
        }
        return null;
    }
    
    /**
     * just can call once
     * @param request
     * @return
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    public static List<FileItem> getFileItems(HttpServletRequest request) throws Exception{
        request.setCharacterEncoding("utf-8");  //设置编码   
        //获得磁盘文件条目工厂   
        DiskFileItemFactory factory = new DiskFileItemFactory();  
        //获取文件需要上传到的路径   
        String path = request.getRealPath("/upload");  //临时路径
        /** 
         * 如果没以下两行设置的话，上传大的 文件 会占用 很多内存，   
         * 原理 它是先存到 暂时存储室，然后在真正写到 对应目录的硬盘上，  
         * 按理来说 当上传一个文件时，其实是上传了两份，第一个是以 .tem 格式的  
         * 然后再将其真正写到 对应目录的硬盘上 
         */  
        factory.setRepository(new File(path));  
        //设置 缓存的大小，当上传文件的容量超过该缓存时，直接放到 暂时存储室   
        factory.setSizeThreshold(1024*1024) ;  
        //高水平的API文件上传处理   
        ServletFileUpload upload = new ServletFileUpload(factory);  
        //最大上传文件256k，单位：字节
        upload.setSizeMax(262144);
        //可以上传多个文件   
        return  (List<FileItem>)upload.parseRequest(request);  
    }
}
