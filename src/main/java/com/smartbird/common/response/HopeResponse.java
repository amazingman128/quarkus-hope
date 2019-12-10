package com.smartbird.common.response;

import com.google.common.collect.Lists;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.*;

/**
 * 响应工具类
 */
public class HopeResponse {
    private static final String SUCCESS = "success";

    /**
     * 普通正确返回
     *
     * @param <T>
     * @return
     */
    public static <T> ResponseResult<T> makeOkRsp() {
        return new ResponseResult<T>().setCode(ResponseCode.SUCCESS).setMsg(SUCCESS);
    }

    /**
     * 普通正确返回+提示
     *
     * @param <T>
     * @return
     */
    public static <T> ResponseResult<T> makeOkRsp(String msg) {
        return new ResponseResult<T>().setCode(ResponseCode.SUCCESS).setMsg(msg);
    }


    /**
     * 带数据正常返回
     *
     * @param data
     * @param <T>
     * @return
     */
    public static <T> ResponseResult<T> makeOkRsp(T data) {
        return new ResponseResult<T>().setCode(ResponseCode.SUCCESS).setMsg(SUCCESS).setData(data);
    }

    /**
     * 带数据以及提示信息正常返回
     *
     * @param data
     * @param <T>
     * @return
     */
    public static <T> ResponseResult<T> makeOkRsp(T data, String message) {
        if (Objects.isNull(message) || message.length() == 0) {
            message = SUCCESS;
        }
        return new ResponseResult<T>().setCode(ResponseCode.SUCCESS).setMsg(message).setData(data);
    }

    /**
     * 普通错误返回
     *
     * @param msg
     * @param <T>
     * @return
     */
    public static <T> ResponseResult<T> makeErrRsp(String msg) {
        return new ResponseResult<T>().setCode(ResponseCode.FAIL).setMsg(msg);
    }

    /**
     * 带集合错误信息返回
     *
     * @param msg    错误提示
     * @param errors 错误信息集合
     * @param <T>
     * @return
     */
    public static <T> ResponseResult<T> makeErrRsp(String msg, List<String> errors) {
        return new ResponseResult<T>().setCode(ResponseCode.FAIL).setMsg(msg).setInfos(errors);
    }

    /**
     * 设置返回编码和信息
     *
     * @param code 响应码
     * @param msg  响应信息
     * @param <T>
     * @return
     */
    public static <T> ResponseResult<T> makeRsp(int code, String msg) {
        return new ResponseResult<T>().setCode(code).setMsg(msg);
    }

    /**
     * 设置状态码 信息和数据返回
     *
     * @param code 状态吗
     * @param msg  信息
     * @param data 数据
     * @param <T>
     * @return
     */
    public static <T> ResponseResult<T> makeRsp(int code, String msg, T data) {
        return new ResponseResult<T>().setCode(code).setMsg(msg).setData(data);
    }

    /**
     * 设置状态码 信息和数据返回
     *
     * @param code 状态吗
     * @param msg  信息
     * @param data 数据
     * @param <T>
     * @return
     */
    public static <T> ResponseResult<T> makeRsp(ResponseCode code, String msg, T data) {
        return new ResponseResult<T>().setCode(code.code).setMsg(msg).setData(data);
    }

    /**
     * 装换输出为Response
     *
     * @param responseResult
     * @return
     */
    public static Response build(Response.Status status, ResponseResult responseResult) {
        return Response.status(status).type(MediaType.APPLICATION_JSON).entity(responseResult).build();
    }


    public static List<String> buildInfos(Exception e) {
        List<String> list = new ArrayList<>();
        if (e instanceof ConstraintViolationException) {
            for (ConstraintViolation<?> constraintViolation : ((ConstraintViolationException) e).getConstraintViolations()) {
                list.add(constraintViolation.getMessage());
            }
        } else {
            list.add(e.getClass() + " ]__[ " + e.getMessage());
        }
        return list;
    }

}
