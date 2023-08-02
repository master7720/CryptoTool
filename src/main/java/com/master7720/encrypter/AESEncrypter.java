package com.master7720.encrypter;

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class AESEncrypter {
    private static final String ALGORITHM = "AES";
    private static final String CIPHER_ALGORITHM = "AES/ECB/PKCS5Padding";
    private static final int NUM_THREADS = Runtime.getRuntime().availableProcessors();

    public static String encrypt(String key, String text) throws EncryptionException {
        SecretKeySpec secretKey = new SecretKeySpec(key.getBytes(StandardCharsets.UTF_8), ALGORITHM);
        Cipher cipher;
        try {
            cipher = Cipher.getInstance(CIPHER_ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException e) {
            throw new EncryptionException("Encryption failed.", e);
        }

        ExecutorService executorService = Executors.newFixedThreadPool(NUM_THREADS);
        int textLength = text.length();
        int chunkSize = (textLength + NUM_THREADS - 1) / NUM_THREADS;
        byte[] encryptedBytes = new byte[textLength];

        try {
            for (int i = 0; i < textLength; i += chunkSize) {
                int endIndex = Math.min(i + chunkSize, textLength);
                String chunk = text.substring(i, endIndex);
                int chunkIndex = i;
                executorService.execute(() -> {
                    try {
                        byte[] chunkBytes = chunk.getBytes(StandardCharsets.UTF_8);
                        byte[] encryptedChunk = cipher.doFinal(chunkBytes);
                        System.arraycopy(encryptedChunk, 0, encryptedBytes, chunkIndex, encryptedChunk.length);
                    } catch (IllegalBlockSizeException | BadPaddingException e) {
                        throw new RuntimeException(e);
                    }
                });
            }
        } finally {
            executorService.shutdown();
            try {
                executorService.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }

        return Base64.getEncoder().encodeToString(encryptedBytes);
    }

    public static String encrypt(String text) {
        return text;
    }

    public static class EncryptionException extends Exception {
        public EncryptionException(String message, Throwable cause) {
            super(message, cause);
        }
    }
}