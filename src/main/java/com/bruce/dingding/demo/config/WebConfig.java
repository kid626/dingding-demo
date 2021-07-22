package com.bruce.dingding.demo.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @Copyright Copyright © 2021 fanzh . All rights reserved.
 * @Desc
 * @ProjectName dingding-demo
 * @Date 2021/7/21 17:54
 * @Author fzh
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/zzd/*").setViewName("zzd/index.html");
        registry.addViewController("/zzd").setViewName("zzd/index.html");
        registry.addViewController("/dd/*").setViewName("dd/index.html");
        registry.addViewController("/dd").setViewName("dd/index.html");
    }
}
