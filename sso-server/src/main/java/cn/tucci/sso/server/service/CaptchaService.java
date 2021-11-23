package cn.tucci.sso.server.service;

import cn.tucci.sso.server.core.annotation.Limit;
import cn.tucci.sso.server.core.constant.CacheConst;
import cn.tucci.sso.server.core.constant.I18nConst;
import cn.tucci.sso.server.core.constant.VerifyConst;
import cn.tucci.sso.server.core.response.ResultStatus;
import cn.tucci.sso.server.core.support.CacheOperator;
import cn.tucci.sso.server.core.support.Message;
import cn.tucci.sso.server.core.task.AsyncTaskExecutor;
import cn.tucci.sso.server.core.utils.Assert;
import cn.tucci.sso.server.core.utils.RandomUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.internet.MimeMessage;

/**
 * @author tucci.lee
 */
@Service
public class CaptchaService {

    private Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * 邮箱验证码模板
     */
    public static final String EMAIL_CAPTCHA_TEMPLATE = "<strong>%s<span style=\"color:#f60;font-size: 24px\">%s</span></strong>";

    private final CacheOperator cacheOperator;
    private final JavaMailSenderImpl mailSender;
    private final AsyncTaskExecutor asyncTaskExecutor;
    private final Message message;
    private final UserAccountService userAccountService;
    private final SmsService smsService;

    public CaptchaService(CacheOperator cacheOperator,
                          JavaMailSenderImpl mailSender,
                          AsyncTaskExecutor asyncTaskExecutor,
                          Message message,
                          UserAccountService userAccountService,
                          SmsService smsService) {
        this.cacheOperator = cacheOperator;
        this.mailSender = mailSender;
        this.asyncTaskExecutor = asyncTaskExecutor;
        this.message = message;
        this.userAccountService = userAccountService;
        this.smsService = smsService;
    }

    /**
     * 校验校验图形
     * <p>
     * 校验成功将缓存删除
     *
     * @param key     key
     * @param captcha 验证码
     */
    public void verifyImgCaptcha(String key, String captcha) {
        Assert.notNull(key, ResultStatus.IMG_CAPTCHA_ERROR);
        Assert.notNull(captcha, ResultStatus.IMG_CAPTCHA_ERROR);
        String cacheKey = CacheConst.getCaptchaImgSigninKey(key);
        String cacheCaptcha = cacheOperator.get(cacheKey);
        Assert.isTrue(captcha.equals(cacheCaptcha), ResultStatus.IMG_CAPTCHA_ERROR);
        cacheOperator.delete(cacheKey);
    }

    /**
     * 校验邮箱验证码
     *
     * @param type    类型
     * @param email   邮箱
     * @param captcha 验证码
     */
    public void verifyEmailCaptcha(int type, String email, String captcha) {
        Assert.notNull(email, ResultStatus.EMAIL_CAPTCHAT_ERROR);
        Assert.notNull(captcha, ResultStatus.EMAIL_CAPTCHAT_ERROR);

        String cacheKey = null;
        switch (type) {
            case VerifyConst.CaptchaType.SIGNUP:
                cacheKey = CacheConst.getCaptchaEmailSignupKey(email);
                break;
            case VerifyConst.CaptchaType.EDIT_PASSWORD:
                cacheKey = CacheConst.getCaptchaEmailPasswordKey(email);
                break;
            case VerifyConst.CaptchaType.EDIT_EMAIL_VERIFY:
                cacheKey = CacheConst.getCaptchaEmailEditEmailVerifyKey(email);
                break;
            case VerifyConst.CaptchaType.EDIT_EMAIL:
                cacheKey = CacheConst.getCaptchaEmailEditEmailKey(email);
                break;
            case VerifyConst.CaptchaType.EDIT_PHONE_VERIFY:
                cacheKey = CacheConst.getCaptchaEmailEditPhoneVerifyKey(email);
                break;
            case VerifyConst.CaptchaType.EDIT_PHONE:
                cacheKey = CacheConst.getCaptchaEmailEditPhoneKey(email);
                break;
        }

        Assert.notNull(cacheKey, ResultStatus.EMAIL_CAPTCHAT_ERROR);
        String cacheValue = cacheOperator.get(cacheKey);
        Assert.isTrue(captcha.equals(cacheValue), ResultStatus.EMAIL_CAPTCHAT_ERROR);
        cacheOperator.delete(cacheKey);
    }

