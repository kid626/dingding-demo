package com.bruce.dingding.demo.controller;

import com.bruce.dingding.demo.config.DingConfig;
import com.bruce.dingding.demo.config.ZzdConfig;
import com.bruce.dingding.demo.model.UserModel;
import com.bruce.dingding.demo.service.UserService;
import com.bruce.dingding.demo.service.impl.DdUserServiceImpl;
import com.bruce.dingding.demo.service.impl.ZzdUserServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Copyright Copyright © 2021 fanzh . All rights reserved.
 * @Desc
 * @ProjectName dingding-demo
 * @Date 2021/7/21 18:00
 * @Author fzh
 */
@Slf4j
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private DingConfig dingConfig;

    @Autowired
    private ZzdConfig zzdConfig;

    private UserService userService;

    @GetMapping("/dd/authCode/{code}")
    public UserModel ddAuthCode(@PathVariable("code") String code) {
        userService = new DdUserServiceImpl(dingConfig);
        return userService.authCode(code);
    }

    @GetMapping("/zzd/authCode/{code}")
    public UserModel zzdAuthCode(@PathVariable("code") String code) {
        userService = new ZzdUserServiceImpl(zzdConfig);
        return userService.authCode(code);
    }

    @GetMapping("/dd/qr/authCode/{code}")
    public UserModel ddAuthCodeByQrCode(@PathVariable("code") String code) {
        userService = new DdUserServiceImpl(dingConfig);
        return userService.authCodeByQrCode(code);
    }

    @GetMapping("/zzd/qr/authCode/{code}")
    public UserModel zzdAuthCodeByQrCode(@PathVariable("code") String code) {
        userService = new ZzdUserServiceImpl(zzdConfig);
        return userService.authCodeByQrCode(code);
    }

    @GetMapping("/dd/jsapi/token")
    public String ddGetJsapiToken() {
        userService = new DdUserServiceImpl(dingConfig);
        return userService.getJsapiToken();
    }

    @GetMapping("/zzd/jsapi/token")
    public String zzdGetJsapiToken() {
        userService = new ZzdUserServiceImpl(zzdConfig);
        return userService.getJsapiToken();
    }


}
