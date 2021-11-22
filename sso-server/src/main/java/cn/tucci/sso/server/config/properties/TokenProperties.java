package cn.tucci.sso.server.config.properties;

/**
 * @author tucci.lee
 */
public class TokenProperties {

    private JwtTokenProperties jwt;

    public static class JwtTokenProperties{
        private String secret;

        public String getSecret() {
            return secret;
        }

        public void setSecret(String secret) {
            this.secret = secret;
        }
    }

    public JwtTokenProperties getJwt() {
        return jwt;
    }

    public void setJwt(JwtTokenProperties jwt) {
        this.jwt = jwt;
    }
}
