package com.hrdate.oj.utils;

import org.springframework.http.HttpHeaders;

/**
 * @author jieguangzhi
 * @date 2021-09-18
 */
public class HeaderUtil {

    /**
     * 获取http header
     *
     * @param token 用户的token
     * @return header
     */
    public static HttpHeaders getHttpHeader(String token, Long groupId) {
        // 请求头
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");
        headers.set("Authorization", token);
        return headers;
    }

    public static HttpHeaders getHttpHeader() {
        // 请求头
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");
        return headers;
    }
}
