package cn.tucci.sso.core.util;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

/**
 * @author tucci.lee
 */
public class WebUtils {

    public static final String Authorization = "Authorization";
    public static final String Bearer = "Bearer ";

    public static Cookie getCookie(HttpServletRequest request, String name) {
        Cookie[] cookies = request.getCookies();
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

    public static String getCookieValue(HttpServletRequest request, String name) {
        Cookie cookie = getCookie(request, name);
        return cookie == null ? null : cookie.getValue();
    }

    public static String getHeader(HttpServletRequest request, String name) {
        return request.getHeader(name);
    }

    public static String getAuthorization(HttpServletRequest request) {
        return getHeader(request,Authorization);
    }

    public static String getBearer(HttpServletRequest request){
        String authorization = getAuthorization(request);
        if(authorization != null && authorization.startsWith(Bearer)){
            return authorization.replace(Bearer, "");
        }
        return null;
    }
}
