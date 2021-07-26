package com.bruce.dingding.demo.service;

import com.bruce.dingding.demo.model.UserModel;

/**
 * @Copyright Copyright © 2021 fanzh . All rights reserved.
 * @Desc
 * @ProjectName dingding-demo
 * @Date 2021/7/21 18:09
 * @Author fzh
 */
public interface UserService {

    /**
     * code 鉴权
     *
     * @param code 鉴权码
     * @return UserModel
     */
    UserModel authCode(String code);

    /**
     * 获取 access_token
     *
     * @return access_token
     */
    String getAccessToken();

    /**
     * 扫码登录鉴权
     *
     * @param code 扫码登录鉴权码
     * @return UserModel
     */
    UserModel authCodeByQrCode(String code);

    /**
     * 获取 jsapi 票据
     *
     * @return ticket
     */
    String getJsapiToken();

}
