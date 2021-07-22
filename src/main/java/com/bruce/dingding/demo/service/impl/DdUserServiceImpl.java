package com.bruce.dingding.demo.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.bruce.dingding.demo.config.DingConfig;
import com.bruce.dingding.demo.exception.DdException;
import com.bruce.dingding.demo.model.UserModel;
import com.bruce.dingding.demo.service.UserService;
import com.dingtalk.api.DefaultDingTalkClient;
import com.dingtalk.api.DingTalkClient;
import com.dingtalk.api.request.*;
import com.dingtalk.api.response.*;
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
    private static final String GET_TOKEN_BY_QRCODE_URL = "https://oapi.dingtalk.com/sns/getuserinfo_bycode";
    private static final String GET_USER_INFO_URL = "https://oapi.dingtalk.com/user/getuserinfo";
    private static final String GET_USER_DETAIL_URL = "https://oapi.dingtalk.com/topapi/v2/user/get";
    private static final String GET_USER_ID_BY_UNION_ID_URL = "https://oapi.dingtalk.com/topapi/user/getbyunionid";

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
                throw new DdException(response.getErrmsg());
            }
            String userId = response.getUserid();
            return getByUserId(accessToken, userId);
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
                throw new DdException(response.getErrmsg());
            }
            return response.getAccessToken();
        } catch (Exception e) {
            log.info("钉钉获取access_token出错", e);
            return "";
        }

    }

    /**
     * https://oapi.dingtalk.com/connect/qrconnect?appid=APPID&response_type=code&scope=snsapi_login&state=STATE&redirect_uri=REDIRECT_URI
     */
    @Override
    public UserModel authCodeByQrCode(String code) {
        log.info("钉钉扫码鉴权，code={}", code);
        try {
            // 通过临时授权码获取授权用户的个人信息
            DefaultDingTalkClient client = new DefaultDingTalkClient(GET_TOKEN_BY_QRCODE_URL);
            OapiSnsGetuserinfoBycodeRequest req = new OapiSnsGetuserinfoBycodeRequest();
            // 通过扫描二维码，跳转指定的redirect_uri后，向url中追加的code临时授权码
            req.setTmpAuthCode(code);
            OapiSnsGetuserinfoBycodeResponse response = client.execute(req, config.getAppKey(), config.getAppSecret());
            log.info("钉钉扫码鉴权返回,response={}", JSONObject.toJSONString(response));
            if (!response.isSuccess()) {
                throw new DdException(response.getErrmsg());
            }
            String unionId = response.getUserInfo().getUnionid();
            String accessToken = getAccessToken();
            String userId = getUserIdByUnionId(accessToken, unionId);
            return getByUserId(accessToken, userId);
        } catch (Exception e) {
            log.info("钉钉获取用户信息出错", e);
            throw new DdException(e);
        }
    }

    /**
     * 根据 userId 换取用户信息
     */
    private UserModel getByUserId(String accessToken, String userId) {
        try {
            DefaultDingTalkClient client = new DefaultDingTalkClient(GET_USER_DETAIL_URL);
            OapiV2UserGetRequest userDetailReq = new OapiV2UserGetRequest();
            userDetailReq.setUserid(userId);
            userDetailReq.setLanguage("zh_CN");
            OapiV2UserGetResponse rsp = client.execute(userDetailReq, accessToken);
            log.info("钉钉获取用户详情返回:rsp={}", JSONObject.toJSONString(rsp));
            if (!rsp.isSuccess()) {
                throw new DdException(rsp.getErrmsg());
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

    /**
     * 根据unionId 换取 userId
     */
    private String getUserIdByUnionId(String accessToken, String unionId) {
        try {
            DingTalkClient client = new DefaultDingTalkClient(GET_USER_ID_BY_UNION_ID_URL);
            OapiUserGetbyunionidRequest req = new OapiUserGetbyunionidRequest();
            req.setUnionid(unionId);
            OapiUserGetbyunionidResponse response = client.execute(req, accessToken);
            log.info("钉钉根据unionId获取userId返回:response={}", JSONObject.toJSONString(response));
            if (!response.isSuccess()) {
                throw new DdException(response.getErrmsg());
            }
            return response.getResult().getUserid();
        } catch (Exception e) {
            log.info("钉钉获取用户信息出错", e);
            throw new DdException(e);
        }

    }
}
