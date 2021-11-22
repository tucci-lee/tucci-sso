package cn.tucci.sso.server.core.utils;

import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

/**
 * @author tucci.lee
 */
public class RsaUtil {

    public static KeyPair generateKeyPair(int keysize) {
        try {
            KeyPairGenerator rsa = KeyPairGenerator.getInstance("RSA");
            rsa.initialize(keysize);
            return rsa.generateKeyPair();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static String getBase64Public(KeyPair keyPair) {
        PublicKey publicKey = keyPair.getPublic();
        byte[] encoded = publicKey.getEncoded();
        return Base64.getEncoder().encodeToString(encoded);
    }

    public static String getBase64Private(KeyPair keyPair) {
        PrivateKey privateKey = keyPair.getPrivate();
        byte[] encoded = privateKey.getEncoded();
        return Base64.getEncoder().encodeToString(encoded);
    }

    public static RSAPublicKey getPublic(String base64Public) {
        try {
            byte[] decode = Base64.getDecoder().decode(base64Public);
            X509EncodedKeySpec keySpec = new X509EncodedKeySpec(decode);
            return (RSAPublicKey) KeyFactory.getInstance("RSA").generatePublic(keySpec);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static RSAPrivateKey getPrivate(String base64Private) {
        try {
            byte[] decode = Base64.getDecoder().decode(base64Private);
            PKCS8EncodedKeySpec priPKCS8 = new PKCS8EncodedKeySpec(decode);
            return (RSAPrivateKey) KeyFactory.getInstance("RSA").generatePrivate(priPKCS8);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
