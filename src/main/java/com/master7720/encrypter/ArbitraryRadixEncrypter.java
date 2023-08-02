package com.master7720.encrypter;

import java.util.StringJoiner;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import java.util.concurrent.*;
import java.util.StringJoiner;
import java.util.concurrent.atomic.AtomicInteger;

import static com.master7720.encrypter.Base64Encrypter.NUM_THREADS;

public class ArbitraryRadixEncrypter {
    private static int radix = 0;
    private static ExecutorService executorService = null;

    public ArbitraryRadixEncrypter(int radix) {
        this.radix = radix;
        this.executorService = Executors.newFixedThreadPool(Math.min(NUM_THREADS, Runtime.getRuntime().availableProcessors()));
    }

    public static String encrypt(String text) {
        AtomicInteger count = new AtomicInteger(text.length());
        StringJoiner encryptedTextJoiner = new StringJoiner("");

        try {
            for (char c : text.toCharArray()) {
                CompletableFuture<Void> future = CompletableFuture.runAsync(() -> {
                    String encryptedChunk = Integer.toString(c, radix);
                    encryptedTextJoiner.add(encryptedChunk);
                    if (count.decrementAndGet() == 0) {
                        executorService.shutdown();
                    }
                }, executorService);

                future.join(); // Wait for the task to complete and handle any exceptions
            }

            executorService.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } catch (CompletionException e) {
            // Handle any exceptions that occurred during encryption
            executorService.shutdownNow();
            throw new RuntimeException("Encryption failed.", e.getCause());
        }

        return encryptedTextJoiner.toString();
    }
}