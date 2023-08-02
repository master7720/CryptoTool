package com.master7720.encrypter;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

public class DESEncrypter {
    private static final ThreadLocal<SecretKey> secretKeyThreadLocal = new ThreadLocal<>();

    public DESEncrypter() throws NoSuchAlgorithmException {
        if (secretKeyThreadLocal.get() == null) {
            // Generate DES secret key only if not already generated for this thread
            KeyGenerator keyGenerator = KeyGenerator.getInstance("DES");
            SecureRandom secureRandom = new SecureRandom();
            keyGenerator.init(secureRandom);
            secretKeyThreadLocal.set(keyGenerator.generateKey());
        }
    }

    public static String encrypt(String text) throws Exception {
        Cipher cipher = Cipher.getInstance("DES");
        cipher.init(Cipher.ENCRYPT_MODE, secretKeyThreadLocal.get());
        try {
            byte[] encryptedBytes = cipher.doFinal(text.getBytes());
            return Base64.getEncoder().encodeToString(encryptedBytes);
        } catch (Exception e) {
            throw new RuntimeException("Encryption failed.", e);
        }
    }
}