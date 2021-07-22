package com.bruce.dingding.demo.model.resp;

import lombok.Data;

import java.io.Serializable;

/**
 * @Copyright Copyright © 2021 fanzh . All rights reserved.
 * @Desc
 * @ProjectName dingding-demo
 * @Date 2021/7/22 11:15
 * @Author fzh
 */
@Data
public class AuthUserResp implements Serializable {


    /**
     * success : true
     * content : {"data":{"accountId":93,"lastName":"洪阳","clientId":"mozi-developer-center","realmId":57,"openid":"6f1a885a4020f3624b71570b74925d7b","realmName":"绣花针测试租户","namespace":"local","nickNameCn":"洪阳","tenantUserId":"57$93","account":"pishi.hy","employeeCode":"pishi.hy"},"responseMessage":"成功","responseCode":"0","success":true}
     */

    private boolean success;
    private ContentBean content;

    @Data
    public static class ContentBean {
        /**
         * data : {"accountId":93,"lastName":"洪阳","clientId":"mozi-developer-center","realmId":57,"openid":"6f1a885a4020f3624b71570b74925d7b","realmName":"绣花针测试租户","namespace":"local","nickNameCn":"洪阳","tenantUserId":"57$93","account":"pishi.hy","employeeCode":"pishi.hy"}
         * responseMessage : 成功
         * responseCode : 0
         * success : true
         */

        private DataBean data;
        private String responseMessage;
        private String responseCode;
        private boolean success;

        @Data
        public static class DataBean {
            /**
             * accountId : 93
             * lastName : 洪阳
             * clientId : mozi-developer-center
             * realmId : 57
             * openid : 6f1a885a4020f3624b71570b74925d7b
             * realmName : 绣花针测试租户
             * namespace : local
             * nickNameCn : 洪阳
             * tenantUserId : 57$93
             * account : pishi.hy
             * employeeCode : pishi.hy
             */

            private int accountId;
            private String lastName;
            private String clientId;
            private int realmId;
            private String openid;
            private String realmName;
            private String namespace;
            private String nickNameCn;
            private String tenantUserId;
            private String account;
            private String employeeCode;

        }
    }
}
