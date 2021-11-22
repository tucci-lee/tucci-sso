package cn.tucci.sso.client.autoconfigure;

/**
 * @author tucci.lee
 */
public class AppFilterProperties {

    private String tokenAuthUrl;
    private String[] urlPatterns;
    private String[] exclusions;

    public String getTokenAuthUrl() {
        return tokenAuthUrl;
    }

    public void setTokenAuthUrl(String tokenAuthUrl) {
        this.tokenAuthUrl = tokenAuthUrl;
    }

    public String[] getUrlPatterns() {
        return urlPatterns;
    }

    public void setUrlPatterns(String[] urlPatterns) {
        this.urlPatterns = urlPatterns;
    }

    public String[] getExclusions() {
        return exclusions;
    }

    public void setExclusions(String[] exclusions) {
        this.exclusions = exclusions;
    }
}
