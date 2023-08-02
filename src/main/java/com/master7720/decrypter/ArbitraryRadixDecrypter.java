package com.master7720.decrypter;

import java.util.StringJoiner;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static com.master7720.decrypter.OctalDecrypter.NUM_THREADS;

public class ArbitraryRadixDecrypter {
    private static int radix = 0;
    private static ExecutorService executorService = null;

    public ArbitraryRadixDecrypter(int radix) {
        this.radix = radix;
        this.executorService = Executors.newFixedThreadPool(Math.min(NUM_THREADS, Runtime.getRuntime().availableProcessors()));
    }

    public static String decrypt(String encryptedText) {
        int textLength = encryptedText.length();

        StringJoiner decryptedTextJoiner = new StringJoiner("");
        for (int i = 0; i < textLength; i++) {
            final char encryptedChar = encryptedText.charAt(i);
            CompletableFuture.runAsync(() -> {
                int decimalValue = Integer.parseInt(Character.toString(encryptedChar), radix);
                decryptedTextJoiner.add(String.valueOf((char) decimalValue));
            }, executorService);
        }

        try {
            executorService.shutdown();
            executorService.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        return decryptedTextJoiner.toString();
    }
}