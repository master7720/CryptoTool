package com.master7720.decrypter;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Ascii85Decrypter {
    private static final int NUM_THREADS = Runtime.getRuntime().availableProcessors();
    private static final int CHUNK_SIZE = 100;

    public static String decrypt(String encryptedText) {
        ExecutorService executorService = Executors.newFixedThreadPool(NUM_THREADS);
        StringBuilder decryptedText = new StringBuilder();

        try {
            for (int i = 0; i < encryptedText.length(); i += CHUNK_SIZE) {
                int endIndex = Math.min(i + CHUNK_SIZE, encryptedText.length());
                String chunk = encryptedText.substring(i, endIndex);
                executorService.execute(() -> {
                    decryptChunk(decryptedText, chunk);
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

    private static synchronized void decryptChunk(StringBuilder decryptedText, String chunk) {
        int value = 0;
        int count = 0;

        for (char c : chunk.toCharArray()) {
            if (c == '~') {
                break; // Stop at the delimiter
            }

            if (c == 'z') {
                decryptedText.append('\0');
                count++;
            } else if (c >= '!' && c <= 'u') {
                value = value * 85 + (c - '!');
                count++;

                if (count == 5) {
                    decodeBlock(decryptedText, value);
                    count = 0;
                    value = 0;
                }
            } else {
                throw new IllegalArgumentException("Invalid character in Ascii85 encoded text.");
            }
        }
    }

    private static void decodeBlock(StringBuilder decryptedText, int value) {
        for (int i = 3; i >= 0; i--) {
            char c = (char) (value % 256);
            value /= 256;
            decryptedText.insert(0, c);
        }
    }
}