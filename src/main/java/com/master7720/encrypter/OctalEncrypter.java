package com.master7720.encrypter;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.ArrayList;
import java.util.List;

public class OctalEncrypter {
    private static final int NUM_THREADS = Runtime.getRuntime().availableProcessors();
    private static final ExecutorService executorService = Executors.newFixedThreadPool(NUM_THREADS);

    public static String encrypt(String text) {
        List<Future<String>> futures = new ArrayList<>();

        try {
            for (char c : text.toCharArray()) {
                futures.add(executorService.submit(() -> Integer.toOctalString((int) c)));
            }

            StringBuilder octalText = new StringBuilder();
            for (Future<String> future : futures) {
                octalText.append(getFutureResult(future));
            }

            return octalText.toString();
        } catch (Exception e) {
            throw new RuntimeException("Encryption failed.", e);
        }
    }

    private static String getFutureResult(Future<String> future) {
        try {
            return future.get();
        } catch (Exception e) {
            throw new RuntimeException("Encryption task execution failed.", e);
        }
    }

    public static void shutdown() {
        executorService.shutdown();
    }
}