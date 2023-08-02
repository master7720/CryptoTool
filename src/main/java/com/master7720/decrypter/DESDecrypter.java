package com.master7720.decrypter;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import java.util.Base64;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class DESDecrypter {

    private static final int NUM_THREADS = Runtime.getRuntime().availableProcessors();

    private static SecretKey secretKey;
    private static ExecutorService executorService;

    public DESDecrypter(SecretKey secretKey) {
        this.secretKey = secretKey;
        this.executorService = Executors.newFixedThreadPool(NUM_THREADS);
    }

    public static String decrypt(String encryptedText) {
        int chunkSize = encryptedText.length() / NUM_THREADS;

        Future<String>[] futures = new Future[NUM_THREADS];

        for (int i = 0; i < NUM_THREADS; i++) {
            int startIndex = i * chunkSize;
            int endIndex = (i == NUM_THREADS - 1) ? encryptedText.length() : (i + 1) * chunkSize;
            String chunk = encryptedText.substring(startIndex, endIndex);
            futures[i] = executorService.submit(() -> decryptChunk(chunk));
        }

        StringBuilder decryptedText = new StringBuilder();
        for (Future<String> future : futures) {
            try {
                String result = future.get();
                decryptedText.append(result);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

        return decryptedText.toString();
    }

    private static String decryptChunk(String chunk) throws Exception {
        Cipher cipher = Cipher.getInstance("DES");
        cipher.init(Cipher.DECRYPT_MODE, secretKey);
        byte[] encryptedBytes = Base64.getDecoder().decode(chunk);
        byte[] decryptedBytes = cipher.doFinal(encryptedBytes);
        return new String(decryptedBytes);
    }

    public void shutdown() {
        executorService.shutdown();
    }
}