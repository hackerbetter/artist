package com.artist.cms.controller;

import java.math.BigDecimal;
import java.util.List;
import javax.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class IndexController {

	//private Logger logger = Logger.getLogger(IndexController.class);
	
	@RequestMapping(value = "/frame/{id}")
	public String header(@PathVariable("id") String id) {
		return id;
	}

}
