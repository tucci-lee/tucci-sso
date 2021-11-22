package cn.tucci.sso.server.service;

import cn.tucci.sso.server.core.constant.LockConst;
import cn.tucci.sso.server.core.response.ResultStatus;
import cn.tucci.sso.server.core.support.Authenticator;
import cn.tucci.sso.server.core.utils.Assert;
import cn.tucci.sso.server.model.dao.UserAccountMapper;
import cn.tucci.sso.server.model.domain.UserAccount;
import cn.tucci.sso.server.model.dto.UserAccountDTO;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author tucci.lee
 */
@Service
public class UserAccountService extends ServiceImpl<UserAccountMapper, UserAccount> {
    /**
     * 脱敏正则表达式
     */
    public static final String EMAIL_DESENSITIZATION = "^(\\w).*(@.*)$"; //(^\\w)[^@]*(@.*$)
    public static final String PHONE_DESENSITIZATION = "^(\\d{3})\\d.*(\\d{4})$";
    /**
     * 脱敏格式
     */
    public static final String DESENSITIZATION_FORMAT = "$1****$2";

    private final RedissonClient redissonClient;
    private final UserProfileService userProfileService;
    private final Authenticator authenticator;
    private final UserAccountMapper userAccountMapper;

    public UserAccountService(RedissonClient redissonClient,
                              UserProfileService userProfileService,
                              Authenticator authenticator,
                              UserAccountMapper userAccountMapper) {
        this.redissonClient = redissonClient;
        this.userProfileService = userProfileService;
        this.authenticator = authenticator;
        this.userAccountMapper = userAccountMapper;
    }

    /**
     * 根据 邮箱/手机 注册账号
     * <p>
     * 1. 同步锁：用户名、邮箱/手机
     * 2. 校验用户名是否存在
     * 3. 校验邮箱/手机是否存在
     * 4. 添加用户账号信息
     * 5. 添加用户基础信息
     *
     * @param account 账号信息
     */
    @Transactional
    public void signupByEmail(UserAccount account) {
        RLock usernameLock = redissonClient.getLock(LockConst.USER_ACCOUNT_USERNAME + account.getUsername());
        RLock emailLock = redissonClient.getLock(LockConst.USER_ACCOUNT_EMAIL + account.getEmail());
        RLock lock = redissonClient.getMultiLock(usernameLock, emailLock);
        lock.lock();
        try {
            UserAccount userAccount = userAccountMapper.selectOne(
                    Wrappers.lambdaQuery(UserAccount.class)
                            .eq(UserAccount::getUsername, account.getUsername())
                            .eq(UserAccount::getIsDeleted, Boolean.FALSE)
            );
            Assert.isNull(userAccount, ResultStatus.USERNAME_EXIST);
            userAccount = userAccountMapper.selectOne(
                    Wrappers.lambdaQuery(UserAccount.class)
                            .eq(UserAccount::getEmail, account.getEmail())
                            .eq(UserAccount::getIsDeleted, Boolean.FALSE)
            );
            Assert.isNull(userAccount, ResultStatus.EMAIL_EXIST);
            userAccountMapper.insert(account);
        } finally {
            lock.unlock();
        }

        userProfileService.add(account.getUid());
    }

    @Transactional
    public void signupByPhone(UserAccount account) {
        RLock usernameLock = redissonClient.getLock(LockConst.USER_ACCOUNT_USERNAME + account.getUsername());
        RLock phoneLock = redissonClient.getLock(LockConst.USER_ACCOUNT_PHONE + account.getPhone());
        RLock lock = redissonClient.getMultiLock(usernameLock, phoneLock);
        lock.lock();
        try {
            UserAccount userAccount = userAccountMapper.selectOne(
                    Wrappers.lambdaQuery(UserAccount.class)
                            .eq(UserAccount::getUsername, account.getUsername())
                            .eq(UserAccount::getIsDeleted, Boolean.FALSE)
            );
            Assert.isNull(userAccount, ResultStatus.USERNAME_EXIST);
            userAccount = userAccountMapper.selectOne(
                    Wrappers.lambdaQuery(UserAccount.class)
                            .eq(UserAccount::getPhone, account.getPhone())
                            .eq(UserAccount::getIsDeleted, Boolean.FALSE)
            );
            Assert.isNull(userAccount, ResultStatus.EMAIL_EXIST);
            userAccountMapper.insert(account);
        } finally {
            lock.unlock();
        }

        userProfileService.add(account.getUid());
    }

