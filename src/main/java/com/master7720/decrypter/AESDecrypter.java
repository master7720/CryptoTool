package com.master7720.decrypter;

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class AESDecrypter {
    private static final String ALGORITHM = "AES";
    private static final String CIPHER_ALGORITHM = "AES/ECB/PKCS5Padding";
    private static final int NUM_THREADS = Runtime.getRuntime().availableProcessors();
    private static final int CHUNK_SIZE = 100;

    public static String decrypt(String key, String encryptedText) {
        byte[] encryptedBytes = Base64.getDecoder().decode(encryptedText);
        SecretKeySpec secretKey = new SecretKeySpec(key.getBytes(StandardCharsets.UTF_8), ALGORITHM);
        int length = encryptedBytes.length;
        byte[] decryptedBytes = new byte[length];

        ExecutorService executorService = Executors.newFixedThreadPool(NUM_THREADS);
        try {
            for (int i = 0; i < length; i += CHUNK_SIZE) {
                int endIndex = Math.min(i + CHUNK_SIZE, length);
                byte[] chunk = new byte[endIndex - i];
                System.arraycopy(encryptedBytes, i, chunk, 0, chunk.length);
                int chunkIndex = i;
                executorService.execute(() -> {
                    try {
                        Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
                        cipher.init(Cipher.DECRYPT_MODE, secretKey);
                        byte[] decryptedChunk = cipher.doFinal(chunk);
                        synchronized (decryptedBytes) {
                            System.arraycopy(decryptedChunk, 0, decryptedBytes, chunkIndex, decryptedChunk.length);
                        }
                    } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | IllegalBlockSizeException | BadPaddingException e) {
                        throw new RuntimeException(e);
                    }
                });
            }
        } finally {
            executorService.shutdown();
        }

        // Wait for all threads to complete
        while (!executorService.isTerminated()) {
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        return new String(decryptedBytes, StandardCharsets.UTF_8);
    }

    public static String decrypt(String encryptedText) {
        return encryptedText;
    }
}