package cn.tucci.sso.server.service;

import cn.tucci.sso.server.config.properties.AliyunProperties;
import cn.tucci.sso.server.config.properties.OssProperties;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Service;

import java.io.InputStream;

/**
 * @author tucci.lee
 */
@EnableConfigurationProperties(AliyunProperties.class)
@Service
public class AliyunOssService implements OssService {

    private final AliyunProperties aliyunProperties;

    public AliyunOssService(AliyunProperties aliyunProperties) {
        this.aliyunProperties = aliyunProperties;
    }

    /**
     * 创建oss client
     *
     * @return OSS
     */
    public OSS create() {
        OssProperties oss = aliyunProperties.getOss();
        return new OSSClientBuilder().build(oss.getEndpoint(), oss.getAccessKeyId(), oss.getAccessKeySecret());
    }

    /**
     * 获取存储桶
     *
     * @return bucket
     */
    public String getBucket() {
        return aliyunProperties.getOss().getBucket();
    }

    @Override
    public String getHost() {
        OssProperties oss = aliyunProperties.getOss();
        String host = oss.getHost();
        if (host == null || host.isEmpty()) {
            host = oss.getBucket() + "." + oss.getEndpoint();
        }
        return host;
    }

    @Override
    public void putObject(String key, InputStream is) {
        OSS oss = create();
        oss.putObject(getBucket(), key, is);
        oss.shutdown();
    }

    @Override
    public void deleteObject(String key) {
        OSS oss = create();
        oss.deleteObject(getBucket(), key);
        oss.shutdown();
    }
}