    /**
     * 根据uid查询用户账号信息，需要对信息脱敏
     *
     * @param uid uid
     * @return 账号信息
     */
    public UserAccountDTO getByUid(Long uid) {
        UserAccount account = userAccountMapper.selectById(uid);
        this.desensitization(account);
        UserAccountDTO result = new UserAccountDTO();
        BeanUtils.copyProperties(account, result);
        return result;
    }

    /**
     * 根据邮箱查询用户uid
     *
     * @param email 邮箱
     * @return boolean
     */
    public Long getUidByEmail(String email) {
        Wrapper<UserAccount> wrapper = Wrappers.lambdaQuery(UserAccount.class)
                .eq(UserAccount::getEmail, email)
                .eq(UserAccount::getIsDeleted, Boolean.FALSE);
        UserAccount account = userAccountMapper.selectOne(wrapper);
        return account == null ? null : account.getUid();
    }

    /**
     * 根据手机获取用户uid
     *
     * @param phone 手机
     * @return boolean
     */
    public Long getUidByPhone(String phone) {
        Wrapper<UserAccount> wrapper = Wrappers.lambdaQuery(UserAccount.class)
                .eq(UserAccount::getPhone, phone)
                .eq(UserAccount::getIsDeleted, Boolean.FALSE);
        UserAccount account = userAccountMapper.selectOne(wrapper);
        return account == null ? null : account.getUid();
    }

    /**
     * 数据脱敏，邮箱、手机号、密码
     *
     * @param account 账号信息
     * @return 账号信息
     */
    public UserAccount desensitization(UserAccount account) {
        String email = account.getEmail();
        if (email != null) {
            String newEmail = email.replaceAll(EMAIL_DESENSITIZATION, DESENSITIZATION_FORMAT);
            account.setEmail(newEmail);
        }
        String phone = account.getPhone();
        if (phone != null) {
            String newPhone = phone.replaceAll(PHONE_DESENSITIZATION, DESENSITIZATION_FORMAT);
            account.setPhone(newPhone);
        }
        if (account.getPassword() != null) {
            account.setPassword("******");
        }
        return account;
    }

    /**
     * 修改密码
     * 1. 修改密码
     * 2. 当前用户登陆的所有token下线
     *
     * @param account 修改密码的信息
     */
    @Transactional
    public void editPassword(UserAccount account) {
        userAccountMapper.updateById(account);
        authenticator.editPassword(account.getUid());
    }

    /**
     * 修改邮箱
     * <p>
     * 1. 同步锁：邮箱
     * 2. 校验是否有用户绑定
     * 3. 修改
     *
     * @param account 修改信息
     */
    public void editEmail(UserAccount account) {
        String email = account.getEmail();
        RLock lock = redissonClient.getLock(LockConst.USER_ACCOUNT_EMAIL + email);
        lock.lock();
        try {
            Long uid = this.getUidByEmail(email);
            Assert.isNull(uid, ResultStatus.EMAIL_EXIST);
            userAccountMapper.updateById(account);
        } finally {
            lock.unlock();
        }
    }

    /**
     * 修改手机
     * <p>
     * 1. 同步锁：手机
     * 2. 校验是否有用户绑定
     * 3. 修改
     *
     * @param account 修改信息
     */
    public void editPhone(UserAccount account) {
        String phone = account.getPhone();
        RLock lock = redissonClient.getLock(LockConst.USER_ACCOUNT_PHONE + phone);
        lock.lock();
        try {
            Long uid = this.getUidByPhone(phone);
            Assert.isNull(uid, ResultStatus.PHONE_EXIST);
            userAccountMapper.updateById(account);
        } finally {
            lock.unlock();
        }
    }
}
