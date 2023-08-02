package com.master7720.decrypter;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class QuotedPrintableDecrypter {
    private static final int NUM_THREADS = Runtime.getRuntime().availableProcessors();
    private static final int CHUNK_SIZE = 100;

    public static String decrypt(String encryptedText) {
        ExecutorService executorService = Executors.newFixedThreadPool(NUM_THREADS);
        int textLength = encryptedText.length();
        StringBuilder decryptedText = new StringBuilder(textLength);

        try {
            for (int i = 0; i < textLength; i += CHUNK_SIZE) {
                int endIndex = Math.min(i + CHUNK_SIZE, textLength);
                String chunk = encryptedText.substring(i, endIndex);
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
        int i = 0;

        while (i < chunk.length()) {
            char c = chunk.charAt(i);
            if (c == '=' && i + 2 < chunk.length()) {
                char firstDigit = chunk.charAt(i + 1);
                char secondDigit = chunk.charAt(i + 2);
                int asciiValue = hexToDecimal(firstDigit) * 16 + hexToDecimal(secondDigit);
                decryptedChunk.append((char) asciiValue);
                i += 3;
            } else {
                decryptedChunk.append(c);
                i++;
            }
        }

        return decryptedChunk.toString();
    }

    private static int hexToDecimal(char digit) {
        if (digit >= '0' && digit <= '9') {
            return digit - '0';
        } else if (digit >= 'A' && digit <= 'F') {
            return digit - 'A' + 10;
        } else if (digit >= 'a' && digit <= 'f') {
            return digit - 'a' + 10;
        }
        throw new IllegalArgumentException("Invalid hexadecimal digit: " + digit);
    }
}