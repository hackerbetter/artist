package com.hackerbetter.artist.cache;

import com.hackerbetter.artist.domain.Tcategory;
import com.hackerbetter.artist.domain.TimageConfig;
import com.hackerbetter.artist.domain.Tpainting;
import com.hackerbetter.artist.util.common.StringUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


/**
 * 信息缓存
 * @author Administrator
 *
 */
@Service
public class InfoCacheService {

	@Autowired
	private CacheService cacheService;

    public List<Tcategory> getAllTcategory(){
        String key=StringUtil.join("_", "client", "category");
        String result=cacheService.get(key);
        List<Tcategory> list=null;
        if(StringUtils.isBlank(result)){
            List<Object> param=new ArrayList<Object>();
            param.add(1);
            list=Tcategory.findList(" where o.state=? "," order by id asc",param);
            cacheService.set(key,Tcategory.toJsonArray(list));
        }else{
            list= (List<Tcategory>) Tcategory.fromJsonArrayToTcategorys(result);
        }
        return list;
    }


	/**
	 * 查询图片
	 * @param type 图片类型 0 首页图片 1 轮播图片
	 * @return
	 */
	public List<TimageConfig> getTimageConfigsByType(String type) {
		String key = StringUtil.join("_", "client", "image", type);
        String cacheJsons=cacheService.get(key);
        List<TimageConfig> timageConfigs =null;
        if(StringUtils.isNotBlank(cacheJsons)){
            timageConfigs =(List<TimageConfig>) TimageConfig.fromJsonArrayToTimageConfigs(cacheJsons);
        }else{
            List<Object> param=new ArrayList<Object>();
            param.add(type);
            param.add("1");
            timageConfigs = TimageConfig.findList(" where o.type=? and o.state=? "," order by sort desc ",param);
            cacheService.set(key, TimageConfig.toJsonArray(timageConfigs));
        }
		return timageConfigs;
	}

    public List<Tpainting> getPaintingsByItem(String item,Integer pageNow,Integer pageSize){
        String versionKey="painting_version";
        Integer version=cacheService.get(versionKey);
        String key=StringUtil.join("_", "client", "painting", item, pageNow + "", pageSize + "", version==null?"0":version+"");
        String tpaintings=cacheService.get(key);
        List<Tpainting> list=null;
        String where=" where o.item= ?";
        List<Object> param=new ArrayList<Object>();
        param.add(item);
        if(StringUtils.isBlank(tpaintings)){
            list=Tpainting.findList(where, " order by o.sort desc", param, pageNow, pageSize);
            cacheService.set(key,Tpainting.toJsonArray(list));
        }else{
            list= (List<Tpainting>) Tpainting.fromJsonArrayToTpaintings(tpaintings);
        }
        return list;
    }

    public Tpainting getTpainting(String paintingId){
        String key= StringUtil.join("_", "client", "painting", paintingId);
        String cache=cacheService.get(key);
        Tpainting tpainting=null;
        if(StringUtils.isBlank(cache)){
            tpainting=Tpainting.findTpainting(Long.parseLong(paintingId));
            cacheService.set(key,tpainting.toJson());
        }else{
            tpainting=Tpainting.fromJsonToTpainting(cache);
        }
        return tpainting;
    }

}
