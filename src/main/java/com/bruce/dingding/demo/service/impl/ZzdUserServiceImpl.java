package com.bruce.dingding.demo.service.impl;

import com.bruce.dingding.demo.config.ZzdConfig;
import com.bruce.dingding.demo.exception.DdException;
import com.bruce.dingding.demo.model.UserModel;
import com.bruce.dingding.demo.service.UserService;
import com.bruce.zwdd.model.base.AuthUserResp;
import com.bruce.zwdd.service.ZwDingBaseService;
import com.bruce.zwdd.service.ZwDingUserService;
import com.bruce.zwdd.service.impl.ZwDingBaseServiceImpl;
import com.bruce.zwdd.service.impl.ZwDingUserServiceImpl;
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

    private final ZwDingUserService zzdUserService;
    private final ZwDingBaseService zzdBaseService;

    public ZzdUserServiceImpl(ZzdConfig config) {
        this.zzdUserService = new ZwDingUserServiceImpl(config.getAppKey(), config.getAppSecret(), config.getDomainName(), config.getProtocol(), config.getTenantId());
        this.zzdBaseService = new ZwDingBaseServiceImpl(config.getAppKey(), config.getAppSecret(), config.getDomainName(), config.getProtocol(), config.getTenantId());
    }

    @Override
    public UserModel authCode(String code) {
        log.info("浙政钉鉴权,code={}", code);
        try {
            AuthUserResp.ContentBean.DataBean userInfo = zzdUserService.getUserByCode(code);
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
            return zzdBaseService.getAccessToken();
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
            AuthUserResp.ContentBean.DataBean userInfo = zzdUserService.getUserByOauth2Code(code);
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
            return zzdBaseService.getJsapiToken();
        } catch (Exception e) {
            log.warn("获取 jsapi 票据 出错", e);
            return "";
        }
    }
}
