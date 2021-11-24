package cn.tucci.sso.server.controller;

import cn.tucci.sso.server.config.properties.SsoProperties;
import cn.tucci.sso.server.core.support.Authenticator;
import cn.tucci.sso.server.core.utils.UrlUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;

/**
 * @author tucci.lee
 */
@Controller
public class HtmlController {

    private final Authenticator authenticator;
    private final SsoProperties ssoProperties;

    public HtmlController(Authenticator authenticator,
                          SsoProperties ssoProperties) {
        this.authenticator = authenticator;
        this.ssoProperties = ssoProperties;
    }

    @GetMapping("signin.html")
    public String signin(HttpServletRequest request) {
        Long uid = authenticator.isWebSignin();
        if (uid != null) {
            String redirectUri = request.getParameter(ssoProperties.getRedirectUriName());
            redirectUri = UrlUtil.getRedirectUri(redirectUri, ssoProperties.getDefaultRedirectUri());
            return "redirect:" + redirectUri;
        }
        return "index";
    }

    @GetMapping("**.html")
    public String html(){
        return "index";
    }

    @GetMapping("/")
    public String index(){
        return "index";
    }
}
