package com.hackerbetter.artist.service;

import com.hackerbetter.artist.protocol.ClientInfo;
import org.apache.camel.Produce;
import org.apache.camel.ProducerTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by hacker on 2014/8/6.
 */
@Service
public class JmsService {

    private Logger logger= LoggerFactory.getLogger(JmsService.class);

    @Produce(uri = "jms:topic:registerSuccess", context = "camelContext")
    private ProducerTemplate registerSuccessTemplate;

    @Produce(uri = "jms:topic:loginSuccess", context = "camelContext")
    private ProducerTemplate loginSuccessTemplate;

    @Async
    public void registerSuccessJms(String userno,ClientInfo clientInfo) {
        Map<String, Object> body = new HashMap<String, Object>();
        body.put("userno", userno);
        body.put("imei", clientInfo.getImei());
        body.put("mac", clientInfo.getMac());
        body.put("username", clientInfo.getUsername());
        body.put("platform", clientInfo.getPlatform());
        body.put("machine", clientInfo.getMachineId());
        body.put("softwareversion", clientInfo.getSoftwareVersion());
        body.put("channel", clientInfo.getCoopId());
        logger.info("registerSuccessTemplate start,bodys:{}", body);
        registerSuccessTemplate.sendBody(body);
    }

    /**
     * 登录成功的Jms
     * @param clientInfo
     */
    @Async
    public void loginSuccessJms(String userno,ClientInfo clientInfo) {
        Map<String, Object> body = new HashMap<String, Object>();
        body.put("userno", userno);
        body.put("username", clientInfo.getUsername());
        body.put("imei", clientInfo.getImei());
        body.put("platform", clientInfo.getPlatform());
        body.put("type", clientInfo.getType());
        body.put("machineId", clientInfo.getMachineId());
        body.put("softwareversion", clientInfo.getSoftwareVersion());
        body.put("channel", clientInfo.getCoopId());
        body.put("mac", clientInfo.getMac());

        logger.info("loginSuccessTemplate start, bodys:{}", body);
        loginSuccessTemplate.sendBody(body);
    }
}
