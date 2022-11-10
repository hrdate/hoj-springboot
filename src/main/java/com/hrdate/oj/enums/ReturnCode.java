package com.hrdate.oj.enums;


import com.hrdate.oj.utils.IReturnCode;
import lombok.Getter;

/**
 * 自定义错误异常信息
 *
 * @author yuboliang
 * @date 2021/5/20
 */
@Getter
public enum ReturnCode implements IReturnCode {
    /**
     * 成功
     */
    SUCCESS(1, "操作成功"),

    SEE_OTHER(303, "See Other"),
    PARAM_ERROR(400, "请求参数错误"),
    UNAUTHORIZED(401, "Unauthorized未登陆"),
    FORBIDDEN(403, "FORBIDDEN拒绝"),
    NOT_FOUND(404, "资源不存在"),
    NOT_SUPPORTED(405, "Method Not SUPPORTED"),
    MEDIA_TYPE_NOT_SUPPORTED(415, "Unsupported Media Type"),
    SERVER_ERROR(500, "内部服务器错误"),

    SYSTEM_ERROR(1001, "系统错误"),
    DB_ERROR(1002, "数据库访问异常"),
    REPEATED_REQ(1003, "请勿重复请求"),
    RPC_ERROR(1004, "RPC 调用异常"),
    BIZ_ERROR(1005, "业务异常");

    /**
     * 响应码
     */
    private final int code;

    /**
     * 响应描述
     */
    private final String msg;

    ReturnCode(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    @Override
    public int getCode() {
        return code;
    }

    @Override
    public String getMsg() {
        return msg;
    }

    public static ReturnCode getReturnCode(String msg) {
        ReturnCode[] values = ReturnCode.values();
        for (ReturnCode value : values) {
            if (value.msg.equals(msg)) {
                return value;
            }
        }
        return null;
    }
}
