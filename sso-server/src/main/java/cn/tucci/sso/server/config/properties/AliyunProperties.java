package cn.tucci.sso.server.config.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author tucci.lee
 */
@ConfigurationProperties(prefix = "aliyun")
public class AliyunProperties {

    private OssProperties oss;
    private SmsProperties sms;

    public OssProperties getOss() {
        return oss;
    }

    public void setOss(OssProperties oss) {
        this.oss = oss;
    }

    public SmsProperties getSms() {
        return sms;
    }

    public void setSms(SmsProperties sms) {
        this.sms = sms;
    }
}