    /**
     * 发送邮箱验证码
     * 1. 根据类型获取邮件主题、内容，缓存验证码的key
     * 2. 缓存验证码
     * 3. 发送邮件
     *
     * @param type  验证码类型
     * @param email 邮箱
     */
    @Limit(rate = 5)
    public void sendEmailCaptcha(int type, String email) {
        if (email == null || email.isEmpty()) {
            return;
        }
        String captcha = RandomUtil.randomInt(4);

        String subject = null;
        String text = null;
        String cacheKey = null;
        switch (type) {
            case VerifyConst.CaptchaType.SIGNUP:
                Assert.isNull(userAccountService.getUidByEmail(email), ResultStatus.EMAIL_EXIST);
                subject = message.getMessage(I18nConst.EMAIL_CAPTCHA_SIGNUP_SUBJECT);
                text = String.format(EMAIL_CAPTCHA_TEMPLATE, message.getMessage(I18nConst.EMAIL_CAPTCHA_SIGNUP_TEXT), captcha);
                cacheKey = CacheConst.getCaptchaEmailSignupKey(email);
                break;
            case VerifyConst.CaptchaType.EDIT_PASSWORD:
                Assert.notNull(userAccountService.getUidByEmail(email), ResultStatus.EMAIL_NOT_EXIST);
                subject = message.getMessage(I18nConst.EMAIL_CAPTCHA_EDIT_PASSWORD_SUBJECT);
                text = String.format(EMAIL_CAPTCHA_TEMPLATE, message.getMessage(I18nConst.EMAIL_CAPTCHA_EDIT_PASSWORD_TEXT), captcha);
                cacheKey = CacheConst.getCaptchaEmailPasswordKey(email);
                break;
            case VerifyConst.CaptchaType.EDIT_EMAIL_VERIFY:
                Assert.notNull(userAccountService.getUidByEmail(email), ResultStatus.EMAIL_NOT_EXIST);
                subject = message.getMessage(I18nConst.EMAIL_CAPTCHA_EDIT_EMAIL_SUBJECT);
                text = String.format(EMAIL_CAPTCHA_TEMPLATE, message.getMessage(I18nConst.EMAIL_CAPTCHA_EDIT_EMAIL_TEXT), captcha);
                cacheKey = CacheConst.getCaptchaEmailEditEmailVerifyKey(email);
                break;
            case VerifyConst.CaptchaType.EDIT_EMAIL:
                Assert.isNull(userAccountService.getUidByEmail(email), ResultStatus.EMAIL_EXIST);
                subject = message.getMessage(I18nConst.EMAIL_CAPTCHA_EDIT_EMAIL_SUBJECT);
                text = String.format(EMAIL_CAPTCHA_TEMPLATE, message.getMessage(I18nConst.EMAIL_CAPTCHA_EDIT_EMAIL_TEXT), captcha);
                cacheKey = CacheConst.getCaptchaEmailEditEmailKey(email);
                break;
            case VerifyConst.CaptchaType.EDIT_PHONE_VERIFY:
                subject = message.getMessage(I18nConst.EMAIL_CAPTCHA_EDIT_PHONE_SUBJECT);
                text = String.format(EMAIL_CAPTCHA_TEMPLATE, message.getMessage(I18nConst.EMAIL_CAPTCHA_EDIT_PHONE_TEXT), captcha);
                cacheKey = CacheConst.getCaptchaEmailEditPhoneVerifyKey(email);
                break;
            case VerifyConst.CaptchaType.EDIT_PHONE:
                Assert.isNull(userAccountService.getUidByEmail(email), ResultStatus.EMAIL_EXIST);
                subject = message.getMessage(I18nConst.EMAIL_CAPTCHA_EDIT_PHONE_SUBJECT);
                text = String.format(EMAIL_CAPTCHA_TEMPLATE, message.getMessage(I18nConst.EMAIL_CAPTCHA_EDIT_PHONE_TEXT), captcha);
                cacheKey = CacheConst.getCaptchaEmailEditPhoneKey(email);
                break;
        }
        if (cacheKey != null) {
            cacheOperator.set(cacheKey, captcha, CacheConst.CAPTCHA_EXPIRY_TIME);
            sendEmail(email, subject, text);
        }
    }

    /**
     * 发送邮件
     *
     * @param email   邮箱
     * @param subject 主题
     * @param text    内容
     */
    private void sendEmail(String email, String subject, String text) {
        asyncTaskExecutor.execute(() -> {
            try {
                MimeMessage mimeMessage = mailSender.createMimeMessage();
                MimeMessageHelper helper = new MimeMessageHelper(mimeMessage);
                helper.setFrom(mailSender.getUsername());
                helper.setTo(email);
                helper.setSubject(subject);
                helper.setText(text, Boolean.TRUE);
                mailSender.send(mimeMessage);
                logger.info("发送邮件{},主题：{}， 内容：{}", email, subject, text);
            } catch (Exception e) {
                logger.error("发送邮件失败", e);
            }
        });
    }

