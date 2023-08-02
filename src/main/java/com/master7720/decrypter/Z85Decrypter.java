package com.master7720.decrypter;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class Z85Decrypter {
    private static final int NUM_THREADS = Runtime.getRuntime().availableProcessors();
    private static final int CHUNK_SIZE = 100;

    public static String decrypt(String encryptedText) {
        int length = encryptedText.length();
        if (length % 5 != 0) {
            throw new IllegalArgumentException("Invalid Z85 encoded text length.");
        }

        ExecutorService executorService = Executors.newFixedThreadPool(NUM_THREADS);
        StringBuilder decryptedText = new StringBuilder();

        try {
            Future<String>[] futures = new Future[length / CHUNK_SIZE + 1];
            for (int i = 0; i < length; i += CHUNK_SIZE) {
                int endIndex = Math.min(i + CHUNK_SIZE, length);
                String chunk = encryptedText.substring(i, endIndex);
                final int index = i;
                futures[i / CHUNK_SIZE] = executorService.submit(() -> decryptChunk(chunk, index));
            }

            for (Future<String> future : futures) {
                decryptedText.append(future.get());
            }
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        } finally {
            executorService.shutdown();
        }

        return decryptedText.toString();
    }

    private static String decryptChunk(String chunk, int chunkIndex) {
        StringBuilder decryptedChunk = new StringBuilder(chunk.length());
        long value = 0;

        for (int j = 0; j < 5; j++) {
            char c = chunk.charAt(j);
            int code = c - 33;
            if (code < 0 || code > 84) {
                throw new IllegalArgumentException("Invalid character in Z85 encoded text.");
            }
            value = value * 85 + code;
        }

        for (int j = 0; j < 4; j++) {
            byte b = (byte) (value >> (3 - j) * 8);
            decryptedChunk.append((char) (b & 0xFF));
        }

        return decryptedChunk.toString();
    }
}