package com.bruce.dingding.demo.model;

import lombok.Data;

/**
 * @Copyright Copyright © 2021 fanzh . All rights reserved.
 * @Desc 封装通用用户模型
 * @ProjectName dingding-demo
 * @Date 2021/7/22 9:36
 * @Author fzh
 */
@Data
public class UserModel {

    /**
     * 钉钉/浙政钉 内用户 id
     */
    private String id;

    /**
     * 用户手机号，注意浙政钉获取不到手机号
     */
    private String phone;

    /**
     * 用户姓名
     */
    private String name;

    /**
     * 用户所属组织名称
     */
    private String orgName;

}
