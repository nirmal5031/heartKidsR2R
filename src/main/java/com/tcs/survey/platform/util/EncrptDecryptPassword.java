package com.tcs.survey.platform.util;

import java.security.Key;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.stereotype.Service;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

@Service
public class EncrptDecryptPassword 
	{
	    private static final String ALGORITHM = "AES";
	    private static final String KEY = "1Hbfh667adfDEJ78";
	    
	    public static String encrypt(String password) throws Exception
	    {
	        Key key = generateKey();
	        Cipher cipher = Cipher.getInstance(ALGORITHM);
	        cipher.init(Cipher.ENCRYPT_MODE, key);
	        byte [] encryptedByteValue = cipher.doFinal(password.getBytes("utf-8"));
	        String encryptedValue64 = new BASE64Encoder().encode(encryptedByteValue);
	        return encryptedValue64;
	               
	    }
	    
	    public static String decrypt(String value) throws Exception
	    {
	        Key key = generateKey();
	        Cipher cipher = Cipher.getInstance(ALGORITHM);
	        cipher.init(Cipher.DECRYPT_MODE, key);
	        byte [] decryptedValue64 = new BASE64Decoder().decodeBuffer(value);
	        byte [] decryptedByteValue = cipher.doFinal(decryptedValue64);
	        String decryptedValue = new String(decryptedByteValue,"utf-8");
	        return decryptedValue;
	                
	    }
	    
	    private static Key generateKey() throws Exception 
	    {
	        Key key = new SecretKeySpec(KEY.getBytes(),ALGORITHM);
	        return key;
	    }
	}
