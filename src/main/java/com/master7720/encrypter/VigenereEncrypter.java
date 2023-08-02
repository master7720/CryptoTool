package com.master7720.encrypter;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.ArrayList;
import java.util.List;

public class VigenereEncrypter {
    private static final int NUM_THREADS = Runtime.getRuntime().availableProcessors();
    private static final int CHUNK_SIZE = 100;

    public static String encrypt(String text, String key) {
        if (text == null || text.isEmpty() || key == null || key.isEmpty()) {
            throw new IllegalArgumentException("Input text and key cannot be null or empty.");
        }

        ExecutorService executorService = Executors.newFixedThreadPool(NUM_THREADS);
        List<Future<String>> futures = new ArrayList<>();

        try {
            int textLength = text.length();
            StringBuilder encryptedText = new StringBuilder(textLength);

            for (int i = 0; i < textLength; i += CHUNK_SIZE) {
                int endIndex = Math.min(i + CHUNK_SIZE, textLength);
                String chunk = text.substring(i, endIndex);
                String chunkKey = generateChunkKey(key, i, endIndex);
                Future<String> future = executorService.submit(() -> encryptChunk(chunk, chunkKey));
                futures.add(future);
            }

            for (Future<String> future : futures) {
                encryptedText.append(future.get());
            }

            return encryptedText.toString();
        } catch (Exception e) {
            throw new RuntimeException("Encryption failed.", e);
        } finally {
            executorService.shutdown();
        }
    }

    private static String generateChunkKey(String key, int startIndex, int endIndex) {
        int keyLength = key.length();
        StringBuilder chunkKey = new StringBuilder(endIndex - startIndex);

        for (int i = startIndex; i < endIndex; i++) {
            char keyChar = key.charAt(i % keyLength);
            chunkKey.append(keyChar);
        }

        return chunkKey.toString();
    }

    private static String encryptChunk(String chunk, String chunkKey) {
        int chunkLength = chunk.length();
        StringBuilder encryptedChunk = new StringBuilder(chunkLength);

        for (int i = 0; i < chunkLength; i++) {
            char c = chunk.charAt(i);
            char keyChar = chunkKey.charAt(i);
            encryptedChunk.append(encryptChar(c, keyChar));
        }

        return encryptedChunk.toString();
    }

    private static char encryptChar(char c, char keyChar) {
        if (Character.isLetter(c)) {
            char base = Character.isLowerCase(c) ? 'a' : 'A';
            int shift = keyChar - 'a';
            c = (char) (base + (c - base + shift) % 26);
        }
        return c;
    }
}