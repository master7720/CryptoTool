package com.master7720.decrypter;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
import java.util.stream.Collectors;

public class HexaDecrypter {
    private static final int NUM_THREADS = Runtime.getRuntime().availableProcessors();

    public static String decrypt(String hexaText) {
        ExecutorService executorService = Executors.newFixedThreadPool(NUM_THREADS);

        try {
            List<Callable<Character>> tasks = new ArrayList<>();

            for (int i = 0; i < hexaText.length(); i += 2) {
                final String hexaChunk = hexaText.substring(i, Math.min(i + 2, hexaText.length()));
                tasks.add(() -> (char) Integer.parseInt(hexaChunk, 16));
            }

            List<Future<Character>> futures = executorService.invokeAll(tasks);
            return futures.stream()
                    .map(HexaDecrypter::getFutureResult)
                    .map(Object::toString)
                    .collect(Collectors.joining());
        } catch (Exception e) {
            throw new RuntimeException("Decryption failed.", e);
        } finally {
            executorService.shutdown();
        }
    }

    private static Character getFutureResult(Future<Character> future) {
        try {
            return future.get();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static String encrypt(String text) {
        return text;
    }
}