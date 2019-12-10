package com.smartbird.module.admin.user.resource;

import com.smartbird.common.param.HopeBaseParam;
import com.smartbird.common.response.HopePage;
import com.smartbird.common.response.HopeResponse;
import com.smartbird.common.response.ResponseResult;
import com.smartbird.db.entity.UserEntity;
import com.smartbird.db.repository.UserRepository;
import com.smartbird.module.admin.user.param.AddUserParam;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@ApplicationScoped
@Path("/admin/user")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class UserResource {
    @Inject
    UserRepository userRepository;


    @GET
    @Path("/{userCode}")
    public UserEntity detail(@PathParam("userCode") String userCode) {
        return userRepository.getByCode(userCode);
    }

    @POST
    @Path("/list")
    public ResponseResult<HopePage<UserEntity>> userList(@Valid HopeBaseParam hopeBaseParam) {
        return HopeResponse.makeOkRsp(userRepository.userList(hopeBaseParam));
    }


    @POST
    @Path("")
    public UserEntity create(@Valid AddUserParam addUserParam) {
        return userRepository.create(addUserParam);
    }
}
