package com.smartbird.common.exception.handler;

import com.smartbird.common.response.HopeResponse;
import com.smartbird.common.response.ResponseResult;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class IllegalStateExceptionHandler implements ExceptionMapper<IllegalStateException> {
    @Override
    public Response toResponse(IllegalStateException e) {
        ResponseResult responseResult = HopeResponse.makeErrRsp("处理失败", HopeResponse.buildInfos(e));
        return HopeResponse.build(Response.Status.BAD_REQUEST, responseResult);
//        return HopeResponse.build(Response.Status.BAD_REQUEST, HopeResponse.makeErrRsp("处理失败"));
    }
}
