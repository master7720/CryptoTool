package com.master7720.decrypter;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class VigenereDecrypter {
    public static String decrypt(String encryptedText, String key) {
        int textLength = encryptedText.length();
        StringBuilder decryptedText = new StringBuilder(textLength);
        int keyLength = key.length();

        ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

        try {
            Future<String>[] futures = new Future[textLength / 100 + 1];
            for (int i = 0; i < textLength; i += 100) {
                int endIndex = Math.min(i + 100, textLength);
                String chunk = encryptedText.substring(i, endIndex);
                String chunkKey = key.substring(i % keyLength, Math.min((i % keyLength) + 100, keyLength));
                final int index = i;
                futures[i / 100] = executorService.submit(() -> decryptChunk(chunk, chunkKey, index));
            }

            for (Future<String> future : futures) {
                decryptedText.append(future.get());
            }
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        } finally {
            executorService.shutdown();
        }

        return decryptedText.toString();
    }

    private static String decryptChunk(String chunk, String key, int startIndex) {
        StringBuilder decryptedChunk = new StringBuilder(chunk.length());
        int keyLength = key.length();

        for (int i = 0; i < chunk.length(); i++) {
            char c = chunk.charAt(i);
            char keyChar = key.charAt((startIndex + i) % keyLength);
            decryptedChunk.append(decryptChar(c, keyChar));
        }

        return decryptedChunk.toString();
    }

    private static char decryptChar(char c, char keyChar) {
        if (Character.isLetter(c)) {
            char base = Character.isLowerCase(c) ? 'a' : 'A';
            int shift = keyChar - 'a';
            c = (char) (base + (c - base - shift + 26) % 26);
        }
        return c;
    }
}