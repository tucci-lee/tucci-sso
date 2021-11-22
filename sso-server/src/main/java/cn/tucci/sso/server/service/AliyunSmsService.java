package cn.tucci.sso.server.service;

import cn.tucci.sso.server.config.properties.AliyunProperties;
import cn.tucci.sso.server.config.properties.SmsProperties;
import com.aliyun.dysmsapi20170525.Client;
import com.aliyun.dysmsapi20170525.models.SendSmsRequest;
import com.aliyun.teaopenapi.models.Config;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Service;

/**
 * @author tucci.lee
 */
@Service
@EnableConfigurationProperties(AliyunProperties.class)
public class AliyunSmsService implements SmsService {
    private Logger logger = LoggerFactory.getLogger(getClass());
    private final AliyunProperties aliyunProperties;

    public AliyunSmsService(AliyunProperties aliyunProperties) {
        this.aliyunProperties = aliyunProperties;
    }

    protected Client client() throws Exception {
        SmsProperties sms = aliyunProperties.getSms();
        Config config = new Config()
                .setAccessKeyId(sms.getAccessKeyId())
                .setAccessKeySecret(sms.getAccessKeySecret());
        config.endpoint = "dysmsapi.aliyuncs.com";
        return new Client(config);
    }

    @Override
    public void send(String phone, String signName, String template, Object param) {
        SendSmsRequest sendSmsRequest = new SendSmsRequest()
                .setPhoneNumbers(phone)
                .setSignName(signName)
                .setTemplateCode(template);
        if (param instanceof String) {
            sendSmsRequest.setTemplateParam((String) param);
        }
        try {
            Client client = client();
            client.sendSms(sendSmsRequest);
            logger.info("发送手机验证码{}, template:{}", phone, template);
        } catch (Exception e) {
            logger.error("发送手机验证码失败", e);
        }

    }

    @Override
    public void send(String phone, String template, Object param) {
        this.send(phone, aliyunProperties.getSms().getSignName(), template, param);
    }
}
