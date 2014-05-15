package com.hackerbetter.artist.service;


import com.hackerbetter.artist.cache.InfoCacheService;
import com.hackerbetter.artist.consts.ErrorCode;
import com.hackerbetter.artist.consts.FavoriteType;
import com.hackerbetter.artist.domain.Tfavorite;
import com.hackerbetter.artist.domain.Tpainting;
import com.hackerbetter.artist.protocol.ClientInfo;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
        if(StringUtils.isBlank(item)){
            return paramError(clientInfo.getImei());
        }
        try {
            List<Tpainting> paintings= infoCacheService.getPaintingsByItem(item);
            if(paintings!=null&&!paintings.isEmpty()){
                for(Tpainting tpainting:paintings){
                    Long supportNum= Tfavorite.querySupportNumByTypeAndPaintingId(tpainting.getId());
                    tpainting.setSupportNum(supportNum);
                }
                return success("查询成功", paintings);
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

}
