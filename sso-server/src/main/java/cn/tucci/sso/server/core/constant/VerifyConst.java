package cn.tucci.sso.server.core.constant;

/**
 * @author tucci.lee
 */
public class VerifyConst {
    /**
     * 用户名
     */
    public static final String USERNAME_REGEXP = "^[a-zA-Z][\\w]{4,20}$";
    /**
     * 密码
     */
    public static final String PASSWORD_REGEXP = "^.{6,30}$";
    /**
     * 手机号
     */
    public static final String PHONE_REGEXP = "^1[0-9]{10}$";

    /**
     * 验证码类型
     * 1：注册
     * 2：修改密码
     * 3：修改邮箱验证
     * 4：修改邮箱
     * 5：修改手机验证
     * 6：修改手机
     */
    public interface CaptchaType {
        int SIGNUP = 1;
        int EDIT_PASSWORD = SIGNUP + 1;
        int EDIT_EMAIL_VERIFY = EDIT_PASSWORD + 1;
        int EDIT_EMAIL = EDIT_EMAIL_VERIFY + 1;
        int EDIT_PHONE_VERIFY = EDIT_EMAIL + 1;
        int EDIT_PHONE = EDIT_PHONE_VERIFY + 1;
    }

    /**
     * 注册类型
     * 1：手机
     * 2：邮箱
     */
    public interface SignupType {
        int PHONE = 1;
        int EMAIL = PHONE + 1;

        int MIN = PHONE;
        int MAX = EMAIL;
    }

    /**
     * 性别
     * 0：女
     * 1：男
     */
    public interface Gender {
        int WOMEN = 0;
        int MAN = WOMEN + 1;

        int MIN = WOMEN;
        int MAX = MAN;
    }
}
