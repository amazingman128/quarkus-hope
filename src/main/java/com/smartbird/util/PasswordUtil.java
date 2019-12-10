package com.smartbird.util;

import cn.hutool.crypto.SecureUtil;

/**
 * 密码工具类
 * <p>
 * 通过加密算法md5(sha1(md5()))生成密码 ，密码对比
 */
public class PasswordUtil {
    /**
     * 生成加密后的密码
     *
     * @param password 加密前密码
     * @return
     */
    public static String generatePassword(String password) {
        StringBuilder passwordResult = new StringBuilder(SecureUtil.sha1(SecureUtil.sha1(SecureUtil.md5(password))));
        passwordResult = passwordResult.reverse();
        return passwordResult.substring(0, passwordResult.length() - 4);
    }

    /**
     * 对比密码是否输入正确
     *
     * @param inputPassword 输入密码
     * @param passwordInDb  加密后存储在数据库中的密码
     * @return
     */
    public static boolean comparePassword(String inputPassword, String passwordInDb) {
        return passwordInDb.equals(generatePassword(inputPassword));
    }
}
