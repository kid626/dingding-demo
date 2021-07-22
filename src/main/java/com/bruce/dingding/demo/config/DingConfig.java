package com.bruce.dingding.demo.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @Copyright Copyright © 2021 fanzh . All rights reserved.
 * @Desc
 * @ProjectName dingding-demo
 * @Date 2021/7/21 18:06
 * @Author fzh
 */
@Component
@Data
@ConfigurationProperties(prefix = "demo.ding")
public class DingConfig {

    private String appKey;
    private String appSecret;
    private String corpId;
    private Long agentId;

}
