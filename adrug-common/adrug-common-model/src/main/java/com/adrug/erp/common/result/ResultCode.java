package com.adrug.erp.common.result;

/**
 * 统一返回码。
 */
public enum ResultCode {

    SUCCESS(0, "成功"),
    BAD_REQUEST(400, "参数错误"),
    UNAUTHORIZED(401, "未认证"),
    FORBIDDEN(403, "无权限"),
    NOT_FOUND(404, "数据不存在"),
    SERVER_ERROR(500, "系统异常");

    private final int code;

    private final String message;

    ResultCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
