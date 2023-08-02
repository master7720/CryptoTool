package com.master7720.decrypter;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.ArrayList;
import java.util.List;

public class BinaryDecrypter {
    private static final int NUM_THREADS = Runtime.getRuntime().availableProcessors();

    public static String decrypt(String binaryText) {
        ExecutorService executorService = Executors.newFixedThreadPool(NUM_THREADS);
        List<Future<String>> futures = new ArrayList<>();

        try {
            for (int i = 0; i < binaryText.length(); i += 8) {
                final String binaryChunk = binaryText.substring(i, Math.min(i + 8, binaryText.length()));
                Future<String> future = executorService.submit(() -> {
                    int decimalValue = Integer.parseInt(binaryChunk, 2);
                    return String.valueOf((char) decimalValue);
                });
                futures.add(future);
            }

            StringBuilder decryptedText = new StringBuilder();
            for (Future<String> future : futures) {
                String decryptedChunk = getFutureResult(future);
                decryptedText.append(decryptedChunk);
            }

            return decryptedText.toString();
        } catch (Exception e) {
            throw new RuntimeException("Decryption failed.", e);
        } finally {
            executorService.shutdown();
        }
    }

    private static String getFutureResult(Future<String> future) {
        try {
            return future.get();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}