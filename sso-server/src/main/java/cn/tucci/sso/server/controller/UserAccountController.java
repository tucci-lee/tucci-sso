package cn.tucci.sso.server.controller;

import cn.tucci.sso.server.core.constant.CacheConst;
import cn.tucci.sso.server.core.constant.VerifyConst;
import cn.tucci.sso.server.core.response.Result;
import cn.tucci.sso.server.core.response.ResultStatus;
import cn.tucci.sso.server.core.support.CacheOperator;
import cn.tucci.sso.server.core.utils.Assert;
import cn.tucci.sso.server.model.body.AccountEmailCaptchaBody;
import cn.tucci.sso.server.model.body.EmailEditBody;
import cn.tucci.sso.server.model.body.EmailEditVerifyBody;
import cn.tucci.sso.server.model.body.PasswordEditBody;
import cn.tucci.sso.server.model.body.PasswordVerifyBody;
import cn.tucci.sso.server.model.body.PhoneEditBody;
import cn.tucci.sso.server.model.body.PhoneEditVerifyBody;
import cn.tucci.sso.server.model.domain.UserAccount;
import cn.tucci.sso.server.model.dto.TokenDTO;
import cn.tucci.sso.server.model.dto.UserAccountDTO;
import cn.tucci.sso.server.service.CaptchaService;
import cn.tucci.sso.server.service.UserAccountService;
import cn.tucci.sso.server.shiro.subject.Subject;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

/**
 * @author tucci.lee
 */
@RestController
@RequestMapping(value = {"account", "app/account"})
public class UserAccountController {

    private final UserAccountService userAccountService;
    private final CaptchaService captchaService;
    private final CacheOperator cacheOperator;

    public UserAccountController(UserAccountService userAccountService,
                                 CaptchaService captchaService,
                                 CacheOperator cacheOperator) {
        this.userAccountService = userAccountService;
        this.captchaService = captchaService;
        this.cacheOperator = cacheOperator;
    }

    /**
     * 获取用户账号信息
     *
     * @return 账号信息
     */
    @GetMapping
    public Result<?> account() {
        Long uid = Subject.get();
        UserAccountDTO account = userAccountService.getByUid(uid);
        return Result.ok(account);
    }

    /**
     * 修改密码前的校验
     * 1. 校验验证码
     * 2. 根据邮箱/手机获取uid
     * 2. 生成token存储缓存
     *
     * @param body 验证码信息
     * @return token
     */
    @PostMapping("password/edit/verify")
    public Result<?> editPasswordVerify(@Validated @RequestBody PasswordVerifyBody body) {
        Long uid;
        if (body.getType() == VerifyConst.SignupType.EMAIL) {
            captchaService.verifyEmailCaptcha(VerifyConst.CaptchaType.EDIT_PASSWORD, body.getEmail(), body.getCaptcha());
            uid = userAccountService.getUidByEmail(body.getEmail());
        } else {
            captchaService.verifyPhoneCaptcha(VerifyConst.CaptchaType.EDIT_PASSWORD, body.getPhone(), body.getCaptcha());
            uid = userAccountService.getUidByPhone(body.getPhone());
        }

        String token = UUID.randomUUID().toString();
        String cacheKey = CacheConst.getTokenEditPasswordKey(token);

        cacheOperator.set(cacheKey, uid.toString(), CacheConst.TOKEN_EXPIRY_TIME);

        TokenDTO result = new TokenDTO()
                .setToken(token);
        return Result.ok(result);
    }

    /**
     * 修改密码
     * 1. 校验token是否有效
     * 2. 修改密码
     *
     * @param body 密码信息
     * @return ok
     */
    @PutMapping("password/edit")
    public Result<?> editPassword(@Validated @RequestBody PasswordEditBody body) {
        String cacheKey = CacheConst.getTokenEditPasswordKey(body.getToken());
        String uid = cacheOperator.get(cacheKey);
        Assert.notNull(uid, ResultStatus.PARAMETER_ERROR);
        cacheOperator.delete(cacheKey);

        UserAccount edit = new UserAccount()
                .setUid(Long.parseLong(uid))
                .setPassword(BCrypt.hashpw(body.getPassword(), BCrypt.gensalt()));
        userAccountService.editPassword(edit);
        return Result.ok();
    }

    /**
     * 发送邮箱验证码，根据当前登陆的用户获取邮箱
     *
     * @param body 验证码类型
     * @return ok
     */
    @PostMapping("captcha/email")
    public Result<?> sendEmailCaptcha(@Validated @RequestBody AccountEmailCaptchaBody body) {
        Long uid = Subject.get();
        UserAccount account = userAccountService.getById(uid);
        captchaService.sendEmailCaptcha(body.getType(), account.getEmail());
        return Result.ok();
    }

