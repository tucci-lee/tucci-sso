package cn.tucci.sso.server.core.utils;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * spring获取request和response
 */
public class WebUtils {

    public static final String Authorization = "Authorization";
    public static final String Bearer = "Bearer ";

    public static HttpServletRequest getRequest() {
        return getAttributes().getRequest();
    }

    public static HttpServletResponse getResponse() {
        return getAttributes().getResponse();
    }

    public static String getHeader(String name) {
        return getRequest().getHeader(name);
    }

    public static void setHeader(String name, String value) {
        getResponse().setHeader(name, value);
    }

    public static String getAuthorization() {
        return getHeader(Authorization);
    }

    public static String getBearer(){
        String authorization = getAuthorization();
        if(authorization != null && authorization.startsWith(Bearer)){
            return authorization.replace(Bearer, "");
        }
        return null;
    }

    public static void setCookie(String key, String value) {
        setCookie(key, value, null, null, null);
    }

    public static void setCookie(String key, String value, int expiry) {
        setCookie(key, value, expiry, null, null);
    }

    public static void setCookie(String key, String value, int expiry, String domain) {
        setCookie(key, value, expiry, domain, null);
    }

    public static void setCookie(String key, String value, Integer expiry, String domain, Boolean httpOnly) {
        Cookie cookie = new Cookie(key, value);
        if(expiry != null) {
            cookie.setMaxAge(expiry);
        }
        if(domain != null) {
            cookie.setDomain(domain);
        }
        if(httpOnly != null) {
            cookie.setHttpOnly(httpOnly);
        }
        getResponse().addCookie(cookie);
    }

    public static Cookie getCookie(String name) {
        Cookie[] cookies = getRequest().getCookies();
        if (cookies == null || cookies.length == 0) {
            return null;
        }
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals(name)) {
                return cookie;
            }
        }
        return null;
    }

    public static String getCookieValue(String name) {
        Cookie cookie = getCookie(name);
        return cookie == null ? null : cookie.getValue();
    }

    public static String getIp() {
        HttpServletRequest request = getRequest();
        String ip = request.getHeader("X-Forwarded-For");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }

        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("X-Real-IP");
        }

        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }

        if (ip != null && ip.indexOf(",") > 0) {
            ip = ip.split(",")[0];
        }

        return "0:0:0:0:0:0:0:1".equals(ip) ? "127.0.0.1" : ip;
    }

    protected static ServletRequestAttributes getAttributes() {
        return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes());
    }
}
