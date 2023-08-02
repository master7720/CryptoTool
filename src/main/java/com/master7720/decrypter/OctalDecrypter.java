package com.master7720.decrypter;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.ArrayList;
import java.util.List;

public class OctalDecrypter {
    static final int NUM_THREADS = Runtime.getRuntime().availableProcessors();
    private static final ExecutorService executorService = Executors.newFixedThreadPool(NUM_THREADS);

    public static String decrypt(String octalText) {
        List<Future<String>> futures = new ArrayList<>();

        try {
            for (int i = 0; i < octalText.length(); i += 3) {
                final String octalChunk = octalText.substring(i, Math.min(i + 3, octalText.length()));
                futures.add(executorService.submit(() -> {
                    int decimalValue = Integer.parseInt(octalChunk, 8);
                    return String.valueOf((char) decimalValue);
                }));
            }

            StringBuilder decryptedText = new StringBuilder();
            for (Future<String> future : futures) {
                decryptedText.append(getFutureResult(future));
            }

            return decryptedText.toString();
        } catch (Exception e) {
            throw new RuntimeException("Decryption failed.", e);
        }
    }

    private static String getFutureResult(Future<String> future) {
        try {
            return future.get();
        } catch (Exception e) {
            throw new RuntimeException("Decryption task execution failed.", e);
        }
    }

    public static void shutdown() {
        executorService.shutdown();
    }
}