    /**
     * 发送手机验证码，根据当前登陆的用户获取手机
     *
     * @param body 验证码类型
     * @return ok
     */
    @PostMapping("captcha/phone")
    public Result<?> sendPhoneCaptcha(@Validated @RequestBody AccountEmailCaptchaBody body) {
        Long uid = Subject.get();
        UserAccount account = userAccountService.getById(uid);
        captchaService.sendPhoneCaptcha(body.getType(), account.getPhone());
        return Result.ok();
    }

    /**
     * 验证：获取修改邮箱前的token
     *
     * @param body 验证码信息
     * @return token
     */
    @PostMapping("email/edit/verify")
    public Result<?> editEmailVerify(@Validated @RequestBody EmailEditVerifyBody body) {
        Long uid = Subject.get();
        UserAccount account = userAccountService.getById(uid);
        if (body.getType() == VerifyConst.SignupType.EMAIL) {
            captchaService.verifyEmailCaptcha(VerifyConst.CaptchaType.EDIT_EMAIL_VERIFY, account.getEmail(), body.getCaptcha());
        } else {
            captchaService.verifyPhoneCaptcha(VerifyConst.CaptchaType.EDIT_EMAIL_VERIFY, account.getPhone(), body.getCaptcha());
        }
        String token = UUID.randomUUID().toString();
        String cacheKey = CacheConst.getTokenEditEmailKey(token);

        cacheOperator.set(cacheKey, uid.toString(), CacheConst.TOKEN_EXPIRY_TIME);

        TokenDTO result = new TokenDTO()
                .setToken(token);
        return Result.ok(result);
    }

    /**
     * 修改邮箱
     * <p>
     * 1. 校验新邮箱验证码
     * 2. 校验token
     * 2. 修改邮箱
     *
     * @param body 邮箱信息
     * @return ok
     */
    @PutMapping("email/edit")
    public Result<?> editEmail(@Validated @RequestBody EmailEditBody body) {
        captchaService.verifyEmailCaptcha(VerifyConst.CaptchaType.EDIT_EMAIL, body.getEmail(), body.getCaptcha());

        String cacheKey = CacheConst.getTokenEditEmailKey(body.getToken());
        String cacheUid = cacheOperator.get(cacheKey);
        Long uid = Subject.get();
        Assert.isTrue(uid.toString().equals(cacheUid), ResultStatus.PARAMETER_ERROR);
        cacheOperator.delete(cacheKey);

        UserAccount account = new UserAccount()
                .setUid(uid)
                .setEmail(body.getEmail());
        userAccountService.editEmail(account);
        return Result.ok();
    }

    /**
     * 验证：获取修改手机前的token
     *
     * @param body 验证码信息
     * @return token
     */
    @PostMapping("phone/edit/verify")
    public Result<?> editPhoneVerify(@Validated @RequestBody PhoneEditVerifyBody body) {
        Long uid = Subject.get();
        UserAccount account = userAccountService.getById(uid);
        if (body.getType() == VerifyConst.SignupType.EMAIL) {
            captchaService.verifyEmailCaptcha(VerifyConst.CaptchaType.EDIT_PHONE_VERIFY, account.getEmail(), body.getCaptcha());
        } else {
            captchaService.verifyPhoneCaptcha(VerifyConst.CaptchaType.EDIT_PHONE_VERIFY, account.getPhone(), body.getCaptcha());
        }
        String token = UUID.randomUUID().toString();
        String cacheKey = CacheConst.getTokenEditPhoneKey(token);

        cacheOperator.set(cacheKey, uid.toString(), CacheConst.TOKEN_EXPIRY_TIME);

        TokenDTO result = new TokenDTO()
                .setToken(token);
        return Result.ok(result);
    }

    /**
     * 修改手机
     * <p>
     * 1. 校验新手机验证码
     * 2. 校验token
     * 2. 修改手机
     *
     * @param body 手机信息
     * @return ok
     */
    @PutMapping("phone/edit")
    public Result<?> editPhone(@Validated @RequestBody PhoneEditBody body) {
        captchaService.verifyPhoneCaptcha(VerifyConst.CaptchaType.EDIT_PHONE, body.getPhone(), body.getCaptcha());

        String cacheKey = CacheConst.getTokenEditPhoneKey(body.getToken());
        String cacheUid = cacheOperator.get(cacheKey);
        Long uid = Subject.get();
        Assert.isTrue(uid.toString().equals(cacheUid), ResultStatus.PARAMETER_ERROR);
        cacheOperator.delete(cacheKey);

        UserAccount account = new UserAccount()
                .setUid(uid)
                .setPhone(body.getPhone());
        userAccountService.editPhone(account);
        return Result.ok();
    }
}
