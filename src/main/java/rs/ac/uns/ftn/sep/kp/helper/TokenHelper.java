package rs.ac.uns.ftn.sep.kp.helper;

import org.apache.commons.lang.RandomStringUtils;

public final class TokenHelper {
    private TokenHelper() {}

    public static String generateRandomToken() {
        return RandomStringUtils.random(32, true, true);
    }
}
