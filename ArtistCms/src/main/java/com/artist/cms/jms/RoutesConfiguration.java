package com.artist.cms.jms;

import org.apache.camel.CamelContext;
import org.apache.camel.builder.RouteBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

@Service
public class RoutesConfiguration {
	
	private Logger logger = LoggerFactory.getLogger(RoutesConfiguration.class);

	@Resource(name="camelContext")
	private CamelContext camelContext;
	
	@PostConstruct
	public void init() throws Exception{
		logger.info("init camel routes");
		camelContext.addRoutes(new RouteBuilder() {
			@Override
			public void configure() throws Exception {
				deadLetterChannel("jms:queue:dead").maximumRedeliveries(-1).redeliveryDelay(3000);
                from("jms:queue:VirtualTopicConsumers.artistCms.registerSuccess?concurrentConsumers=20").to("bean:registerSuccessListener?method=process").routeId("注册成功通知");
                from("jms:queue:VirtualTopicConsumers.artistCms.loginSuccess?concurrentConsumers=20").to("bean:loginSuccessListener?method=process").routeId("登录成功通知");
                from("jms:queue:VirtualTopicConsumers.artistCms.recordUserInfo?concurrentConsumers=20").to("bean:recordUserInfoListener?method=process").routeId("记录用户信息通知");
			}
		});
	}
}
