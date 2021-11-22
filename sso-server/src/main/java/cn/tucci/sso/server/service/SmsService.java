package cn.tucci.sso.server.service;

/**
 * @author tucci.lee
 */
public interface SmsService {

    /**
     * 发送手机验证码
     *
     * @param phone    手机号
     * @param signName 签名
     * @param template 模板id
     * @param param    参数
     */
    void send(String phone, String signName, String template, Object param);

    /**
     * 发送手机验证码，不需要传入签名
     *
     * @param phone    手机号
     * @param template 模板id
     * @param param    参数
     */
    void send(String phone, String template, Object param);

}
