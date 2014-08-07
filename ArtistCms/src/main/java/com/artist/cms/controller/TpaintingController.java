package com.artist.cms.controller;

import com.artist.cms.cache.CacheService;
import com.artist.cms.domain.Tcategory;
import com.artist.cms.domain.Tfavorite;
import com.artist.cms.domain.TimageConfig;
import com.artist.cms.domain.Tpainting;
import com.artist.cms.dto.ResponseData;
import com.artist.cms.util.Page;
import com.artist.cms.util.StringUtil;
import org.apache.commons.lang.StringUtils;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocumentList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by hacker on 2014/5/3.
 */
@RequestMapping(value = "/painting")
@Controller
public class TpaintingController {
    private static Logger logger= LoggerFactory.getLogger(TpaintingController.class);
    @Autowired
    private CacheService cacheService;
    @RequestMapping("/list")
    public  ModelAndView list(@ModelAttribute("page") Page page, ModelAndView view) {
        try {
            List<Tcategory> tcategories=Tcategory.findAllTcategorys();
            Tpainting.findList("", "order by o.item,o.sort desc",  new ArrayList<Object>(), page);
            view.addObject("tcategories", tcategories);
            view.addObject("page", page);
        } catch (Exception e) {
            logger.error("加载作品信息异常",e);
            view.addObject("errorMsg","获取作品失败");
        }
        view.setViewName("painting/list");
        return view;
    }
    @RequestMapping("/query")
    public ModelAndView query(@ModelAttribute("page") Page page,String memo,Long categoryId,String item, ModelAndView view){
        try {
            List<Tcategory> tcategories=Tcategory.findAllTcategorys();
            StringBuilder sb=new StringBuilder("Tpainting_solrsummary_t:(");
            if(StringUtils.isNotBlank(memo)){
                sb.append("+(").append(memo).append(")");
            }
            if(categoryId!=null){
                sb.append(" +").append(categoryId);
            }
            if(StringUtils.isNotBlank(item)){
                sb.append(" +").append(item);
            }
            sb.append(")");
            SolrQuery query=new SolrQuery(sb.toString().toLowerCase());
            query.setStart(page.getPageIndex()*page.getMaxResult()).setRows(page.getMaxResult());
            QueryResponse response=Tpainting.search(query);
            SolrDocumentList results=response.getResults();
            if(results==null){
                page.setList(null);
            }
            Long totalResult=results.getNumFound();
            page.setTotalResult(totalResult.intValue());
            page.setList(Tpainting.getList(results));
            view.addObject("tcategories", tcategories);
            view.addObject("memo", memo);
            view.addObject("categoryId", categoryId);
            view.addObject("item", item);
            view.addObject("page", page);
        } catch (Exception e) {
            logger.error("加载作品信息异常",e);
            view.addObject("errorMsg","获取作品失败");
        }
        view.setViewName("painting/list");
        return view;
    }
    @RequestMapping("/addUI")
    public ModelAndView addUI(ModelAndView view){
        List<Tcategory> tcategories=Tcategory.findAllTcategorys();
        view.addObject("tcategories", tcategories);
        view.setViewName("painting/addUI");
        return view;
    }
    @RequestMapping("/add")
    public ModelAndView add(Tpainting tpainting,String shortImage,ModelAndView view){
        if(StringUtils.isNotBlank(shortImage)){
            tpainting.setShortImage(StringUtil.getImageSrc(shortImage));
        }
        tpainting.persist();
        updateCache(tpainting);//更新缓存
        return this.list(new Page(), view);
    }

