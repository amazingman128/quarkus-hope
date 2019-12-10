package com.smartbird.common.exception.handler;

import com.smartbird.common.response.HopeResponse;
import org.jose4j.jwt.consumer.InvalidJwtException;

import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import java.util.List;
import java.util.Map;

/**
 * JWT过期或者签名异常处理
 */
@Provider
@Produces(MediaType.APPLICATION_JSON)
public class InvalidJwtExceptionHandler implements ExceptionMapper<InvalidJwtException> {
    @Override
    public Response toResponse(InvalidJwtException e) {
        List<Map<String, Object>> infos = HopeResponse.buildInfos(e);
        return HopeResponse.build(Response.Status.BAD_REQUEST, HopeResponse.makeErrRsp("抱歉，处理出错了", infos));
    }
}
