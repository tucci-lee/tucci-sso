package cn.tucci.sso.core.filter;

import cn.tucci.sso.core.util.WebUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import okhttp3.OkHttpClient;
import okhttp3.Request;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

/**
 * @author tucci.lee
 */
public class WebFilter extends AbstractFilter {

    private String ssoAuthUrl = "http://localhost:8080/signin.html";
    private String tokenAuthUrl = "http://localhost:8080/auth";
    private String redirectUriName = "redirectUri";
    private String cookieName = "sso-token";

    /**
     * 请求sso server，token是否有效
     *
     * @param request  request
     * @param response response
     * @return boolean
     * @throws IOException e
     */
    @Override
    protected boolean isAccessAllowed(HttpServletRequest request, HttpServletResponse response) throws IOException {
        OkHttpClient client = new OkHttpClient();
        Request req = new Request.Builder()
                .url(tokenAuthUrl + "?token=" + WebUtils.getCookieValue(request, cookieName))
                .build();
        String res = client.newCall(req).execute().body().string();
        JSONObject json = JSON.parseObject(res);
        return json.getBoolean("status");
    }

    @Override
    protected void onAccessDenied(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String appect = request.getHeader("Accept");
        if (appect != null && appect.contains("application/json")) {
            PrintWriter out = response.getWriter();
            out.write(errorMsg());
        } else {
            StringBuffer url = request.getRequestURL();
            String queryString = request.getQueryString();
            if (queryString != null && !queryString.isEmpty()) {
                url.append("?").append(queryString);
            }
            String redirectUri = ssoAuthUrl + "?" + redirectUriName + "=" +
                    URLEncoder.encode(url.toString(), StandardCharsets.UTF_8.name());
            response.sendRedirect(redirectUri);
        }
    }

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
}
