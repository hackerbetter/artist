package com.hackerbetter.artist.domain;

import com.hackerbetter.artist.dto.ImgDto;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.map.ObjectMapper;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.roo.addon.entity.RooEntity;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.json.RooJson;
import org.springframework.roo.addon.solr.RooSolrSearchable;
import org.springframework.roo.addon.tostring.RooToString;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

/**
 * Created by hacker on 2014/4/29.
 * 绘画作品
 */
@RooJavaBean
@RooJson
@RooToString
@RooSolrSearchable
@RooEntity(versionField="", table="Tpainting", persistenceUnit="persistenceUnit", transactionManager="transactionManager")
public class Tpainting  implements Serializable {
    private static Logger logger= LoggerFactory.getLogger(Tpainting.class);

    @NotNull
    @Column(name="title")
    private String title;

    @Column(name="author",length = 10)
    private String author;

    @Column(name="countries",length = 10)
    private String countries;

    @Column(name="item",length = 20)
    private String item;//栏目

    @Column(name="shortImage",length = 255)
    private String shortImage;

    @Lob
    @NotNull
    @Column(name="content")
    private String content;

    @JsonIgnore
    @NotNull
    @Column(name="state",length = 1)
    private String state="1"; //0 停用 1启用

    @NotNull
    @Column(name="createtime")
    private Date createtime=new Date();

    @Column(name="categoryId")
    private long categoryId;

    @JsonIgnore
    @NotNull
    @Column(name="sort")
    private Date sort=new Date();

    @Transient
    private Long supportNum;
    @Transient
    private String isSupport;
    @Transient
    private List<ImgDto> imgs=new ArrayList<ImgDto>();


    public String toJson(){
        try {
            ObjectMapper om = new ObjectMapper();
            return om.writeValueAsString(this);
        } catch (IOException e) {
        }
        return "";
    }

    public static String toJsonArray(Collection<Tpainting> collection){
        try {
            ObjectMapper om = new ObjectMapper();
            return om.writeValueAsString(collection);
        } catch (IOException e) {
        }
        return "";
    }


    public static List<Tpainting> getList(SolrDocumentList results){
        List<Tpainting> list=new ArrayList<Tpainting>();
        for(SolrDocument doc:results){
            Long id=Long.parseLong(doc.getFieldValue("tpainting.id_l")+"");
            String author=doc.getFieldValue("tpainting.author_s")+"";
            Long categoryId=Long.parseLong(doc.getFieldValue("tpainting.categoryid_l")+"");
            String item=doc.getFieldValue("tpainting.item_s")+"";
            String state=doc.getFieldValue("tpainting.state_s")+"";
            String title=doc.getFieldValue("tpainting.title_s")+"";
            String content=doc.getFieldValue("tpainting.content_s")+"";
            String countries=doc.getFieldValue("tpainting.countries_s")+"";
            Date createTime=(Date)doc.getFieldValue("tpainting.createtime_dt");
            String shortImage=doc.getFieldValue("tpainting.shortimage_s")+"";
            Tpainting t=new Tpainting();
            t.setId(id);
            t.setTitle(title);
            t.setAuthor(author);
            t.setCategoryId(categoryId);
            t.setItem(item);
            t.setState(state);
            t.setShortImage(shortImage);
            t.setContent(content);
            t.setCreatetime(createTime);
            t.setCountries(countries);
            list.add(t);
        }
        return list;
    }

    public static List<Tpainting> findList(String where, String orderby, List<Object> params,Integer pageNow,Integer pageSize) {
        try {
            TypedQuery<Tpainting> q = entityManager().createQuery(
                    "SELECT o FROM Tpainting o " + where + orderby, Tpainting.class);
            if (null != params && !params.isEmpty()) {
                int index = 1;
                for (Object param : params) {
                    q.setParameter(index, param);
                    index = index + 1;
                }
            }
            q.setFirstResult(pageNow * pageSize)
                    .setMaxResults(pageSize);
            return q.getResultList();

        } catch (Exception e) {
            logger.error(e.getMessage());
            return null;
        }
    }

    public static List<Tpainting> findList(String where, String orderby, List<Object> params) {
        TypedQuery<Tpainting> q = entityManager().createQuery(
                "SELECT o FROM Tpainting o " + where + orderby, Tpainting.class);
        if (null != params && !params.isEmpty()) {
            int index = 1;
            for (Object param : params) {
                q.setParameter(index, param);
                index = index + 1;
            }
        }
        return q.getResultList();
    }

    public static List<Tpainting> findList(List<Long> ids){
        return entityManager().createQuery("select o FROM Tpainting o where o.id in (:ids)",Tpainting.class).setParameter("ids",ids).getResultList();
    }

    public static Long count(String where,List<Object> params){
        TypedQuery<Long> totalQ = entityManager().createQuery(
                "select count(o) from Tpainting o " + where, Long.class);
        if (null != params && !params.isEmpty()) {
            int index = 1;
            for (Object param : params) {
                totalQ.setParameter(index, param);
                index = index + 1;
            }
        }
        return totalQ.getSingleResult();
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
    public void parseImg() {
        Document doc = Jsoup.parse(content);
        Elements elements = doc.getElementsByTag("img");
        for (int i = 0, length = elements.size(); i < length; i++) {
            Element element = elements.get(i);
            String ref = "<!--IMG#" + i + "-->";
            String src = element.attr("src");
            String height = element.attr("height");
            String width = element.attr("width");
            String alt = element.attr("alt");
            ImgDto img = new ImgDto(ref, width, height, alt, src);
            imgs.add(img);
            element.parent().html(ref);
        }
        this.content=doc.toString();
    }

    /**
     * 转换所有图片标签
     * @param list
     */
    public static void parseAllImg(List<Tpainting> list){
        for(Tpainting tpainting:list){
            tpainting.parseImg();
        }
    }
}
