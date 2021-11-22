package cn.tucci.sso.core.filter;

import org.springframework.util.AntPathMatcher;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author tucci.lee
 */
public abstract class AbstractFilter implements Filter {

    private final AntPathMatcher antPathMatcher = new AntPathMatcher();

    private String[] exclusions = new String[0];

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        // 排除url，直接放行
        String uri = request.getRequestURI();
        for (String excludeUrl : exclusions) {
            if (antPathMatcher.match(excludeUrl, uri)) {
                filterChain.doFilter(servletRequest, servletResponse);
                return;
            }
        }

        if (!isAccessAllowed(request, response)) {
            onAccessDenied(request, response);
            return;
        }

        filterChain.doFilter(servletRequest, servletResponse);
    }

    /**
     * 是否通行
     *
     * @param request  request
     * @param response response
     * @return 是否通行
     * @throws IOException e
     */
    protected abstract boolean isAccessAllowed(HttpServletRequest request, HttpServletResponse response) throws IOException;

    /**
     * 错误处理
     *
     * @param request  request
     * @param response response
     * @throws IOException e
     */
    protected abstract void onAccessDenied(HttpServletRequest request, HttpServletResponse response) throws IOException;


    protected String errorMsg(){
        return "{\"code\":10101,\"msg\":\"unauthenticated\",\"status\":false}";
    }

    public void setExclusions(String... exclusions) {
        this.exclusions = exclusions;
    }
}
