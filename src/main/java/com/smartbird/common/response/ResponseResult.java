package com.smartbird.common.response;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * @param <T>
 */
public class ResponseResult<T> implements Serializable {

    /**
     * 状态吗
     */
    public int code;
    /**
     * 相应信息
     */
    private String msg;

    /**
     * 相应数据
     */
    private T data;


    /**
     * 错误提示信息集合
     */
    public List<String> infos;

    /**
     * 设置状态吗
     *
     * @param code
     * @return
     */
    public ResponseResult<T> setCode(ResponseCode code) {
        this.code = code.code;
        return this;
    }

    /**
     * 获取状态码
     *
     * @return
     */
    public int getCode() {
        return code;
    }

    /**
     * 设置编码
     *
     * @param code
     * @return
     */
    public ResponseResult<T> setCode(int code) {
        this.code = code;
        return this;
    }

    /**
     * 设置响应信息
     *
     * @param msg
     * @return
     */
    public ResponseResult<T> setMsg(String msg) {
        this.msg = msg;
        return this;
    }

    /**
     * 获取响应信息
     *
     * @return
     */
    public String getMsg() {
        return msg;
    }

    /**
     * 设置响应数据
     *
     * @param data
     * @return
     */
    public ResponseResult<T> setData(T data) {
        this.data = data;
        return this;
    }

    /**
     * 获取响应数据
     *
     * @return
     */
    public T getData() {
        return data;
    }

    /**
     * 获取响应错误信息集合
     *
     * @return
     */
    public List<String> getInfos() {
        return infos;
    }

    /**
     * 设置响应错误信息集合
     *
     * @param infos
     * @return
     */
    public ResponseResult<T> setInfos(List<String> infos) {
        this.infos = infos;
        return this;
    }
}
