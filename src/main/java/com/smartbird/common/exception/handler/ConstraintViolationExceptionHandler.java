package com.smartbird.common.exception.handler;

import com.smartbird.common.response.HopeResponse;
import com.smartbird.common.response.ResponseResult;

import javax.validation.ConstraintViolationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class ConstraintViolationExceptionHandler implements ExceptionMapper<ConstraintViolationException> {
    @Override
    public Response toResponse(ConstraintViolationException e) {
        ResponseResult responseResult = HopeResponse.makeErrRsp("参数不正确", HopeResponse.buildInfos(e));
        return HopeResponse.build(Response.Status.BAD_REQUEST, responseResult);
    }
}
