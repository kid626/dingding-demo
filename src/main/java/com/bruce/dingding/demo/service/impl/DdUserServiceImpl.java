package com.bruce.dingding.demo.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.bruce.dingding.demo.config.DingConfig;
import com.bruce.dingding.demo.exception.DdException;
import com.bruce.dingding.demo.model.UserModel;
import com.bruce.dingding.demo.service.UserService;
import com.bruce.dingding.service.DingBaseService;
import com.bruce.dingding.service.DingUserService;
import com.bruce.dingding.service.impl.DingBaseServiceImpl;
import com.bruce.dingding.service.impl.DingUserServiceImpl;
import com.dingtalk.api.response.OapiSnsGetuserinfoBycodeResponse;
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

    private final DingUserService dingUserService;
    private final DingBaseService dingBaseService;

    public DdUserServiceImpl(DingConfig config) {
        dingUserService = new DingUserServiceImpl(config.getAgentId(), config.getAppKey(), config.getAppSecret());
        dingBaseService = new DingBaseServiceImpl(config.getAgentId(), config.getAppKey(), config.getAppSecret());
    }

    @Override
    public UserModel authCode(String code) {
        log.info("钉钉鉴权,code={}", code);
        try {
            String accessToken = getAccessToken();
            OapiUserGetuserinfoResponse response = dingBaseService.getUserInfo(code, accessToken);
            log.info("钉钉获取用户id返回:response={}", JSONObject.toJSONString(response));
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
            return dingBaseService.getAccessToken();
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
            OapiSnsGetuserinfoBycodeResponse response = dingBaseService.getUserInfoByCode(code);
            log.info("钉钉扫码鉴权返回,response={}", JSONObject.toJSONString(response));
            String unionId = response.getUserInfo().getUnionid();
            String accessToken = getAccessToken();
            String userId = getUserIdByUnionId(accessToken, unionId);
            return getByUserId(accessToken, userId);
        } catch (Exception e) {
            log.info("钉钉获取用户信息出错", e);
            throw new DdException(e);
        }
    }

    @Override
    public String getJsapiToken() {
        return dingBaseService.getJsapiToken(getAccessToken());
    }

    /**
     * 根据 userId 换取用户信息
     */
    private UserModel getByUserId(String accessToken, String userId) {
        try {
            OapiV2UserGetResponse.UserGetResponse rsp = dingUserService.getByUserId(userId, accessToken);
            log.info("钉钉获取用户详情返回:rsp={}", JSONObject.toJSONString(rsp));
            UserModel model = new UserModel();
            model.setId(userId);
            model.setName(rsp.getName());
            model.setPhone(rsp.getMobile());
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
            return dingUserService.getByUnionId(unionId, accessToken);
        } catch (Exception e) {
            log.info("钉钉获取用户信息出错", e);
            throw new DdException(e);
        }

    }
}
