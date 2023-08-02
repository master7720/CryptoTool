package com.master7720.encrypter;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.ArrayList;
import java.util.List;

public class HexaEncrypter {
    private static final int NUM_THREADS = Runtime.getRuntime().availableProcessors();

    public static String encrypt(String text) {
        ExecutorService executorService = Executors.newFixedThreadPool(NUM_THREADS);
        List<Future<String>> futures = new ArrayList<>();

        try {
            for (char c : text.toCharArray()) {
                futures.add(executorService.submit(() -> String.format("%02X", (int) c)));
            }

            StringBuilder hexaText = new StringBuilder();
            for (Future<String> future : futures) {
                hexaText.append(getFutureResult(future));
            }

            return hexaText.toString();
        } catch (Exception e) {
            throw new RuntimeException("Encryption failed.", e);
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