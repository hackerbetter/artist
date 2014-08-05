package com.hackerbetter.artist.handler;

import com.hackerbetter.artist.protocol.ArtistServerInterfaceHandler;
import org.springframework.stereotype.Service;
import com.hackerbetter.artist.cache.InfoCacheService;
import com.hackerbetter.artist.protocol.ClientInfo;
import com.hackerbetter.artist.util.Response;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by hacker on 2014/5/15.
 */
@Service("category")
public class CategoryHandler implements ArtistServerInterfaceHandler {

    @Autowired
    private InfoCacheService infoCacheService;
    @Override
    public String execute(ClientInfo clientInfo) {
        return Response.success("查询成功",infoCacheService.getAllTcategory());
    }
}
