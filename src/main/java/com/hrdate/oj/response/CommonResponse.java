package com.hrdate.oj.response;

import java.util.Map;

/**
 * 公共的返回类
 *
 **/
public class CommonResponse<T> {

    /**
     * 1表示成功，非1表示失败
     **/
    private Integer code;

    /**
     * 成功或失败的信息，成功返回"succeed"，一般失败返回"failed" ,其他失败根据具体业务场景返回信息
     **/
    private String message;

    /**
     * 返回的结果数据
     **/
    private T resultData;

    /**
     * 信息相关的数据，一般都是失败信息相关的数据
     **/
    private Map<String, String> messageData;

    /**
     * 构造方法
     **/
    private CommonResponse() {
    }

    /**
     * 包含参数的构造方法
     **/
    private CommonResponse(int code, String message, T t, Map<String, String> messageData) {
        this.code = code;
        this.message = message;
        this.resultData = t;
        this.messageData = messageData;
    }


    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getResultData() {
        return resultData;
    }

    public void setResultData(T resultData) {
        this.resultData = resultData;
    }

    public static CommonResponse failedResult() {
        return failedResult("failed");
    }

    public static CommonResponse failedResult(String msg) {
        return failedResult(msg, -1);
    }

    /**
     * 泛型的公共的失败响应信息
     *
     * @param message 信息
     * @return 包含失败信息的公共响应类
     **/
    public static <T> CommonResponse<T> failedResultWithGenerics(String message) {
        return new CommonResponse<>(-1, message, null, null);
    }

    /**
     * 泛型的公共的失败响应信息
     *
     * @param message 错误信息
     * @param code    错误代码
     * @return 包含失败信息的公共响应类
     **/
    public static <T> CommonResponse<T> failedResultWithGenerics(String message, int code) {
        return new CommonResponse<>(code, message, null, null);
    }

    public static CommonResponse failedResult(String msg, int code) {
        return failedResult(msg, code, null);
    }

    public static CommonResponse failedResult(String msg, int code, Map<String, String> messageData) {
        return resultTemplate(null, msg, code, messageData);
    }

    public static CommonResponse failedResult(String msg, int code, Object rst , Map<String, String> messageData) {
        return resultTemplate(rst, msg, code, messageData);
    }

    public static CommonResponse succeedResult() {
        return succeedResult("succeed");
    }

    public static CommonResponse succeedResult(String msg) {
        return succeedResult(msg, 1);
    }

    public static CommonResponse succeedResult(String msg, int code) {
        return succeedResult(null, msg, code);
    }

    public static CommonResponse succeedResult(Object rst) {
        return succeedResult(rst, "succeed", 1);
    }

    /**
     * 泛型构造的返回成功的数据
     *
     * @param t 传递的数据的泛型
     * @return 含有数据的公共响应信息
     **/
    public static <T> CommonResponse<T> succeedResultWithGenerics(T t) {
        return new CommonResponse<>(1, "success", t, null);
    }

    public static <T> CommonResponse<T> succeedResultWithGenerics(T t, String message) {
        return new CommonResponse<>(1, message, t, null);
    }

    public static CommonResponse succeedResult(Object rst, String msg) {
        return succeedResult(rst, msg, 1);
    }

    public static CommonResponse succeedResult(Object rst, String msg, int code) {
        return resultTemplate(rst, msg, code, null);
    }

    public static CommonResponse resultTemplate(Object rst, String msg, int code, Map<String, String> messageData) {
        CommonResponse res = new CommonResponse();
        res.setCode(code);
        res.setResultData(rst);
        res.setMessage(msg);
        res.setMessageData(messageData);
        return res;
    }


    public Map<String, String> getMessageData() {
        return messageData;
    }

    public void setMessageData(Map<String, String> messageData) {
        this.messageData = messageData;
    }


    @Override
    public String toString() {
        return "CommonResponse{" +
                "code=" + code +
                ", message='" + message + '\'' +
                ", resultData=" + resultData +
                ", messageData=" + messageData +
                '}';
    }
}
