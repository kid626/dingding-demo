package com.bruce.dingding.demo.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.bruce.dingding.demo.config.DingConfig;
import com.bruce.dingding.demo.exception.DdException;
import com.bruce.dingding.demo.model.UserModel;
import com.bruce.dingding.demo.service.UserService;
import com.dingtalk.api.DefaultDingTalkClient;
import com.dingtalk.api.DingTalkClient;
import com.dingtalk.api.request.OapiGettokenRequest;
import com.dingtalk.api.request.OapiUserGetuserinfoRequest;
import com.dingtalk.api.request.OapiV2UserGetRequest;
import com.dingtalk.api.response.OapiGettokenResponse;
import com.dingtalk.api.response.OapiUserGetuserinfoResponse;
import com.dingtalk.api.response.OapiV2UserGetResponse;
import lombok.extern.slf4j.Slf4j;

/**
 * @Copyright Copyright © 2021 fanzh . All rights reserved.
 * @Desc
 * @ProjectName dingding-demo
 * @Date 2021/7/21 18:09
 * @Author fzh
 */
@Slf4j
public class DdUserServiceImpl implements UserService {

    private static final String GET_TOKEN_URL = "https://oapi.dingtalk.com/gettoken";
    private static final String GET_USER_INFO_URL = "https://oapi.dingtalk.com/user/getuserinfo";
    private static final String GET_USER_DETAIL_URL = "https://oapi.dingtalk.com/topapi/v2/user/get";

    private final DingConfig config;

    public DdUserServiceImpl(DingConfig config) {
        this.config = config;
    }

    @Override
    public UserModel authCode(String code) {
        log.info("钉钉鉴权,code={}", code);
        try {
            String accessToken = getAccessToken();
            DingTalkClient client = new DefaultDingTalkClient(GET_USER_INFO_URL);
            OapiUserGetuserinfoRequest req = new OapiUserGetuserinfoRequest();
            req.setCode(code);
            req.setHttpMethod("GET");
            OapiUserGetuserinfoResponse response = client.execute(req, accessToken);
            log.info("钉钉获取用户id返回:response={}", JSONObject.toJSONString(response));
            if (!response.isSuccess()) {
                throw new Exception(response.getErrmsg());
            }
            String userId = response.getUserid();
            client = new DefaultDingTalkClient(GET_USER_DETAIL_URL);
            OapiV2UserGetRequest userDetailReq = new OapiV2UserGetRequest();
            userDetailReq.setUserid(userId);
            userDetailReq.setLanguage("zh_CN");
            OapiV2UserGetResponse rsp = client.execute(userDetailReq, accessToken);
            log.info("钉钉获取用户详情返回:rsp={}", JSONObject.toJSONString(rsp));
            if (!rsp.isSuccess()) {
                throw new Exception(rsp.getErrmsg());
            }
            UserModel model = new UserModel();
            model.setId(userId);
            model.setName(rsp.getResult().getName());
            model.setPhone(rsp.getResult().getMobile());
            return model;
        } catch (Exception e) {
            log.info("钉钉获取用户信息出错", e);
            throw new DdException(e);
        }
    }

    @Override
    public String getAccessToken() {
        log.info("钉钉获取access_token");
        try {
            DingTalkClient client = new DefaultDingTalkClient(GET_TOKEN_URL);
            OapiGettokenRequest request = new OapiGettokenRequest();
            request.setAppkey(config.getAppKey());
            request.setAppsecret(config.getAppSecret());
            request.setHttpMethod("GET");
            OapiGettokenResponse response = client.execute(request);
            log.info("钉钉获取access_token返回,response={}", JSONObject.toJSONString(response));
            if (!response.isSuccess()) {
                throw new Exception(response.getErrmsg());
            }
            return response.getAccessToken();
        } catch (Exception e) {
            log.info("钉钉获取access_token出错", e);
            return "";
        }

    }
}
