package com.master7720.encrypter;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class QuotedPrintableEncrypter {
    private static final int NUM_THREADS = Runtime.getRuntime().availableProcessors();
    private static final int CHUNK_SIZE = 100;

    public static String encrypt(String text) {
        if (text == null || text.isEmpty()) {
            throw new IllegalArgumentException("Input text cannot be null or empty.");
        }

        ExecutorService executorService = Executors.newFixedThreadPool(NUM_THREADS);

        try {
            int textLength = text.length();
            StringBuilder encryptedText = new StringBuilder(textLength);

            for (int i = 0; i < textLength; i += CHUNK_SIZE) {
                int endIndex = Math.min(i + CHUNK_SIZE, textLength);
                String chunk = processChunk(text, i, endIndex, executorService);
                encryptedText.append(chunk);
            }

            return encryptedText.toString();
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            executorService.shutdown();
        }
    }

    private static String processChunk(String text, int startIndex, int endIndex, ExecutorService executorService) {
        StringBuilder encryptedChunk = new StringBuilder(endIndex - startIndex);

        for (int i = startIndex; i < endIndex; i++) {
            char c = text.charAt(i);
            int asciiValue = (int) c;

            if ((asciiValue < 33 || asciiValue > 126 || c == '=') && !Character.isWhitespace(c)) {
                encryptedChunk.append('=');
                encryptedChunk.append(String.format("%02X", asciiValue));
            } else {
                encryptedChunk.append(c);
            }
        }

        return encryptedChunk.toString();
    }
}