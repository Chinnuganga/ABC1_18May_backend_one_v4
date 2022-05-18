package com.scripted.generic;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.AlgorithmParameters;
import java.security.GeneralSecurityException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.log4j.Logger;

import com.scripted.dataload.PropertyDriver;

public class GenericUtils {
	
	private static final Logger log = Logger.getLogger(GenericUtils.class);

    private static SecretKeySpec createSecretKey(char[] password, byte[] salt, int iterationCount, int keyLength) throws NoSuchAlgorithmException, InvalidKeySpecException {
    	 SecretKey keyTmp = null;
    	try {
    	SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA512");
        PBEKeySpec keySpec = new PBEKeySpec(password, salt, iterationCount, keyLength);
         keyTmp = keyFactory.generateSecret(keySpec);
       } catch (Exception ex) {
			log.error("Error while creating secret key "+"Exception :"+ex);
       }
       return new SecretKeySpec(keyTmp.getEncoded(), "AES");
    }

    public static String encryptPass(String password) throws GeneralSecurityException, UnsupportedEncodingException {
    	byte[] cryptoText = null;
    	byte[] iv = null;
    	try {
    	SecretKeySpec key = genrateKey();
        Cipher pbeCipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        pbeCipher.init(Cipher.ENCRYPT_MODE, key);
        AlgorithmParameters parameters = pbeCipher.getParameters();
        IvParameterSpec ivParameterSpec = parameters.getParameterSpec(IvParameterSpec.class);
        cryptoText = pbeCipher.doFinal(password.getBytes("UTF-8"));
        iv = ivParameterSpec.getIV();
    	 } catch (Exception ex) {
 			log.error("Error while encrypting password"+"Exception :"+ex);
        }
        return base64Encode(iv) + ":" + base64Encode(cryptoText);
    }

    private static String base64Encode(byte[] bytes) {
        return Base64.getEncoder().encodeToString(bytes);
    }

    public static String decryptPass(String encpass) throws GeneralSecurityException, IOException {
    	Cipher pbeCipher = null;
    	String property = null;
    	try {
    	SecretKeySpec key = genrateKey();
        String iv = encpass.split(":")[0];
         property = encpass.split(":")[1];
         pbeCipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        pbeCipher.init(Cipher.DECRYPT_MODE, key, new IvParameterSpec(base64Decode(iv)));
    	 } catch (Exception ex) {
  			log.error("Error while decrypting password"+"Exception :"+ex);
         }
        return new String(pbeCipher.doFinal(base64Decode(property)), "UTF-8");
    }

    private static byte[] base64Decode(String property) throws IOException {
        return Base64.getDecoder().decode(property);
    }
    private static SecretKeySpec genrateKey()
    {
    	try {
    		String passHint = "Skriptmate";
        // The salt (probably) can be stored along with the encrypted data
        byte[] salt = new String("192837465").getBytes();
        // Decreasing this speeds down startup time and can be useful during testing, but it also makes it easier for brute force attackers
        int iterationCount = 40000;
        // Other values give me java.security.InvalidKeyException: Illegal key size or default parameters
        int keyLength = 128;
        SecretKeySpec key;
			key = createSecretKey(passHint.toCharArray(),
			        salt, iterationCount, keyLength);
			return key;
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			log.error("Error while generating key"+"Exception :"+e);
		} catch (InvalidKeySpecException e) {
			e.printStackTrace();
			log.error("Error while generating key"+"Exception :"+e);
		}
		return null;
    }
    
}
