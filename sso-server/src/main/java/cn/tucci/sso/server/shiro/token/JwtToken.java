package cn.tucci.sso.server.shiro.token;

import org.apache.shiro.authc.AuthenticationToken;

/**
 * @author tucci.lee
 */
public class JwtToken implements AuthenticationToken {

    private String token;

    public JwtToken(String token){
        this.token = token;
    }

    @Override
    public Object getPrincipal() {
        return token;
    }

    @Override
    public Object getCredentials() {
        return null;
    }

    public String getToken() {
        return token;
    }
}
