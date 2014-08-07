package com.artist.cms.domain;

import org.springframework.roo.addon.entity.RooEntity;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.json.RooJson;
import org.springframework.roo.addon.tostring.RooToString;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@RooJavaBean
@RooToString
@RooJson
@RooEntity(versionField="", table="tplatform", persistenceUnit="persistenceUnit", transactionManager="transactionManager")
public class Tplatform {
	@Column(name = "name")
	private String name;
}