    /**
     * 发送手机验证码
     *
     * @param type  验证码类型
     * @param phone 手机
     */
    @Limit(rate = 5)
    public void sendPhoneCaptcha(int type, String phone) {
        if (phone == null || phone.isEmpty()) {
            return;
        }
        String captcha = RandomUtil.randomInt(4);

        String template = null;
        String cacheKey = null;
        switch (type) {
            case VerifyConst.CaptchaType.SIGNUP:
                Assert.isNull(userAccountService.getUidByPhone(phone), ResultStatus.PHONE_EXIST);
                template = message.getMessage(I18nConst.PHONE_CAPTCHA_SIGNUP_TEMPLATE);
                cacheKey = CacheConst.getCaptchaPhoneSignupKey(phone);
                break;
            case VerifyConst.CaptchaType.EDIT_PASSWORD:
                Assert.notNull(userAccountService.getUidByPhone(phone), ResultStatus.PHONE_NOT_EXIST);
                template = message.getMessage(I18nConst.PHONE_CAPTCHA_EDIT_PASSWORD_TEMPLATE);
                cacheKey = CacheConst.getCaptchaPhonePasswordKey(phone);
                break;
            case VerifyConst.CaptchaType.EDIT_EMAIL_VERIFY:
                Assert.notNull(userAccountService.getUidByPhone(phone), ResultStatus.PHONE_NOT_EXIST);
                template = message.getMessage(I18nConst.PHONE_CAPTCHA_EDIT_EMAIL_TEMPLATE);
                cacheKey = CacheConst.getCaptchaPhoneEditEmailVerifyKey(phone);
                break;
            case VerifyConst.CaptchaType.EDIT_EMAIL:
                Assert.isNull(userAccountService.getUidByPhone(phone), ResultStatus.PHONE_EXIST);
                template = message.getMessage(I18nConst.PHONE_CAPTCHA_EDIT_EMAIL_TEMPLATE);
                cacheKey = CacheConst.getCaptchaPhoneEditEmailKey(phone);
                break;
            case VerifyConst.CaptchaType.EDIT_PHONE_VERIFY:
                Assert.notNull(userAccountService.getUidByPhone(phone), ResultStatus.PHONE_NOT_EXIST);
                template = message.getMessage(I18nConst.PHONE_CAPTCHA_EDIT_PHONE_TEMPLATE);
                cacheKey = CacheConst.getCaptchaPhoneEditPhoneVerifyKey(phone);
                break;
            case VerifyConst.CaptchaType.EDIT_PHONE:
                Assert.isNull(userAccountService.getUidByPhone(phone), ResultStatus.PHONE_EXIST);
                template = message.getMessage(I18nConst.PHONE_CAPTCHA_EDIT_PHONE_TEMPLATE);
                cacheKey = CacheConst.getCaptchaPhoneEditPhoneKey(phone);
                break;
        }
        if (cacheKey != null) {
            cacheOperator.set(cacheKey, captcha, CacheConst.CAPTCHA_EXPIRY_TIME);
            smsService.send(phone, template, "{\"code\":" + captcha + "}");
        }
    }

    public void verifyPhoneCaptcha(int type, String phone, String captcha) {
        Assert.notNull(phone, ResultStatus.PHONE_CAPTCHAT_ERROR);
        Assert.notNull(captcha, ResultStatus.PHONE_CAPTCHAT_ERROR);

        String cacheKey = null;
        switch (type) {
            case VerifyConst.CaptchaType.SIGNUP:
                cacheKey = CacheConst.getCaptchaPhoneSignupKey(phone);
                break;
            case VerifyConst.CaptchaType.EDIT_PASSWORD:
                cacheKey = CacheConst.getCaptchaPhonePasswordKey(phone);
                break;
            case VerifyConst.CaptchaType.EDIT_EMAIL_VERIFY:
                cacheKey = CacheConst.getCaptchaPhoneEditEmailVerifyKey(phone);
                break;
            case VerifyConst.CaptchaType.EDIT_EMAIL:
                cacheKey = CacheConst.getCaptchaPhoneEditEmailKey(phone);
                break;
            case VerifyConst.CaptchaType.EDIT_PHONE_VERIFY:
                cacheKey = CacheConst.getCaptchaPhoneEditPhoneVerifyKey(phone);
                break;
            case VerifyConst.CaptchaType.EDIT_PHONE:
                cacheKey = CacheConst.getCaptchaPhoneEditPhoneKey(phone);
                break;
        }

        Assert.notNull(cacheKey, ResultStatus.PHONE_CAPTCHAT_ERROR);
        String cacheValue = cacheOperator.get(cacheKey);
        Assert.isTrue(captcha.equals(cacheValue), ResultStatus.PHONE_CAPTCHAT_ERROR);
        cacheOperator.delete(cacheKey);
    }
}
