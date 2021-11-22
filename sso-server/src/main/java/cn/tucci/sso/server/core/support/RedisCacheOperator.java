package cn.tucci.sso.server.core.support;

import org.redisson.api.RedissonClient;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * @author tucci.lee
 */
public class RedisCacheOperator implements CacheOperator {

    private final StringRedisTemplate stringRedisTemplate;

    public RedisCacheOperator(StringRedisTemplate stringRedisTemplate) {
        this.stringRedisTemplate = stringRedisTemplate;
    }

    @Override
    public void set(String key, String value) {
        stringRedisTemplate.opsForValue().set(key, value);
    }

    @Override
    public void set(String key, String value, long timeout) {
        stringRedisTemplate.opsForValue().set(key, value, timeout, TimeUnit.SECONDS);
    }

    @Override
    public String get(String key) {
        return stringRedisTemplate.opsForValue().get(key);
    }

    @Override
    public Set<String> keys(String key) {
        return stringRedisTemplate.keys(key);
    }

    @Override
    public void delete(String key) {
        stringRedisTemplate.delete(key);
    }

    @Override
    public void delete(Set<String> keys) {
        stringRedisTemplate.delete(keys);
    }

}
