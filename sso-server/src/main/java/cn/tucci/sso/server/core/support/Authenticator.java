package cn.tucci.sso.server.core.support;

import cn.tucci.sso.server.config.properties.SsoProperties;
import cn.tucci.sso.server.core.constant.CacheConst;
import cn.tucci.sso.server.core.utils.WebUtils;

import java.util.Set;

/**
 * @author tucci.lee
 */
public class Authenticator {

    private final TokenCodec tokenCodec;
    private final CacheOperator cacheOperator;
    private final SsoProperties ssoProperties;

    public Authenticator(TokenCodec tokenCodec,
                         CacheOperator cacheOperator,
                         SsoProperties ssoProperties) {
        this.tokenCodec = tokenCodec;
        this.cacheOperator = cacheOperator;
        this.ssoProperties = ssoProperties;
    }

    /**
     * 登陆生成token，设置到缓存中
     *
     * @param uid        uid
     * @param rememberMe 记住我
     * @return token
     */
    public String webSignin(Long uid, boolean rememberMe) {
        String token = tokenCodec.encode(uid);
        if (rememberMe) {
            WebUtils.setCookie(ssoProperties.getCookieName(), token, CacheConst.WEB_TOKEN_REMEMBERME_EXPIRY_TIME,
                    ssoProperties.getDomain(), Boolean.TRUE);
            cacheOperator.set(CacheConst.getUserTokenWebKey(uid, token), " ", CacheConst.WEB_TOKEN_REMEMBERME_EXPIRY_TIME);
        } else {
            WebUtils.setCookie(ssoProperties.getCookieName(), token, CacheConst.WEB_TOKEN_EXPIRY_TIME,
                    ssoProperties.getDomain(), Boolean.TRUE);
            cacheOperator.set(CacheConst.getUserTokenWebKey(uid, token), " ", CacheConst.WEB_TOKEN_EXPIRY_TIME);
        }
        return token;
    }

    /**
     * app登陆
     *
     * @param uid uid
     * @return token
     */
    public String appSignin(Long uid) {
        String token = tokenCodec.encode(uid);
        cacheOperator.set(CacheConst.getUserTokenAppKey(uid), token, CacheConst.APP_TOKEN_EXPIRY_TIME);
        return token;
    }

    public Long isWebSignin() {
        String token = WebUtils.getCookieValue(ssoProperties.getCookieName());
        return this.isWebSignin(token);
    }

    public Long isWebSignin(String token) {
        Long uid = tokenCodec.decode(token);
        if (uid == null) {
            return null;
        }
        String value = cacheOperator.get(CacheConst.getUserTokenWebKey(uid, token));
        if (value != null) {
            return uid;
        }
        return null;
    }

    public Long isAppSignin() {
        String token = WebUtils.getBearer();
        return this.isAppSignin(token);
    }

    public Long isAppSignin(String token) {
        Long uid = tokenCodec.decode(token);
        if (uid == null) {
            return null;
        }
        String cacheToken = cacheOperator.get(CacheConst.getUserTokenAppKey(uid));
        if (token.equals(cacheToken)) {
            return uid;
        }
        return null;
    }


    public void signout() {
        String token = WebUtils.getCookieValue(ssoProperties.getCookieName());
        if (token != null) {
            this.signout(token);
        }
        token = WebUtils.getBearer();
        if (token != null) {
            this.signout(token);
        }
    }

    /**
     * 登出
     */
    public void signout(String token) {
        Long uid = tokenCodec.decode(token);
        if (uid == null) {
            return;
        }
        // web登出
        cacheOperator.delete(CacheConst.getUserTokenWebKey(uid, token));
        // app登出
        String cacheToken = cacheOperator.get(CacheConst.getUserTokenAppKey(uid));
        if (token.equals(cacheToken)) {
            cacheOperator.delete(CacheConst.getUserTokenAppKey(uid));
        }
    }

    /**
     * 修改密码，所有用户下线
     */
    public void editPassword(Long uid) {
        // 删除web登陆的用户
        Set<String> keys = cacheOperator.keys(CacheConst.getUserTokenWebKey(uid, "*"));
        cacheOperator.delete(keys);
        // 删除app登陆的用户
        cacheOperator.delete(CacheConst.getUserTokenAppKey(uid));
    }
}
