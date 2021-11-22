package cn.tucci.sso.server.config;

import cn.tucci.sso.server.config.properties.SsoProperties;
import cn.tucci.sso.server.config.properties.TokenProperties;
import cn.tucci.sso.server.core.support.Authenticator;
import cn.tucci.sso.server.core.support.CacheOperator;
import cn.tucci.sso.server.core.support.JwtTokenCodec;
import cn.tucci.sso.server.core.support.RedisCacheOperator;
import cn.tucci.sso.server.core.support.TokenCodec;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.StringRedisTemplate;

/**
 * @author tucci.lee
 */
@Configuration
@EnableConfigurationProperties(SsoProperties.class)
public class SsoAutoConfiguration {

    @Bean
    public TokenCodec tokenCodec(SsoProperties ssoProperties) {
        TokenProperties properties = ssoProperties.getToken();
        TokenProperties.JwtTokenProperties jwt = properties.getJwt();
        return new JwtTokenCodec(jwt.getSecret());
    }

    @Bean
    public CacheOperator cacheOperator(StringRedisTemplate stringRedisTemplate) {
        return new RedisCacheOperator(stringRedisTemplate);
    }

    @Bean
    public Authenticator authenticator(TokenCodec tokenCodec, CacheOperator cacheOperator, SsoProperties ssoProperties) {
        return new Authenticator(tokenCodec, cacheOperator, ssoProperties);
    }
}
