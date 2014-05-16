package com.hackerbetter.artist.service;

import com.hackerbetter.artist.cache.InfoCacheService;
import com.hackerbetter.artist.domain.TimageConfig;
import com.hackerbetter.artist.domain.Tpainting;
import com.hackerbetter.artist.dto.HomeImage;
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
    public String queryHomeImage() {
        List<TimageConfig> list=infoCacheService.getTimageConfigsByType("0");//所有首页图片
        List<HomeImage> result=new ArrayList<HomeImage>();
        for(TimageConfig t:list){
            HomeImage h=new HomeImage();
            try {
                BeanUtils.copyProperties(h,t);
                Tpainting p=Tpainting.findTpainting(t.getTpaintingId());
                result.add(h);
                if(p==null){
                    continue;
                }
                h.setCountries(p.getCountries());
                h.setAuthor(p.getAuthor());
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

    /**
     * 查询轮播图片
     * @return
     */
    public String queryHeadImage() {
        return Response.success("查询成功",infoCacheService.getTimageConfigsByType("1"));
    }
}
