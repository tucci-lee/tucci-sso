package cn.tucci.sso.server.core.constant;

/**
 * @author tucci.lee
 */
public class CacheConst {
    /**
     * 图片验证码：登陆
     */
    public static final String CAPTCHA_IMG_SIGNIN = "captcha:img:signin:";
    /**
     * 邮箱验证码：注册
     */
    public static final String CAPTCHA_EMAIL_SIGNUP = "captcha:email:signup:";
    /**
     * 邮箱验证码：重置密码
     */
    public static final String CAPTHCA_EMAIL_PASSWORD = "captcha:email:password:";
    /**
     * 邮箱验证码：修改邮箱验证
     */
    public static final String CAPTCHA_EMAIL_EDIT_EMAIL_VERIFY = "captcha:email:edit:email:verify:";
    /**
     * 邮箱验证码：修改邮箱
     */
    public static final String CAPTCHA_EMAIL_EDIT_EMAIL = "captcha:email:edit:email:";
    /**
     * 邮箱验证码：修改手机验证
     */
    public static final String CAPTCHA_EMAIL_EDIT_PHONE_VERIFY = "captcha:email:edit:phone:verify:";
    /**
     * 邮箱验证码：修改手机
     */
    public static final String CAPTCHA_EMAIL_EDIT_PHONE = "captcha:email:edit:phone:";

    /**
     * 手机验证码：注册
     */
    public static final String CAPTCHA_PHONE_SIGNUP = "captcha:phone:signup:";
    /**
     * 手机验证码：重置密码
     */
    public static final String CAPTHCA_PHONE_PASSWORD = "captcha:phone:password:";
    /**
     * 手机验证码：修改邮箱验证
     */
    public static final String CAPTCHA_PHONE_EDIT_EMAIL_VERIFY = "captcha:phone:edit:email:verify:";
    /**
     * 手机验证码：修改邮箱
     */
    public static final String CAPTCHA_PHONE_EDIT_EMAIL = "captcha:phone:edit:email:";
    /**
     * 手机验证码：修改手机验证
     */
    public static final String CAPTCHA_PHONE_EDIT_PHONE_VERIFY = "captcha:phone:edit:phone:verify:";
    /**
     * 手机验证码：修改手机
     */
    public static final String CAPTCHA_PHONE_EDIT_PHONE = "captcha:phone:edit:phone:";

    /**
     * 验证码过期时间（秒）
     */
    public static final int CAPTCHA_EXPIRY_TIME = 5 * 60;

    /**
     * 用户token：web
     */
    public static final String USER_TOKEN_WEB = "token:web:%s:%s";
    /**
     * 用户token：app，app只能有一个用户登陆
     */
    public static final String USER_TOKEN_APP = "token:app:";
    /**
     * token: 修改密码
     */
    public static final String TOKEN_EDIT_PASSWORD = "token:edit:password:";
    /**
     * token：修改邮箱
     */
    public static final String TOKEN_EDIT_EMAIL = "token:edit:email:";
    /**
     * token：修改手机
     */
    public static final String TOKEN_EDIT_PHONE = "token:edit:phone:";
    /**
     * token过期时间
     */
    public static final int TOKEN_EXPIRY_TIME = 5 * 60;
    /**
     * web用户登陆token过期时间（秒）
     */
    public static final int WEB_TOKEN_EXPIRY_TIME = 60 * 60;
    /**
     * web用户登陆token记住我过期时间（秒）
     */
    public static final int WEB_TOKEN_REMEMBERME_EXPIRY_TIME = 30 * 24 * 60 * 60;
    /**
     * app用户登陆token过期时间（秒）
     */
    public static final int APP_TOKEN_EXPIRY_TIME = 12 * 30 * 24 * 60 * 60;

    public static String getUserTokenWebKey(Long uid, String token) {
        return String.format(USER_TOKEN_WEB, uid, token);
    }

    public static String getUserTokenAppKey(Long uid) {
        return USER_TOKEN_APP + uid;
    }

    public static String getTokenEditPasswordKey(String token) {
        return TOKEN_EDIT_PASSWORD + token;
    }

    public static String getTokenEditEmailKey(String token) {
        return TOKEN_EDIT_EMAIL + token;
    }

    public static String getTokenEditPhoneKey(String token) {
        return TOKEN_EDIT_PHONE + token;
    }

    public static String getCaptchaImgSigninKey(String cid) {
        return CAPTCHA_IMG_SIGNIN + cid;
    }

    public static String getCaptchaEmailSignupKey(String email) {
        return CAPTCHA_EMAIL_SIGNUP + email;
    }

    public static String getCaptchaEmailPasswordKey(String email) {
        return CAPTHCA_EMAIL_PASSWORD + email;
    }

    public static String getCaptchaEmailEditEmailKey(String email) {
        return CAPTCHA_EMAIL_EDIT_EMAIL + email;
    }

    public static String getCaptchaEmailEditEmailVerifyKey(String email) {
        return CAPTCHA_EMAIL_EDIT_EMAIL_VERIFY + email;
    }

    public static String getCaptchaEmailEditPhoneVerifyKey(String email) {
        return CAPTCHA_EMAIL_EDIT_PHONE_VERIFY + email;
    }

    public static String getCaptchaEmailEditPhoneKey(String email) {
        return CAPTCHA_EMAIL_EDIT_PHONE + email;
    }

    public static String getCaptchaPhoneSignupKey(String phone) {
        return CAPTCHA_PHONE_SIGNUP + phone;
    }

    public static String getCaptchaPhonePasswordKey(String phone) {
        return CAPTHCA_PHONE_PASSWORD + phone;
    }

    public static String getCaptchaPhoneEditEmailKey(String phone) {
        return CAPTCHA_PHONE_EDIT_EMAIL + phone;
    }

    public static String getCaptchaPhoneEditEmailVerifyKey(String phone) {
        return CAPTCHA_PHONE_EDIT_EMAIL_VERIFY + phone;
    }

    public static String getCaptchaPhoneEditPhoneVerifyKey(String phone) {
        return CAPTCHA_PHONE_EDIT_PHONE_VERIFY + phone;
    }

    public static String getCaptchaPhoneEditPhoneKey(String phone) {
        return CAPTCHA_PHONE_EDIT_PHONE + phone;
    }
}
