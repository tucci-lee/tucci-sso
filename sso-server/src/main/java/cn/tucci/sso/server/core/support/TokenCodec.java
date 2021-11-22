package cn.tucci.sso.server.core.support;


/**
 * token编解码器
 *
 * @author tucci.lee
 */
public interface TokenCodec {

    /**
     * 加密token
     *
     * @param uid uid
     * @return token
     */
    String encode(Long uid);

    /**
     * 解密token
     *
     * @param token token
     * @return uid
     */
    Long decode(String token);
}
