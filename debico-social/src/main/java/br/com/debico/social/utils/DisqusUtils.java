package br.com.debico.social.utils;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Formatter;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

public class DisqusUtils {

    private static final String HMAC_SHA1_ALGORITHM = "HmacSHA1";

    private DisqusUtils() {

    }

    /**
     * Implementa o algorítimo de assinatura para realizar o SSO no Disqus.
     * 
     * @since 2.0.5
     * @see <a
     *      href="https://github.com/disqus/DISQUS-API-Recipes/blob/master/sso/java/sso.java">SSO
     *      Disqus Java Impl</a>
     * @param data
     * @param key
     * @return a chave com a assinatura definida
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeyException
     */
    public static String calcularRFC2104HMAC(String data, String key)
	    throws NoSuchAlgorithmException, InvalidKeyException {

	SecretKeySpec signingKey = new SecretKeySpec(key.getBytes(),
		HMAC_SHA1_ALGORITHM);
	Mac mac = Mac.getInstance(HMAC_SHA1_ALGORITHM);
	mac.init(signingKey);

	return toHexString(mac.doFinal(data.getBytes()));
    }

    private static String toHexString(byte[] bytes) {
	Formatter formatter = new Formatter();
	for (byte b : bytes) {
	    formatter.format("%02x", b);
	}
	String hexString = formatter.toString();
	formatter.close();

	return hexString;
    }

}
