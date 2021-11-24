package cn.tucci.sso.server.service.impl;

import cn.tucci.sso.server.core.constant.LockConst;
import cn.tucci.sso.server.core.response.ResultStatus;
import cn.tucci.sso.server.core.support.Authenticator;
import cn.tucci.sso.server.core.utils.Assert;
import cn.tucci.sso.server.model.dao.UserAccountMapper;
import cn.tucci.sso.server.model.domain.UserAccount;
import cn.tucci.sso.server.model.dto.UserAccountDTO;
import cn.tucci.sso.server.service.UserAccountService;
import cn.tucci.sso.server.service.UserProfileService;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author tucci.lee
 */
@Service
public class UserAccountServiceImpl implements UserAccountService {

    private final RedissonClient redissonClient;
    private final UserProfileService userProfileService;
    private final Authenticator authenticator;
    private final UserAccountMapper userAccountMapper;

    public UserAccountServiceImpl(RedissonClient redissonClient,
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
    @Override
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
    @Override
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

    @Override
    public UserAccount getByUid(Long uid) {
        return userAccountMapper.selectById(uid);
    }

    @Override
    public UserAccountDTO getDesensitizationByUid(Long uid) {
        UserAccount account = userAccountMapper.selectById(uid);
        return this.desensitization(account);
    }

    @Override
    public UserAccount getByEmail(String email) {
        Wrapper<UserAccount> wrapper = Wrappers.lambdaQuery(UserAccount.class)
                .eq(UserAccount::getEmail, email)
                .eq(UserAccount::getIsDeleted, Boolean.FALSE);
        return userAccountMapper.selectOne(wrapper);
    }

    @Override
    public UserAccount getByPhone(String phone) {
        Wrapper<UserAccount> wrapper = Wrappers.lambdaQuery(UserAccount.class)
                .eq(UserAccount::getPhone, phone)
                .eq(UserAccount::getIsDeleted, Boolean.FALSE);
        return userAccountMapper.selectOne(wrapper);
    }

    /**
     * 数据脱敏，邮箱、手机
     *
     * @param account 账号信息
     * @return 脱敏后的账号信息
     */
    protected UserAccountDTO desensitization(UserAccount account) {
        UserAccountDTO result = new UserAccountDTO();
        String email = account.getEmail();
        if (email != null) {
            String newEmail = email.replaceAll(EMAIL_DESENSITIZATION, DESENSITIZATION_FORMAT);
            result.setEmail(newEmail);
        }
        String phone = account.getPhone();
        if (phone != null) {
            String newPhone = phone.replaceAll(PHONE_DESENSITIZATION, DESENSITIZATION_FORMAT);
            result.setPhone(newPhone);
        }
        return result;
    }

    /**
     * 修改密码
     * 1. 修改密码
     * 2. 当前用户登陆的所有token下线
     *
     * @param uid      uid
     * @param password 新密码
     */
    @Transactional
    @Override
    public void editPassword(Long uid, String password) {
        UserAccount account = new UserAccount()
                .setUid(uid)
                .setPassword(password);
        userAccountMapper.updateById(account);
        authenticator.editPassword(account.getUid());
    }

    /**
     * 修改邮箱
     * 1. 同步锁：邮箱
     * 2. 校验是否有用户绑定
     * 3. 修改
     *
     * @param uid   uid
     * @param email 新邮箱
     */
    @Override
    public void editEmail(Long uid, String email) {
        RLock lock = redissonClient.getLock(LockConst.USER_ACCOUNT_EMAIL + email);
        lock.lock();
        try {
            Assert.isNull(this.getByEmail(email), ResultStatus.EMAIL_EXIST);
            UserAccount account = new UserAccount()
                    .setUid(uid)
                    .setEmail(email);
            userAccountMapper.updateById(account);
        } finally {
            lock.unlock();
        }
    }

    /**
     * 修改手机
     * 1. 同步锁：手机
     * 2. 校验是否有用户绑定
     * 3. 修改
     *
     * @param uid   uid
     * @param phone 新手机
     */
    public void editPhone(Long uid, String phone) {
        RLock lock = redissonClient.getLock(LockConst.USER_ACCOUNT_PHONE + phone);
        lock.lock();
        try {
            Assert.isNull(this.getByPhone(phone), ResultStatus.PHONE_EXIST);
            UserAccount account = new UserAccount()
                    .setUid(uid)
                    .setPhone(phone);
            userAccountMapper.updateById(account);
        } finally {
            lock.unlock();
        }
    }

    @Override
    public UserAccount getByAccount(String account) {
        Wrapper<UserAccount> wrapper = Wrappers.lambdaQuery(UserAccount.class)
                .eq(UserAccount::getIsDeleted, Boolean.FALSE)
                .and(w -> w.eq(UserAccount::getUsername, account)
                        .or().eq(UserAccount::getEmail, account)
                        .or().eq(UserAccount::getPhone, account));
        return userAccountMapper.selectOne(wrapper);
    }
}
