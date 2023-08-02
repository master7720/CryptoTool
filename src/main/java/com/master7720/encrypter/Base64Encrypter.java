package com.master7720.encrypter;

import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class Base64Encrypter {
    static final int NUM_THREADS = Runtime.getRuntime().availableProcessors();
    private static final int CHUNK_SIZE = 100;

    public static String encrypt(String text) {
        if (text == null || text.isEmpty()) {
            throw new IllegalArgumentException("Input text cannot be null or empty.");
        }

        ExecutorService executorService = Executors.newFixedThreadPool(NUM_THREADS);

        try {
            List<CompletableFuture<byte[]>> futures = new ArrayList<>();

            for (int i = 0; i < text.length(); i += CHUNK_SIZE) {
                int endIndex = Math.min(i + CHUNK_SIZE, text.length());
                String chunk = text.substring(i, endIndex);
                CompletableFuture<byte[]> future = CompletableFuture.supplyAsync(() -> encodeChunk(chunk), executorService);
                futures.add(future);
            }

            // Combine the results and concatenate the encoded chunks
            String encodedText = futures.stream()
                    .map(CompletableFuture::join)
                    .collect(Collectors.collectingAndThen(Collectors.toList(), list -> {
                        byte[] result = new byte[list.stream().mapToInt(arr -> arr.length).sum()];
                        int offset = 0;
                        for (byte[] arr : list) {
                            System.arraycopy(arr, 0, result, offset, arr.length);
                            offset += arr.length;
                        }
                        return new String(result);
                    }));

            return encodedText;
        } finally {
            executorService.shutdown();
            try {
                executorService.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    private static byte[] encodeChunk(String chunk) {
        return Base64.getEncoder().encode(chunk.getBytes());
    }
}