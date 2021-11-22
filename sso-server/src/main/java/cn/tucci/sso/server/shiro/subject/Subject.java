package cn.tucci.sso.server.shiro.subject;

import cn.tucci.sso.server.shiro.filter.WebUserFilter;

/**
 * 获取当前登陆用户的uid
 *
 * @author tucci.lee
 * @see WebUserFilter
 */
public class Subject {

    private static final ThreadLocal<Long> UIDS = new ThreadLocal<>();

    public static void set(Long uid) {
        UIDS.set(uid);
    }

    public static Long get() {
        return UIDS.get();
    }

    public static void remove() {
        UIDS.remove();
    }
}
