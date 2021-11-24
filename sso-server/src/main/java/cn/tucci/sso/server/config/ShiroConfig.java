package cn.tucci.sso.server.config;

import cn.tucci.sso.server.config.properties.SsoProperties;
import cn.tucci.sso.server.core.support.Authenticator;
import cn.tucci.sso.server.shiro.filter.AppUserFilter;
import cn.tucci.sso.server.shiro.filter.WebUserFilter;
import cn.tucci.sso.server.shiro.realm.UsernamePasswordRealm;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @author tucci.lee
 */
@Configuration
public class ShiroConfig {

    private final SsoProperties ssoProperties;
    private final Authenticator authenticator;

    public ShiroConfig(SsoProperties ssoProperties,
                       Authenticator authenticator) {
        this.ssoProperties = ssoProperties;
        this.authenticator = authenticator;
    }

    @Bean
    public ShiroFilterFactoryBean shiroFilterFactoryBean(SecurityManager securityManager) {
        // 过滤链
        Map<String, String> filterChainMap = new LinkedHashMap<>();
        // 静态资源
        filterChainMap.put("/js/**", "anon");
        filterChainMap.put("/css/**", "anon");
        filterChainMap.put("/favicon.ico", "anon");
        filterChainMap.put("/password.html", "anon");
        filterChainMap.put("/signin.html", "anon");
        filterChainMap.put("/signup.html", "anon");
        filterChainMap.put("/error/**", "anon");

        // 接口
        filterChainMap.put("/open/**", "anon");
        filterChainMap.put("/captcha/**", "anon");
        filterChainMap.put("/**/password/**", "anon");
        filterChainMap.put("/signin", "anon");
        filterChainMap.put("/signup", "anon");
        filterChainMap.put("/app/signin", "anon");
        filterChainMap.put("/web/token", "anon");
        filterChainMap.put("/app/token", "anon");
        filterChainMap.put("/app/**", "appUser");
        filterChainMap.put("/druid/**", "perms[admin]"); // 未配置
        filterChainMap.put("/**", "webUser"); // 需要登陆的html app用户无法访问

        // 自定义filter
        WebUserFilter webUserFilter = new WebUserFilter(authenticator);
        webUserFilter.setSsoAuthUrl(ssoProperties.getSsoAuthUri());
        webUserFilter.setRedirectUriName(ssoProperties.getRedirectUriName());
        AppUserFilter appUserFilter = new AppUserFilter(authenticator);
        Map<String, Filter> filters = new LinkedHashMap<>();
        filters.put("webUser", webUserFilter);
        filters.put("appUser", appUserFilter);

        ShiroFilterFactoryBean bean = new ShiroFilterFactoryBean();
        bean.setSecurityManager(securityManager);
        bean.setFilterChainDefinitionMap(filterChainMap);
        bean.setFilters(filters);

        return bean;
    }

    @Bean
    public SecurityManager securityManager(UsernamePasswordRealm usernamePasswordRealm) {
        List<Realm> realms = new ArrayList<>();
        realms.add(usernamePasswordRealm);

        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setRealms(realms);
        return securityManager;
    }
}
