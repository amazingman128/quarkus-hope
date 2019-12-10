package com.smartbird.common.response;

/**
 * 常用HTTP状态码
 */
public enum ResponseCode {
    /**
     * 成功
     * <p>
     * 服务器已经成功处理了请求。通常，这表示服务器提供了请求的网页。
     */
    SUCCESS(200),

    /**
     * 无内容
     * <p>
     * 服务器成功处理了请求，但没有返回任何内容
     */
    NO_RESULT(204),

    /**
     * 错误请求
     * <p>
     * 服务器不理解请求的语法,如参数错误等
     */
    FAIL(400),

    /**
     * 未授权
     * <p>
     * 请求要求身份验证。对于需要登录的网页，服务器可能返回此响应
     */
    UNAUTHORIZED(401),

    /**
     * 没有权限
     * <p>
     * 无访问权限，服务器拒绝请求
     */
    PERMISSIONDEBY(403),


    /**
     * 服务器错误
     * <p>
     * 服务器内部错误  服务器遇到错误，无法完成请求
     */
    SERVERERROR(500);

    public int code;

    ResponseCode(int code) {
        this.code = code;
    }
}
