package cn.tucci.sso.server.core.support;

import java.util.Set;

/**
 * @author tucci.lee
 */
public interface CacheOperator {
    /**
     * 设置缓存，没有过期时间
     *
     * @param key   key
     * @param value value
     */
    void set(String key, String value);

    /**
     * 设置缓存，有过期时间（秒）
     *
     * @param key     key
     * @param value   value
     * @param timeout 过期时间
     */
    void set(String key, String value, long timeout);

    /**
     * 获取缓存
     *
     * @param key key
     * @return value
     */
    String get(String key);

    /**
     * 获取所有匹配的key
     *
     * @param key key
     * @return 匹配的key
     */
    Set<String> keys(String key);

    /**
     * 删除缓存
     *
     * @param key key
     */
    void delete(String key);

    /**
     * 删除所有key的缓存
     *
     * @param keys keys
     */
    void delete(Set<String> keys);
}
