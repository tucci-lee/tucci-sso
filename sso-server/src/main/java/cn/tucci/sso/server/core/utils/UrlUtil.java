package cn.tucci.sso.server.core.utils;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

/**
 * @author tucci.lee
 */
public class UrlUtil {

    public static final String URI_REGEX = "(http|https):\\/\\/(.+)";

    /**
     * 获取重定向uri
     *
     * @param redirectUri        重定向uri
     * @param defaultRedirectUri 默认的重定向uri
     * @return redirectUri
     */
    public static String getRedirectUri(String redirectUri, String defaultRedirectUri) {
        try {
            redirectUri = URLDecoder.decode(redirectUri, StandardCharsets.UTF_8.toString());
        } catch (Exception e) {
            return defaultRedirectUri;
        }
        // 校验url是否是一个正确的url
        if (redirectUri != null && redirectUri.matches(URI_REGEX)) {
            return redirectUri;
        }
        if(defaultRedirectUri == null || !defaultRedirectUri.matches(URI_REGEX)){
            throw new RuntimeException("no redirectUri");
        }else {
            return defaultRedirectUri;
        }
    }
}
