package cn.tucci.sso.client.autoconfigure;

/**
 * @author tucci.lee
 */
public class WebFilterProperties {

    private String ssoAuthUrl;
    private String tokenAuthUrl;
    private String redirectUriName = "redirect_uri";
    private String cookieName = "sso-token";
    private String[] urlPatterns;
    private String[] exclusions;

    public String getSsoAuthUrl() {
        return ssoAuthUrl;
    }

    public void setSsoAuthUrl(String ssoAuthUrl) {
        this.ssoAuthUrl = ssoAuthUrl;
    }

    public String getTokenAuthUrl() {
        return tokenAuthUrl;
    }

    public void setTokenAuthUrl(String tokenAuthUrl) {
        this.tokenAuthUrl = tokenAuthUrl;
    }

    public String getRedirectUriName() {
        return redirectUriName;
    }

    public void setRedirectUriName(String redirectUriName) {
        this.redirectUriName = redirectUriName;
    }

    public String getCookieName() {
        return cookieName;
    }

    public void setCookieName(String cookieName) {
        this.cookieName = cookieName;
    }

    public String[] getExclusions() {
        return exclusions;
    }

    public void setExclusions(String[] exclusions) {
        this.exclusions = exclusions;
    }

    public String[] getUrlPatterns() {
        return urlPatterns;
    }

    public void setUrlPatterns(String[] urlPatterns) {
        this.urlPatterns = urlPatterns;
    }
}
