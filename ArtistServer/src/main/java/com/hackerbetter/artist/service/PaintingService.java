package com.hackerbetter.artist.service;


import com.hackerbetter.artist.cache.InfoCacheService;
import com.hackerbetter.artist.consts.ErrorCode;
import com.hackerbetter.artist.consts.FavoriteType;
import com.hackerbetter.artist.domain.Tfavorite;
import com.hackerbetter.artist.domain.Tpainting;
import com.hackerbetter.artist.dto.Page;
import com.hackerbetter.artist.protocol.ClientInfo;
import com.hackerbetter.artist.util.JsonUtil;
import org.apache.commons.lang.StringUtils;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocumentList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static com.hackerbetter.artist.util.Response.*;

/**
 * Created by hacker on 2014/5/6.
 */
@Service
public class PaintingService {

    @Autowired
    private InfoCacheService infoCacheService;

    private Logger logger= LoggerFactory.getLogger(PaintingService.class);

    public String queryList(ClientInfo clientInfo) {
        String item=clientInfo.getItem();//栏目
        String pageIndex = clientInfo.getPageindex(); //当前页数
        String maxresult = clientInfo.getMaxresult(); //每页显示的条数
        Integer pageNow=0;
        Integer pageSize=10;
        if (StringUtils.isNotBlank(pageIndex)) {
            pageNow=Integer.parseInt(pageIndex);
        }
        if (StringUtils.isNotBlank(maxresult)) {
            pageSize = Integer.parseInt(maxresult);
        }
        if(StringUtils.isBlank(item)){
            return paramError(clientInfo.getImei());
        }
        try {
            List<Tpainting> list=infoCacheService.getPaintingsByItem(item,pageNow,pageSize);
            long count=infoCacheService.count(item);
            long totalPage= count/pageSize+(count%pageSize>0?1:0);
            if(list!=null&&!list.isEmpty()){
                String hasMore=totalPage>pageNow?"true":"false";
                String result= success("查询成功", list);
                return JsonUtil.add(result,"hasMore",hasMore);
            }
            return fail(ErrorCode.NoRecord);
        } catch (Exception e) {
            logger.error(e.getMessage());
            return fail("系统繁忙");
        }
    }

    public String queryDetail(ClientInfo clientInfo) {
        String paintingId=clientInfo.getPaintingId();//作品id
        String userno= clientInfo.getUserno();//用户编号
        if(StringUtils.isBlank(paintingId)){
            return fail(ErrorCode.PaintingEmpty);
        }
        try {
            Tpainting painting=infoCacheService.getTpainting(paintingId);
            if(painting!=null){
                Long supportNum= Tfavorite.querySupportNumByTypeAndPaintingId(painting.getId());
                painting.setSupportNum(supportNum);
                if(StringUtils.isBlank(userno)){
                    return success("查询成功",painting);
                }
                Tfavorite myfavorite= Tfavorite.findByUsernoAndPaintingId(Long.parseLong(userno),painting.getId(),FavoriteType.STAR);
                if(myfavorite!=null&&myfavorite.getState()==1){
                    painting.setIsSupport("true");
                }else{
                    painting.setIsSupport("false");
                }
                return success("查询成功",painting);
            }
            return fail(ErrorCode.NoRecord);
        } catch (Exception e) {
            logger.error(e.getMessage());
            return fail("系统繁忙");
        }
    }

    @Transactional
    public String support(ClientInfo clientInfo) {
        String paintingId=clientInfo.getPaintingId();//作品id
        String userno= clientInfo.getUserno();//用户编号
        if(StringUtils.isBlank(paintingId)){
            return fail(ErrorCode.PaintingEmpty);
        }
        if(StringUtils.isBlank(userno)){
            return fail(ErrorCode.UserMod_UserNoEmpty);
        }
        try {
            Tfavorite.operate(paintingId, userno,FavoriteType.STAR);
            return success("成功");
        } catch (Exception e) {
            logger.error(e.getMessage());
            return fail("系统繁忙");
        }
    }
    @Transactional
    public String collect(ClientInfo clientInfo) {
        String paintingId=clientInfo.getPaintingId();//作品id
        String userno= clientInfo.getUserno();//用户编号
        if(StringUtils.isBlank(paintingId)){
            return fail(ErrorCode.PaintingEmpty);
        }
        if(StringUtils.isBlank(userno)){
            return fail(ErrorCode.UserMod_UserNoEmpty);
        }
        try {
            Tfavorite.operate(paintingId, userno,FavoriteType.COLLECT);
            return success("成功");
        } catch (Exception e) {
            logger.error(e.getMessage());
            return fail("系统繁忙");
        }
    }

    /**
     * 全文索引
     * @param clientInfo
     * @return
     */
    public String query(ClientInfo clientInfo) {
        try {
            String keyStr=clientInfo.getKeyStr();//关键字
            String pageIndex = clientInfo.getPageindex(); //当前页数
            List<Tpainting> list=new ArrayList<Tpainting>();
            Integer pageNow=0;
            Integer pageSize=10;
            Long totalResult=0L;
            Long totalPage=0L;
            if (StringUtils.isNotBlank(pageIndex)) {
                pageNow=Integer.parseInt(pageIndex);
            }
            String maxresult = clientInfo.getMaxresult(); //每页显示的条数
            if (StringUtils.isNotBlank(maxresult)) {
                pageSize = Integer.parseInt(maxresult);
            }
            StringBuilder sb=new StringBuilder("Tpainting_solrsummary_t:");
            if(StringUtils.isNotBlank(keyStr)){
                sb.append("(").append(keyStr).append(")");
            }
            SolrQuery query=new SolrQuery(sb.toString().toLowerCase());
            query.setStart(pageNow*pageSize).setRows(pageSize);
            QueryResponse response=Tpainting.search(query);
            SolrDocumentList results=response.getResults();
            if(results==null){
                return success("查询成功",new Page<Tpainting>(pageNow,pageSize,totalResult,totalPage,list));
            }
            totalResult=results.getNumFound();
            totalPage=totalResult/pageSize+(totalResult%pageSize>0?1:0);
            list=Tpainting.getList(results);
            Tpainting.parseAllImg(list);
            return success("查询成功",new Page<Tpainting>(pageNow,pageSize,totalResult,totalPage,list));
        } catch (Exception e) {
            logger.error("关键字查询作品异常",e);
            return fail("系统繁忙");
        }
    }
}
