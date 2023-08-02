package com.master7720.encrypter;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class Ascii85Encrypter {
    private static final int NUM_THREADS = Runtime.getRuntime().availableProcessors();
    private static final int CHUNK_SIZE = 100;

    public static String encrypt(String text) {
        if (text == null || text.isEmpty()) {
            throw new IllegalArgumentException("Input text cannot be null or empty.");
        }

        ExecutorService executorService = Executors.newFixedThreadPool(NUM_THREADS);
        StringBuilder encryptedText = new StringBuilder();

        try {
            int textLength = text.length();
            for (int i = 0; i < textLength; i += CHUNK_SIZE) {
                int endIndex = Math.min(i + CHUNK_SIZE, textLength);
                String chunk = text.substring(i, endIndex);
                Future<String> future = executorService.submit(() -> encryptChunk(chunk));
                try {
                    String encryptedChunk = future.get();
                    encryptedText.append(encryptedChunk);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        } finally {
            executorService.shutdown();
        }

        return encryptedText.toString();
    }

    private static String encryptChunk(String chunk) {
        StringBuilder encryptedChunk = new StringBuilder();
        int count = 0;
        int value = 0;

        for (char c : chunk.toCharArray()) {
            value = value * 256 + c;
            count++;

            if (count == 4) {
                encodeBlock(encryptedChunk, value);
                count = 0;
                value = 0;
            }
        }

        while (count < 4) {
            value = value * 256;
            count++;
        }
        encodeBlock(encryptedChunk, value);

        return encryptedChunk.toString();
    }

    private static void encodeBlock(StringBuilder encryptedChunk, int value) {
        for (int i = 4; i >= 0; i--) {
            int remainder = value % 85;
            value /= 85;
            char c = (char) (remainder + '!');
            encryptedChunk.insert(0, c);
        }
    }
}