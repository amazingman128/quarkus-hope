package com.smartbird.db.repository;

import com.smartbird.common.param.HopeBaseParam;
import com.smartbird.common.response.HopePage;
import com.smartbird.db.entity.UserEntity;
import com.smartbird.module.admin.user.mapper.UserMapper;
import com.smartbird.module.admin.user.param.AddUserParam;
import com.smartbird.util.PasswordUtil;
import com.smartbird.util.SnowFlakeUtil;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import io.quarkus.panache.common.Page;

import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;
import java.sql.Timestamp;
import java.util.List;
import java.util.Objects;

@ApplicationScoped
@Transactional
public class UserRepository implements PanacheRepositoryBase<UserEntity, Long> {

    /**
     * 获取用户列表
     *
     * @param hopeBaseParam
     * @return
     */
    public HopePage<UserEntity> userList(HopeBaseParam hopeBaseParam) {
        PanacheQuery<UserEntity> livingPersons = UserEntity.findAll();
        return HopePage.Builder.build(livingPersons, hopeBaseParam);
    }

    /**
     * 通过用户编号获取用户信息
     *
     * @param userCode
     * @return
     */
    public UserEntity getByCode(String userCode) {
        if (Objects.isNull(userCode)) {
            return null;
        }
        return UserEntity.find("user_code", userCode).firstResult();
    }


    /**
     * 创建用户
     *
     * @param addUserParam
     * @return
     */
    public UserEntity create(AddUserParam addUserParam) {
        UserEntity userEntity = UserMapper.INSTANCE.toUser(addUserParam);
        userEntity.loginPass = PasswordUtil.generatePassword(addUserParam.loginPass);
        userEntity.userCode = SnowFlakeUtil.getDistributedID();
        userEntity.createTime = new Timestamp(System.currentTimeMillis());
        userEntity.updateTime = new Timestamp(System.currentTimeMillis());
        userEntity.persist();
        return userEntity;
    }

}
