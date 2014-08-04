package com.hackerbetter.artist.dto;

import com.hackerbetter.artist.domain.Tpainting;
import org.apache.commons.beanutils.BeanUtils;
import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.map.ObjectMapper;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.json.RooJson;

import java.io.IOException;
import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

/**
 * Created by hacker on 2014/8/4.
 */
@RooJavaBean
@RooJson
public class PaintingDto  implements Serializable {
    private static Logger logger= LoggerFactory.getLogger(PaintingDto.class);
    private Long  id;
    private String title;
    private String author;
    private String countries;
    private String item;//栏目
    private String shortImage;
    private String content;
    @JsonIgnore
    private String state="1"; //0 停用 1启用
    private Date createtime=new Date();
    private long categoryId;
    @JsonIgnore
    private Date sort=new Date();
    private Long supportNum;
    private String isSupport;
    private List<ImgDto> imgs=new ArrayList<ImgDto>();


    public void add(ImgDto imgDto){
        imgs.add(imgDto);
    }

    /**
     * 将内容中的所有图片标签<img></img> 转换为客户端方便解析的格式
     * 例如将<img width="" heigh="" src=""></img>
         转为
         {
         "ref": "<!--IMG#0-->",
         "pixel": "550*413",
         "alt": "",
         "src": "http://img2.cache.netease.com/auto/2014/5/14/20140514085858a3882.jpg"
         }
     */
    public static PaintingDto parseImg(Tpainting tpainting) {
        PaintingDto paintingDto=new PaintingDto();
        try {
            BeanUtils.copyProperties(paintingDto, tpainting);
            Document doc = Jsoup.parse(tpainting.getContent());
            Elements elements = doc.getElementsByTag("img");
            for (int i = 0, length = elements.size(); i < length; i++) {
                Element element = elements.get(i);
                String ref = "<!--IMG#" + i + "-->";
                String src = element.attr("src");
                String height = element.attr("height");
                String width = element.attr("width");
                String alt = element.attr("alt");
                ImgDto img = new ImgDto(ref, width, height, alt, src);
                paintingDto.add(img);
                element.parent().html(ref);
            }
            paintingDto.setContent(doc.toString());
        } catch (IllegalAccessException e) {
            logger.error(e.getMessage());
        } catch (InvocationTargetException e) {
            logger.error(e.getMessage());
        }
        return paintingDto;
    }

    /**
     * 转换所有图片标签
     * @param list
     */
    public static List<PaintingDto> parseAllImg(List<Tpainting> list){
        List<PaintingDto> paintingDtos=new ArrayList<PaintingDto>();
        for(Tpainting tpainting:list){
            paintingDtos.add(parseImg(tpainting));
        }
        return paintingDtos;
    }

    public static List<PaintingDto> toPaintingDtos(List<Tpainting> list){
        List<PaintingDto> paintingDtos=new ArrayList<PaintingDto>();
        for(Tpainting tpainting:list){
            PaintingDto paintingDto=new PaintingDto();
            try {
                BeanUtils.copyProperties(paintingDto,tpainting);
            } catch (IllegalAccessException e) {
                logger.error(e.getMessage());
            } catch (InvocationTargetException e) {
                logger.error(e.getMessage());
            }
            paintingDtos.add(paintingDto);
        }
        return paintingDtos;
    }

    public String toJson(){
        try {
            ObjectMapper om = new ObjectMapper();
            return om.writeValueAsString(this);
        } catch (IOException e) {
        }
        return "";
    }

    public static String toJsonArray(Collection<PaintingDto> collection){
        try {
            ObjectMapper om = new ObjectMapper();
            return om.writeValueAsString(collection);
        } catch (IOException e) {
        }
        return "";
    }
}
