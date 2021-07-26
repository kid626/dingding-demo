package com.bruce.dingding.demo.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.xxpt.gateway.shared.client.http.ExecutableClient;
import com.alibaba.xxpt.gateway.shared.client.http.PostClient;
import com.bruce.dingding.demo.config.ZzdConfig;
import com.bruce.dingding.demo.exception.DdException;
import com.bruce.dingding.demo.model.UserModel;
import com.bruce.dingding.demo.model.resp.AuthUserResp;
import com.bruce.dingding.demo.model.resp.GetTokenResp;
import com.bruce.dingding.demo.model.resp.JsapiTokenResp;
import com.bruce.dingding.demo.service.UserService;
import lombok.extern.slf4j.Slf4j;

/**
 * @Copyright Copyright © 2021 fanzh . All rights reserved.
 * @Desc
 * @ProjectName dingding-demo
 * @Date 2021/7/22 10:03
 * @Author fzh
 */
@Slf4j
public class ZzdUserServiceImpl implements UserService {

    private static final String GET_TOKEN_URL = "/gettoken.json";
    private static final String GET_USER_INFO_URL = "/rpc/oauth2/dingtalk_app_user.json";
    private static final String GET_TOKEN_BY_QRCODE_URL = "/rpc/oauth2/getuserinfo_bycode.json";
    private static final String GET_JSAPI_TOKEN_URL = "/get_jsapi_token.json";


    private final ZzdConfig config;

    public ZzdUserServiceImpl(ZzdConfig config) {
        this.config = config;
    }

    @Override
    public UserModel authCode(String code) {
        log.info("浙政钉鉴权,code={}", code);
        try {
            ExecutableClient executableClient = ExecutableClient.getInstance();
            executableClient.setAccessKey(config.getAppKey());
            executableClient.setSecretKey(config.getAppSecret());
            executableClient.setDomainName(config.getDomainName());
            executableClient.setProtocal("https");
            executableClient.init();
            PostClient postClient = executableClient.newPostClient(GET_USER_INFO_URL);
            postClient.addParameter("access_token", getAccessToken());
            postClient.addParameter("auth_code", code);
            String apiResult = postClient.post();
            log.info("浙政钉获取用户详情返回:response={}", apiResult);
            AuthUserResp response = JSONObject.parseObject(apiResult, AuthUserResp.class);
            if (!response.isSuccess()) {
                throw new DdException(response.getContent().getResponseMessage());
            }
            if (!response.getContent().isSuccess()) {
                throw new DdException(response.getContent().getResponseMessage());
            }
            AuthUserResp.ContentBean.DataBean userInfo = response.getContent().getData();
            UserModel model = new UserModel();
            model.setName(userInfo.getLastName());
            model.setId(String.valueOf(userInfo.getAccountId()));
            model.setOrgName(userInfo.getRealmName());
            return model;
        } catch (Exception e) {
            log.info("浙政钉获取用户信息出错", e);
            throw new DdException(e);
        }
    }

    @Override
    public String getAccessToken() {
        log.info("浙政钉获取access_token");
        try {
            ExecutableClient executableClient = ExecutableClient.getInstance();
            executableClient.setAccessKey(config.getAppKey());
            executableClient.setSecretKey(config.getAppSecret());
            executableClient.setDomainName(config.getDomainName());
            executableClient.setProtocal("https");
            executableClient.init();
            PostClient postClient = executableClient.newPostClient(GET_TOKEN_URL);
            //设置参数
            postClient.addParameter("appkey", config.getAppKey());
            postClient.addParameter("appsecret", config.getAppSecret());
            //调用API
            String apiResult = postClient.post();
            GetTokenResp response = JSONObject.parseObject(apiResult, GetTokenResp.class);
            log.info("浙政钉获取access_token返回,response={}", response);
            if (!response.isSuccess()) {
                throw new DdException(response.getContent().getResponseMessage());
            }
            if (!response.getContent().isSuccess()) {
                throw new DdException(response.getContent().getResponseMessage());
            }
            return response.getContent().getData().getAccessToken();
        } catch (Exception e) {
            log.info("浙政钉获取access_token出错", e);
            return "";
        }
    }

    /**
     * https://login.dg-work.cn/oauth2/auth.htm?response_type=code&client_id=应用标识&redirect_uri=回调地址&scope=get_user_info&authType=QRCODE
     */
    @Override
    public UserModel authCodeByQrCode(String code) {
        log.info("浙政钉扫码鉴权,code={}", code);
        try {
            ExecutableClient executableClient = ExecutableClient.getInstance();
            executableClient.setAccessKey(config.getAppKey());
            executableClient.setSecretKey(config.getAppSecret());
            executableClient.setDomainName(config.getDomainName());
            executableClient.setProtocal("https");
            executableClient.init();
            PostClient postClient = executableClient.newPostClient(GET_TOKEN_BY_QRCODE_URL);
            postClient.addParameter("access_token", getAccessToken());
            postClient.addParameter("code", code);
            String apiResult = postClient.post();
            log.info("浙政钉扫码鉴权返回:response={}", apiResult);
            AuthUserResp response = JSONObject.parseObject(apiResult, AuthUserResp.class);
            if (!response.isSuccess()) {
                throw new DdException(response.getContent().getResponseMessage());
            }
            if (!response.getContent().isSuccess()) {
                throw new DdException(response.getContent().getResponseMessage());
            }
            AuthUserResp.ContentBean.DataBean userInfo = response.getContent().getData();
            UserModel model = new UserModel();
            model.setName(userInfo.getLastName());
            model.setId(String.valueOf(userInfo.getAccountId()));
            model.setOrgName(userInfo.getRealmName());
            return model;
        } catch (Exception e) {
            log.info("浙政钉扫码鉴权出错", e);
            throw new DdException(e);
        }
    }

    @Override
    public String getJsapiToken() {
        log.info("获取 jsapi 票据");
        try {
            ExecutableClient executableClient = ExecutableClient.getInstance();
            executableClient.setAccessKey(config.getAppKey());
            executableClient.setSecretKey(config.getAppSecret());
            executableClient.setDomainName(config.getDomainName());
            executableClient.setProtocal("https");
            executableClient.init();
            PostClient postClient = executableClient.newPostClient(GET_JSAPI_TOKEN_URL);
            postClient.addParameter("accessToken", getAccessToken());
            String apiResult = postClient.post();
            log.info("获取 jsapi token :{}", apiResult);
            JsapiTokenResp result = JSONObject.parseObject(apiResult, JsapiTokenResp.class);
            if (!result.isSuccess()) {
                throw new DdException(result.getContent().getResponseMessage());
            }
            if (!result.getContent().isSuccess()) {
                throw new DdException(result.getContent().getResponseMessage());
            }
            return result.getContent().getData().getAccessToken();
        } catch (Exception e) {
            log.warn("获取 jsapi 票据 出错", e);
            return "";
        }
    }
}
