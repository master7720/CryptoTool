package com.master7720.decrypter;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class TripleDESDecrypter {
    private static final String ALGORITHM = "DESede";

    public static String decrypt(String key, String encryptedText) throws Exception {
        ExecutorService decryptExecutor = Executors.newSingleThreadExecutor();
        try {
            Future<String> future = decryptExecutor.submit(() -> {
                return performDecryption(key, encryptedText);
            });
            return future.get();
        } finally {
            decryptExecutor.shutdown();
        }
    }

    private static String performDecryption(String key, String encryptedText) {
        try {
            byte[] keyBytes = key.getBytes(StandardCharsets.UTF_8);
            byte[] encryptedBytes = Base64.getDecoder().decode(encryptedText);

            DESedeKeySpec spec = new DESedeKeySpec(keyBytes);
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(ALGORITHM);
            SecretKey secretKey = keyFactory.generateSecret(spec);

            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, secretKey);

            byte[] decryptedBytes = cipher.doFinal(encryptedBytes);
            return new String(decryptedBytes, StandardCharsets.UTF_8);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static String decrypt(String encryptedText) {
        return encryptedText;
    }
}