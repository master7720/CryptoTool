package com.master7720.decrypter;

import java.util.Base64;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Base64Decrypter {
    private static final int NUM_THREADS = Runtime.getRuntime().availableProcessors();
    private static final int CHUNK_SIZE = 100;

    public static String decrypt(String encryptedText) {
        ExecutorService executorService = Executors.newFixedThreadPool(NUM_THREADS);
        int length = encryptedText.length();
        byte[] decodedBytes = new byte[length];

        try {
            for (int i = 0; i < length; i += CHUNK_SIZE) {
                int endIndex = Math.min(i + CHUNK_SIZE, length);
                String chunk = encryptedText.substring(i, endIndex);
                int chunkIndex = i;
                executorService.execute(() -> {
                    byte[] decodedChunk = Base64.getDecoder().decode(chunk);
                    System.arraycopy(decodedChunk, 0, decodedBytes, chunkIndex, decodedChunk.length);
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

        return new String(decodedBytes);
    }
}