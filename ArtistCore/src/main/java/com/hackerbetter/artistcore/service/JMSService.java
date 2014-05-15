package com.hackerbetter.artistcore.service;

import org.apache.camel.Produce;
import org.apache.camel.ProducerTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * 登录
 * @author zhanghuan
 *
 */
@Service
public class JMSService {

	private Logger logger = LoggerFactory.getLogger(JMSService.class);
	
	@Produce(uri = "jms:topic:loginSuccess", context = "coreCamelContext")
	private ProducerTemplate loginSuccessTemplate;
	
	@Produce(uri = "jms:topic:registerSuccess", context = "coreCamelContext")
	private ProducerTemplate registerSuccessTemplate;
}
