package com.artist.cms.controller;

import com.artist.cms.consts.PropertiesConstant;
import com.artist.cms.dto.UeditorResponse;
import com.artist.cms.util.DateUtils;
import com.artist.cms.util.Uploader;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by hacker on 2014/5/4.
 */
@RequestMapping("/ueditor")
@Controller
public class UeditorController {

    private Logger logger= LoggerFactory.getLogger(UeditorController.class);

    @RequestMapping("/manager")
    public @ResponseBody String manager(){
        String imgStr ="";
        String realpath = PropertiesConstant.uploadPath;
        List<File> files = getFiles(realpath,new ArrayList());
        for(File file :files ){
            String dir=file.getParentFile().getName();
            if(DateUtils.isDate(dir)){
                imgStr+=PropertiesConstant.imageUrl+dir+"/"+file.getName()+"ue_separate_ue";
            }else{
                imgStr+=PropertiesConstant.imageUrl+file.getName()+"ue_separate_ue";
            }
        }
        if(StringUtils.isNotBlank(imgStr)){
            imgStr = imgStr.substring(0,imgStr.lastIndexOf("ue_separate_ue")).replace(File.separator, "/").trim();
        }
        return  imgStr;
    }

    public List getFiles(String realpath, List files) {
        File realFile = new File(realpath);
        if (realFile.isDirectory()) {
            File[] subfiles = realFile.listFiles();
            for(File file :subfiles ){
                if(file.isDirectory()){
                    getFiles(file.getAbsolutePath(),files);
                }else{
                    if(!getFileType(file.getName()).equals("")) {
                        files.add(file);
                    }
                }
            }
        }
        return files;
    }

    public String getFileType(String fileName){
        String[] fileType = {".gif" , ".png" , ".jpg" , ".jpeg" , ".bmp"};
        Iterator<String> type = Arrays.asList(fileType).iterator();
        while(type.hasNext()){
            String t = type.next();
            if(fileName.toLowerCase().endsWith(t)){
                return t;
            }
        }
        return "";
    }

    /**
     * 根据字符串创建本地目录 并按照日期建立子目录返回
     * @param path
     * @return
     */
    private String getFolder(String path) {
        SimpleDateFormat formater = new SimpleDateFormat("yyyyMMdd");
        path += "/" + formater.format(new Date());
        File dir = new File(path);
        if (!dir.exists()) {
            try {
                dir.mkdirs();
            } catch (Exception e) {
                logger.error("创建目录失败",e);
                return "";
            }
        }
        return path;
    }
}
