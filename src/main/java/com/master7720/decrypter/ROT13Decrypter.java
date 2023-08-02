package com.master7720.decrypter;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ROT13Decrypter {
    private static final int NUM_THREADS = Runtime.getRuntime().availableProcessors();
    private static final int CHUNK_SIZE = 100;

    public static String decrypt(String text) {
        ExecutorService executorService = Executors.newFixedThreadPool(NUM_THREADS);
        int textLength = text.length();
        StringBuilder decryptedText = new StringBuilder(textLength);

        try {
            for (int i = 0; i < textLength; i += CHUNK_SIZE) {
                int endIndex = Math.min(i + CHUNK_SIZE, textLength);
                String chunk = text.substring(i, endIndex);
                executorService.execute(() -> {
                    String decryptedChunk = decryptChunk(chunk);
                    synchronized (decryptedText) {
                        decryptedText.append(decryptedChunk);
                    }
                });
            }
        } finally {
            executorService.shutdown();
        }

        // Wait for all threads to complete
        while (!executorService.isTerminated()) {
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        return decryptedText.toString();
    }

    private static String decryptChunk(String chunk) {
        StringBuilder decryptedChunk = new StringBuilder(chunk.length());

        for (char ch : chunk.toCharArray()) {
            if (Character.isLetter(ch)) {
                char base = Character.isLowerCase(ch) ? 'a' : 'A';
                int shift = 13;
                ch = (char) (base + (ch - base - shift + 26) % 26);
            }
            decryptedChunk.append(ch);
        }

        return decryptedChunk.toString();
    }
}