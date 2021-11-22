package cn.tucci.sso.client.autoconfigure;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author tucci.lee
 */
@ConfigurationProperties("sso.client")
public class SsoClientProperties {

    private WebFilterProperties web;

    private AppFilterProperties app;

    public WebFilterProperties getWeb() {
        return web;
    }

    public void setWeb(WebFilterProperties web) {
        this.web = web;
    }

    public AppFilterProperties getApp() {
        return app;
    }

    public void setApp(AppFilterProperties app) {
        this.app = app;
    }
}
