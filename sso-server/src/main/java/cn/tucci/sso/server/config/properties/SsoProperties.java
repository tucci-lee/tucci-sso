package cn.tucci.sso.server.config.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author tucci.lee
 */
@ConfigurationProperties(prefix = "sso")
public class SsoProperties {

    private String cookieName;

    private String redirectUriName;

    private String defaultRedirectUri;

    private String ssoAuthUri;

    private String domain;

    private TokenProperties token;

    public String getCookieName() {
        return cookieName;
    }

    public void setCookieName(String cookieName) {
        this.cookieName = cookieName;
    }

    public String getRedirectUriName() {
        return redirectUriName;
    }

    public void setRedirectUriName(String redirectUriName) {
        this.redirectUriName = redirectUriName;
    }

    public String getDefaultRedirectUri() {
        return defaultRedirectUri;
    }

    public void setDefaultRedirectUri(String defaultRedirectUri) {
        this.defaultRedirectUri = defaultRedirectUri;
    }

    public String getSsoAuthUri() {
        return ssoAuthUri;
    }

    public void setSsoAuthUri(String ssoAuthUri) {
        this.ssoAuthUri = ssoAuthUri;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public TokenProperties getToken() {
        return token;
    }

    public void setToken(TokenProperties token) {
        this.token = token;
    }
}
