package cn.tucci.sso.server.core.utils;

/**
 * @author tucci.lee
 */
public class RandomUtil {

    private static final String INT = "1234567890";
    private static final String STRING = "qwertyuiopasdfghjklzxcvbnmQWERTYUIOPASDFGHJKLZXCVBNM1234567890";

    public static String random(char[] chars, int num) {
        StringBuilder sb = new StringBuilder();
        for (int i=0; i<num; i++) {
            int random = (int) (Math.random() * chars.length);
            sb.append(chars[random]);
        }
        return sb.toString();
    }

    public static String random(int num){
        char[] chars = STRING.toCharArray();
        return random(chars, num);
    }

    public static String randomInt(int num){
        char[] chars = INT.toCharArray();
        return random(chars, num);
    }
}
