// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package com.hackerbetter.artist.domain;

import com.hackerbetter.artist.dto.ImgDto;
import java.lang.Long;
import java.lang.String;
import java.util.Date;
import java.util.List;

privileged aspect Tpainting_Roo_JavaBean {
    
    public String Tpainting.getTitle() {
        return this.title;
    }
    
    public void Tpainting.setTitle(String title) {
        this.title = title;
    }
    
    public String Tpainting.getAuthor() {
        return this.author;
    }
    
    public void Tpainting.setAuthor(String author) {
        this.author = author;
    }
    
    public String Tpainting.getCountries() {
        return this.countries;
    }
    
    public void Tpainting.setCountries(String countries) {
        this.countries = countries;
    }
    
    public String Tpainting.getItem() {
        return this.item;
    }
    
    public void Tpainting.setItem(String item) {
        this.item = item;
    }
    
    public String Tpainting.getShortImage() {
        return this.shortImage;
    }
    
    public void Tpainting.setShortImage(String shortImage) {
        this.shortImage = shortImage;
    }
    
    public String Tpainting.getContent() {
        return this.content;
    }
    
    public void Tpainting.setContent(String content) {
        this.content = content;
    }
    
    public String Tpainting.getState() {
        return this.state;
    }
    
    public void Tpainting.setState(String state) {
        this.state = state;
    }
    
    public Date Tpainting.getCreatetime() {
        return this.createtime;
    }
    
    public void Tpainting.setCreatetime(Date createtime) {
        this.createtime = createtime;
    }
    
    public long Tpainting.getCategoryId() {
        return this.categoryId;
    }
    
    public void Tpainting.setCategoryId(long categoryId) {
        this.categoryId = categoryId;
    }
    
    public Date Tpainting.getSort() {
        return this.sort;
    }
    
    public void Tpainting.setSort(Date sort) {
        this.sort = sort;
    }
    
    public Long Tpainting.getSupportNum() {
        return this.supportNum;
    }
    
    public void Tpainting.setSupportNum(Long supportNum) {
        this.supportNum = supportNum;
    }
    
    public String Tpainting.getIsSupport() {
        return this.isSupport;
    }
    
    public void Tpainting.setIsSupport(String isSupport) {
        this.isSupport = isSupport;
    }
    
    public List<ImgDto> Tpainting.getImgs() {
        return this.imgs;
    }
    
    public void Tpainting.setImgs(List<ImgDto> imgs) {
        this.imgs = imgs;
    }
    
}