    @RequestMapping("/editUI")
    public ModelAndView editUI(@RequestParam("id") Long id, ModelAndView view){
        try {
            List<Tcategory> tcategories=Tcategory.findAllTcategorys();
            Tpainting tpainting=Tpainting.findTpainting(id);
            tpainting.setShortImage(StringUtil.buildImgTag(tpainting.getShortImage()));
            view.addObject("tcategories", tcategories);
            view.addObject("tpainting", tpainting);
        } catch (Exception e) {
            logger.info("editUI出现异常",e);
        }
        view.setViewName("painting/editUI");
        return view;
    }
    @RequestMapping("/edit")
    @Transactional
    public ModelAndView edit(Long id,String state ,String title
                            ,String countries,String author
                            ,String item
                            ,String shortImage
                            ,@RequestParam(value="categoryId",defaultValue = "0")Long categoryId
                            ,String content,ModelAndView view){
        try {
            Tpainting tpainting=Tpainting.findTpainting(id);
            if(StringUtils.isNotBlank(shortImage)){
                tpainting.setShortImage(StringUtil.getImageSrc(shortImage));
            }
            tpainting.setState(state);
            tpainting.setTitle(title);
            tpainting.setAuthor(author);
            tpainting.setCountries(countries);
            tpainting.setItem(item);
            tpainting.setCategoryId(categoryId);
            tpainting.setContent(content);
            tpainting.merge();
            updateCache(tpainting);
        } catch (Exception e) {
            logger.error("作品编辑保存失败",e);
            view.addObject("errorMsg","保存失败");
        }
        return this.list(new Page(), view);
    }

    @RequestMapping(value = "/top", method = RequestMethod.POST)
    public @ResponseBody
    ResponseData top(@RequestParam("id") Long id) {
        logger.info("painting/top");
        String errormsg = "置顶成功";
        ResponseData data = new ResponseData();
        try{
            Tpainting tpainting = Tpainting.findTpainting(id);
            tpainting.setSort(new Date());
            tpainting.merge();
            data.setErrorCode("0");
            updateCache(tpainting);
        }catch(Exception e){
            data.setErrorCode("4");
            logger.error("painting/top");
            errormsg="置顶失败";
        }
        data.setValue(errormsg);
        return data;
    }

    /**
     * 推荐至首页
     * @param id
     * @return
     */
    @RequestMapping(value = "/recommend", method = RequestMethod.POST)
    public @ResponseBody
    ResponseData recommend(@RequestParam("id") Long id) {
        logger.info("painting/recommend");
        String errormsg = "推荐成功";
        ResponseData data = new ResponseData();
        try{
            Tpainting tpainting = Tpainting.findTpainting(id);
            List<Object> param= new ArrayList<Object>();
            param.add(tpainting.getId());
            param.add("0");
            List<TimageConfig> timageConfigs=TimageConfig.findList(" where o.tpaintingId=? and o.type=? "," ",param);
            if(timageConfigs==null||timageConfigs.isEmpty()){
                TimageConfig timageConfig=new TimageConfig();
                timageConfig.setTpaintingId(tpainting.getId());
                timageConfig.setUrl(tpainting.getShortImage());
                timageConfig.setType("0");
                timageConfig.setState("1");
                timageConfig.persist();
                data.setErrorCode("0");
            }else{
                data.setErrorCode("2");
                errormsg="首页已经存在该作品";
            }
        }catch(Exception e){
            data.setErrorCode("4");
            logger.error("painting/recommend",e);
            errormsg="推荐失败";
        }
        data.setValue(errormsg);
        return data;
    }

    /**
     * 排序上升一次
     */
    @RequestMapping(value = "/floating", method = RequestMethod.GET)
    @Transactional
    public  ModelAndView floating(@RequestParam("id") Long id,ModelAndView view){
        logger.info("painting/floating");
        try{
            Tpainting tpainting = Tpainting.findTpainting(id);
            Tpainting abovePainting=tpainting.findAbovePainting();
            if(abovePainting==null){
                return this.list(new Page(), view);
            }
            Date selfSort=tpainting.getSort();
            Date aboveSort=abovePainting.getSort();
            //和上一个交换位置
            tpainting.setSort(aboveSort);
            abovePainting.setSort(selfSort);
            tpainting.merge();
            abovePainting.merge();
            updateCache(tpainting);
            updateCache(abovePainting);
        }catch(Exception e){
            logger.error("上升失败",e);
            view.addObject("errorMsg","上升失败");
        }
        return this.list(new Page(), view);
    }


    private void updateCache(Tpainting tpainting){
        String versionKey="painting_version";
        Integer version=cacheService.get(versionKey);
        if(version==null){
            version=0;
        }
        cacheService.set(versionKey,version+1);
        String keyOne=StringUtil.join("_", "client", "painting",String.valueOf(tpainting.getId()));
        cacheService.delete(keyOne);
    }



}
