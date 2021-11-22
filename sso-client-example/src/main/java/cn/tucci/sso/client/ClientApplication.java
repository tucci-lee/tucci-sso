package cn.tucci.sso.client;

import cn.tucci.sso.core.util.WebUtils;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@SpringBootApplication
@RestController
public class ClientApplication {
    public static void main(String[] args) {
        SpringApplication.run(ClientApplication.class, args);
    }

    @Value("${sso.client.web.cookie-name}")
    private String cookieName;

    @GetMapping("web")
    public String web(HttpServletRequest request) throws IOException {
        String token = WebUtils.getCookieValue(request, cookieName);
        OkHttpClient client = new OkHttpClient();
        Request req = new Request.Builder()
                .url("http://sso.2cci.cn/profile")
                .header("cookie", cookieName + "=" + token)
                .build();
        return client.newCall(req).execute().body().string();
    }

    @GetMapping("app")
    public String app(HttpServletRequest request) throws IOException {
        String token = WebUtils.getBearer(request);
        OkHttpClient client = new OkHttpClient();
        Request req = new Request.Builder()
                .url("http://sso.2cci.cn/app/profile")
                .header("Authorization", "Bearer " + token)
                .build();
        return client.newCall(req).execute().body().string();
    }

}
