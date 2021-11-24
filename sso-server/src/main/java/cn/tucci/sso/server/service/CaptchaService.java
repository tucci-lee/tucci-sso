package cn.tucci.sso.server.service;

/**
 * @author tucci.lee
 */
public interface CaptchaService {

    /**
     * 校验图片验证码
     *
     * @param key     缓存的key名称
     * @param captcha 验证码
     */
    void verifyImgCaptcha(String key, String captcha);

    /**
     * 发送邮箱验证码
     *
     * @param type  验证码类型
     * @param email 邮箱
     */
    void sendEmailCaptcha(int type, String email);

    /**
     * 校验邮箱验证码
     *
     * @param type    验证码类型
     * @param email   邮箱
     * @param captcha 验证码
     */
    void verifyEmailCaptcha(int type, String email, String captcha);

    /**
     * 发送手机验证码
     *
     * @param type  验证码类型
     * @param phone 手机
     */
    void sendPhoneCaptcha(int type, String phone);

    /**
     * 校验手机验证码
     * @param type 验证码类型
     * @param phone 手机
     * @param captcha 验证码
     */
    void verifyPhoneCaptcha(int type, String phone, String captcha);
}
