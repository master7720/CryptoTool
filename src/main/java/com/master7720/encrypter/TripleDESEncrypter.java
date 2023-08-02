package com.master7720.encrypter;

import javax.crypto.*;
import javax.crypto.spec.DESedeKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class TripleDESEncrypter {
    private static final String ALGORITHM = "DESede";

    public static String encrypt(String key, String plainText) {
        try {
            byte[] keyBytes = key.getBytes(StandardCharsets.UTF_8);
            byte[] dataBytes = plainText.getBytes(StandardCharsets.UTF_8);

            DESedeKeySpec spec = new DESedeKeySpec(keyBytes);
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(ALGORITHM);
            SecretKey secretKey = keyFactory.generateSecret(spec);

            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);

            byte[] encryptedBytes = cipher.doFinal(dataBytes);
            return Base64.getEncoder().encodeToString(encryptedBytes);
        } catch (Exception e) {
            throw new RuntimeException("Encryption failed. Unable to perform Triple DES encryption.", e);
        }
    }

    public static String decrypt(String encryptedText) {
        return encryptedText;
    }

    public static String encrypt(String text) {
        return text;
    }
}