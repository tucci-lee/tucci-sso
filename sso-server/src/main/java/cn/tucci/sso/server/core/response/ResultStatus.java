package cn.tucci.sso.server.core.response;


import cn.tucci.sso.server.core.constant.I18nConst;

/**
 * 返回结果的状态码和信息
 *
 * @author tucci.lee
 */
public enum ResultStatus {


    OK(0, I18nConst.OK),

    /**
     * 2xxxx 服务器错误
     * <p>
     * 20000 服务器错误
     * 20101 服务停用
     * 20201 系统限流
     */
    SERVER_ERROR(20000, I18nConst.SERVER_ERROR),
    SERVICE_DISABLED(20101, I18nConst.SERVICE_DISABLED),
    SYSTEM_LIMIT(20201, I18nConst.SYSTEM_LIMIT),

    /**
     * 1xxxx 客户端错误
     * <p>
     * 10001 业务异常
     * 10002 参数错误
     */
    BUSINESS_EXCEPTION(10001, I18nConst.BUSINESS_EXCEPTION),
    PARAMETER_ERROR(10002, I18nConst.PARAMETER_ERROR),
    /**
     * 101xx 权限相关
     * <p>
     * 10101 未认证
     * 10102 未授权
     * 10103 认证过期
     * 10111 拒绝访问，如内部api
     */
    UNAUTHENTICATED(10101, I18nConst.UNAUTHENTICATED),
    UNAUTHORIZED(10102, I18nConst.UNAUTHORIZED),
    AUTHENTICATE_EXPIRED(10103, I18nConst.AUTHENTICATE_EXPIRED),
    ACCESS_DENIED(10111, I18nConst.ACCESS_DENIED),

    /**
     * 102xx 请求错误
     * <p>
     * 10201 请求方法不支持
     * 10202 不支持的媒体类型
     * 10203 参数类型错误
     * 10204 json解析错误
     * 10205 未找到
     * 10206 请求频繁
     */
    METHOD_NOT_ALLOWED(10201, I18nConst.METHOD_NOT_ALLOWED),
    UNSUPPORTED_MEDIA_TYPE(10202, I18nConst.UNSUPPORTED_MEDIA_TYPE),
    PARAMETER_TYPE_ERROR(10203, I18nConst.PARAMETER_TYPE_ERROR),
    JSON_PARSE_ERROR(10204, I18nConst.JSON_PARSE_ERROR),
    NOT_FOUND(10205, I18nConst.NOT_FOUND),
    FREQUENT_REQUESTS(10206, I18nConst.FREQUENT_REQUESTS),

    /**
     * 103xx 账号错误
     * <p>
     * 10301 邮箱已经存在
     * 10302 邮箱不存在
     * 10303 用户名已经存在
     * 10304 用户名错误
     * 10305 账号锁定
     * 10306 密码错误
     * 10307 手机已经存在
     * 10308 手机不存在
     */
    EMAIL_EXIST(10301, I18nConst.EMAIL_EXIST),
    EMAIL_NOT_EXIST(10302, I18nConst.EMAIL_NOT_EXIST),
    USERNAME_EXIST(10303, I18nConst.USERNAME_EXIST),
    USERNAME_ERROR(10304, I18nConst.USERNAME_ERROR),
    ACCOUNT_LOCK(10305, I18nConst.ACCOUNT_LOCK),
    PASSWORD_ERROR(10306, I18nConst.PASSWORD_ERROR),
    PHONE_EXIST(10307, I18nConst.PHONE_EXIST),
    PHONE_NOT_EXIST(10308, I18nConst.PHONE_NOT_EXIST),

    /**
     * 104xx 验证码错误
     * <p>
     * 10401 图片验证码错误
     * 10402 邮箱验证码错误
     * 10403 手机验证码错误
     */
    IMG_CAPTCHA_ERROR(10401, I18nConst.CAPTCHA_ERROR),
    EMAIL_CAPTCHAT_ERROR(10402, I18nConst.CAPTCHA_ERROR),
    PHONE_CAPTCHAT_ERROR(10403, I18nConst.CAPTCHA_ERROR),

    /**
     * 105xx 媒体文件错误
     *
     * 10501 文件类型错误
     */
    FILE_TYPE_ERROR(10501, I18nConst.FILE_TYPE_ERROR),
    ;

    private int code;
    private String msg;

    ResultStatus(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int code() {
        return this.code;
    }

    public String msg() {
        return this.msg;
    }
}
