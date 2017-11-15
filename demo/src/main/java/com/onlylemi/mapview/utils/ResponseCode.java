package com.onlylemi.mapview.utils;

/**
 * Created by admin on 2017/11/14.
 */

public class ResponseCode {
    public static final int RESPONSE_SUCCESS=200; //请求成功,请求信息在响应中
    public static final int RESPONSE_ACCEPTED=300;//请求已被接受,需要做进一步处理
    public static final int RESPONSE_UNAUTHORIZED=400; //权限验证失败
    public static final int RESPONSE_FAIL=500; //失败
}
