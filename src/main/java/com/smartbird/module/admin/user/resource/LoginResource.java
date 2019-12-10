package com.smartbird.module.admin.user.resource;


import com.smartbird.common.response.HopeResponse;
import com.smartbird.common.response.ResponseResult;
import com.smartbird.util.JwtUtil;

import javax.enterprise.context.RequestScoped;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.HashMap;
import java.util.Map;

@RequestScoped
@Path("/admin/login")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class LoginResource {


    @POST
    @Path("")
    public ResponseResult<Object> login() {
        try {
            Map<String, Object> map = new HashMap<>();
            map.put("name", "逍遥客");
            return HopeResponse.makeOkRsp(JwtUtil.generateTokenString(60, map));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return HopeResponse.makeErrRsp("出错了");
    }

    @GET
    @Path("/verify")
    public Object verify() {
        String toke = "eyJraWQiOiIxMjAyNDY5NDE1NTY2NDk5ODQwIiwiYWxnIjoiUlMyNTYifQ.eyJpc3MiOiJJc3N1ZXIiLCJhdWQiOiJBdWRpZW5jZSIsImV4cCI6MTU3NTUyOTYyNCwianRpIjoibG4tdUlLNDh1UndnSlZLVXZMRkF0dyIsImlhdCI6MTU3NTUyNjAyNCwibmJmIjoxNTc1NTI1OTA0LCJzdWIiOiJzdWJqZWN0IiwibmFtZSI6IumAjemBpeWuoiJ9.f9Mxtm74EQQEi06ck896bndLmT2Yy6FcpnN-0PqQdSO_LKrnTO98iHXkCCWHaGHUUG5VqNcsrsdqJ3nXWrOibFuyU1Z2hVi__8avSyzSxk0uM3iZxoAz90vG4H7_IiPwk0F_bGF1peHRBK_e-5NX469ktzHAeUf4zph1fXGzrY8l7ANnEEU2-w0djnNtWeLXcjr1u6TfSrD3yh2EqLTUksplroEkww6jekU-xKIh8VktFpc5mc_wrxavDg12h1aiuzojkL7fX0EE86429XtG6qu-J1hel-aI3WNPjvRPhCgj2erAVvCf-EWBJemESE5Xh0ThCaGszv6vD6rURiRzYw";
        try {
            System.out.println(JwtUtil.verify(toke));
        } catch (Exception e) {
            e.printStackTrace();
        }
        throw new IllegalStateException("看看怎么样");
    }
}
