package com.hackerbetter.artist.cache;

import com.hackerbetter.artist.domain.Tcategory;
import com.hackerbetter.artist.domain.TimageConfig;
import com.hackerbetter.artist.domain.Tpainting;
import com.hackerbetter.artist.dto.PaintingDto;
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

    public List<PaintingDto> getPaintingsByItem(String item,Integer pageNow,Integer pageSize){
        String versionKey="painting_version";
        Integer version=cacheService.get(versionKey);
        String key=StringUtil.join("_", "client", "painting", item, pageNow + "", pageSize + "", version==null?"0":version+"");
        String tpaintings=cacheService.get(key);
        String where=" where o.item= ?";
        List<Object> param=new ArrayList<Object>();
        param.add(item);
        if(StringUtils.isBlank(tpaintings)){
            List<Tpainting> list=Tpainting.findList(where, " order by o.sort desc", param, pageNow, pageSize);
            List<PaintingDto> paintingDtos=PaintingDto.parseAllImg(list);
            cacheService.set(key,PaintingDto.toJsonArray(paintingDtos));
            return paintingDtos;
        }else{
            return (List<PaintingDto>) PaintingDto.fromJsonArrayToPaintingDtoes(tpaintings);
        }
    }

    public long count(String item){
        String versionKey="painting_version";
        Integer version=cacheService.get(versionKey);
        String key=StringUtil.join("_", "client", "painting","count", item,version==null?"0":version+"");
        Long count=cacheService.get(key);
        if(count==null||count==0){
            String where=" where o.item= ?";
            List<Object> param=new ArrayList<Object>();
            param.add(item);
            count=Tpainting.count(where,param);
            cacheService.set(key,count);
        }
        return count;
    }

    public PaintingDto getTpainting(String paintingId){
        String key= StringUtil.join("_", "client", "painting", paintingId);
        String cache=cacheService.get(key);
        if(StringUtils.isBlank(cache)){
            Tpainting tpainting=Tpainting.findTpainting(Long.parseLong(paintingId));
            PaintingDto paintingDto=PaintingDto.parseImg(tpainting);
            cacheService.set(key,paintingDto.toJson());
            return paintingDto;
        }else{
           return PaintingDto.fromJsonToPaintingDto(cache);
        }
    }

}
