package com.master7720.encrypter;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class BinaryEncrypter {
    private static final int NUM_THREADS = Runtime.getRuntime().availableProcessors();

    public static String encrypt(String text) {
        ExecutorService executorService = Executors.newFixedThreadPool(NUM_THREADS);

        try {
            StringBuilder binaryText = new StringBuilder();
            for (char c : text.toCharArray()) {
                executorService.execute(() -> {
                    String binaryString = Integer.toBinaryString(c);
                    while (binaryString.length() < 8) {
                        binaryString = "0" + binaryString;
                    }
                    synchronized (binaryText) {
                        binaryText.append(binaryString);
                    }
                });
            }

            executorService.shutdown();
            executorService.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
            return binaryText.toString();
        } catch (Exception e) {
            throw new RuntimeException("Encryption failed.", e);
        }
    }
}