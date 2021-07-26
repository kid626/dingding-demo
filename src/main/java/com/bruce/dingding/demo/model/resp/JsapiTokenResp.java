package com.bruce.dingding.demo.model.resp;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @Copyright Copyright © 2021 fanzh . All rights reserved.
 * @Desc
 * @ProjectName dingding-demo
 * @Date 2021/7/26 9:53
 * @Author fzh
 */
@NoArgsConstructor
@Data
public class JsapiTokenResp implements Serializable {

    /**
     * {
     * "success":true,
     * "content":{
     * "data":{
     * "expiresIn":4561,
     * "accessToken":"jsApi_009dde783756450184e897b9e2943c71"
     * },
     * "success":true,
     * "requestId":"df04428415763320830387621d291f",
     * "responseMessage":"OK",
     * "responseCode":"0"
     * }
     * }
     */

    private boolean success;
    private ContentDTO content;

    @Data
    public static class ContentDTO {

        private DataDTO data;
        private String requestId;
        private String responseMessage;
        private String responseCode;
        private boolean success;

        @Data
        public static class DataDTO {
            private Long expiresIn;
            private String accessToken;
        }
    }
}
