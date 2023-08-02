package com.master7720.encrypter;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.List;
import java.util.ArrayList;

public class Base16Encrypter {
    private static final int NUM_THREADS = Runtime.getRuntime().availableProcessors();
    private static final int CHUNK_SIZE = 100;

    public static String encrypt(String text) {
        if (text == null || text.isEmpty()) {
            throw new IllegalArgumentException("Input text cannot be null or empty.");
        }

        ExecutorService executorService = Executors.newFixedThreadPool(NUM_THREADS);
        List<Future<String>> futures = new ArrayList<>();
        StringBuilder encryptedText = new StringBuilder();

        try {
            for (int i = 0; i < text.length(); i += CHUNK_SIZE) {
                int endIndex = Math.min(i + CHUNK_SIZE, text.length());
                String chunk = text.substring(i, endIndex);
                Future<String> future = executorService.submit(() -> encryptChunk(chunk));
                futures.add(future);
            }

            for (Future<String> future : futures) {
                encryptedText.append(future.get());
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            executorService.shutdown();
        }

        return encryptedText.toString();
    }

    private static String encryptChunk(String chunk) {
        StringBuilder encryptedChunk = new StringBuilder();
        for (char c : chunk.toCharArray()) {
            String hexValue = String.format("%02x", (int) c); // Convert char to two-digit hexadecimal value
            encryptedChunk.append(hexValue);
        }
        return encryptedChunk.toString();
    }
}