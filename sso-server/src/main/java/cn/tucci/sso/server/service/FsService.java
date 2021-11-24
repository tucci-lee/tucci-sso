package cn.tucci.sso.server.service;

import java.io.InputStream;
import java.util.UUID;

/**
 * File System 文件系统操作
 *
 * @author tucci.lee
 */
public interface FsService {

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

    String getDomain();

    /**
     * 上传文件
     *
     * @param key key
     * @param is  流
     */
    void upload(String key, InputStream is);

    /**
     * 删除文件
     *
     * @param key key
     */
    void delete(String key);
}
