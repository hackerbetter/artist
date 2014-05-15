package com.artist.cms.controller;

import com.artist.cms.cache.CacheService;
import com.artist.cms.consts.PropertiesConstant;
import com.artist.cms.domain.TimageConfig;
import com.artist.cms.domain.Tpainting;
import com.artist.cms.dto.ResponseData;
import com.artist.cms.util.Page;
import com.artist.cms.util.StringUtil;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by hacker on 2014/5/1.
 */
@RequestMapping(value = "/imageconfig")
@Controller
public class TimageController {
    private static Logger logger= LoggerFactory.getLogger(TimageController.class);
    @Autowired
    private CacheService cacheService;


    @SuppressWarnings({ "rawtypes", "unchecked" })
    @RequestMapping("/list")
    public ModelAndView list(@ModelAttribute("page") Page page, ModelAndView view) {
        try {
            StringBuilder builder = new StringBuilder("");
            List<Object> params = new ArrayList<Object>();
            List<Tpainting> tpaintings=Tpainting.findAllTpaintings();
            TimageConfig.findList(builder.toString(), "order by o.type,o.sort desc", params, page);
            view.addObject("tpaintings", tpaintings);
            view.addObject("page", page);
        } catch (Exception e) {
            logger.error("加载图片信息异常",e);
        }
        view.setViewName("imageconfig/list");
        return view;
    }
    @RequestMapping("/addUI")
    public ModelAndView addUI(ModelAndView view) {
        List<Tpainting> tpaintings = Tpainting.findAllTpaintings();
        view.addObject("tpaintings", tpaintings);
        view.setViewName("imageconfig/addUI");
        return view;
    }

    @SuppressWarnings("rawtypes")
    @RequestMapping("/add")
    @Transactional
    public ModelAndView add(HttpServletRequest request, @ModelAttribute("userinfo") TimageConfig imageConfig, ModelAndView view) {
        String errormsg = "添加成功";
        try {
            MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
            MultipartFile file = multipartRequest.getFile("file");
            if(file==null){
                errormsg = "文件为空";
                view.addObject("errormsg", errormsg);
                return this.list(new Page(), view);
            }
            String originalName = file.getOriginalFilename();
            String fileName=System.currentTimeMillis()+originalName;//文件名=时间戳+原始文件名称
            String realPath = PropertiesConstant.uploadPath+fileName;
            String url=PropertiesConstant.imageUrl+fileName;
            //判断路径是否存在，如果不存在则创建此目录
            File dir = new File(PropertiesConstant.uploadPath);
            if (!dir.exists()) {
                dir.mkdir();
            }
            File destination = new File(realPath);
            file.transferTo(destination);
            imageConfig.setRealPath(realPath);
            imageConfig.setUrl(url);
            imageConfig.persist();
            //清楚缓存
            cleanCache(imageConfig.getType());
        } catch (Exception e) {
            errormsg = e.getMessage();
            logger.error(e.getMessage());
        }
        view.addObject("errormsg", errormsg);
        return this.list(new Page(), view);
    }

    @RequestMapping("/editUI")
    public ModelAndView editUI(@RequestParam("id") Long id, ModelAndView view) {
        try {
            List<Tpainting> tpaintings = Tpainting.findAllTpaintings();
            TimageConfig timageConfig=TimageConfig.findTimageConfig(id);
            view.addObject("tpaintings", tpaintings);
            view.addObject("imageconfig", timageConfig);
        } catch (Exception e) {
            logger.info("editUI出现异常",e);
        }
        view.setViewName("imageconfig/editUI");
        return view;
    }

    @RequestMapping("/edit")
    @Transactional
    public ModelAndView edit(Long id,String state,Long tpaintingId ,ModelAndView view) {
        String errormsg = "修改成功";
        try {
            TimageConfig timageConfig=TimageConfig.findTimageConfig(id);
            timageConfig.setState(state);
            timageConfig.setTpaintingId(tpaintingId);
            timageConfig.merge();
            //清楚缓存
            cleanCache(timageConfig.getType());
        } catch (Exception e) {
            logger.error(e.getMessage());
            errormsg = e.toString();
        }
        view.addObject("errormsg", errormsg);
        return this.list(new Page(), view);
    }

    @RequestMapping(value = "/top", method = RequestMethod.POST)
    public @ResponseBody
    ResponseData top(@RequestParam("id") Long id) {
        logger.info("imageconfig/top");
        String errormsg = "置顶成功";
        ResponseData data = new ResponseData();
        try{
            TimageConfig timageConfig = TimageConfig.findTimageConfig(id);
            timageConfig.setSort(new Date());
            timageConfig.merge();
            cleanCache(timageConfig.getType());
            data.setErrorCode("0");
        }catch(Exception e){
            data.setErrorCode("4");
            logger.error("imageconfig/top");
            errormsg="置顶失败";
        }
        data.setValue(errormsg);
        return data;
    }

    @RequestMapping(value = "/remove", method = RequestMethod.POST)
    public @ResponseBody
    ResponseData remove(@RequestParam("id") Long id) {
        logger.info("imageconfig/remove");
        String errormsg = "删除成功";
        ResponseData data = new ResponseData();
        try{
            TimageConfig before = TimageConfig.findTimageConfig(id);
            before.remove();
            TimageConfig after = TimageConfig.findTimageConfig(id);
            if(null!=before&&null==after){
                File destination = new File(before.getRealPath());
                if (destination.exists()) {
                    destination.delete();
                    data.setErrorCode("0");
                    cleanCache(before.getType());
                }else{
                    errormsg="未找到图片文件，删除失败";
                    data.setErrorCode("1");
                }
            }
        }catch(Exception e){
            data.setErrorCode("4");
            errormsg="删除失败";
            logger.error("imageconfig/remove");
        }
        data.setValue(errormsg);
        return data;
    }
    /**
     * 清除缓存
     * @param type
     */
    private void cleanCache(String type) {
        logger.info("清除Image缓存");
        cacheService.set(StringUtil.join("_", "client", "image", type),"");
    }
}
