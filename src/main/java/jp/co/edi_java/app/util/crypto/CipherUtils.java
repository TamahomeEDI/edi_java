package jp.co.edi_java.app.util.crypto;

import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.EncoderException;
import org.apache.commons.codec.net.URLCodec;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class CipherUtils {

	// 16, 24, 32
	private static final String AES_KEY = "qazWsxEdi@OrderTamahomeEdcRfv123";

	public static String getEncryptAESWithURIEncode(String text) {
		URLCodec codec = new URLCodec("UTF-8");
		String uriText = encrypt(text, AES_KEY, "AES");
		try {
			uriText = codec.encode(uriText);
		} catch (EncoderException e) {
			log.info(e.getMessage());
		}
		return uriText;
	}

	public static String getDecryptAESWithURIDecode(String text) {
		URLCodec codec = new URLCodec("UTF-8");
		String uriText = "";
		try {
			uriText = codec.decode(text);
		} catch (DecoderException e) {
			log.info(e.getMessage());
		}
		return decrypt(uriText, AES_KEY, "AES");
	}

	public static String getEncryptAES(String text) {
		return encrypt(text, AES_KEY, "AES");
	}

	public static String getDecryptAES(String text) {
		return decrypt(text, AES_KEY, "AES");
	}

	public static String encrypt(String text, String key, String algorithm) {
		String ret = "";
		try {
			Cipher cipher = Cipher.getInstance(algorithm);
			cipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(key.getBytes(StandardCharsets.UTF_8), algorithm));
			ret = new String (Base64.getUrlEncoder().encode(cipher.doFinal(text.getBytes(StandardCharsets.UTF_8))));

		} catch (NoSuchAlgorithmException e) {
			log.info(e.getMessage());
			//throw new CoreRuntimeException(e);
		} catch (NoSuchPaddingException e) {
			log.info(e.getMessage());
			//throw new CoreRuntimeException(e);
		} catch (InvalidKeyException e) {
			log.info(e.getMessage());
			//throw new CoreRuntimeException(e);
		} catch (IllegalBlockSizeException e) {
			log.info(e.getMessage());
			//throw new CoreRuntimeException(e);
		} catch (BadPaddingException e) {
			log.info(e.getMessage());
			//throw new CoreRuntimeException(e);
		}
		return ret;
    }

    public static String decrypt(String text, String key, String algorithm) {
    	String ret = "";
    	try {
    		Cipher cipher = Cipher.getInstance(algorithm);
            cipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(key.getBytes(StandardCharsets.UTF_8), algorithm));
            ret = new String (cipher.doFinal(Base64.getUrlDecoder().decode(text.getBytes(StandardCharsets.UTF_8))));

    	} catch (NoSuchAlgorithmException e) {
    		log.info(e.getMessage());
			//throw new CoreRuntimeException(e);
		} catch (NoSuchPaddingException e) {
			log.info(e.getMessage());
			//throw new CoreRuntimeException(e);
		} catch (InvalidKeyException e) {
			log.info(e.getMessage());
			//throw new CoreRuntimeException(e);
		} catch (IllegalBlockSizeException e) {
			log.info(e.getMessage());
			//throw new CoreRuntimeException(e);
		} catch (BadPaddingException e) {
			log.info(e.getMessage());
			//throw new CoreRuntimeException(e);
		}
		return ret;
    }

}
