package com.hackerbetter.artist.handler;

import com.hackerbetter.artist.protocol.ArtistServerInterfaceHandler;
import com.hackerbetter.artist.protocol.ClientInfo;
import com.hackerbetter.artist.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 图片相关请求
 * Created by hacker on 2014/5/5.
 */
@Service("image")
public class ImageHandler implements ArtistServerInterfaceHandler {
    @Autowired
    private ImageService imageService;

    public String execute(ClientInfo clientInfo) {
        String imageType = clientInfo.getImageType().replace(" ",""); //图片类型 0 首页图片 1 轮播图片
        return imageService.queryImage(imageType);
    }
}
