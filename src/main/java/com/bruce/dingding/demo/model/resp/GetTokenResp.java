package com.bruce.dingding.demo.model.resp;

import lombok.Data;

/**
 * @Copyright Copyright © 2021 fanzh . All rights reserved.
 * @Desc
 * @ProjectName dingding-demo
 * @Date 2021/7/22 11:15
 * @Author fzh
 */
@Data
public class GetTokenResp {

    /**
     * success : true
     * content : {"data":{"expiresIn":7200,"accessToken":"app_b9501e571cc245fcac66e39645a96f02"},"success":true,"requestId":"ddc4fb8315997917598187761e","responseMessage":"OK","responseCode":"0"}
     */

    private boolean success;
    private ContentBean content;

    @Data
    public static class ContentBean {
        /**
         * data : {"expiresIn":7200,"accessToken":"app_b9501e571cc245fcac66e39645a96f02"}
         * success : true
         * requestId : ddc4fb8315997917598187761e
         * responseMessage : OK
         * responseCode : 0
         */

        private DataBean data;
        private boolean success;
        private String requestId;
        private String responseMessage;
        private String responseCode;

        @Data
        public static class DataBean {
            /**
             * expiresIn : 7200
             * accessToken : app_b9501e571cc245fcac66e39645a96f02
             */

            private int expiresIn;
            private String accessToken;

        }
    }
}
