package com.master7720.encrypter;

import javax.crypto.*;
import javax.crypto.spec.DESedeKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class TripleDESEncrypter {
    private static final String ALGORITHM = "DESede";
    private static final int NUM_THREADS = Runtime.getRuntime().availableProcessors();
    private static final ExecutorService encryptExecutor = Executors.newFixedThreadPool(NUM_THREADS);

    public static String encrypt(String key, String plainText) {
        try {
            byte[] keyBytes = key.getBytes(StandardCharsets.UTF_8);
            byte[] dataBytes = plainText.getBytes(StandardCharsets.UTF_8);

            DESedeKeySpec spec = new DESedeKeySpec(keyBytes);
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(ALGORITHM);
            SecretKey secretKey = keyFactory.generateSecret(spec);

            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);

            int length = dataBytes.length;
            int chunkSize = (length + NUM_THREADS - 1) / NUM_THREADS;
            Future<byte[]>[] futures = new Future[NUM_THREADS];

            for (int i = 0; i < NUM_THREADS; i++) {
                int startIndex = i * chunkSize;
                int endIndex = Math.min(startIndex + chunkSize, length);
                byte[] chunk = new byte[endIndex - startIndex];
                System.arraycopy(dataBytes, startIndex, chunk, 0, chunk.length);
                futures[i] = encryptExecutor.submit(() -> cipher.doFinal(chunk));
            }

            StringBuilder encryptedText = new StringBuilder();
            for (Future<byte[]> future : futures) {
                byte[] encryptedChunk = future.get();
                encryptedText.append(Base64.getEncoder().encodeToString(encryptedChunk));
            }

            return encryptedText.toString();
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