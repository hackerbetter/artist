package com.artist.cms.controller;

import com.artist.cms.cache.CacheService;
import com.artist.cms.domain.Tcategory;
import com.artist.cms.dto.ResponseData;
import com.artist.cms.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

/**
 * Created by hacker on 2014/5/3.
 */
@RequestMapping(value = "/category")
@Controller
public class TcategoryController {

    private static Logger logger= LoggerFactory.getLogger(TcategoryController.class);
    @Autowired
    private CacheService cacheService;

    @RequestMapping("/list")
    public ModelAndView list(ModelAndView view) {
        List<Tcategory> list= Tcategory.findAllTcategorys();
        view.addObject("categorys",list);
        view.setViewName("category/list");
        return view;
    }
    @RequestMapping("/query")
    public ModelAndView query(String memo,ModelAndView view){
        String []memos=memo.replace(" ",",").split(",");
        List<Tcategory> list= Tcategory.findTcategorysByMemos(memos);
        view.addObject("categorys",list);
        view.setViewName("category/list");
        return view;
    }

    @RequestMapping("/addUI")
    public ModelAndView addUI(ModelAndView view){
        view.setViewName("category/addUI");
        return view;
    }
    @RequestMapping("/add")
    public ModelAndView add(Tcategory tcategory,ModelAndView view){
        try {
            tcategory.persist();
            cleanCache();
        } catch (Exception e) {
            logger.error("添加作品类别",e);
            view.addObject("errormsg", "添加失败");
        }
        return list(view);
    }
    @RequestMapping("/remove")
    public
    @ResponseBody
    ResponseData  remove(Long id,ModelAndView view){
        logger.info("category/remove");
        ResponseData data = new ResponseData();
        try {
            Tcategory tcategory=Tcategory.findTcategory(id);
            tcategory.remove();
            cleanCache();
            data.setErrorCode("0");
        } catch (Exception e) {
            logger.error("删除作品类别",e);
            data.setValue("删除失败");
            data.setErrorCode("4");
        }
        return data;
    }
    @RequestMapping("/editUI")
    public ModelAndView editUI(Long id,ModelAndView view){
        Tcategory tcategory=Tcategory.findTcategory(id);
        view.addObject("category",tcategory);
        view.setViewName("category/editUI");
        return view;
    }
    @RequestMapping("/edit")
    public ModelAndView edit(Long id,String name,String memo,ModelAndView view){
        try {
            Tcategory tcategory=Tcategory.findTcategory(id);
            tcategory.setName(name);
            tcategory.setMemo(memo);
            tcategory.merge();
            cleanCache();
        } catch (Exception e) {
            logger.error(e.getMessage());
            view.addObject("errormsg", "修改失败");
        }
        return list(view);
    }

    /**
     * 清除缓存
     */
    private void cleanCache() {
        logger.info("清除category缓存");
        cacheService.set(StringUtil.join("_", "client", "category"),"");
    }
}
