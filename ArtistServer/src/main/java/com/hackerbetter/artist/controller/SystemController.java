package com.hackerbetter.artist.controller;

import com.hackerbetter.artist.consts.ErrorCode;
import com.hackerbetter.artist.util.Response;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import com.hackerbetter.artist.cache.CacheService;

/**
 * 系统Controller
 * @author Administrator
 *
 */
@RequestMapping("/system")
@Controller
public class SystemController {

	private Logger logger = LoggerFactory.getLogger(SystemController.class);
	
	@Autowired
	private CacheService cacheService;
	
	/**
	 * 刷新缓存
	 * @return
	 */
	@RequestMapping(value = "/flushCache", method = RequestMethod.GET)
	public @ResponseBody
    Response flushCache() {
		JSONObject responseJson = new JSONObject();
		logger.info("刷新缓存 start");
		try {
			cacheService.flushAll();
            return new Response(ErrorCode.OK);
		} catch (Exception e) {
			logger.error("刷新缓存发生异常", e);
            return new Response(ErrorCode.ERROR);
		}
	}
	
}
