package com.master7720.decrypter;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Base16Decrypter {
    private static final int NUM_THREADS = Runtime.getRuntime().availableProcessors();
    private static final int CHUNK_SIZE = 100;

    public static String decrypt(String encryptedText) {
        if (encryptedText.length() % 2 != 0) {
            throw new IllegalArgumentException("Invalid Base16 encoded text length.");
        }

        int length = encryptedText.length();
        ExecutorService executorService = Executors.newFixedThreadPool(NUM_THREADS);
        StringBuilder decryptedText = new StringBuilder();

        try {
            for (int i = 0; i < length; i += CHUNK_SIZE) {
                int endIndex = Math.min(i + CHUNK_SIZE, length);
                String chunk = encryptedText.substring(i, endIndex);
                executorService.execute(() -> {
                    String decryptedChunk = decryptChunk(chunk);
                    decryptedText.append(decryptedChunk);
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

        return decryptedText.toString();
    }

    private static String decryptChunk(String chunk) {
        StringBuilder decryptedChunk = new StringBuilder();
        for (int i = 0; i < chunk.length() - 1; i += 2) {
            String hexValue = chunk.substring(i, i + 2);
            try {
                char decryptedChar = (char) Integer.parseInt(hexValue, 16);
                decryptedChunk.append(decryptedChar);
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("Invalid character in Base16 encoded text: " + e.getMessage());
            }
        }
        return decryptedChunk.toString();
    }
}