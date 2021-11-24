package cn.tucci.sso.server.service.impl;

import cn.tucci.sso.server.config.properties.AliyunProperties;
import cn.tucci.sso.server.config.properties.OssProperties;
import cn.tucci.sso.server.service.FsService;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.model.PutObjectResult;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Service;

import java.io.InputStream;

/**
 * @author tucci.lee
 */
@EnableConfigurationProperties(AliyunProperties.class)
@Service
public class AliyunOssService implements FsService {

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

    @Override
    public String getDomain() {
        OssProperties oss = aliyunProperties.getOss();
        String domain = oss.getDomain();
        if (domain == null || domain.isEmpty()) {
            domain = oss.getBucket() + "." + oss.getEndpoint() + "/";
        }
        return domain;
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
    public void upload(String key, InputStream is) {
        OSS oss = create();
        PutObjectResult result = oss.putObject(getBucket(), key, is);
        oss.shutdown();
    }

    @Override
    public void delete(String key) {
        OSS oss = create();
        oss.deleteObject(getBucket(), key);
        oss.shutdown();
    }
}
