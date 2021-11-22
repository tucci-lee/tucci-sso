package cn.tucci.sso.client.autoconfigure;

import cn.tucci.sso.core.filter.AppFilter;
import cn.tucci.sso.core.filter.WebFilter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author tucci.lee
 */
@Configuration
@EnableConfigurationProperties(SsoClientProperties.class)
public class SsoClientAutoConfigure {

    @Bean
    @ConditionalOnProperty(name = {"sso.client.web.enabled"}, havingValue = "true")
    public FilterRegistrationBean<WebFilter> webFilter(SsoClientProperties properties) {
        WebFilterProperties config = properties.getWeb();
        WebFilter webFilter = new WebFilter();
        if (config.getCookieName() != null) {
            webFilter.setCookieName(config.getCookieName());
        }
        if (config.getRedirectUriName() != null) {
            webFilter.setRedirectUriName(config.getRedirectUriName());
        }
        if (config.getSsoAuthUrl() != null) {
            webFilter.setSsoAuthUrl(config.getSsoAuthUrl());
        }
        if (config.getTokenAuthUrl() != null) {
            webFilter.setTokenAuthUrl(config.getTokenAuthUrl());
        }
        if (config.getExclusions() != null) {
            webFilter.setExclusions(config.getExclusions());
        }
        FilterRegistrationBean<WebFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(webFilter);
        registrationBean.addUrlPatterns(config.getUrlPatterns() != null ? config.getUrlPatterns() : new String[]{"/*"});
        return registrationBean;
    }

    @Bean
    @ConditionalOnProperty(name = {"sso.client.app.enabled"}, havingValue = "true")
    public FilterRegistrationBean<AppFilter> appFilter(SsoClientProperties properties) {
        AppFilterProperties config = properties.getApp();
        AppFilter appFilter = new AppFilter();
        if (config.getTokenAuthUrl() != null) {
            appFilter.setTokenAuthUrl(config.getTokenAuthUrl());
        }
        if (config.getExclusions() != null) {
            appFilter.setExclusions(config.getExclusions());
        }
        FilterRegistrationBean<AppFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(appFilter);
        registrationBean.addUrlPatterns(config.getUrlPatterns() != null ? config.getUrlPatterns() : new String[]{"/*"});
        return registrationBean;
    }
}
