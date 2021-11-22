package cn.tucci.sso.server.controller;

import cn.tucci.sso.server.config.properties.SsoProperties;
import cn.tucci.sso.server.core.support.Authenticator;
import cn.tucci.sso.server.core.utils.UrlUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

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
    public String signin(String redirect_uri) {
        Long uid = authenticator.isWebSignin();
        if (uid != null) {
            redirect_uri = UrlUtil.getRedirectUri(redirect_uri, ssoProperties.getDefaultRedirectUri());
            return "redirect:" + redirect_uri;
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
