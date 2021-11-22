package cn.tucci.sso.server.shiro.filter;

import cn.tucci.sso.server.core.response.Result;
import cn.tucci.sso.server.core.response.ResultStatus;
import cn.tucci.sso.server.core.support.Authenticator;
import cn.tucci.sso.server.shiro.subject.Subject;
import com.alibaba.fastjson.JSON;
import org.apache.shiro.web.filter.AccessControlFilter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 用户登陆filter，这个filter只对当前sso server起作用
 *
 * @author tucci.lee
 */
public class AppUserFilter extends AccessControlFilter {

    private final Authenticator authenticator;

    public AppUserFilter(Authenticator authenticator) {
        this.authenticator = authenticator;
    }

    /**
     * 判断token是否有效，
     * 有效则将解析的uid存储到Subject中
     *
     * @param request  request
     * @param response response
     * @param o        object
     * @return isAccessAllowed
     * @throws Exception Exception
     */
    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object o) throws Exception {
        Long uid = authenticator.isAppSignin();
        if (uid != null) {
            Subject.set(uid);
            return true;
        }
        return false;
    }

    /**
     * 如果用户未登陆的处理
     *
     * @param request  request
     * @param response response
     * @return onAccessDenied
     * @throws Exception Exception
     */
    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
        res.setContentType("application/json;charset=UTF-8");
        String result = JSON.toJSONString(Result.fail(ResultStatus.UNAUTHENTICATED));
        res.getWriter().print(result);
        return false;
    }

}
