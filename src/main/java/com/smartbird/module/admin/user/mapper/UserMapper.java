package com.smartbird.module.admin.user.mapper;

import com.smartbird.db.entity.UserEntity;
import com.smartbird.module.admin.user.param.AddUserParam;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * UserEntity转换工具
 */
@Mapper
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    /**
     * 转换addUserParam为UserEntity数据
     *
     * @param addUserParam
     * @return
     */
    UserEntity toUser(AddUserParam addUserParam);
}
