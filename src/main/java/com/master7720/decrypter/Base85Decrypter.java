package com.master7720.decrypter;

import java.nio.charset.StandardCharsets;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Base85Decrypter {
    private static final int NUM_THREADS = Runtime.getRuntime().availableProcessors();
    private static final int CHUNK_SIZE = 100;

    public static String decrypt(String encryptedText) {
        int length = encryptedText.length();
        if (length % 5 != 0) {
            throw new IllegalArgumentException("Invalid Base85 encoded text length.");
        }

        ExecutorService executorService = Executors.newFixedThreadPool(NUM_THREADS);
        byte[] decryptedData = new byte[length * 4 / 5];

        try {
            for (int i = 0; i < length; i += CHUNK_SIZE) {
                int endIndex = Math.min(i + CHUNK_SIZE, length);
                String chunk = encryptedText.substring(i, endIndex);
                int chunkIndex = i;
                executorService.execute(() -> {
                    byte[] decryptedChunk = decryptChunk(chunk);
                    System.arraycopy(decryptedChunk, 0, decryptedData, chunkIndex * 4 / 5, decryptedChunk.length);
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

        // Remove padding bytes if any
        int padding = 0;
        for (int i = length - 1; i >= length - 5; i--) {
            if (encryptedText.charAt(i) == 'z') {
                padding++;
            } else {
                break;
            }
        }

        byte[] resultData = new byte[decryptedData.length - padding];
        System.arraycopy(decryptedData, 0, resultData, 0, resultData.length);

        // Convert decrypted bytes to String using UTF-8 encoding
        return new String(resultData, StandardCharsets.UTF_8);
    }

    private static byte[] decryptChunk(String chunk) {
        int chunkLength = chunk.length();
        byte[] decryptedChunk = new byte[chunkLength * 4 / 5];
        int dataIndex = 0;

        for (int i = 0; i < chunkLength; i += 5) {
            int value = 0;
            for (int j = 0; j < 5; j++) {
                char c = chunk.charAt(i + j);
                if (c < 33 || c > 117) {
                    throw new IllegalArgumentException("Invalid character in Base85 encoded text.");
                }
                value = value * 85 + (c - 33);
            }
            for (int j = 3; j >= 0; j--) {
                decryptedChunk[dataIndex++] = (byte) (value >> (j * 8) & 0xFF);
            }
        }

        return decryptedChunk;
    }
}