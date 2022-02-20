package com.example.imageserver;

import lombok.Data;

@Data
public class BaseResponse {

    private Object data;
    private int code = 0;

    public static BaseResponse  ok(Object data) {
        BaseResponse baseResponse = new BaseResponse();
        baseResponse.data = data;
        return baseResponse;
    }

    public static BaseResponse  ok() {
        BaseResponse baseResponse = new BaseResponse();
        baseResponse.data = "ok";
        return baseResponse;
    }

    public static BaseResponse  error() {
        BaseResponse baseResponse = new BaseResponse();
        baseResponse.code = 50008;
        return baseResponse;
    }


}
