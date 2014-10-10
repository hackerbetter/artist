package com.hackerbetter.artist.service;


import com.codahale.metrics.annotation.Timed;
import com.hackerbetter.artist.cache.InfoCacheService;
import com.hackerbetter.artist.consts.ErrorCode;
import com.hackerbetter.artist.consts.FavoriteType;
import com.hackerbetter.artist.domain.Tfavorite;
import com.hackerbetter.artist.domain.Tmessage;
import com.hackerbetter.artist.domain.Tpainting;
import com.hackerbetter.artist.dto.Page;
import com.hackerbetter.artist.protocol.ClientInfo;
import com.hackerbetter.artist.util.JsonUtil;
import com.hackerbetter.artist.util.Response;
import com.hackerbetter.artist.util.common.StringUtil;
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

    @Autowired
    private CoreService coreService;

    private Logger logger= LoggerFactory.getLogger(PaintingService.class);

    @Timed(name="查询指定栏目作品")
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
                String hasMore=totalPage-1>pageNow?"true":"false";
                String result= success("查询成功", list);
                return JsonUtil.add(result,"hasMore",hasMore);
            }
            return fail(ErrorCode.NoRecord);
        } catch (Exception e) {
            logger.error("查询{}栏目作品异常",item,e);
            return fail("系统繁忙");
        }
    }

    @Timed(name="查询作品详情")
    public String queryDetail(ClientInfo clientInfo) {
        String paintingId=clientInfo.getPaintingId();//作品id
        String userno= clientInfo.getUserno();//用户编号
        if(StringUtils.isBlank(paintingId)){
            return fail(ErrorCode.PaintingEmpty);
        }
        try {
            Tpainting painting=infoCacheService.getTpainting(paintingId);
            if(painting!=null){
                Long supportNum= infoCacheService.querySupportNumByTypeAndPaintingId(painting.getId());
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
    @Timed(name="作品点赞")
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
            String userValid=coreService.validateUserByUserno(userno);
            if(StringUtils.isNotBlank(userValid)){
                return userValid;
            }
            Tfavorite.operate(paintingId, userno,FavoriteType.STAR);
            infoCacheService.deleteSupportNumCache(paintingId);
            return success("成功");
        } catch (Exception e) {
            logger.error(e.getMessage());
            return fail("系统繁忙");
        }
    }
    @Transactional
    @Timed(name="作品收藏")
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
            String userValid=coreService.validateUserByUserno(userno);
            if(StringUtils.isNotBlank(userValid)){
                return userValid;
            }
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
    @Timed(name="作品关键字查询")
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

    @Timed(name="评论")
    public String comment(ClientInfo clientInfo){
        try {
            String nickname=clientInfo.getNickname();//昵称
            String message=clientInfo.getContent();//评论内容
            String paintingId=clientInfo.getPaintingId();//作品id
            String replyTo=clientInfo.getReplyTo();//要回复的评论Id
            String imei=clientInfo.getImei();
            if(StringUtil.isEmpty(nickname,message,paintingId)){
                return paramError(imei);
            }
            Tmessage tmessage=new Tmessage();
            tmessage.setContent(message);
            tmessage.setPaintingId(Long.parseLong(paintingId));
            if(StringUtils.isNotBlank(replyTo)){
               Tmessage reply= Tmessage.findTmessage(Long.parseLong(replyTo));
               tmessage.setReplyTo(reply);
            }
            tmessage.setAuthor(nickname);
            tmessage.persist();
        } catch (Exception e) {
            logger.error("评论异常",e);
            return Response.fail("系统繁忙");
        }
        return Response.success("评论成功");
    }

    @Timed(name="查询评论")
    public String queryComment(ClientInfo clientInfo){
        try {
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
            String paintingId=clientInfo.getPaintingId();//作品id
            String imei=clientInfo.getImei();
            if(StringUtils.isBlank(paintingId)){
                return Response.paramError(imei);
            }
            List<Tmessage> list=Tmessage.findAllByPaintingId(Long.parseLong(paintingId),pageNow,pageSize);
            long count=Tmessage.count(Long.parseLong(paintingId));
            long totalPage= count/pageSize+(count%pageSize>0?1:0);
            if(list!=null&&!list.isEmpty()){
                String hasMore=totalPage-1>pageNow?"true":"false";
                String result= success("查询成功", list);
                return JsonUtil.add(result,"hasMore",hasMore);
            }
            return fail(ErrorCode.NoRecord);
        } catch (NumberFormatException e) {
            logger.error("查询评论异常",e);
            return fail("系统繁忙");
        }
    }
}
