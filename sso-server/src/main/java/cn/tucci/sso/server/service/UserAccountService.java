package cn.tucci.sso.server.service;

import cn.tucci.sso.server.model.domain.UserAccount;
import cn.tucci.sso.server.model.dto.UserAccountDTO;

/**
 * @author tucci.lee
 */
public interface UserAccountService {
    /**
     * 脱敏正则表达式
     */
    String EMAIL_DESENSITIZATION = "^(\\w).*(@.*)$";
    String PHONE_DESENSITIZATION = "^(\\d{3})\\d.*(\\d{4})$";
    /**
     * 脱敏格式
     */
    String DESENSITIZATION_FORMAT = "$1****$2";

    /**
     * 根据邮箱注册账号
     *
     * @param account 注册账号信息
     */
    void signupByEmail(UserAccount account);

    /**
     * 根据手机注册账号
     *
     * @param account 注册账号信息
     */
    void signupByPhone(UserAccount account);

    /**
     * 获取账号信息
     *
     * @param uid uid
     * @return Account
     */
    UserAccount getByUid(Long uid);

    /**
     * 查询脱敏的账号信息
     *
     * @param uid uid
     * @return UserAccountDTO
     */
    UserAccountDTO getDesensitizationByUid(Long uid);

    /**
     * 根据邮箱查询账号信息
     *
     * @param email 邮箱
     * @return 账号信息
     */
    UserAccount getByEmail(String email);

    /**
     * 根据手机查询账号信息
     *
     * @param phone 手机
     * @return 账号信息
     */
    UserAccount getByPhone(String phone);

    /**
     * 修改密码
     *
     * @param uid      uid
     * @param password 新密码
     */
    void editPassword(Long uid, String password);

    /**
     * 修改邮箱
     *
     * @param uid   uid
     * @param email 新邮箱
     */
    void editEmail(Long uid, String email);

    /**
     * 修改手机
     *
     * @param uid   uid
     * @param phone 新手机
     */
    void editPhone(Long uid, String phone);

    /**
     * 根据账号/邮箱/手机查询用户信息
     *
     * @param account 账号/邮箱/手机
     * @return UserAccount
     */
    UserAccount getByAccount(String account);
}
