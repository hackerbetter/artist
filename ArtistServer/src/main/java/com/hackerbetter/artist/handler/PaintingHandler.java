package com.hackerbetter.artist.handler;

import com.hackerbetter.artist.protocol.ClientInfo;
import com.hackerbetter.artist.protocol.LotserverInterfaceHandler;
import com.hackerbetter.artist.service.PaintingService;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.hackerbetter.artist.util.Response.paramError;

/**
 * Created by hacker on 2014/5/6.
 */
@Service("painting")
public class PaintingHandler implements LotserverInterfaceHandler {
    private Logger logger = LoggerFactory.getLogger(PaintingHandler.class);
    @Autowired
    private PaintingService paintingService;

    public String execute(ClientInfo clientInfo) {
        String responseString = "";
        String requestType = clientInfo.getRequestType(); //请求类型
        if (StringUtils.equals(requestType, "list")) { //获取指定栏目所有作品
            responseString = paintingService.queryList(clientInfo);
        } else if (StringUtils.equals(requestType, "detail")) { //查询作品详情
            responseString = paintingService.queryDetail(clientInfo);
        } else if (StringUtils.equals(requestType, "support")) { //赞作品
            responseString = paintingService.support(clientInfo);
        } else if (StringUtils.equals(requestType, "collect")) { //收藏作品
            responseString = paintingService.collect(clientInfo);
        } else { //参数错误
            responseString =paramError(clientInfo.getImei());
        }
        return responseString;
    }
}
