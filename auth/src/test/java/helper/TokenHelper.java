package helper;

import io.jsonwebtoken.security.Keys;

import java.security.Key;
import java.util.Arrays;

public class TokenHelper {

    public static Key createSigningKey() {
        byte[] signingKeyBytes = new byte[64];
        Arrays.fill(signingKeyBytes, (byte) 0);
        return Keys.hmacShaKeyFor(signingKeyBytes);
    }
}
