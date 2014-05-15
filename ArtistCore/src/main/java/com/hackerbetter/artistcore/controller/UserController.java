package com.hackerbetter.artistcore.controller;

import com.hackerbetter.artistcore.service.TuserinfoService;
import com.hackerbetter.artistcore.api.Response;
import com.hackerbetter.artistcore.constants.ErrorCode;
import com.hackerbetter.artistcore.domain.TuserInfo;
import com.hackerbetter.artistcore.utils.PaySign;
import com.hackerbetter.artistcore.exception.ArtistException;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by hacker on 2014/4/30.
 */
@RequestMapping("/user")
@Controller
public class UserController {
    private Logger logger= LoggerFactory.getLogger(UserController.class);
    @Autowired
    TuserinfoService userinfoService;
    /**
     * 用户注册
     *
     * @param userinfo
     * @param userState
     *            用户状态，默认为"1"
     * @return
     */
    @RequestMapping(method = RequestMethod.POST, value = "/register")
    public @ResponseBody
    Response register(
            @ModelAttribute("userinfo") TuserInfo userinfo,
            @RequestParam(value = "userState", defaultValue = "1", required = false) Integer userState) {

        Response response = new Response();
        ErrorCode result = ErrorCode.OK;
        try {
            userinfo.setState(userState);
            userinfoService.register(userinfo);
            response.setValue(userinfo);
        } catch (ArtistException e) {

            result = e.getErrorCode();
        } catch (Exception e) {

            logger.error("用户注册出错，", e);
            result = ErrorCode.ERROR;
        }

        response.setErrorCode(result.value);
        return response;
    }

    /**
     * 用户修改信息
     *
     * @param uiModel
     * @param request
     * @return
     */
    @RequestMapping(method = RequestMethod.POST, value = "/modify")
    public @ResponseBody
    Response modify(Model uiModel, HttpServletRequest request) {

        // 获取参数
        Map<String, String> params = buildParams(request);
        Response rd = new Response();
        ErrorCode result = ErrorCode.OK;
        TuserInfo userinfo = null;
        try {
            userinfo = userinfoService.modify(params);
            rd.setValue(userinfo);
        } catch (ArtistException e) {
            result = e.getErrorCode();
        } catch (Exception e) {

            logger.error("用户修改信息出错，", e);
            result = ErrorCode.ERROR;
        }

        rd.setErrorCode(result.value);
        return rd;
    }

    @RequestMapping(method = RequestMethod.POST, value = "/resetPassword")
    public @ResponseBody
    Response resetPassword(Model uiModel,
                               @RequestParam("userno") String userno,
                               @RequestParam("password") String password) {
        Response rd = new Response();
        try {
            TuserInfo userinfo = TuserInfo.findTuserInfo(userno);
            userinfo.setPassword(PaySign.EncoderByMd5(password));
            userinfo.merge();
            rd.setErrorCode(ErrorCode.OK.value);
        } catch (Exception e) {
            logger.error("重置用户密码时失败！" + e.getMessage(), e);
            rd.setErrorCode(ErrorCode.ERROR.value);
        }
        return rd;
    }

    @RequestMapping(method = RequestMethod.POST, value = "/resetName")
    public @ResponseBody
    Response resetName(Model uiModel,
                           @RequestParam("userno") String userno,
                           @RequestParam("name") String name) {
        Response rd = new Response();
        logger.info("/resetName userno:{},name:{}",new String[]{userno,name});
        try {
            if (StringUtils.isBlank(userno) || StringUtils.isBlank(name)) {
                throw new ArtistException(ErrorCode.ParametersIsNull);
            }
            TuserInfo userinfo =TuserInfo.findTuserInfo(userno);
            userinfo.setName(name);
            rd.setValue(userinfo.merge());
            rd.setErrorCode(ErrorCode.OK.value);
        } catch (Exception e) {
            logger.error("修改name时失败！" + e.getMessage(), e);
            rd.setErrorCode(ErrorCode.ERROR.value);
        }
        return rd;
    }

    @RequestMapping(method = RequestMethod.POST, value = "/resetNickname")
    public @ResponseBody
    Response resetNickName(Model uiModel, @RequestParam("userno") String userno,
                               @RequestParam("nickname") String nickname) {
        Response rd = new Response();
        logger.info("/nickname userno:{},nickname:{}", new String[] { userno, nickname });
        try {
            if (StringUtils.isBlank(userno) || StringUtils.isBlank(nickname)) {
                throw new ArtistException(ErrorCode.ParametersIsNull);
            }
            TuserInfo userinfo =TuserInfo.findTuserInfo(userno);
            userinfo.setNickname(nickname);
            rd.setValue(userinfo.merge());
            rd.setErrorCode(ErrorCode.OK.value);
        } catch (Exception e) {
            logger.error("修改nickname时失败！" + e.getMessage(), e);
            rd.setErrorCode(ErrorCode.ERROR.value);
        }
        return rd;
    }

    @RequestMapping(method = RequestMethod.POST, value = "/updateState")
    public @ResponseBody
    Response updateState(Model uiModel,
                             @RequestParam("userno") String userno,
                             @RequestParam("state") Integer state) {
        Response rd = new Response();
        try {
            TuserInfo userinfo =TuserInfo.findTuserInfo(userno);
            userinfo.setState(state);
            userinfo.merge();
            rd.setErrorCode(ErrorCode.OK.value);
        } catch (Exception e) {
            logger.error("重置用户状态时失败！" + e.getMessage(), e);
            rd.setErrorCode(ErrorCode.ERROR.value);
        }
        return rd;
    }

    @SuppressWarnings("unchecked")
    private Map<String, String> buildParams(HttpServletRequest request) {

        Map<String, String> params = new HashMap<String, String>();
        Enumeration<String> e = request.getParameterNames();
        while (e.hasMoreElements()) {

            String key = e.nextElement();
            params.put(key, request.getParameter(key));
        }
        return params;
    }

    /**
     * 根据用户信息查找用户
     *
     * @return
     */
    @RequestMapping(method = RequestMethod.POST,value = "/findUserInfo")
    public @ResponseBody
    Response findUserInfo(@ModelAttribute("userinfo") TuserInfo userinfo) {
        logger.info("查找用户");
        Response rd = new Response();
        logger.info("用户信息：{}",userinfo.toString());
        List<TuserInfo> tuserInfos = TuserInfo.findTuserInfos(userinfo);
        if (null != tuserInfos&&!tuserInfos.isEmpty()) {
            rd.setErrorCode(ErrorCode.OK.value);
            rd.setValue(tuserInfos);
        } else {
            rd.setErrorCode(ErrorCode.UserMod_UserNotExists.value);
        }
        return rd;
    }

    /**
     * 根据用户信息查找用户，如果存在多个只返回第一个
     * @return
     */
    @RequestMapping(method = RequestMethod.POST,value = "/findOne")
    public @ResponseBody
    Response findOne(@ModelAttribute("userinfo") TuserInfo userinfo) {
        logger.info("查找用户");
        Response rd = new Response();
        logger.info("用户信息：{}",userinfo.toString());
        List<TuserInfo> tuserInfos = TuserInfo.findTuserInfos(userinfo);
        if (null != tuserInfos&&!tuserInfos.isEmpty()) {
            rd.setErrorCode(ErrorCode.OK.value);
            rd.setValue(tuserInfos.get(0));
        } else {
            rd.setErrorCode(ErrorCode.UserMod_UserNotExists.value);
        }
        return rd;
    }

}
