package cn.tucci.sso.server.service;

import java.io.InputStream;
import java.util.UUID;

/**
 * @author tucci.lee
 */
public interface OssService {

    /**
     * 头像存储前缀
     */
    String AVATAR = "profile/avatar/";

    /**
     * 获取存储key
     *
     * @param prefix   前缀
     * @param fileName 文件名
     * @return key
     */
    default String getKey(String prefix, String fileName) {
        if (fileName == null) {
            return null;
        }
        int index = fileName.lastIndexOf(".");
        String suffix = "";
        if(index != -1){
            suffix = fileName.substring(index);
        }
        if (prefix != null) {
            return prefix + UUID.randomUUID() + suffix;
        } else {
            return UUID.randomUUID() + suffix;
        }
    }

    /**
     * 获取oss的host
     *
     * @return host
     */
    String getHost();

    /**
     * 获取全路径
     *
     * @param key key
     * @return 文件url
     */
    default String getObjectUrl(String key) {
        return getHost() + "/" + key;
    }

    /**
     * 上传对象
     *
     * @param key key
     * @param is  对象流
     */
    void putObject(String key, InputStream is);

    /**
     * 删除对象
     *
     * @param key key
     */
    void deleteObject(String key);
}
