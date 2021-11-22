package cn.tucci.sso.server.core.support;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;

/**
 * @author tucci.lee
 */
public class JwtTokenCodec implements TokenCodec {

    private Logger logger = LoggerFactory.getLogger(getClass());

    public static final String UID = "uid";

    private String secret;

    public JwtTokenCodec(String secret){
        this.secret = secret;
    }

    @Override
    public String encode(Long uid) {
        return JWT.create()
                .withIssuedAt(new Date())
                .withClaim(UID, uid)
                .sign(Algorithm.HMAC256(secret));
    }

    @Override
    public Long decode(String token) {
        JWTVerifier verifier = JWT.require(Algorithm.HMAC256(secret)).build();
        try {
            DecodedJWT verify = verifier.verify(token);
            return verify.getClaim(UID).asLong();
        }catch (RuntimeException e){
            return null;
        }
    }

}
