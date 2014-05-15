package com.hackerbetter.artist.handler;

import com.hackerbetter.artist.protocol.ClientInfo;
import com.hackerbetter.artist.protocol.LotserverInterfaceHandler;
import com.hackerbetter.artist.service.ImageService;
import com.hackerbetter.artist.util.Response;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.hackerbetter.artist.util.Response.paramError;

/**
 * 图片相关请求
 * Created by hacker on 2014/5/5.
 */
@Service("image")
public class ImageHandler implements LotserverInterfaceHandler {
    @Autowired
    private ImageService imageService;

    public String execute(ClientInfo clientInfo) {
        String imageType = clientInfo.getImageType().replace(" ",""); //图片类型 0 首页图片 1 轮播图片
        if(StringUtils.equals(imageType,"0")){
            return imageService.queryHomeImage();
        }else if(StringUtils.equals(imageType,"1")){
            return imageService.queryHeadImage();
        }

        return paramError(clientInfo.getImei());
    }
}
