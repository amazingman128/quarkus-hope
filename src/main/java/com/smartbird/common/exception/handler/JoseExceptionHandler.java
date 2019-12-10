package com.smartbird.common.exception.handler;

import com.smartbird.common.response.HopeResponse;
import org.jose4j.lang.JoseException;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class JoseExceptionHandler implements ExceptionMapper<JoseException> {
    @Override
    public Response toResponse(JoseException e) {
        return HopeResponse.build(Response.Status.BAD_REQUEST, HopeResponse.makeErrRsp(e.getMessage()));
    }
}
