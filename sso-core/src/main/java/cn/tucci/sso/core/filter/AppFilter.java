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

/**
 * @author tucci.lee
 */
public class AppFilter extends AbstractFilter {

    private String tokenAuthUrl = "http://localhost:8080/auth";

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
                .url(tokenAuthUrl + "?token=" + WebUtils.getBearer(request))
                .build();
        String res = client.newCall(req).execute().body().string();
        JSONObject json = JSON.parseObject(res);
        return json.getBoolean("status");
    }

    @Override
    protected void onAccessDenied(HttpServletRequest request, HttpServletResponse response) throws IOException {
        PrintWriter out = response.getWriter();
        out.write(errorMsg());
    }

    public String getTokenAuthUrl() {
        return tokenAuthUrl;
    }

    public void setTokenAuthUrl(String tokenAuthUrl) {
        this.tokenAuthUrl = tokenAuthUrl;
    }

}
