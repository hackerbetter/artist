package com.hackerbetter.artist.service;

import com.codahale.metrics.annotation.Timed;
import com.hackerbetter.artist.cache.InfoCacheService;
import com.hackerbetter.artist.domain.TimageConfig;
import com.hackerbetter.artist.domain.Tpainting;
import com.hackerbetter.artist.dto.Image;
import com.hackerbetter.artist.util.Response;
import org.apache.commons.beanutils.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by hacker on 2014/5/15.
 */
@Service
public class ImageService {
    private Logger logger = LoggerFactory.getLogger(ImageService.class);
    @Autowired
    private InfoCacheService infoCacheService;

    /**
     * 查询首页图片
     * @return
     */
    @Timed(name="查询图片")
    public String queryImage(String type) {
        List<TimageConfig> list=infoCacheService.getTimageConfigsByType(type);
        List<Image> result=new ArrayList<Image>();
        for(TimageConfig t:list){
            Image h=new Image();
            try {
                BeanUtils.copyProperties(h,t);
                Tpainting p=Tpainting.findTpainting(t.getTpaintingId());
                result.add(h);
                if(p==null){
                    continue;
                }
                h.setCountries(p.getCountries());
                h.setAuthor(p.getAuthor());
                h.setCategoryId(p.getCategoryId());
                h.setCreatetime(p.getCreatetime());
                h.setTitle(p.getTitle());
            } catch (IllegalAccessException e) {
                logger.error(e.getMessage());
                return Response.fail("系统繁忙");
            } catch (InvocationTargetException e) {
                logger.error(e.getMessage());
                return Response.fail("系统繁忙");
            }
        }
        return Response.success("查询成功",result);
    }

}
