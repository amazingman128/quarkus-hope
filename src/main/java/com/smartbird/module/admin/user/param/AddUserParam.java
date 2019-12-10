package com.smartbird.module.admin.user.param;

import javax.validation.constraints.NotBlank;

/**
 * 添加用户请求参数
 */
public class AddUserParam {

    /**
     * 登录名称
     */
    @NotBlank(message = "登录名不能为空")
    public String loginName;

    public String nickName;

    @NotBlank(message = "登录密码不能为空")
    public String loginPass;
}
