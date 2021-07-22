package com.bruce.dingding.demo.exception;

/**
 * @Copyright Copyright © 2021 fanzh . All rights reserved.
 * @Desc
 * @ProjectName dingding-demo
 * @Date 2021/7/22 10:22
 * @Author fzh
 */
public class DdException extends RuntimeException {

    public DdException() {
        // TODO Auto-generated constructor stub
    }

    public DdException(Throwable msg) {
        super(msg);
    }

    public DdException(String msg) {
        super(msg);
    }

    public DdException(String msg, Throwable e) {
        super(msg, e);
    }
}
